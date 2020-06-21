$(document).ready(function() {

    //set global variable for API functions
    paApi = {};

    paApi.getAllFiles = function(onFetchFunc){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/photo-anthropology/files/all",
            success: onFetchFunc(data)
            //TODO: надо также обработать случай, когда с сервера пришла ошибка.
            // Например выбросить сообщени через alert


        });
    };

    $("#get_all_groups").click(function(){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/groups/all",
            success: function(data){
                for(let k in data) {
                    $("#content").empty().append("" +
                        "<tr><th>id</th><th>Group name</th><th>Group question</th></tr>" +
                        "<tr><td>" + data[k].id + "</td>" +
                        "<td>" + data[k].group_name + "</td>" +
                        "<td>" + data[k].group_question+"</td>" +
                        "<td><button>Удалить</button></td></tr>");
                }
            }
        });
    });

    $("#get_group_by_id").click(function(){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/groups/getGroup/id/",
            data: $(id),
            success: function(data){
                $("#content").empty().append("<tr><th>id</th><th>Group name</th><th>Group question</th></tr><tr><td>" + data[k].id + "</td>" +
                    "<td>" + data[k].group_name + "</td>" +
                    "<td>" + data[k].group_question+"</td>" +
                    "<td><button>Удалить</button></td></tr>");

            }
        });
    });

    $("#get_file_by_id").click(function(){
        $.ajax({
            type : "GET",
            url : "http://localhost:8080/photo-anthropology/files/getFile/id/9",
            success: function(data){
                $("#content").empty().append("<tr><th>id</th><th>File name</th><th>Date created</th></tr>" +
                    "<tr><td>" + data.id + "</td>" +
                    "<td>" + data.file_name + "</td>" +
                    "<td>" + data.date_created["dayOfMonth"]+"."+data.date_created["month"]+"."+data.date_created["year"] + "</td>" +
                    "<td><button>Удалить</button></td></tr>");
            }
        });
    });

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