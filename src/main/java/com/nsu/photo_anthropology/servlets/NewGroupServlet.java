package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.validation.InfoValidation;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class NewGroupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        InfoValidation infoValidation = new InfoValidation(req);
        infoValidation.setRequestAttributesIfGroupNotValidGroup();

        if (infoValidation.getExceptionCounter() == 0) {

            try {
                this.saveNewGroup(req, infoValidation);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String nextJSP = "GroupsTools";
            try {
                resp.sendRedirect(nextJSP);
            } catch (Exception e) {
                Logger logger = Logger.getLogger(NewGroupServlet.class.getName());
                logger.info(e.getMessage());
                resp.sendRedirect("error_page.jsp");
            }

        } else {
            String nextJSP = "new_group.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
            try {
                dispatcher.forward(req, resp);
            } catch (Exception e) {
                Logger logger = Logger.getLogger(NewGroupServlet.class.getName());
                logger.info(e.getMessage());
                resp.sendRedirect("error_page.jsp");
            }
        }
    }

    private void saveNewGroup(HttpServletRequest req, InfoValidation infoValidation) throws SQLException {

        String groupName = infoValidation.getGroupName();
        String groupQuestion = infoValidation.getGroupQuestion();
        String groupTags = infoValidation.getTags();

        TagDao tagDao = new TagDao();
        GroupDao groupDao = new GroupDao();
        Group group;

        if (req.getParameter("groupId") == null) {
            group = new Group(groupName, groupQuestion, groupTags);
            groupDao.save(group);
        } else {
            int groupId;
            try {
                groupId = Integer.parseInt(req.getParameter("groupId"));
            } catch (Exception e) {
                throw new PhotoAnthropologyRuntimeException("NewGroupServlet: Ошибка приполучении данных о создаваемой группе.");
            }
            group = new Group(groupId, groupName, groupQuestion, groupTags);
            groupDao.update(group);

        }

    }

}
