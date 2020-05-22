package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.Dao;
import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NewGroupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        InfoValidation infoValidation = new InfoValidation(req);
        infoValidation.isValidGroup();

        String groupName = infoValidation.getGroupName();
        String groupQuestion = infoValidation.getGroupQuestion();
        String groupTags = infoValidation.getTags();

        if(infoValidation.getExceptionCounter() == 0) {

            GroupDao groupDao = new GroupDao();
            Group newGroup = new Group(groupName, groupQuestion);
            groupDao.save(newGroup);

            if(!groupTags.equalsIgnoreCase("")) {
                String[] tagsInGroup = groupTags.split(",");
                Dao tagDao = new TagDao();
                for (String tag : tagsInGroup) {
                    tag = tag.replace(" ", "");
                    tagDao.save(new Tag(tag, newGroup.getGroupName()));
                }
            }
            String nextJSP = "GroupsTools";
            resp.sendRedirect(nextJSP);
        } else {
            String nextJSP = "new_group.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
            dispatcher.forward(req, resp);
        }
    }
}
