package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.structure_entities.Group;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class GroupToolsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        GroupDao groupDao = new GroupDao();
        List<Group> allGroups = groupDao.getAll();
        req.setAttribute("allGroups", allGroups);

        String nextJSP = "group_tools.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        try {
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(GroupToolsServlet.class.getName());
            logger.info(e.getMessage());
            resp.sendRedirect("error_page.jsp");
        }
    }
}
