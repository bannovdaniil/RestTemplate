package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.service.DepartmentService;
import org.example.service.impl.DepartmentServiceImpl;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.DepartmentUpdateDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = {"/department/*"})
public class DepartmentServlet extends HttpServlet {
    private final transient DepartmentService departmentService = DepartmentServiceImpl.getInstance();
    private final ObjectMapper objectMapper;

    public DepartmentServlet() {
        this.objectMapper = new ObjectMapper();
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);

        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            if ("all".equals(pathPart[1])) {
                List<DepartmentOutGoingDto> departmentDtoList = departmentService.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(departmentDtoList);
            } else {
                Long departmentId = Long.parseLong(pathPart[1]);
                DepartmentOutGoingDto departmentDto = departmentService.findById(departmentId);
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(departmentDto);
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            Long departmentId = Long.parseLong(pathPart[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            if (req.getPathInfo().contains("/deleteUser/")) {
                if ("deleteUser".equals(pathPart[2])) {
                    Long userId = Long.parseLong(pathPart[3]);
                    departmentService.deleteUserFromDepartment(departmentId, userId);
                }
            } else {
                departmentService.delete(departmentId);
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request. ";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = null;
        Optional<DepartmentIncomingDto> departmentResponse;
        try {
            departmentResponse = Optional.ofNullable(objectMapper.readValue(json, DepartmentIncomingDto.class));
            DepartmentIncomingDto department = departmentResponse.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(departmentService.save(department));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect department Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);

        String responseAnswer = "";
        Optional<DepartmentUpdateDto> departmentResponse;
        try {
            if (req.getPathInfo().contains("/addUser/")) {
                String[] pathPart = req.getPathInfo().split("/");
                if (pathPart.length > 3 && "addUser".equals(pathPart[2])) {
                    Long departmentId = Long.parseLong(pathPart[1]);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    Long userId = Long.parseLong(pathPart[3]);
                    departmentService.addUserToDepartment(departmentId, userId);
                }
            } else {
                departmentResponse = Optional.ofNullable(objectMapper.readValue(json, DepartmentUpdateDto.class));
                DepartmentUpdateDto departmentUpdateDto = departmentResponse.orElseThrow(IllegalArgumentException::new);
                departmentService.update(departmentUpdateDto);
            }
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect department Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }
}
