package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.RoleService;
import org.example.service.impl.RoleServiceImpl;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;
import org.example.servlet.dto.RoleUpdateDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = {"/role/*"})
public class RoleServlet extends HttpServlet {
    private static final String ROLE_ID_PARAM = "roleId";
    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    public RoleServlet() {
        this.roleService = RoleServiceImpl.getInstance();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);


        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            switch (pathPart[1]) {
                case "all":
                    List<RoleOutGoingDto> roleDtoList = roleService.findAll();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    responseAnswer = objectMapper.writeValueAsString(roleDtoList);
                    break;
                case ROLE_ID_PARAM:
                    Long roleId = Long.parseLong(pathPart[2]);
                    RoleOutGoingDto roleDto = roleService.findById(roleId);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    responseAnswer = objectMapper.writeValueAsString(roleDto);
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    responseAnswer = "Illegal argument.";
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");

            if (pathPart[1].equals(ROLE_ID_PARAM)) {
                Long roleId = Long.parseLong(pathPart[2]);
                if (roleService.delete(roleId)) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseAnswer = "Illegal argument.";
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = null;
        Optional<RoleIncomingDto> roleResponse;
        try {
            roleResponse = Optional.ofNullable(objectMapper.readValue(json, RoleIncomingDto.class));
            RoleIncomingDto role = roleResponse.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(roleService.save(role));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect role Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = "";
        Optional<RoleUpdateDto> roleResponse;
        try {
            roleResponse = Optional.ofNullable(objectMapper.readValue(json, RoleUpdateDto.class));
            RoleUpdateDto roleUpdateDto = roleResponse.orElseThrow(IllegalArgumentException::new);
            roleService.update(roleUpdateDto);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect role Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    private static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
