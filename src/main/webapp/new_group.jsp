<%--
  Created by IntelliJ IDEA.
  User: Эльдорадо
  Date: 22.05.2020
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>Adding Group</title>
    <link rel="StyleSheet" href="css/index.css" type="text/css" media="screen"/>
</head>

<%
    String action;
    if (request.getAttribute("groupId") == null) {
        action = "AddGroup";
        System.out.println(action);
    } else {
        int groupId = (int) request.getAttribute("groupId");
        action = "AddGroup?groupId=" + groupId;
    }
%>
<body>
<div class="new_group_form">
    <form action="<%=action%>" method="post" name="form">
        <label>Название группы:<br><br>
            <input type="text" name="groupName" placeholder="Введите название группы" value="${prevGroupName}"/>
        </label>
        <p class="message">${groupNameMes}</p>
        <label>Вопрос группы:<br><br>
            <input type="text" name="groupQuestion" placeholder="Введите вопрос группы" value="${prevGroupQuestion}"/>
        </label>
        <p class="message">${groupQuestionMes}</p>
        <label>Теги:<br><br>
            <input type="textarea" name="groupTags" placeholder="Укажите теги через запятую" value="${prevGroupTags}">
        </label>
        <p class="message">${groupTagsMes}</p>
        <input type="submit" value="Сохранить" class="btn-sign">
    </form>
</div>
</body>
</html>
