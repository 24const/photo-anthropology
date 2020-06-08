package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.structure_entities.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GroupToolsServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error_page.jsp";
    private static Logger logger = LoggerFactory.getLogger(GroupToolsServlet.class.getName());

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
            logger.error(e.getMessage());
            resp.sendRedirect(ERROR_PAGE);
        }
    }
}
