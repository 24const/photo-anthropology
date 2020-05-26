package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.structure_entities.Group;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GroupToolsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws PhotoAnthropologyRuntimeException {

        GroupDao groupDao = new GroupDao();
        List<Group> allGroups = groupDao.getAll();
        req.setAttribute("allGroups", allGroups);

        String nextJSP = "group_tools.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        try {
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("GroupToolsServlet: ошибка при переходе на страницу group_tools.jsp.");
        }
    }
}
