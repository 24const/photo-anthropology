$(document).ready(function () {

    paApi = {};

    paApi.getAllFiles = function (onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/all",
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {paApi.alert_error("Ошибка при попытке получения файлов.")}
        });
    };

    paApi.getAllGroups = function (onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/all",
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {paApi.alert_error("Ошибка при попытке получения групп.")}
        });
    };

    paApi.getFileById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/getFile/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {paApi.alert_error("Невозможно получить информацию о файле с таким id.")}
        });
    };

    paApi.getGroupById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/getGroup/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {paApi.alert_error("Невозможно получить информацию о группе с таким id.")}
        });
    };

    paApi.getImageById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/images/getImage/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            },
            error: function () {paApi.alert_error("Невозможно получить информацию об изображении с таким id.")}
        });
    };

    paApi.deleteFileById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/files/delete/id/" + id,
            success: function () {
                onFetchFunc()
            },
            error: function () {paApi.alert_error("Невозможно удалить файл.")}
        });
    };

    paApi.deleteGroupById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/groups/delete/id/" + id,
            success: function () {
                onFetchFunc()
            },
            error: function () {paApi.alert_error("Невозможно удалить группу.")}
        });
    };

    paApi.deleteTagById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/tags/delete/id/" + id,
            success: function () {
                onFetchFunc()
            },
            error: function () {paApi.alert_error("Невозможно удалить тэг.")}
        });
    };

    paApi.deleteImageById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/images/delete/id/" + id,
            success: function () {
                onFetchFunc()
            },
            error: function () {paApi.alert_error("Невозможно удалить изображение.")}
        });
    };

    paApi.saveNewGroup = function (data, onFetchFunc) {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/photo-anthropology/groups/save',
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (){onFetchFunc()},
            error: function () {paApi.alert_error("Не удалось сохранить группу.")}
        })
    };

    paApi.saveNewFile = function (data, onFetchFunc) {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/photo-anthropology/files/save',
            enctype: 'multipart/form-data',
            data: data,
            processData: false,
            contentType: false,
            success: function (){onFetchFunc()},
            error: function () {paApi.alert_error("Не удалось сохранить файл.")}
        })
    };
    paApi.alert_error = function(message){
        alert(message)
    }
});