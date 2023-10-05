package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.util.InitSqlScheme;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/db"})
public class DbServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder sb = new StringBuilder("Create data base:\n\n");
        sb.append("New version\n");

        ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        sb.append("Create schema\n");
        InitSqlScheme.initSqlScheme(connectionManager);
        sb.append("Create data\n");
        InitSqlScheme.initSqlData(connectionManager);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(sb.toString());
        printWriter.flush();
    }


}
