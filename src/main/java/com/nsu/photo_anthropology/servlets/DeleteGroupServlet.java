package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class DeleteGroupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("groupId"));
        } catch (NumberFormatException e) {
            Logger logger = Logger.getLogger(DeleteGroupServlet.class.getName());
            logger.info(e.getMessage());
            //TODO: имеет ли дальнейший смысл выполнения программы если id не задан?
            //TODO: показать страницу с ошибкой ErrorServlet
        }
        GroupDao groupDao = new GroupDao();
        try {
            groupDao.deleteById(id);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(DeleteGroupServlet.class.getName());
            logger.info(e.getMessage());
            //TODO: показать страницу с ошибкой ErrorServlet
        }
        String nextJSP = "GroupsTools";
        try {
            resp.sendRedirect(nextJSP);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(DeleteGroupServlet.class.getName());
            logger.info(e.getMessage());
            //TODO: показать страницу с ошибкой ErrorServlet
        }
    }
}
