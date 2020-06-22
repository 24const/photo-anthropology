$(document).ready(function() {

    //set global variable for API functions
    paApi = {};

    paApi.getAllFiles = function(onFetchFunc){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/all",
            success: function (data){onFetchFunc(data)},
            error: function (){
                alert("Ошибка при попытке получения файлов.")
            }
        });
    };

    paApi.getAllGroups = function(onFetchFunc){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/all",
            success: function (data){onFetchFunc(data)},
            error: function (){
                alert("Ошибка при попытке получения групп.")
            }
        });
    };

    paApi.getFileById = function(id, onFetchFunc){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/files/getFile/id/"+ id,
            success: function (data){onFetchFunc(data)},
            error: function () {
                alert("Невозможно получить информацию о файле с таким id.")
            }
        });
    };

    paApi.getGroupById = function(id, onFetchFunc){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/groups/getGroup/id/" + id,
            success: function (data){onFetchFunc(data)},
            error: function () {
                alert("Невозможно получить информацию о группе с таким id.")
            }
        });
    };

    paApi.deleteFileById = function(id, onFetchFunc){
        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/photo-anthropology/files/delete/id/"+ id,
            success: function (){onFetchFunc("Файл успешно удален.")},
            error: function (){onFetchFunc("Невозможно удалить файл.")}
        });
    };

    paApi.deleteGroupById = function(id, onFetchFunc){
        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/photo-anthropology/groups/delete/id/" + id,
            success: function (){onFetchFunc("Группа успешно удалена.")},
            error: function (){onFetchFunc("Невозможно удалить группу.")}
        });
    };

    paApi.deleteTagById = function(id, onFetchFunc){
        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/photo-anthropology/tags/delete/id/" + id,
            success: function (){onFetchFunc("Тэг успешно удален.")},
            error: function (){onFetchFunc("Невозможно удалить тэг.")}
        });
    };

    paApi.deleteImageById = function(id, onFetchFunc){
        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/photo-anthropology/images/delete/id/" + id,
            success: function (){onFetchFunc("Изображение успешно удалено.")},
            error: function (){onFetchFunc("Невозможно удалить изображение.")}
        });
    };

    $(function() {
        $('button[type=submit]').click(function(e) {
            e.preventDefault();
            $('input').next().remove();
            $.ajax({
                type: "POST",
                url : 'groups/save',
                data : $('form[name=groupForm]').serialize(),
                success : function(res) {
                    if(res.validated){
                        $('#resultContainer pre code').text(JSON.stringify(res.groups));
                        $('#resultContainer').show();
                    }else{
                        $.each(res.errorMessages,function(key,value){
                            $('input[name='+key+']').after('<span class="error">'+value+'</span>');
                        });
                    }
                }
            })
        });
    });
});