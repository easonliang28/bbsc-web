let price = parseFloat("0");

$( document ).ready(function() {
    $("#select-all").on("change",function() {
        var i;
        var isChecked = $(this).is(":checked");
        if(isChecked)
        for (i=0;i<$(".select-item").length;i++){
            $(".select-item")[i].checked=true;
        }
        else {

            for (i = 0; i < $(".select-item").length; i++) {
                $(".select-item")[i].checked = false;
            }
        }
        checkReset();
        updatePrice();
    });

    $(".select-item").on("change",function() {
        checkReset();
        var j=0;
        var i;
        for (i=0;i<$(".select-item").length;i++){
            if($(".select-item")[i].checked===true)
                j++;
        }
        if(j=== $(".select-item").length){
            $("#select-all")[0].checked=true;
            updatePrice();
            return;
        }
            $("#select-all")[0].checked=false;
        updatePrice();
    });
    $('.cart-qty').each(function() {
        var price = ($(this).val()*$(this).attr("price")).toFixed(2);
        $(this).parent().find("h5").html("$"+price);
    });
    $('.cart-qty').on("change",function() {
        var price = ($(this).val()*$(this).attr("price")).toFixed(2);
        $(this).parent().find("h5").html("$"+price);
        const pid = $(this).attr("pid");
        updatePayCart(pid);
    });
    $('.payType').on("change",function() {

        const pid = $(this).attr("pid");
        console.log($(this).attr("pid"));
        updatePayCart(pid);
    });
    $('.deleteCartItem').on("click",function() {
        console.log($(this));
        var here = $(this);
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
                const pid = here.attr("pid");
                console.log(here.attr("pid"));
                deletePayCart(pid,$(".payCartItem-"+pid));
            }
        });
    });
});

function updatePayCart(pid){
    var dates = {};
    dates["pid"] = pid+"";
    dates["payType"] = document.getElementById("item-category-payType-"+pid).value;
    dates["qty"] = $("#cart-qty-"+pid).val();
    dates["rate"] = 0;
    dates["pay"] = false;
    console.log(dates);
    $.ajax({
        url: "/update/pay_cart",
        data: JSON.stringify(dates),
        type: "Put",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        success: function(returnData){
            console.log(returnData);
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            // console.log(jqXHR);
            Swal.fire(
                "Fail", //title
                jqXHR["responseJSON"]["message"], //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}

function deletePayCart(pid,deletedHTML){
    $.ajax({
        url: "/database/pay_cart/"+pid,
        type: "DELETE",
        dataType: "text",
        success: function(returnData){
            console.log(returnData);
            $("#total").find("span").html($(".payCartItem").length-1);
            deletedHTML.hide('slow', function(){
                deletedHTML.remove();
                if($(".payCartItem").length===0){
                    $(".mainRow .col-lg-8").fadeIn();
                $(".mainRow .col-lg-8")[0].innerHTML+="<div class=\"col-12 card \">\n" +
                    "<h1 class=\"\">You do not any item in cart.</h1>\n" +
                    " <div class=\"card-body \">\n" +
                    "<a id=\"\" class=\"btn btn-primary\" onclick=\"\" href=\"/\">\n" +
                    "Back to shopping!\n" +
                    "</a>\n" +
                    " </div>\n" +
                    "</div>";
                }

            });
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            // console.log(jqXHR);
            Swal.fire(
                "Fail", //title
                jqXHR["responseJSON"]["message"], //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}

function updatePrice(){
    price = parseFloat("0");
    var i;
    $(".right-item-col")[0].innerHTML="";
    var isChecked = $("#select-all").is(":checked");
    if(isChecked)
        addAllRightItemCol();
    else if($(".select-item").length !==0)
    for (i=0;i<$(".select-item").length;i++){
        isChecked = $(".select-item")[i].checked;
        if(isChecked)
            addRightItemCol(i);
    }
    else {
    }
}
function addAllRightItemCol(){
    var i;
    for (i=0;i<$(".select-item").length;i++){
        addRightItemCol(i);
    }
}
function addRightItemCol(i){
    $(".right-item-col")[0].innerHTML+= "<h4 class='cal-price'><b>"+$(".cart-price")[i].innerHTML+"</b></h4>";
    var y = parseFloat($(".cart-price")[i].innerHTML.substring(1));
    price+=y;
    price=Math.round(price * 100) / 100;
    $("#price-result")[0].innerHTML = "$"+ (price);

    var j=0;
    var i;
    for (i=0;i<$(".select-item").length;i++){
        if($(".select-item")[i].checked===true)
            j++;
    }
    checkReset();
}
function checkReset(){
    var j=0;
    var i;
    for (i=0;i<$(".select-item").length;i++){
        if($(".select-item")[i].checked===true)
            j++;
    }
    console.log(j+"/"+$(".select-item").length);
    console.log(j===0);
    if(j===0) {
        $("#price-result")[0].innerHTML = "$0";
        $(".right-item-col")[0].innerHTML = "";
        price = parseFloat("0");
    }
}
function checkOut(){
    let pidList =[];
    let cash = 0.0;
    let money = 0.0;
    var i;
    if($(".select-item").length===0)return;
    for (i=0;i<$(".select-item").length;i++){
        if($(".select-item")[i].checked===true) {
            var pid= $(".select-item")[i].getAttribute("pid");
            pidList.push(parseInt(pid));
            var type=$(".payType")[i].value;
            if(type==="money") {
                var str =$(".cart-price")[i].innerHTML.substring(1);
                money += parseFloat(str);
            }
            else {
                var str =$(".cart-price")[i].innerHTML.substring(1);
                cash += parseFloat(str);
            }
        }
    }
    console.log(pidList);
    console.log(money);
    console.log(cash);
    let data = {};
    data["pidList"] = pidList;
    console.log(data);
    var userMoney= $(".money").attr("money");
    userMoney=Math.round(userMoney * 100) / 100;
    console.log(userMoney+"-"+money);
    console.log(userMoney- money>=0);
    if(userMoney- money>=0){
        $.ajax({
            url: "/update/buy",
            data: JSON.stringify(data),
            type: "Put",
            dataType: "text",
            contentType: "application/json;charset=utf-8",
            traditional: true,
            success: function(returnData){
                console.log(returnData);
                if(returnData==="No stock!"){
                    Swal.fire(
                        "Payment is fail.", //title
                        "No stock!", //message ["responseJSON"]["message"]
                        "error" //image --> success/info/warning/error/question
                    ).then(function (){location.reload();});
                }
                else {

                    Swal.fire(
                        "Payment is good!", //title
                        returnData, //message ["responseJSON"]["message"]
                        "success" //image --> success/info/warning/error/question
                    ).then(function (){location.reload();});
                }
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                console.log(jqXHR);
                Swal.fire(
                    "Payment is fail.", //title
                    "", //message ["responseJSON"]["message"]
                    "error" //image --> success/info/warning/error/question
                ).then(function (){location.reload();});
            }
        });//.then(function (){location.reload();});
    }
    else{
        Swal.fire({
            title:"Please add some money for your payment", //title
            icon: 'warning',
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
                        checkOut().then(function (){location.reload();});
                    },
                    error: function(jqXHR, ajaxOptions, thrownError){
                        console.log(jqXHR);
                        Swal.fire(
                            "Payment is fail", //title
                            "", //message
                            "error" //image --> success/info/warning/error/question
                        );
                    }
                }).then(function (){location.reload();});
            }
        });
    }
}