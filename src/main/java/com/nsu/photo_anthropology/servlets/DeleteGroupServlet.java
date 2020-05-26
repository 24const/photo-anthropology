package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.dao.GroupDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteGroupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        int id;
        try {
            id = Integer.parseInt(req.getParameter("groupId"));
        } catch (NumberFormatException e) {
            throw new PhotoAnthropologyRuntimeException("DeleteGroupServlet: ошибка при получении информации об удаляемой группе.");
        }
        GroupDao groupDao = new GroupDao();
        try {
            groupDao.deleteById(id);
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("DeleteGroupServlet: ошибка при попытке удаления группы.");
        }
        String nextJSP = "GroupsTools";
        try {
            resp.sendRedirect(nextJSP);
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("DeleteGroupServlet: ошибка при попытке перехода на страницу GroupsTools.");
        }
    }
}
