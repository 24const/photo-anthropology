<%--
  Created by IntelliJ IDEA.
  User: Эльдорадо
  Date: 22.05.2020
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding Group</title>
    <link rel="StyleSheet" href="css/index.css" type="text/css" media="screen"/>
</head>
<body>
<div class="new_group_form">
    <form action="AddGroup" method="post" name="form">
        <label>Название группы:<br><br>
            <input type="text" name="groupName" placeholder="" value="${groupName}"/>
        </label>
        <p class="message">${groupNameMes}</p>
        <label>Вопрос группы:<br><br>
            <input type="text" name="groupQuestion" placeholder="" value="${groupQuestion}"/>
        </label>
        <p class="message">${groupQuestionMes}</p>
        <label>Теги:<br><br>
            <textarea name="groupTags" placeholder="Укажите теги через запятую" value="${groupTags}"></textarea>
        </label>
        <p class="message">${groupTagsMes}</p>
        <input type="submit" value="Добавить группу" class="btn-sign">
    </form>
</div>
</body>
</html>
