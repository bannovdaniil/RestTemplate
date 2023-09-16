package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.UserDtoMapperImpl;

import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet(urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public UserServlet() {
        this.userService = new UserServiceImpl();
        this.userDtoMapper = new UserDtoMapperImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        var out = resp.getOutputStream();
        out.print("Get Method \n");
        out.println(LocalDateTime.now().toString());
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        var out = resp.getOutputStream();
        out.print("Post Method");
        out.close();
    }
}
