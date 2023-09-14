package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.service.SimpleService;

import java.io.IOException;
import java.util.UUID;


@WebServlet(name = "SimpleServlet", value = "/simple")
public class SimpleServlet extends HttpServlet {
    private SimpleService service;
    //   private SimpleDtomapper dtomapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();// Our Id from request
        User byId = service.findById(uuid);
        //       OutGoingDto outGoingDto = dtomapper.map(byId);
        // return our DTO
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //    SimpleEntity simpleEntity = dtomapper.map(new IncomingDto());
        //      SimpleEntity saved = service.save(simpleEntity);
        //      OutGoingDto map = dtomapper.map(saved);
        // return our DTO, not necessary
    }
}
