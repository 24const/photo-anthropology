$(document).ready(function () {

    paApi = {};

    paApi.getAllFiles = function (onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/all",
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {
                alert("Ошибка при попытке получения файлов.")
            }
        });
    };

    paApi.getAllGroups = function (onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/all",
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {
                alert("Ошибка при попытке получения групп.")
            }
        });
    };

    paApi.getFileById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/getFile/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {
                alert("Невозможно получить информацию о файле с таким id.")
            }
        });
    };

    paApi.getGroupById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/getGroup/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {
                alert("Невозможно получить информацию о группе с таким id.")
            }
        });
    };

    paApi.deleteFileById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/files/delete/id/" + id,
            success: function () {
                onFetchFunc("Файл успешно удален.")
            },
            error: function () {
                onFetchFunc("Невозможно удалить файл.")
            }
        });
    };

    paApi.deleteGroupById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/groups/delete/id/" + id,
            success: function () {
                onFetchFunc("Группа успешно удалена.")
            },
            error: function () {
                onFetchFunc("Невозможно удалить группу.")
            }
        });
    };

    paApi.deleteTagById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/tags/delete/id/" + id,
            success: function () {
                onFetchFunc("Тэг успешно удален.")
            },
            error: function () {
                onFetchFunc("Невозможно удалить тэг.")
            }
        });
    };

    paApi.deleteImageById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/images/delete/id/" + id,
            success: function () {
                onFetchFunc("Изображение успешно удалено.")
            },
            error: function () {
                onFetchFunc("Невозможно удалить изображение.")
            }
        });
    };

    paApi.saveNewGroup = function (data, onFetchFunc) {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/photo-anthropology/groups/save',
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (){onFetchFunc("Группа успешно сохранена.")},
            error: function () {onFetchFunc("Не удалось сохранить группу.")}
        })
    };

    // paApi.saveNewFile = function (data, onFetchFunc) {
    //     $.ajax({
    //         type: "POST",
    //         url: 'http://localhost:8080/photo-anthropology/files/save',
    //         contentType: "application/json",
    //         data: JSON.stringify(data),
    //         dataType: 'json',
    //         success: function (){onFetchFunc("Файл успешно сохранена.")},
    //         error: function () {onFetchFunc("Не удалось сохранить файл.")}
    //     })
    // };

});