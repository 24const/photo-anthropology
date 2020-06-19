<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>PhotoAnthropology</title>
</head>
<body>
<button><a href="files.html"><p>Настройки файлов</p></a></button>
<button><a href="groups.html"><p>Настройки групп</p></a></button>
<p id = "files"></p>
</body>
</html>

<script>
    $.getJSON( "/files/all", function(data) {
        console.log(data.id);
        $('#files').text(data.id);
    });
</script>