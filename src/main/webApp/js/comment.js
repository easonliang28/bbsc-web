//add-comment-btn


$( document ).ready(function() {

    $('.add-comment-btn').on("click",function(i) {
        let data = {};
        data["id"] = $("#uid").attr("id");
        data["iid"] = $("#iid").attr("iid");
        data["comment"] = $("#add-comment").val();
        console.log(data);
        $.ajax({
            url: "/registration/comment",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "text",
            contentType: "application/json;charset=utf-8",
            success: function(data){
                toast.fire("comment added!",
                    "",
                    "success");
            },
            error: function(jqXHR, ajaxOptions, thrownError){
                console.log(jqXHR);
            }
        });
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