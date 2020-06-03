package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.dao.InitDao;

import javax.servlet.http.HttpServlet;

public class StartServlet extends HttpServlet {

    @Override
    public void init() {
        InitDao.createDbSchema();
    }
}
