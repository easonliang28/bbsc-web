function addCategory(){
    swal.fire({
        title: 'Add new Category',
        showCloseButton: true,
        showCancelButton: true,
        confirmButtonText:
            '<svg xmlns="http://www.w3.org/2000/svg" width="1rem" height="1rem" fill="currentColor" class="bi bi-file-arrow-up" viewBox="0 0 16 16">\n' +
            '  <path d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>\n' +
            '  <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>\n' +
            '</svg> submit!',
        html:
            "<div class=\"input-group alert alert-secondary categoryBlock-new\" role=\"alert\">" +
            "<div style='display: flow-root !important;flex: 1 1 auto;width: 1%' class='input-group-prepend '>" +
            "<input type='text' class = 'form-control form-control-lg' id='category-name-new' value='' placeholder='category name'>" +
            "<hr class=\"hr-text\" data-content=\"Tabs\">" +
            "<div class='tab-new'>"+
            "</div></div>" +
            "<div style='margin-left:10px;display: grid;' class='input-group-prepend'>" +
            " <button  type=\"button\" class=\"btn-category btn btn-outline-primary\" onclick='addNewTab()'>\n" +
            "  <span aria-hidden=\"true\">add tab</span>\n" +
            " </button>" +
            " <button type=\"button\" class=\"btn-category btn btn-outline-info\" onclick='clearNewTabs()'>\n" +
            "  <span aria-hidden=\"true\">clear tabs</span>\n" +
            " </button>" +
            " </button>" +
            "</div>" +
            "</div>"
    }).then(function (dismiss){
        if(dismiss["isDenied"]=== true||dismiss["isDismissed"]=== true) {
            return;
        }
        if(dismiss["isConfirmed"]=== true&& document.getElementById("category-name-new").value ==="") {
            Swal.fire(
                "Name can not be null!", //title
                "", //message
                "error" //image --> success/info/warning/error/question
            );
            return;
        }
        var all = $(".tabBlock-new").map(function() {
            return "\""+this.innerHTML+"\"";
        }).get();
        var name = document.getElementById("category-name-new").value;
        var dates = "{\n" +
            "    \"name\": \""+name+"\",\n" +
            "    \"tab\": ["+all +"]\n" +
            "}";
        $.ajax({
            url: "/registration/category",
            data: dates,
            type: "POST",
            dataType: "text",
            contentType: "application/json;charset=utf-8",
            success: function(returnMessage){
                $.ajax({

                    url: "/components/admin/categoryBlock/"+returnMessage,
                    type: "POST",
                    dataType: "text",
                    contentType: "application/json;charset=utf-8",
                    success: function(returnMessage){
                        $('.container')[0].innerHTML+=returnMessage;
                        $(".categoryBlock").last().animate({opacity:"1",right:"0px"}, 300);
                    }
                });
                toast.fire({
                    title: "Category added!",
                    icon: 'success'
                });
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                Swal.fire(
                    "Fail", //title
                    jqXHR, //message
                    "error" //image --> success/info/warning/error/question
                );
            }
        });

    });

}
function addNewTab(){

    $(".tab-new")[0].innerHTML+="<div class=\"blockTab alert alert-secondary alert-dismissible fade show \" role=\"alert\"><div class='btn tabBlockIn tabBlock-new' edit_type=\"click\">new</div><button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
        "  <span aria-hidden=\"true\">×</span>\n" +
        "</button></div>";

}
function clearNewTabs(){
    $('.tab-new').empty();
}
function deleteCategory(categoryId){
    swal.fire({
        title: 'confirm to delete it?',
        text: 'you can not restore it!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',
        buttonsStyling: false
    }).then(function(dismiss) {
        // console.log(dismiss);
        if (dismiss["isDismissed"] === true) {
            swal.fire(
                'cancel',
                'category is fine :)',
                'error'
            );
        }else{
            $.ajax({
                url: "/database/category/"+categoryId,
                type: "DELETE",
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                success: function(returnMessage){
                    toast.fire({
                        title: returnMessage,
                        icon: 'success'
                    });
                    $('.categoryBlock-'+categoryId).hide('slow', function(){ $('.categoryBlock-'+categoryId).remove(); });
                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    var errorMessage = JSON.parse(jqXHR);
                    Swal.fire(
                        "Fail", //title
                        jqXHR, //message
                        "error" //image --> success/info/warning/error/question
                    );
                }
            });

        }
    } );
}

function addTab(categoryId){
    var name = ".tab-"+categoryId;
    var all = $(".tabBlock-"+categoryId).map(function() {
        return this.innerHTML;
    }).get();
    $(name)[0].innerHTML+="<div class=\"blockTab alert alert-secondary alert-dismissible fade show \" role=\"alert\"><div class='btn tabBlockIn tabBlock-"+categoryId+"' edit_type=\"click\">new</div><button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
        "  <span aria-hidden=\"true\">×</span>\n" +
        "</button></div>";
    // console.log(all.join());
}
function updateCategory(categoryId){

    var all = $(".tabBlock-"+categoryId.toString()).map(function() {
        return "\""+this.innerHTML+"\"";
    }).get();
    var name = document.getElementById("category-name-"+categoryId.toString()).value;
    var dates = "{\n" +
        "\t\"category_id\":\""+categoryId+"\",\n" +
        "    \"name\": \""+name+"\",\n" +
        "    \"tab\": ["+all +"]\n" +
        "}";
    // console.log(dates);
    $.ajax({
        url: "/update/category/",
        data: dates,
        type: "Put",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            Swal.fire(returnData,
                "",
                "success")
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            var errorMessage = JSON.parse(jqXHR.responseText);
            Swal.fire(
                "Fail", //title
                errorMessage["message"], //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}
function refreshCategory(categoryId){
    $.ajax({
        url: "/database/category/"+categoryId,
        type: "GET",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            clearTabs(categoryId);
            // console.log(returnData["tab"].length);
            var content = "";
            for(i = 0;i < returnData["tab"].length;i++)
                content += "<div class=\"blockTab alert alert-secondary alert-dismissible fade show \" role=\"alert\"><div class='btn tabBlockIn tabBlock-"+categoryId+"' edit_type=\"click\">"+returnData["tab"][i]+"</div><button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "  <span aria-hidden=\"true\">×</span>\n" +
                    "</button></div>";
            // console.log(returnData["tab"]);
            // console.log(content);
            $(".tab-"+categoryId)[0].innerHTML+=content;

        },
        error: function(jqXHR, ajaxOptions, thrownError){
            var errorMessage = JSON.parse(jqXHR.responseText);
            Swal.fire(
                "Fail", //title
                errorMessage["message"], //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}
function clearTabs(categoryId){
    $(".tab-"+categoryId).empty();
}

$(document).on("click",".tabBlockIn",function (event){
    event.preventDefault();
    if($(this).attr("edit_type")=== "button"){return false;}
    $(this).closest("div").attr("contenteditable",true);
    $(this).addClass("bg-light").css("padding","5px");
    $(this).focus();
})
$(document).on("focusout",".tabBlockIn",function (event){
    event.preventDefault();
    if($(this).attr("edit_type")=== "button"){return false;}
    $(this).removeAttr("contenteditable",true).removeClass("bg-light").css("padding",'');
    if($(this).html() ===""){
        $(this).closest(".blockTab").remove();
    }

})
var toast = Swal.mixin({
    showCloseButton: true,
    toast: true,
    icon: 'success',
    title: 'General Title',
    animation: false,
    position: 'bottom-right',
    showConfirmButton: false,
    timer: 2500,
    timerProgressBar: true
});
var toast2 = Swal.mixin({
    showCloseButton: true,
    toast: true,
    icon: 'success',
    title: 'General Title',
    animation: false,
    position: 'bottom-right',
    showConfirmButton: false,
    timer: 3450,
    timerProgressBar: true
});

$( document ).ready(function() {
    setTimeout(function (){
        $(".categoryBlock").each(function(i) {
            var position = $(this).position().top;
            $(this).delay((3000/$(".categoryBlock").length)*i).animate({opacity:"1",right:"0px"}, 300);
            setTimeout(function (){
                $( document ).delay().scrollTop(position);


            },(3000/$(".categoryBlock").length)*i+
                3000/$(".categoryBlock").length*4);
            setTimeout(function (){$( document ).delay().scrollTop(0);},3500);

            toast2.fire("loading...",
                "",
                "success");
        });
    },130);
});