$(document).ready(function() {
    $("#get_all_files").click(function(){
        paApi.getAllFiles(function(fileList) {
            for(let file in fileList) {
                $("#content").empty().append("<tr><th>id</th><th>File name</th><th>Date created</th></tr>" +
                    "<tr><td>" + fileList[file].id + "</td>" +
                    "<td>" + fileList[file].file_name + "</td>" +
                    "<td>" + fileList[file].date_created["dayOfMonth"]+"."+fileList[file].date_created["month"]+"."+fileList[file].date_created["year"] + "</td>" +
                    "<td><button>Удалить</button></td></tr>");
            }
        });
    });
});