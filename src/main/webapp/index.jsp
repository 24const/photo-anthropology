<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<button><a href="GroupsTools">Настройки тегов</a></button>
<h2>Hello World!</h2>
<h3>File Upload:</h3>
Select a file to upload: <br/>
<form action = "UploadFile" method = "post"
      enctype = "multipart/form-data">
    <input type = "file" name = "sourceFilePath"/>
    <br/>
    <input type = "submit" value = "Upload File" />
</form>
</body>
</html>
