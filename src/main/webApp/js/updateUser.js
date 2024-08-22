
function saveUser(_id){
    const dates = getJsonData(_id);
    $.ajax({
        url: "/update/user/",
        data: JSON.stringify(dates),
        type: "Put",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            Swal.fire(returnData,
                "",
                "success");
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            // console.log(jqXHR);
            if(jqXHR["status"]===200){
                Swal.fire("user updated!",
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
function refreshUser(_id){
    $.ajax({
        url: "/database/customer/get/"+_id,
        type: "GET",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            //clear
            console.log(returnData);
            document.getElementById("user-name").value = returnData["name"];
            document.getElementById("user-address").value = returnData["address"];
            document.getElementById("user-email").value = returnData["email"];
            document.getElementById("user-tel").value = returnData["tel"];
            document.getElementById("user-dob").value = returnData["dob"];
            document.getElementById("user-icon").value = returnData["icon"];
            document.getElementById("user-description").value = returnData["description"];

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
function getJsonData(_id){
    let dataJSON = {};
    dataJSON["id"] = _id;
    dataJSON["name"] = document.getElementById("user-name").value;
    dataJSON["address"] = document.getElementById("user-address").value;
    dataJSON["email"] = document.getElementById("user-email").value;
    dataJSON["tel"] = document.getElementById("user-tel").value;
    dataJSON["dob"] = document.getElementById("user-dob").value;
    dataJSON["icon"] = document.getElementById("user-icon").value;
    dataJSON["description"] = document.getElementById("user-description").value;
    return dataJSON;
}
//upload icon file
$(function(){
    $(".user-upload-icon").on("change",function (event) {

        event.preventDefault();

        let _id = $(this).closest("div").find("#user-id").text();
        console.log($(this).closest("#user-id"));
        uploadFile(_id,".user-upload-icon");

    });

    uploadFile = function (_id, classClicked) {

        let form = $('#UploadForm')[0];
        // console.log(classClicked);
        let data = new FormData(form);
        let newFile = new File([$(classClicked)[0].files[0]], _id + '.png', {type: 'image/png'});
        // var data ={};
        // data["uploadFile"] = newFile;
        // data.append("_id","1");
        data.append("uploadFile", newFile);
        // console.log(newFile);

        $("#btnSubmit").prop("disabled", true);

        $.ajax({
            type: "POST",               //使用POST傳輸,檔案上傳只能用POST
            url: "/upload/img/customer", //要傳輸對應的接口
            data: data,         //要傳輸的資料,我們將form 內upload打包成data
            enctype: 'multipart/form-data',
            processData: false, //防止jquery將data變成query String
            contentType: false,
            cache: false,  //不做快取
            async: false, //設為同步
            timeout: 1000000, //設定傳輸的timeout,時間內沒完成則中斷
            success: function (data) {
                if (data !== "Please select file!") {
                        $("#user-icon").val(data);
                    $(".card-img-top").attr("src",data);
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

$( document ).ready(function() {
    $(".userBlock").eacheach(function(i) {
        $(this).delay((5000/$(".userBlock").length)*i).animate({opacity:"1",right:"0px"}, 300);
    });

});