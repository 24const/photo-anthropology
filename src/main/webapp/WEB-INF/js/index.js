$(document).ready(function () {

    $("#get_all_files").click(function () {
        paApi.getAllFiles(function (fileList) {
            for (let file in fileList) {
                $("#content").empty().append("" +
                    "<table><tr><th>id</th><th>File name</th><th>Date created</th></tr>" +
                    "<tr><td id='file_id'>" + fileList[file].id + "</td>" +
                    "<td>" + fileList[file].file_name + "</td>" +
                    "<td>" + fileList[file].date_created["dayOfMonth"] + "." + fileList[file].date_created["month"] + "." + fileList[file].date_created["year"] + "</td>" +
                    "<td><button onclick='get_file_by_id("+fileList[file].id+")'>Удалить</button></td>" +
                    "<td><button onclick='get_file_by_id("+fileList[file].id+")'>Инфо</button></td>" +
                    "</tr></table>");
            }
        });
    });

    $("#get_all_groups").click(function () {
        paApi.getAllGroups(function (groupList) {
            for (let group in groupList) {
                $("#content").empty().append("" +
                    "<table><tr><th>id</th><th>Group name</th><th>Group question</th></tr>" +
                    "<tr><td id='group_id'>" + groupList[group].id + "</td>" +
                    "<td>" + groupList[group].group_name + "</td>" +
                    "<td>" + groupList[group].group_question + "</td>" +
                    "<td><button onclick='delete_group_by_id("+groupList[group].id+")'>Удалить</button></td>" +
                    "<td><button onclick='get_group_by_id("+groupList[group].id+")'>Инфо</button></td>" +
                    "</tr></table>");
            }
        });
    });
});

function get_file_by_id(id){
    paApi.getFileById(id, function (file) {
        $("#content").empty().append("" +
            "<table><tr><th>id</th><th>File name</th><th>Date created</th></tr>" +
            "<tr><td>" + file.id + "</td>" +
            "<td>" + file.file_name + "</td>" +
            "<td>" + file.date_created["dayOfMonth"]+"."+file.date_created["month"]+"."+file.date_created["year"] + "</td>" +
            "<td><button onclick='delete_file_by_id("+file.id+")'>Удалить</button></td>" +
            "</tr></table>");
    })
}

function get_group_by_id(id){
    paApi.getGroupById(id, function (group) {
        $("#content").empty().append(""+
            "<table><tr><th>id</th><th>Group name</th><th>Group question</th></tr>" +
            "<tr><td>" + group.id + "</td>" +
            "<td>" + group.group_name + "</td>" +
            "<td>" + group.group_question + "</td>" +
            "<td><button onclick='delete_group_by_id("+group.id+")'>Удалить</button></td></tr></table>");
    })
}
function delete_file_by_id(id){
    paApi.deleteFileById(id, function (file) {
        $("#content").empty().append("Файл удален");
    })
}

function delete_group_by_id(id){
    paApi.deleteGroupById(id, function (group) {
        $("#content").empty().append("Группа удалена");
    })
}
