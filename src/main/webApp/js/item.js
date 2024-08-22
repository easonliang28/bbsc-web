
function addToCart(iid,id){

    swal.fire({
        title: 'New shop register',
        showCloseButton: true,
        showCancelButton: true,
        title: 'add to cart',
        html: "<span></span><input type='number' id='add-qty' class='form-control' max='1000' min='0' value='1' placeholder=\"Qty\"/>",
        preConfirm: function() {
            return new Promise((resolve, reject) => {
                // get your inputs using their placeholder or maybe add IDs to them
                resolve({
                    qty: $("#add-qty").val()
                });
            });
        }
    }).then((data)=>{
        console.log(data);
        console.log(getJsonData(iid,id,data["value"]["qty"]));
        $.ajax({
            url: "/registration/payCart/",
            data: JSON.stringify(getJsonData(iid,id,data["value"]["qty"])),
            type: "POST",
            dataType: "text",
            contentType: "application/json;charset=utf-8",
            success:function(returnData){
                Swal.fire(
                    "Item added!", //title
                    "<a href='/user/payCart' class='btn btn-primary'>Go to your shopping cart</a>", //message
                    "success" //image --> success/info/warning/error/question
                );
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                //var errorMessage = JSON.parse(jqXHR.responseText);
                console.log(jqXHR);
                Swal.fire(
                    "Search Fail", //title
                    "", //message
                    "error" //image --> success/info/warning/error/question
                );
            }
        });

    });
}

function getJsonData(iid,id,qty){
    let dataJSON = {};
    dataJSON["iid"] = iid;
    dataJSON["id"] = id;
    var qty = parseInt(qty);
    if(qty <0) qty=0;
    dataJSON["qty"] = qty;
    return dataJSON;
}

function favourite(iid){

        console.log(iid);
        console.log($("#favourites-"+iid).attr("value"));
        console.log($("#favourites-"+iid).attr("fid"));
        if($("#favourites-"+iid).attr("value")===true||
            $("#favourites-"+iid).attr("value")==="true"){

            $.ajax({
                url: "/database/favourites/"+$("#favourites-"+iid).attr("fid"),
                type: "DELETE",
                dataType: "text",
                success: function(data){
                    toast.fire("Favourite removed!",
                        "",
                        "success");
                    $(this).attr("fid","");
                    $("#favourites-"+iid).attr("value")
                    $("#favourites-"+iid).css("color","black");
                    $("#favourites-"+iid).attr("value",false);
                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    console.log(jqXHR);
                    if(jqXHR["responseText"]==="Favourite added!")
                        toast.fire("Favourite removed!",
                            "",
                            "success");
                }
            });
        }
        else {
            $.ajax({
                url: "/registration/favourites/"+iid,
                type: "POST",
                dataType: "text",
                success: function(data){
                    toast.fire("Favourite added!",
                        "",
                        "success");
                    console.log(data);
                    $("#favourites-"+iid).attr("fid",data);
                    $("#favourites-"+iid).attr("value",true);
                    $("#favourites-"+iid).css("color","#ec2745");
                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    console.log(jqXHR);
                    if(jqXHR["responseText"]==="Favourite added!") {
                        toast.fire("Favourite added!",
                            "",
                            "success");

                        $("#favourites-"+iid).attr("fid",jqXHR);
                        $("#favourites-"+iid).attr("value",true);
                        $("#favourites-"+iid).css("color","#ec2745");
                    }
                    else
                        Swal.fire(
                            "Fail", //title
                            jqXHR["responseText"], //message
                            "error" //image --> success/info/warning/error/question
                        );
                }
            });
        }


}

$(window).resize(function() {
    $('.img-icon').each(function(i) {
        $(this).css("height",$(this).width());
        $(this).css("padding-bottom",$(this).width()-$(this).height());
    });
});

$( document ).ready(function() {

    $('.img-icon').each(function(i) {
        $(this).css("width","100%");
        $(this).css("height",$(this).width());
        $(this).css("padding-bottom",$(this).width()-$(this).height());
        $(this).css("background-color","#bbbdc6");
    });
    $(".favourite-icon").each(function (){
        if($(this).attr("fid")!=="")
            $(this).css("color","#ec2745");
    })

    $(".favourite-icon").on("mouseleave",function () {
        $(this).closest("svg").html("<path d=\"m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z\"/>");

    });
    $(".favourite-icon").on("mouseover",function () {
        $(this).closest("svg").html("<path fill-rule=\"evenodd\" d=\"M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z\"/>");

    });
});

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
/*
$('.img-icon').each(function(i) {
    $(this).css("width","100%");
    $(this).css("height",$(this).width());
    $(this).css("padding-bottom",$(this).width()-$(this).height());
    $(this).css("background-color","#bbbdc6");
});*/
