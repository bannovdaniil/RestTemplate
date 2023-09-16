package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(urlPatterns = {"/user"}, name = "Rest")
public class UserServlet extends HttpServlet {
//    private UserService service;
    //   private SimpleDtomapper dtomapper;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("Hello there from Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //    SimpleEntity simpleEntity = dtomapper.map(new IncomingDto());
        //      SimpleEntity saved = service.save(simpleEntity);
        //      OutGoingDto map = dtomapper.map(saved);
        // return our DTO, not necessary
    }
}
