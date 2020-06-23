$(document).ready(function () {
    $("#get_all_files").click(function () {
        paApi.getAllFiles(function (fileList) {
            console.log(fileList);
            if (fileList.length !== 0) {
                files_table(fileList);
            } else {
                $("#content").empty().append("<p>На данный момент не сохранено ни одного файла.</p>")
            }
        });
    });

    $("#get_all_groups").click(function () {
        paApi.getAllGroups(function (groupList) {
            console.log(groupList);
            if (groupList.length !== 0) {
                groups_table(groupList);
            } else {
                $("#content").empty().append("<p>На данный момент не сохранено ни одной группы.</p>")
            }
        });
    });

    $("#btn_save_new_group").click(function () {
        let new_group = {};
        new_group["group_name"] = $("[name='group_name']").val();
        new_group["group_question"] = $("[name='group_question']").val();
        let tags_in_string = $("[name='tags']").val().split(', ');
        let tags = [];
        let i = 0;
        for (let tag in tags_in_string){
            tags[i] = ({tag_name: tags_in_string[tag]});
            i++;
        }
        new_group["tags"] = tags;
        paApi.saveNewGroup(new_group, function (groupList) {});
    });

    $("#btn_save_new_file").click(function () {
        let data = new FormData($("[name='file']")[0]);
        paApi.saveNewFile(data, function (savedFile) {});
    });

    /* Попытка вынести форму в отдельную функцию, чтобы появлялась
       при нажатии на кнопку, но так не отправляются данные
     */
    // $("#new_group").click(function(){
    //     let content = $("[id = 'group_form']");
    //     content.empty().append("    <form method=\"post\" id=\"save_new_group\">\n" +
    //         "        <table>\n" +
    //         "            <tr>\n" +
    //         "                <td>Group name</td>\n" +
    //         "                <td><label>\n" +
    //         "                    <input name=\"group_name\" type=\"text\" />\n" +
    //         "                </label></td>\n" +
    //         "            </tr>\n" +
    //         "            <tr>\n" +
    //         "                <td>Group question</td>\n" +
    //         "                <td><label>\n" +
    //         "                    <input name=\"group_question\" type=\"text\" />\n" +
    //         "                </label></td>\n" +
    //         "            </tr>\n" +
    //         "            <tr>\n" +
    //         "                <td>Email</td>\n" +
    //         "                <td><label>\n" +
    //         "                    <input name=\"tags\" type=\"text\" />\n" +
    //         "                </label></td>\n" +
    //         "            </tr>\n" +
    //         "            <tr>\n" +
    //         "                <td></td>\n" +
    //         "                <td><button id=\"btn_save_new_group\" type = \"submit\">Submit</button></td>\n" +
    //         "            </tr>\n" +
    //         "        </table>\n" +
    //         "    </form>")
    // })
});

function groups_table(groupList){
    let content = $("[id = 'content']");
    content.empty().append("<table><tr><th>id</th><th>Group name</th><th>Group question</th><th>Actions</th><th></th></tr>");
    for (let group in groupList) {
        $("#content").append("" +
            "<tr><td id='group_id'>" + groupList[group].id + "</td>" +
            "<td>" + groupList[group].group_name + "</td>" +
            "<td>" + groupList[group].group_question + "</td>" +
            "<td><button onclick='delete_group_by_id(" + groupList[group].id + ")'>Удалить</button></td>" +
            "<td><button onclick='get_group_by_id(" + groupList[group].id + ")'>Инфо</button></td>" +
            "<td><button onclick='update_group_by_id(" + groupList[group].id + ")'>Изменить</button></td>" +
        "</tr>");
    }
    content.append("</table>");
}

function files_table(fileList) {
    let content = $("[id = 'content']");
    content.empty().append("<table><tr><th>id</th><th>File name</th><th>Date created</th><th>Actions</th><th></th></tr>");
    for (let file in fileList) {
        $("#content").append("" +
            "<tr><td id='file_id'>" + fileList[file].id + "</td>" +
            "<td>" + fileList[file].file_name + "</td>" +
            "<td>" + fileList[file].date_created["dayOfMonth"] + " " + fileList[file].date_created["month"] + " " + fileList[file].date_created["year"] + "</td>" +
            "<td><button onclick='delete_file_by_id(" + fileList[file].id + ")'>Удалить</button></td>" +
            "<td><button onclick='get_file_by_id(" + fileList[file].id + ")'>Инфо</button></td>" +
            "<td><button onclick='update_file_by_id(" + fileList[file].id + ")'>Изменить</button></td>" +
            "</tr>");
    }
    content.append("</table>");
}

function image_table(file) {
    let content = $("[id = 'content']");
    content.append("<table>");
    for(let image in file.images){
        $("#content").append("<tr><td>" + file.images[image].image_path + "</td>" +
            "<td><button onclick='delete_image_by_id(" + file.images[image].id + ", "+ file.id+")'>Удалить</button></td></tr>");
    }
    content.append("</table>");
}

function tag_table(group) {
    let content = $("[id = 'content']");
    content.append("<table>");
    for(let tag in group.tags){
        $("#content").append("<tr><td>" + group.tags[tag].tag_name + "</td>" +
            "<td><button onclick='delete_tag_by_id(" + group.tags[tag].id + ", "+ group.id+")'>Удалить</button></td></tr>");
    }
    content.append("</table>");
}

function get_file_by_id(id) {
    paApi.getFileById(id, function (file) {
        $("#content").empty().append("" +
            "<table><tr><th>id</th><td>" + file.id + "</td></tr>" +
            "<tr><th>File name</th><td>" + file.file_name + "</td></tr>" +
            "<tr><th>Date created</th><td>" + file.date_created["dayOfMonth"] + " " + file.date_created["month"] + " " + file.date_created["year"] + "</td></tr>" +
            "<tr><td><button onclick='delete_file_by_id(" + file.id + ")'>Удалить</button></td></tr></table>"
        );
        if (file.images.length !== 0){
            image_table(file);
        }
    })
}

function get_group_by_id(id) {
    paApi.getGroupById(id, function (group) {
        $("#content").empty().append("" +
            "<table><tr><th>id</th><td>" + group.id + "</td></tr>" +
            "<tr><th>Group name</th><td>" + group.group_name + "</td></tr>" +
            "<tr><th>Group question</th><td>" + group.group_question + "</td></tr>" +
            "<tr><th><button onclick='delete_group_by_id(" + group.id + ")'>Удалить</button></th><td></td></tr></table>"
        );
        if (group.tags.length !== 0){
            tag_table(group)
        }
    })
}

function delete_file_by_id(id) {
    paApi.deleteFileById(id, function (file_answ) {
        paApi.getAllFiles(function (fileList) {
            console.log(fileList);
            if (fileList.length !== 0) {
                files_table(fileList);
            } else {
                $("#content").empty().append("<p>На данный момент не сохранено ни одного файла.</p>")
            }
        });
    });
}

function delete_group_by_id(id) {
    paApi.deleteGroupById(id, function (group_answ) {
        paApi.getAllGroups(function (groupList) {
            console.log(groupList);
            if (groupList.length !== 0) {
                groups_table(groupList);
            } else {
                $("#content").empty().append("<p>На данный момент не сохранено ни одной группы.</p>")
            }
        });
    });
}

function delete_tag_by_id(tag_id, group_id) {
    paApi.deleteTagById(tag_id, function (tag_answ) {
        get_group_by_id(group_id)
    })
}

function delete_image_by_id(image_id, file_id) {
    paApi.deleteImageById(image_id, function (image_answ) {
        get_file_by_id(file_id)
    })
}

