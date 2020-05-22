package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.GroupDao;

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
            req.setAttribute("deletingMes","Нельзя удалить группу, содержащую теги!");
        }

        String nextJSP = "GroupsTools";
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);

    }
}
