$(document).ready(function () {

    paApi = {};

    paApi.getAllFiles = function (onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/all",
            success: function (data) {
                onFetchFunc(data)
            }
        });
    };

    paApi.getAllGroups = function (onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/all",
            success: function (data) {
                onFetchFunc(data)
            }
        });
    };

    paApi.getFileById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/getFile/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            }
        });
    };

    paApi.getGroupById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/getGroup/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            }
        });
    };

    paApi.getImageById = function (id, onFetchFunc) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/images/getImage/id/" + id,
            success: function (data) {
                onFetchFunc(data)
            }
        });
    };

    paApi.deleteFileById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/files/delete/id/" + id,
            success: function () {
                onFetchFunc()
            }
        });
    };

    paApi.deleteGroupById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/groups/delete/id/" + id,
            success: function () {
                onFetchFunc()
            }
        });
    };

    paApi.deleteTagById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/tags/delete/id/" + id,
            success: function () {
                onFetchFunc()
            }
        });
    };

    paApi.deleteImageById = function (id, onFetchFunc) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/photo-anthropology/images/delete/id/" + id,
            success: function () {
                onFetchFunc()
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
            success: function (){onFetchFunc()}
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
            success: function (){onFetchFunc()}
        })
    };
});