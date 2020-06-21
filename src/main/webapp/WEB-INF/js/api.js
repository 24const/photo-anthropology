$(document).ready(function() {

    //set global variable for API functions
    paApi = {};

    paApi.getAllFiles = function(onFetchFunc){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/all",
            success: function (data){onFetchFunc(data)},
            //TODO: надо также обработать случай, когда с сервера пришла ошибка.
            // Например выбросить сообщени через alert
        });
    };

    paApi.getAllGroups = function(onFetchFunc){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/groups/all",
            success: function (data){onFetchFunc(data)},
            //TODO: надо также обработать случай, когда с сервера пришла ошибка.
            // Например выбросить сообщени через alert
        });
    };

    paApi.getFileById = function(id, onFetchFunc){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/files/getFile/id/"+ id,
            success: function (data){onFetchFunc(data)}
        });
    };

    paApi.getGroupById = function(id, onFetchFunc){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/groups/getGroup/id/" + id,
            success: function (data){onFetchFunc(data)}
        });
    };

    paApi.deleteFileById = function(id, onFetchFunc){
        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/photo-anthropology/files/delete/id/"+ id,
            success: function (data){onFetchFunc(data)}
        });
    };

    paApi.deleteGroupById = function(id, onFetchFunc){
        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/photo-anthropology/groups/delete/id/" + id,
            success: function (data){onFetchFunc(data)}
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