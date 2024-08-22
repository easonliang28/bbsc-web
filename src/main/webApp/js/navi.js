function customerRegister(event) {
    const requestURL = "/registration";
    const dataJSON = {};
    dataJSON["name"] = document.getElementById("name").value;
    dataJSON["address"] = document.getElementById("address").value;
    dataJSON["email"] = document.getElementById("email").value;
    dataJSON["dob"] = document.getElementById("DOB").value;
    dataJSON["tel"] = document.getElementById("tel").value;
    dataJSON["icon"] = "/customer/icon";
    dataJSON["pwd"] = document.getElementById("pwd").value;
    event.preventDefault();

    $.ajax({
        url: requestURL,
        data: JSON.stringify(dataJSON),
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        success: function(){
            Swal.fire("Registered",
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

function setting(id){
    let html=   "<div class='row'>" +
                    "<a class='btn btn-primary mb-3 col-12' href='/user/setting/profile'>Edit profile</a>" +
                    "<a class='btn btn-primary mb-3 col-12' href='#' onclick='addNaviMoney()'>Add money</a>" +
                    "<a class='btn btn-primary mb-3 col-12' href='#' onclick='changePassword("+id+")'>Reset password</a>" +
                "</div>";
    swal.fire({
        title: 'Setting menu',
        html:html,
        showCloseButton: true,
        showConfirmButton: false
    });
}
function adminSetting(){
    let html=   "<div class='row'>" +
                    "<a class='btn btn-primary mb-3 col-12' href='/admin/update/category'>Manage category</a>" +
                    "<a class='btn btn-primary mb-3 col-12' href='/admin/update/shop'>Manage shop</a>" +
                    "<a class='btn btn-primary mb-3 col-12' onclick='adminItem()'>Manage item</a>" +
                    "<a class='btn btn-primary mb-3 col-12' onclick='adminUser()'>Manage user</a>" +
                "</div>";
    swal.fire({
        title: 'Setting menu',
        html:html,
        showCloseButton: true,
        showConfirmButton: false
    });
}

function adminUser(){
    swal.fire({
        title: 'Please enter user id to edit.',
        html:"<input id='id' type='number' min='1' max='9223372036854775807'/>",
        showCloseButton: true,
        showConfirmButton: true,
        confirmButtonText: 'confirm',
        preConfirm: function() {
            return new Promise((resolve, reject) => {
                resolve({
                    id: $("#id")[0].value
                });
            });
        }
    }).then((data) => {
            // your input data object will be usable from here
            console.log(data);
        window.location.href = '/admin/update/user/'+data["value"]["id"];
    });
}
function adminItem(){
    swal.fire({
        title: 'Please enter shop id to edit.',
        html:"<input id='sid' type='number' min='1' max='9223372036854775807'/>",
        showCloseButton: true,
        showConfirmButton: true,
        confirmButtonText: 'confirm',
        preConfirm: function() {
            return new Promise((resolve, reject) => {
                resolve({
                    sid: $("#sid")[0].value
                });
            });
        }
    }).then((data) => {
            // your input data object will be usable from here
            console.log(data);
        window.location.href = '/admin/update/'+data["value"]["sid"]+"/item";
    });
}

window.addEventListener('load',function(){
    const form = document.getElementById('form-register');
    if(form){
        form.addEventListener('submit', customerRegister);
        // console.log("loaded--");
    }
});

var toastLogout = Swal.mixin({
    showCloseButton: true,
    toast: true,
    icon: 'success',
    title: 'General Title',
    animation: false,
    position: 'top-right',
    showConfirmButton: false,
    timer: 1000,
    timerProgressBar: true
});
var toastPassword = Swal.mixin({
    showCloseButton: true,
    toast: true,
    icon: 'success',
    title: 'General Title',
    animation: false,
    position: 'bottom-right',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
});
function wrongPassword(){
    toastPassword.fire({
        title: 'Wrong Password',
        icon: 'error'
    });
}

function logout(){

    toastLogout.fire({
        title: 'Logout in 1 second...',
        icon: 'success'

    }).then(function () {
            document.location.href="/logout";
    });
}

function changePassword(id){

    let htmlStr = "<input id='old-password' type='password' class='form-control mb-3' placeholder='Old password'/>" +
        "<input id='new-password' type='password' class='form-control mb-3' placeholder='New password'/>"+
        "<input id='confirm-new-password' type='password' class='form-control mb-3' placeholder='Comfrim new password'/>";
    swal.fire({
        title: 'Change password',
        showCloseButton: true,
        showCancelButton: true,
        confirmButtonText:
            '<svg xmlns="http://www.w3.org/2000/svg" width="1rem" height="1rem" fill="currentColor" class="bi bi-file-arrow-up" viewBox="0 0 16 16">\n' +
            '  <path d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>\n' +
            '  <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>\n' +
            '</svg> submit!',
        html: htmlStr,
        preConfirm: function() {
            return new Promise((resolve, reject) => {
                // get your inputs using their placeholder or maybe add IDs to them
                resolve({
                    oldPassword: $('#old-password')[0].value,
                    newPassword: $('#new-password')[0].value,
                    newPassword2: $('#confirm-new-password')[0].value
                });

                // maybe also reject() on some condition
            });
        }
    }).then(function (dismiss){
        if(dismiss["isDenied"]=== true||dismiss["isDismissed"]=== true) {
            return;
        }

        if(dismiss["isConfirmed"]=== true&& (
            dismiss["value"]["newPassword"] !== dismiss["value"]["newPassword2"] ||
            dismiss["value"]["newPassword"] === "" ||
            dismiss["value"]["oldPassword"] === ""
            // document.getElementById("item-icon-new").value ===""
        )){
            Swal.fire(
                "Confirm password can not be null or different!", //title
                "", //message
                "error" //image --> success/info/warning/error/question
            );
            return;
        }

        let dataJSON = {};
        dataJSON["id"] = id;
        dataJSON["oldPassword"] = dismiss["value"]["oldPassword"];
        dataJSON["newPassword"] = dismiss["value"]["newPassword"];

        console.log(dataJSON);
        $.ajax({
            url: "update/password",
            data: JSON.stringify(dataJSON),
            type: "PUT",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function(data){
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                console.log(jqXHR);
                if(jqXHR["responseText"]==="Password updated!")
                Swal.fire("Password updated!",
                    "",
                    "success");
                else
                Swal.fire(
                    "Fail", //title
                    jqXHR["responseText"], //message
                    "error" //image --> success/info/warning/error/question
                );
            }
        });


    });


}

$(document).ready(function () {

    $(".nav-heart ").on("mouseover",function (){
        $(this).html("" +
            "  <path fill-rule=\"evenodd\" d=\"M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z\"/>\n" +
            "");
        $(this).css("color","#ec2745");
    });
    $(".nav-heart ").on("mouseleave",function (){
        $(this).html("" +
            "  <path d=\"m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z\"/>" +
            "");
        $(this).css("color","black");
    });


    //fail to make it appear when mousing over on the item category hover
    //it works sometimes but sometimes will return bug from bootstrap
    /*$('#navi-category').mouseleave(function () {
        $(this).dropdown('toggle');
    });
    $('#navbarCategoryDropdown').mouseenter(function () {
        $(this).dropdown('show');
        $(".navbarCategoryDropdownBlock ").dropdown('show');
        console.log("on");
    });
    $('.dropdown-toggle').mouseover(function() {
        $('.dropdown-menu').show();
    })

    $('.dropdown-toggle').mouseout(function() {
        t = setTimeout(function() {
            $('.dropdown-menu').hide();
        }, 100);

        $('.dropdown-menu').on('mouseenter', function() {
            $('.dropdown-menu').show();
            clearTimeout(t);
        }).on('mouseleave', function() {
            $('.dropdown-menu').hide();
        })
    })*/
});


document.addEventListener("DOMContentLoaded", function(){
    /////// Prevent closing from click inside dropdown
    document.querySelectorAll('.dropdown-menu').forEach(function(element){
        element.addEventListener('click', function (e) {
            e.stopPropagation();
        });
    })
    document.querySelectorAll('.megamenu ').forEach(function(element){
        element.addEventListener('click', function (e) {
            e.stopPropagation();
        });
    });

});
function addNaviMoney(){

    var userMoney= $(".money").attr("money");
    if(userMoney<0) userMoney=0;
    Swal.fire({
        title:"How mach do you want to add?", //title
        showCancelButton: true,
        cancelButtonColor: '#d33',
        html: "<h4>you have $"+userMoney+"</h4>" +
            "<input id='add-money' type='number' min='0' max='20000' class='form-control'/>"
    }).then(function (dismiss){
        if(dismiss["isDenied"]=== true||dismiss["isDismissed"]=== true) {
            return;
        }else {
            var money = document.getElementById("add-money").value;
            var data = {};
            data["money"] = money;
            $.ajax({
                url: "/update/addMoney",
                data: JSON.stringify(data),
                type: "Put",
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                traditional: true,
                success: function(returnData){
                    console.log(returnData);
                    document.getElementsByClassName("money")[0].setAttribute("money",returnData);
                    document.getElementsByClassName("money")[0].innerHTML="Your money: $"+returnData;
                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    console.log(jqXHR);
                    Swal.fire(
                        "Adding is fail", //title
                        "", //message
                        "error" //image --> success/info/warning/error/question
                    ).then(function (){
                        location.reload();
                    });
                }
            });
        }
    });
}