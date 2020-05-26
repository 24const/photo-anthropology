package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
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

public class ChangeGroupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        int id;
        try {
            id = Integer.parseInt(req.getParameter("groupId"));
        } catch (NumberFormatException e) {
            throw new PhotoAnthropologyRuntimeException("ChangeGroupServlet: ошибка при получении информации об удаляемой группе.");
        }

        GroupDao groupDao = new GroupDao();
        Group group = groupDao.getById(id);
        TagDao tagDao = new TagDao();
        List<Tag> tagsInGroup = tagDao.getAllTagsInGroup(group);

        StringBuilder stringBuilder = new StringBuilder();
        for (Tag tag : tagsInGroup) {
            stringBuilder.append(tag.getTagName());
            stringBuilder.append(", ");
        }
        String tags = stringBuilder.toString();
        if (tags.lastIndexOf(',') != -1) {
            tags = tags.substring(0, tags.lastIndexOf(','));
        }

        req.setAttribute("prevGroupName", group.getGroupName());
        req.setAttribute("prevGroupQuestion", group.getGroupQuestion());
        req.setAttribute("prevGroupTags", tags);
        req.setAttribute("groupId", group.getId());

        String nextJSP = "new_group.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        try {
            dispatcher.forward(req, resp);
        } catch (IOException | ServletException e) {
            throw new PhotoAnthropologyRuntimeException("ChangeGroupServlet: ошибка при попытке перехода на страницу new_group.jsp.");
        }
    }
}
