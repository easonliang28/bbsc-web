function addItem(){
    let htmlStr = "";
    $.ajax({

        url: "/components/newItemTabBlock",
        type: "POST",
        contentType: "text/plain;charset=utf-8",
        async : false,
        success:function(returnData){
            // console.log("returnData:");
            console.log(returnData);
            htmlStr = returnData;

        }
    });

    swal.fire({
        title: 'New item register',
        showCloseButton: true,
        showCancelButton: true,
        confirmButtonText:
            '<svg xmlns="http://www.w3.org/2000/svg" width="1rem" height="1rem" fill="currentColor" class="bi bi-file-arrow-up" viewBox="0 0 16 16">\n' +
            '  <path d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>\n' +
            '  <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>\n' +
            '</svg> submit!',
        html: htmlStr,
        didOpen: () => {
            $("#item-category-new").on("change",function (event) {
                clearNewItemTab();
            });
            $("#iconUploadFileNew").on("change",function (event) {

                event.preventDefault();

                $.ajax({

                    url: "/database/item/nextIid",
                    type: "Get",
                    contentType: "text/plain;charset=utf-8",
                    async : false,
                    success:function(returnData){
                        // console.log("returnData:");
                        // console.log(returnData);
                        $(".item-upload-icon-new").addClass(".item-upload-icon-new-"+returnData);
                        uploadFile(returnData,"#iconUploadFileNew",true);


                    },
                    error: function(jqXHR, ajaxOptions, thrownError){
                        // console.log("jqXHR:");
                        // console.log(jqXHR);
                        uploadFile("","#iconUploadFileNew",true);
                    }

                });

            });
        }
    }).then(function (dismiss){
        if(dismiss["isDenied"]=== true||dismiss["isDismissed"]=== true) {
            return;
        }
        if(dismiss["isConfirmed"]=== true&& (
            document.getElementById("item-name-new").value ==="" ||
            document.getElementById("item-description-new").value ==="" ||
            document.getElementById("item-price-new").value ==="" ||
            document.getElementById("item-stock-new").value ===""
            // document.getElementById("item-icon-new").value ===""
        )){
            Swal.fire(
                "Column can not be null!", //title
                "", //message
                "error" //image --> success/info/warning/error/question
            );
            return;
        }
        let dataJSON = {};
        dataJSON["sid"] = $("#sid").text();
        dataJSON["name"] = document.getElementById("item-name-new").value;
        dataJSON["description"] = document.getElementById("item-description-new").value;
        var price = parseFloat($("#item-price-new").val());
        dataJSON["price"] = price.toFixed(2);
        dataJSON["stock"] = Math.floor(document.getElementById("item-stock-new").value);
        dataJSON["icon"] = document.getElementById("item-icon-new").value;
        dataJSON["category_id"] = $("#item-category-new").val();
        let tabs = [];
        var i;
        for (i = 0;i < $(".item-tab-select-new").length;i++){
            let tab = $(".item-tab-select-new")[i];
            tabs.push(tab.value);
        }
        tabs=removeDups(tabs);
        dataJSON["tabs"] = tabs;
        // console.log(JSON.stringify(dataJSON));
        $.ajax({
            url: "/registration/item",
            data: JSON.stringify(dataJSON),
            type: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function(returnMessage){
                $.ajax({

                    url: "/components/itemBlock/"+returnMessage,
                    type: "POST",
                    dataType: "text",
                    contentType: "application/json;charset=utf-8",
                    success: function(returnMessage){
                        $('.container')[0].innerHTML+=returnMessage;
                        $(".itemBlock").last().animate({opacity:"1",right:"0px"}, 300);
                    }
                });
                toast.fire({
                    title: "Item registered!",
                    icon: 'success'
                });
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                console.log(jqXHR);
                Swal.fire(
                    "Fail", //title
                    jqXHR.message, //message
                    "error" //image --> success/info/warning/error/question
                );
            }
        });

    });

}
function deleteItem(iid){
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
                'Item is fine :)',
                'error'
            );
        }else{
            $.ajax({
                url: "/database/item/"+iid,
                type: "DELETE",
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                success: function(returnMessage){
                    toast.fire({
                        title: "Item deleted!",
                        icon: 'success'
                    });
                    $('.itemBlock-'+iid).hide('slow', function(){ $('.itemBlock-'+iid).remove(); });
                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    console.log(jqXHR);
                    var errorMessage = JSON.parse(jqXHR);
                    Swal.fire(
                        "Fail", //title
                        errorMessage, //message
                        "error" //image --> success/info/warning/error/question
                    );
                }
            });

        }
    } );
}

function updateItem(sid){

    var dates = getJsonData(sid);
    // console.log(dates);
    $.ajax({
        url: "/update/item/",
        data: JSON.stringify(dates),
        type: "Put",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            toast.fire("Item updated!",
                "",
                "success");
            // refreshItem(returnData);
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            // console.log(jqXHR);
            if(jqXHR["status"]===200){
                Swal.fire("Item updated!",
                    "",
                    "success");
            }else
                console.log(jqXHR);
            Swal.fire(
                "Fail", //title
                '', //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}
function refreshItem(iid){
    $.ajax({
        url: "/components/itemTabBlock/"+iid,
        type: "POST",
        contentType: "text/plain;charset=utf-8",
        success: function(returnData){
            var str =returnData;

            Swal.fire(
                "Refreshed", //title
                "", //message
                "success" //image --> success/info/warning/error/question
            );
            $(".itemBlock-"+iid)[0].outerHTML=returnData;
            $(".itemBlock").animate({opacity:"1",right:"0px"}, 0);

            $(".tabBlockIn select").on("change",function(){
                var tabs=$(this).find("option");
                var i;
                console.log(tabs.length);
                for (i=0;i<tabs.length;i++){
                    console.log(tabs[i].value+"==="+$(this).val());
                    if (tabs[i].value===$(this).val()){
                        console.log(i);
                        console.log(tabs[i]);
                        tabs[i].setAttribute("selected","selected");
                        break;
                    }
                }
            });
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            console.log(jqXHR);
            Swal.fire(
                "Fail", //title
                jqXHR, //message
                "error" //image --> success/info/warning/error/question
            );
            $(".itemBlock").animate({opacity:"1",right:"0px"}, 0);
        }
    });
}
function removeDups(names) {
    let unique = {};
    names.forEach(function(i) {
        if(!unique[i]) {
            unique[i] = true;
        }
    });
    return Object.keys(unique);
}
function getJsonData(iid){
    let dataJSON = {};
    dataJSON["iid"] = iid;
    dataJSON["sid"] = $("#sid").text();
    dataJSON["name"] = document.getElementById("item-name-"+iid).value;
    dataJSON["description"] = document.getElementById("item-description-"+iid).value;
    var price = parseFloat($("#item-price-"+iid).val());
    dataJSON["price"] = price.toFixed(2);
    dataJSON["stock"] = Math.floor(document.getElementById("item-stock-"+iid).value);
    dataJSON["icon"] = document.getElementById("item-icon-"+iid).value;
    dataJSON["category_id"] = $("#item-category-"+iid).val();

    let tabs = [];
    var i;
    for (i = 0;i < $(".item-tab-select-"+iid).length;i++){
        let tab = $(".item-tab-select-"+iid)[i];
        tabs.push(tab.value);
    }
    tabs=removeDups(tabs);
    dataJSON["tabs"] = tabs;
    return dataJSON;
}
function addNewItemTab(){
    console.log("/components/newItemTabSelect/"+$("#item-category-new").val()+"/"+$(".item-tab-select-new").length);
    $.ajax({
        url: "/components/newItemTabSelect/"+$("#item-category-new").val()+"/"+$(".item-tab-select-new").length,
        type: "Post",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            console.log(returnData);
            if(returnData==="null")
                $(".item-tab-new")[0].innerHTML+="<div class=\"alert alert-warning\" role=\"alert\">No tab in this category.</div>";
            else
            $(".item-tab-new")[0].innerHTML+="<div class=\" alert alert-secondary alert-dismissible fade show blockTab\" role=\"alert\">" +
                "<div class='btn tabBlockIn tabBlock-new' edit_type='click'>"+returnData+"</div>"+
                "<button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "  <span aria-hidden=\"true\">×</span>\n" +
                "</button></div>";

            $(".tabBlockIn select").on("change",function(){
                var tabs=$(this).find("option");
                var i;
                console.log(tabs.length);
                for (i=0;i<tabs.length;i++){
                    console.log(tabs[i].value+"==="+$(this).val());
                    if (tabs[i].value===$(this).val()){
                        console.log(i);
                        console.log(tabs[i]);
                        tabs[i].setAttribute("selected","selected");
                        break;
                    }
                }
            });
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            console.log(jqXHR);
        }
    });
}
function addItemTab(iid){
    var name = ".item-tab-"+iid;
    $.ajax({
        url: "/components/itemTabSelect/"+iid+"/"+$("#item-category-"+iid).val()+"/"+$(".item-tab-select-"+iid).length+"/"+$(".item-tab-select-"+iid).val(),
        type: "Post",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            console.log(returnData);
            if(returnData==="null")
                $(name)[0].innerHTML+="<div class=\"alert alert-warning\" role=\"alert\">No tab in this category.</div>";
            else
            $(name)[0].innerHTML+="<div class=\" alert alert-secondary alert-dismissible fade show blockTab\" role=\"alert\">" +
                "<div class='btn tabBlockIn tabBlock-"+iid+"' edit_type='click'>"+returnData+"</div>"+
                "<button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "  <span aria-hidden=\"true\">×</span>\n" +
                "</button></div>";

            $(".tabBlockIn select").on("change",function(){
                var tabs=$(this).find("option");
                var i;
                console.log(tabs.length);
                for (i=0;i<tabs.length;i++){
                    console.log(tabs[i].value+"==="+$(this).val());
                    if (tabs[i].value===$(this).val()){
                        console.log(i);
                        console.log(tabs[i]);
                        tabs[i].setAttribute("selected","selected");
                        break;
                    }
                }
            });
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            console.log(jqXHR);
        }
    });
    // console.log(all.join());
}
function clearItemTab(iid){
    $(".item-tab-"+iid).empty();
}
function clearNewItemTab(){
    $(".item-tab-new").empty();
}
//upload icon file
$(function(){
    $(".item-upload-icon").on("change",function (event) {

        event.preventDefault();

        let sid = $(this).closest("div").find(".sid").text();
        // console.log($(this).closest(".sid"));
        uploadFile(sid,"#iconUploadFile"+sid, false);

    });

    uploadFile = function (sid, classClicked,newItem) {

        let form = $('#UploadForm-'+sid)[0];
        console.log(classClicked);
        console.log($(classClicked)[0]);
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
            url: "/upload/img/item",
            data: data,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            async: false,
            timeout: 1000000,
            success: function (data) {
                if (data !== "Please select file!") {
                    if(newItem)
                        $("#item-icon-new").val(data);
                    else {
                        $("#item-icon-" + sid).val(data);
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
    timer: 3500,
    timerProgressBar: true
});
$( document ).ready(function() {
    setTimeout(function (){
    $(".itemBlock").each(function(i) {
        var position = $(this).position().top;
        $(this).delay((3000/$(".itemBlock").length)*i).animate({opacity:"1",right:"0px"}, 300);
        setTimeout(function (){
            $( document ).delay().scrollTop(position);


        },(3000/$(".itemBlock").length)*i+
            3000/$(".itemBlock").length*4);
        setTimeout(function (){$( document ).delay().scrollTop(0);},3500);

        toast2.fire("loading...",
            "",
            "success");
    });
    },130);
    $(".item-category-select").on("change",function (){
        console.log($(this).parent().parent().parent().find(".item-tab"));
        $(this).parent().parent().parent().find(".item-tab").empty();
    });
    $(".tabBlockIn select").on("change",function(){
        var tabs=$(this).find("option");
        var i;
        console.log(tabs.length);
        for (i=0;i<tabs.length;i++){
            console.log(tabs[i].value+"==="+$(this).val());
            if (tabs[i].value===$(this).val()){
                console.log(i);
                console.log(tabs[i]);
                tabs[i].setAttribute("selected","selected");
                break;
            }
        }
    });
});