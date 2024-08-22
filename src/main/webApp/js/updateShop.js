function addShop(){
    swal.fire({
        title: 'New shop register',
        showCloseButton: true,
        showCancelButton: true,
        confirmButtonText:
            '<svg xmlns="http://www.w3.org/2000/svg" width="1rem" height="1rem" fill="currentColor" class="bi bi-file-arrow-up" viewBox="0 0 16 16">\n' +
            '  <path d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>\n' +
            '  <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>\n' +
            '</svg> submit!',
        html:
            "<div class=\"input-group alert alert-secondary shopBlock-new\" role=\"alert\">" +
            " <div style='display: flow-root !important;flex: 1 1 auto;width: 1%' class='input-group-prepend '>" +
            " <div class=\"input-group mb-3\">\n" +
            "  <span class=\"input-group-text\">Shop name</span>\n" +
            "  <input type='text' class = 'form-control form-control-lg' id='shop-name-new' value='' placeholder='shop name'>" +
            " </div>"+
            " <div class=\"input-group mb-3\">\n" +
            "  <span class=\"input-group-text\">Shop address</span>\n" +
            "  <input type='text' class = 'form-control form-control-lg' id='shop-address-new' value='' placeholder='shop address'>" +
            " </div>"+
            " <div class=\"input-group mb-3\">\n" +
            "  <span class=\"input-group-text\">email</span>\n" +
            "  <input type='text' class = 'form-control form-control-lg' id='shop-email-new' value='' placeholder='shop email'>" +
            " </div>"+
            " <div class=\"input-group mb-3\">\n" +
            "  <span class=\"input-group-text\">tel</span>\n" +
            "  <input type='tel' pattern=\"[0-9]{20}\" size='20' class = 'form-control form-control-lg' id='shop-tel-new' value='' placeholder='shop tel'>" +
            " </div>"+
            " <div class=\"input-group mb-3\">\n" +
            "  <input style='display:none' type='text' class = 'form-control' id='shop-icon-new' value=''>" +
            "  <span class=\"input-group-text\">icon</span>\n" +
            "  <div class='sid' style='display:none' ></div>" +
            "  <input class='form-control form-control-lg' id='iconUploadFileNew' type=\"file\" name=\"uploadFiles\" multiple=\"multiple\" accept=\".png\" style='padding-bottom: 33px;'/>" +
            " </div>"+
            "</div>" +
            "</div>",
        didOpen: () => {
            $("#iconUploadFileNew").on("change",function (event) {

                event.preventDefault();

                $.ajax({

                    url: "/database/shop/nextSid",
                    type: "Get",
                    contentType: "text/plain;charset=utf-8",
                    async : false,
                    success:function(returnData){
                        // console.log("returnData:");
                        // console.log(returnData);
                        $(".shop-upload-icon-new").addClass(".shop-upload-icon-new"+returnData);
                        uploadFile(returnData,"#iconUploadFileNew",true);

                    },
                    error: function(jqXHR, ajaxOptions, thrownError){
                        // console.log("jqXHR:");
                        // console.log(jqXHR);
                        uploadFile("",".shop-upload-icon-",true);
                    }

                });

            });
        }
    }).then(function (dismiss){
        if(dismiss["isDenied"]=== true||dismiss["isDismissed"]=== true) {
            return;
        }
        if(dismiss["isConfirmed"]=== true&& (
            document.getElementById("shop-name-new").value ==="" ||
            document.getElementById("shop-address-new").value ==="" ||
            document.getElementById("shop-email-new").value ==="" ||
            document.getElementById("shop-tel-new").value ===""
            // document.getElementById("shop-icon-new").value ===""
        )){
            Swal.fire(
                "Column can not be null!", //title
                "", //message
                "error" //image --> success/info/warning/error/question
            );
            return;
        }
        let dataJSON = {};
        dataJSON["name"] = document.getElementById("shop-name-new").value;
        dataJSON["address"] = document.getElementById("shop-address-new").value;
        dataJSON["email"] = document.getElementById("shop-email-new").value;
        dataJSON["tel"] = document.getElementById("shop-tel-new").value;
        dataJSON["icon"] = document.getElementById("shop-icon-new").value;
        // console.log(JSON.stringify(dataJSON));
        $.ajax({
            url: "/registration/shop",
            data: JSON.stringify(dataJSON),
            type: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function(returnMessage){
                $.ajax({

                    url: "/components/shopBlock/"+returnMessage["sid"],
                    type: "POST",
                    dataType: "text",
                    contentType: "application/json;charset=utf-8",
                    success: function(returnMessage){
                        $("#h2").remove();
                        $('.container')[0].innerHTML+=returnMessage;
                        $(".shopBlock").last().animate({opacity:"1",right:"0px"}, 300);
                    }
                });
                toast.fire({
                    title: "Shop registered!",
                    icon: 'success'
                });
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                Swal.fire(
                    "Fail", //title
                    jqXHR.message, //message
                    "error" //image --> success/info/warning/error/question
                );
            }
        });

    });

}
function deleteShop(sid){
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
                'shop is fine :)',
                'error'
            );
        }else{
            $.ajax({
                url: "/database/shop/"+sid,
                type: "DELETE",
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                success: function(returnMessage){
                    toast.fire({
                        title: "Shop deleted!",
                        icon: 'success'
                    });
                    $('.shopBlock-'+sid).hide('slow', function(){
                        $('.shopBlock-'+sid).remove();
                        if($(".shopBlock").length===0)
                            $(".container")[0].innerHTML += "<h2 id='h2'>You do not have any shop.</h2>";
                    });
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

function updateShop(sid){

    var dates = getJsonData(sid);
    // console.log(dates);
    $.ajax({
        url: "/update/shop/",
        data: JSON.stringify(dates),
        type: "Put",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            Swal.fire(returnData["message"],
                "",
                "success");
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            // console.log(jqXHR);
            if(jqXHR["status"]===200){
                Swal.fire("Shop updated!",
                    "",
                    "success");
            }else
            Swal.fire(
                "Fail", //title
                jqXHR["responseJSON"]["message"], //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}
function refreshShop(sid){
    $.ajax({
        url: "/database/shop/"+sid,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            //clear
            // console.log(returnData);
            $(".shopBlock-"+sid+" #shop-name-"+sid).val(returnData["name"]);
            $(".shopBlock-"+sid+" #shop-address-"+sid).val(returnData["address"]);
            $(".shopBlock-"+sid+" #shop-email-"+sid).val(returnData["email"]);
            $(".shopBlock-"+sid+" #shop-tel-"+sid).val(returnData["tel"]);
            $(".shopBlock-"+sid+" #shop-icon-"+sid).val(returnData["icon"]);

            toast.fire({
                title: "Refreshed!",
                icon: 'success'
            });
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
function getData(sid){
    var name = document.getElementById("shop-address-"+sid).value;
    var address = document.getElementById("shop-address-"+sid).value;
    var email = document.getElementById("shop-email-"+sid).value;
    var tel = document.getElementById("shop-tel-"+sid).value;
    var icon = document.getElementById("shop-icon-"+sid).value;
    return  "{\n" +
        "\t\"sid\":\""+sid+"\",\n" +
        "    \"name\": \""+name+"\",\n" +
        "    \"address\": ["+address +"]\n" +
        "    \"email\": ["+email +"]\n" +
        "    \"tel\": ["+tel +"]\n" +
        "    \"icon\": ["+icon +"]\n" +
        "}";
}
function getJsonData(sid){
    let dataJSON = {};
    dataJSON["sid"] = sid;
    dataJSON["name"] = document.getElementById("shop-name-"+sid).value;
    dataJSON["address"] = document.getElementById("shop-address-"+sid).value;
    dataJSON["email"] = document.getElementById("shop-email-"+sid).value;
    dataJSON["tel"] = document.getElementById("shop-tel-"+sid).value;
    dataJSON["icon"] = document.getElementById("shop-icon-"+sid).value;
    return dataJSON;
}
function getNewData(){
    var name = $("#shop-address-new").val();
    var address = $("#shop-address-new").val();
    var email = $("#shop-email-new").val();
    var tel = document.getElementById("shop-tel-new").val();
    var icon = document.getElementById("shop-icon-new").val();
    return  "{\n" +
        "\t\"sid\":\""+sid+"\",\n" +
        "    \"name\": \""+name+"\",\n" +
        "    \"address\": ["+address +"]\n" +
        "    \"email\": ["+email +"]\n" +
        "    \"tel\": ["+tel +"]\n" +
        "    \"icon\": ["+icon +"]\n" +
        "}";
}
//upload icon file
$(function(){
    $(".shop-upload-icon").on("change",function (event) {

        event.preventDefault();

        let sid = $(this).closest("div").find(".sid").text();
        // console.log($(this).closest(".sid"));
        uploadFile(sid,".shop-upload-icon-"+sid, false);

    });

    uploadFile = function (sid, classClicked,newShop) {

        let form = $('#UploadForm-'+sid)[0];
        // console.log(classClicked);
        let data = new FormData(form);
        let newFile = new File([$(classClicked)[0].files[0]], sid + '.png', {type: 'image/png'});
        // var data ={};
        // data["uploadFile"] = newFile;
        // data.append("sid","1");
        data.append("uploadFile", newFile);
        // console.log(newFile);

        $("#btnSubmit").prop("disabled", true);

        $.ajax({
            type: "POST",
            url: "/upload/img/shop",
            data: data,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            async: false,
            timeout: 1000000,
            success: function (data) {
                if (data !== "Please select file!") {
                    if(newShop)
                        $("#shop-icon-new").val(data);
                    else {
                        $("#shop-icon-" + sid).val(data);
                        $(".img-" + sid).attr("src",data);
                    }
                    // console.log("success data:");
                    // console.log(data);
                } else {
                    Swal.fire(
                        "upload Fail", //title
                        data, //message
                        "error" //image --> success/info/warning/error/question
                    );
                }
            },
            error: function (e) {
                // console.log(e.responseText);
                Swal.fire(
                    "upload Fail", //title
                    e["responseText"], //message
                    "error" //image --> success/info/warning/error/question
                );
            }
        })
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
        $(".shopBlock").each(function(i) {
            var position = $(this).position().top;
            $(this).delay((3000/$(".shopBlock").length)*i).animate({opacity:"1",right:"0px"}, 300);
            setTimeout(function (){
                $( document ).delay().scrollTop(position);


            },(3000/$(".shopBlock").length)*i+
                3000/$(".shopBlock").length*4);
            setTimeout(function (){$( document ).delay().scrollTop(0);},3500);

        });
    },130);
});