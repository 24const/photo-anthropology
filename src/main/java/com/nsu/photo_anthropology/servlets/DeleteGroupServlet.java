package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteGroupServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error_page.jsp";
    private static Logger logger = Logger.getLogger(DeleteGroupServlet.class.getName());

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
            logger.error(e.getMessage());
            resp.sendRedirect(ERROR_PAGE);
        }
    }
}
