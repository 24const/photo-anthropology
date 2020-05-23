package com.nsu.photo_anthropology.servlets;

import javax.servlet.http.HttpServletRequest;

public class InfoValidation {
    private String groupName = null;
    private String groupQuestion = null;
    private String groupTags = null;
    private int exceptionCounter = 0;
    private HttpServletRequest request;

    public InfoValidation(HttpServletRequest request) {
        this.request = request;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupQuestion() {
        return groupQuestion;
    }

    public String getTags() {
        return groupTags;
    }

    public int getExceptionCounter(){
        return exceptionCounter;
    }

    public void isValidGroup() {

        if (!request.getParameter("groupName").equalsIgnoreCase("")) {
            groupName = request.getParameter("groupName");
            request.setAttribute("groupName", groupName);
        } else {
            request.setAttribute("groupNameMes", "Укажите название группы.");
            this.exceptionCounter++;
        }
        if (!request.getParameter("groupQuestion").equalsIgnoreCase("")) {
            groupQuestion = request.getParameter("groupQuestion");
            request.setAttribute("groupQuestion", groupQuestion);
        } else {
            request.setAttribute("groupQuestionMes", "Укажите вопрос, на который отвечают теги данной группы.");
            this.exceptionCounter++;
        }
        if (!request.getParameter("groupTags").equalsIgnoreCase("")) {
            groupTags = request.getParameter("groupTags");
            request.setAttribute("groupTags", groupTags);
        } else {
            groupTags = "";
        }
    }
}
