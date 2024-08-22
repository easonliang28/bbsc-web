function rate(pid){

    Swal.fire({
        title:"Please enter your rating", //title
        icon: 'info',
        showCancelButton: true,
        cancelButtonColor: '#d33',
        html: "<h4>(1 to 5 mark)</h4>" +
            "<input id='rating' name='rating' type='number' min='1' max='5' value='1' class='form-control'/>",
        preConfirm: function() {
            return new Promise((resolve, reject) => {
                resolve({
                    rating: $('#rating')[0].value
                });
            });
        }
    }).then(function (dismiss){
        console.log(dismiss);
        if(dismiss["isDenied"]=== true||dismiss["isDismissed"]=== true) {
            return;
        }else {
            var data = {};
            var rate = dismiss["value"]["rating"];
            data["rate"] = rate;
            data["pid"] = pid;
            $.ajax({
                url: "/update/rate",
                data: JSON.stringify(data),
                type: "Put",
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                traditional: true,
                success: function(returnData){
                    console.log(returnData);
                    $(".img-btn2-"+pid)[0].innerHTML="Your rating: "+dismiss["value"]["rating"];
                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    console.log(jqXHR);
                }
            }).then(function (){toast.fire("Thank for your rating!","","success")});
        }
    });
}

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