package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.repository.exception.NotFoundException;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.UserDtoMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {
    private static final String USERID_PARAM = "userId";
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final ObjectMapper objectMapper;

    public UserServlet() {
        this.userService = UserServiceImpl.getInstance();
        this.objectMapper = new ObjectMapper();
        this.userDtoMapper = new UserDtoMapperImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);

        PrintWriter printWriter = resp.getWriter();

        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            switch (pathPart[1]) {
                case "all":
                    List<User> userList = userService.findAll();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    responseAnswer = objectMapper.writeValueAsString(userList);
                    break;
                case USERID_PARAM:
                    Long userId = Long.parseLong(pathPart[2]);
                    User user = userService.findById(userId);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    responseAnswer = objectMapper.writeValueAsString(user);
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
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);

        PrintWriter printWriter = resp.getWriter();

        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");

            if (pathPart[1].equals(USERID_PARAM)) {
                Long userId = Long.parseLong(pathPart[2]);
                if (userService.delete(userId)) {
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
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    private static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);

        PrintWriter printWriter = resp.getWriter();
        String responseAnswer;
        String userIdString = req.getParameter(USERID_PARAM);

        try {
            Long userId = Long.parseLong(userIdString);
            User user = userService.findById(userId);
            resp.setStatus(HttpServletResponse.SC_OK);
            responseAnswer = objectMapper.writeValueAsString(user);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseAnswer = e.getMessage();
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect userId.";
        }
        printWriter.write(responseAnswer);
        printWriter.flush();
    }
}
