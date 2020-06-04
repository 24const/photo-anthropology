package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class DeleteGroupServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error_page.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("groupId"));
            GroupDao groupDao = new GroupDao();
            String nextJSP = "GroupsTools";
            groupDao.deleteGroupById(id);
            resp.sendRedirect(nextJSP);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(DeleteGroupServlet.class.getName());
            logger.info(e.getMessage());
            resp.sendRedirect(ERROR_PAGE);
        }
    }
}
