package com.nsu.photo_anthropology.validation;

import javax.servlet.http.HttpServletRequest;

public class InfoValidation {

    private static final String GROUP_NAME_PARAMETER = "groupName";
    private static final String GROUP_QUESTION_PARAMETER = "groupQuestion";
    private static final String GROUP_TAGS_PARAMETER = "groupTags";

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

    public void setRequestAttributesIfGroupNotValidGroup() {

        if (!request.getParameter(GROUP_NAME_PARAMETER).equalsIgnoreCase("")) {
            groupName = request.getParameter(GROUP_NAME_PARAMETER);
            request.setAttribute(GROUP_NAME_PARAMETER, groupName);
        } else {
            request.setAttribute("groupNameMes", "Укажите название группы.");
            this.exceptionCounter++;
        }
        if (!request.getParameter(GROUP_QUESTION_PARAMETER).equalsIgnoreCase("")) {
            groupQuestion = request.getParameter(GROUP_QUESTION_PARAMETER);
            request.setAttribute(GROUP_QUESTION_PARAMETER, groupQuestion);
        } else {
            request.setAttribute("groupQuestionMes", "Укажите вопрос, на который отвечают теги данной группы.");
            this.exceptionCounter++;
        }
        if (!request.getParameter(GROUP_TAGS_PARAMETER).equalsIgnoreCase("")) {
            groupTags = request.getParameter(GROUP_TAGS_PARAMETER);
            request.setAttribute(GROUP_TAGS_PARAMETER, groupTags);
        } else {
            groupTags = "";
        }
    }
}
