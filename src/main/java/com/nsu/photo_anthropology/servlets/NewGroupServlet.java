package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.validation.InfoValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class NewGroupServlet extends HttpServlet {

    private static final String ERROR_PAGE = "error_page.jsp";
    private static Logger logger = LoggerFactory.getLogger(GroupToolsServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        InfoValidation infoValidation = new InfoValidation(req);
        infoValidation.setRequestAttributesIfGroupNotValidGroup();

        if (infoValidation.getExceptionCounter() == 0) {

            try {
                saveNewGroup(req, infoValidation);
            } catch (SQLException e) {
                logger.info(e.getMessage());
                resp.sendRedirect(ERROR_PAGE);
            }

            String nextJSP = "GroupsTools";
            try {
                resp.sendRedirect(nextJSP);
            } catch (Exception e) {
                logger.info(e.getMessage());
                resp.sendRedirect(ERROR_PAGE);
            }

        } else {
            String nextJSP = "new_group.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
            try {
                dispatcher.forward(req, resp);
            } catch (Exception e) {
                logger.info(e.getMessage());
                resp.sendRedirect(ERROR_PAGE);
            }
        }
    }

    private void saveNewGroup(HttpServletRequest req, InfoValidation infoValidation) throws SQLException {

        String groupName = infoValidation.getGroupName();
        String groupQuestion = infoValidation.getGroupQuestion();
        String groupTags = infoValidation.getTags();

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
                throw new PhotoAnthropologyRuntimeException("NewGroupServlet: Ошибка приполучении данных о создаваемой группе.", e);
            }
            group = new Group(groupId, groupName, groupQuestion, groupTags);
            groupDao.update(group);

        }

    }

}
