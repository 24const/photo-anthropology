<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head><title>Hello World!</title></head>
<body>
<button><a href="GroupsTools">Настройки тегов</a></button>
<h2>Hello World!</h2>
<h3>File Upload:</h3>
Select a file to upload: <br/>
<form action="UploadFile" method="post"
      enctype="multipart/form-data">
    <input type="file" name="sourceFilePath"/>
    <br/>
    <input type="submit" value="Upload File"/>
</form>
<a href="C:\Users\Ksenia\Desktop\photo-anthropology\src\main\webapp\files.html">index</a>
<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Question</th>
        <th>Tags</th>
    </tr>
    <c:forEach items="${listGroup}" var="group">
        <tr>
            <td>${group.id}</td>
            <td>${group.name}</td>
            <td>${group.email}</td>
            <td>${group.address}</td>
        </tr>
    </c:forEach>
</table>
</body>
