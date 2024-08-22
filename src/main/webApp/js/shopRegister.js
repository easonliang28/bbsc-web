function shopRegister(event) {
    const requestURL = "/registration/shop";
    const dataJSON = {};
    dataJSON["name"] = document.getElementById("name").value;
    dataJSON["address"] = document.getElementById("address").value;
    dataJSON["email"] = document.getElementById("email").value;
    dataJSON["tel"] = document.getElementById("tel").value;
    dataJSON["icon"] = "";
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

window.addEventListener('load',function(){
    const form = document.getElementById('form-register-shop');
    if(form){
        form.addEventListener('submit', shopRegister);
        console.log("loaded--");
    }
});