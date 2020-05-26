package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewGroupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        InfoValidation infoValidation = new InfoValidation(req);
        infoValidation.isValidGroup();

        if (infoValidation.getExceptionCounter() == 0) {

            this.saveNewGroup(req, infoValidation);

            String nextJSP = "GroupsTools";
            try {
                resp.sendRedirect(nextJSP);
            } catch (Exception e) {
                throw new PhotoAnthropologyRuntimeException("NewGroupServlet: Ошибка при переходе на страницу GroupsTools.");
            }

        } else {
            String nextJSP = "new_group.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
            try {
                dispatcher.forward(req, resp);
            } catch (Exception e) {
                throw new PhotoAnthropologyRuntimeException("NewGroupServlet: Ошибка при переходе на страницу new_group.jsp.");
            }
        }
    }

    private void saveNewGroup(HttpServletRequest req, InfoValidation infoValidation){

        String groupName = infoValidation.getGroupName();
        String groupQuestion = infoValidation.getGroupQuestion();
        String groupTags = infoValidation.getTags();

        TagDao tagDao = new TagDao();
        GroupDao groupDao = new GroupDao();
        Group group;

        if (req.getParameter("groupId") == null) {
            group = new Group(groupName, groupQuestion);
            groupDao.save(group);
        } else {
            int groupId;
            try {
                groupId = Integer.parseInt(req.getParameter("groupId"));
            } catch (Exception e) {
                throw new PhotoAnthropologyRuntimeException("NewGroupServlet: Ошибка приполучении данных о создаваемой группе.");
            }
            group = new Group(groupId, groupName, groupQuestion);
            groupDao.update(group);
            tagDao.deleteAllTagsInGroup(groupId);
        }

        if (!groupTags.equalsIgnoreCase("")) {
            String[] tagsInGroup = groupTags.split(",");
            for (String tag : tagsInGroup) {
                if (tag.indexOf(' ') == 0) {
                    tag = tag.replaceFirst(" ", "");
                }
                tagDao.save(new Tag(tag, group.getGroupName()));
            }
        }
    }

}
