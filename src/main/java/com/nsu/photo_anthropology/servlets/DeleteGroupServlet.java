package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteGroupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        int id = Integer.parseInt(req.getParameter("id"));
        GroupDao groupDao = new GroupDao();
        int isRemoved = groupDao.deleteById(id);

        if(isRemoved==0){
            TagDao tagDao = new TagDao();
            //TODO: 1. удаление группы предпологает удаление всех связных тэгов.
            //TODO: Т.е. логика удаления групп с тэгами должна быть в GroupDao в методе delete(id)
            //TODO: 2. Удаление\изменение связных сущностей из БД должно быть выполненно в транзакции https://proselyte.net/tutorials/jdbc/transactions/
            tagDao.deleteAllTagsInGroup(id);
            groupDao.deleteById(id);
        }

        String nextJSP = "GroupsTools";
        resp.sendRedirect(nextJSP);
    }
}
