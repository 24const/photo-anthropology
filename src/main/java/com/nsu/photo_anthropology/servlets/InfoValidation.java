package com.nsu.photo_anthropology.servlets;

import javax.servlet.http.HttpServletRequest;

public class InfoValidation {

    private static final String GROUPNAMEPARAMETER = "groupName";
    private static final String GROUPQUESTIONPARAMETER = "groupQuestion";
    private static final String GROUPTAGSPARAMETER = "groupTags";

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

    public int getExceptionCounter() {
        return exceptionCounter;
    }

    public void isValidGroup() {

        if (!request.getParameter(GROUPNAMEPARAMETER).equalsIgnoreCase("")) {
            groupName = request.getParameter(GROUPNAMEPARAMETER);
            request.setAttribute(GROUPNAMEPARAMETER, groupName);
        } else {
            request.setAttribute("groupNameMes", "Укажите название группы.");
            this.exceptionCounter++;
        }
        if (!request.getParameter(GROUPQUESTIONPARAMETER).equalsIgnoreCase("")) {
            groupQuestion = request.getParameter(GROUPQUESTIONPARAMETER);
            request.setAttribute(GROUPQUESTIONPARAMETER, groupQuestion);
        } else {
            request.setAttribute("groupQuestionMes", "Укажите вопрос, на который отвечают теги данной группы.");
            this.exceptionCounter++;
        }
        if (!request.getParameter(GROUPTAGSPARAMETER).equalsIgnoreCase("")) {
            groupTags = request.getParameter(GROUPTAGSPARAMETER);
            request.setAttribute(GROUPTAGSPARAMETER, groupTags);
        } else {
            groupTags = "";
        }
    }
}
