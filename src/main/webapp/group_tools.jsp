<%@ page import="com.nsu.photo_anthropology.structure_entities.Group" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Ksenia
  Date: 22.05.2020
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Group Tools</title>
    <link rel="StyleSheet" href="css/index.css" type="text/css" media="screen"/>
</head>
<body>
<div class="container">
    <button><a href="new_group.jsp">Добавить новую группу</a></button>
    <table class="group_table">
        <tr>
            <th id="group_name">Группа</th>
            <th id="group_question">Вопрос группы</th>
            <th id="changing">Редактирование</th>
            <th id="delete">Удалить</th>
        </tr>

        <%
        for(Group group:(List<Group>)request.getAttribute("allGroups")) { %>
        <tr>
            <td><%=group.getGroupName()%></td>
            <td><%=group.getGroupQuestion()%></td>
            <td><a href="ChangeGroup?groupId=<%=group.getId()%>">Изменить</a></td>
            <td><a href="DeleteGroup?id=<%=group.getId()%>">Удалить</a></td>
        <%}%>
    </table>
    <p class="message">${deletingMes}</p>
</div>
</body>
</html>
