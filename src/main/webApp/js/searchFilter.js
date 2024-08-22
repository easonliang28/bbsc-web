let tabs = [];
let _min = 0;
let _max = 30000;
let pageINF = {};
pageINF["sortDirection"] = "ASC";
pageINF["pageNumber"] = 1;
pageINF["keyword"] = "";
pageINF["sortBy"] = "name";
let words= "";
setTimeout(init2slider('id66', 'id66b', 'id661', 'id662', 'id66i1', 'id66i2','input-search-bar'), 0);

function init2slider(idX, btwX, btn1X, btn2X, input1, input2,input3) {
    var slider = document.getElementById(idX);
    var between = document.getElementById(btwX);
    var button1 = document.getElementById(btn1X);
    var button2 = document.getElementById(btn2X);
    var inpt1 = document.getElementById(input1);
    var inpt2 = document.getElementById(input2);
    var inpt3 = document.getElementById(input3);

    var min=inpt1.min;
    var max=inpt1.max;

    /*init*/
    var sliderCoords = getCoords(slider);
    button1.style.marginLeft = '0px';
    button2.style.marginLeft = (slider.offsetWidth-button1.offsetWidth) + 'px';
    between.style.width = (slider.offsetWidth-button1.offsetWidth) + 'px';
    inpt1.value = min;
    inpt2.value = max;

    inpt1.onchange= function(evt)
    {
        if (parseInt(inpt1.value) < min)
            inpt1.value = min;
        if (parseInt(inpt1.value) > max)
            inpt1.value = max;
        if (parseInt(inpt1.value) > parseInt(inpt2.value))
        {
            var temp = inpt1.value;
            inpt1.value = inpt2.value;
            inpt2.value = temp;
        }


        var sliderCoords = getCoords(slider);
        var per1 = parseInt(inpt1.value-min)*100/(max-min);
        var per2 = parseInt(inpt2.value-min)*100/(max-min);
        var left1 = per1*(slider.offsetWidth-button1.offsetWidth)/100;
        var left2 = per2*(slider.offsetWidth-button1.offsetWidth)/100;

        button1.style.marginLeft = left1 + 'px';
        button2.style.marginLeft = left2 + 'px';

        if (left1 > left2)
        {
            between.style.width = (left1-left2) + 'px';
            between.style.marginLeft = left2 + 'px';
        }
        else
        {
            between.style.width = (left2-left1) + 'px';
            between.style.marginLeft = left1 + 'px';
        }
    }
    inpt2.onchange= function(evt)
    {
        if (parseInt(inpt2.value) < min)
            inpt2.value = min;
        if (parseInt(inpt2.value) > max)
            inpt2.value = max;
        if (parseInt(inpt1.value) > parseInt(inpt2.value))
        {
            var temp = inpt1.value;
            inpt1.value = inpt2.value;
            inpt2.value = temp;
        }

        var sliderCoords = getCoords(slider);
        var per1 = parseInt(inpt1.value-min)*100/(max-min);
        var per2 = parseInt(inpt2.value-min)*100/(max-min);
        var left1 = per1*(slider.offsetWidth-button1.offsetWidth)/100;
        var left2 = per2*(slider.offsetWidth-button1.offsetWidth)/100;

        button1.style.marginLeft = left1 + 'px';
        button2.style.marginLeft = left2 + 'px';

        if (left1 > left2)
        {
            between.style.width = (left1-left2) + 'px';
            between.style.marginLeft = left2 + 'px';
        }
        else
        {
            between.style.width = (left2-left1) + 'px';
            between.style.marginLeft = left1 + 'px';
        }
    }

    /*mouse*/
    button1.onmousedown = function(evt) {
        var sliderCoords = getCoords(slider);
        var betweenCoords = getCoords(between);
        var buttonCoords1 = getCoords(button1);
        var buttonCoords2 = getCoords(button2);
        var shiftX2 = evt.pageX - buttonCoords2.left;
        var shiftX1 = evt.pageX - buttonCoords1.left;

        document.onmousemove = function(evt) {
            var left1 = evt.pageX - shiftX1 - sliderCoords.left;
            var right1 = slider.offsetWidth - button1.offsetWidth;
            if (left1 < 0) left1 = 0;
            if (left1 > right1) left1 = right1;
            button1.style.marginLeft = left1 + 'px';


            shiftX2 = evt.pageX - buttonCoords2.left;
            var left2 = evt.pageX - shiftX2 - sliderCoords.left;
            var right2 = slider.offsetWidth - button2.offsetWidth;
            if (left2 < 0) left2 = 0;
            if (left2 > right2) left2 = right2;

            var per_min = 0;
            var per_max = 0;
            if (left1 > left2)
            {
                between.style.width = (left1-left2) + 'px';
                between.style.marginLeft = left2 + 'px';

                per_min = left2*100/(slider.offsetWidth-button1.offsetWidth);
                per_max = left1*100/(slider.offsetWidth-button1.offsetWidth);
            }
            else
            {
                between.style.width = (left2-left1) + 'px';
                between.style.marginLeft = left1 + 'px';

                per_min = left1*100/(slider.offsetWidth-button1.offsetWidth);
                per_max = left2*100/(slider.offsetWidth-button1.offsetWidth);
            }
            inpt1.value= (parseInt(min)+Math.round((max-min)*per_min/100));
            inpt2.value= (parseInt(min)+Math.round((max-min)*per_max/100));

        };
        document.onmouseup = function() {
            updateResult(inpt1.value,inpt2.value);
            document.onmousemove = document.onmouseup = null;
        };
        return false;
    };

    button2.onmousedown = function(evt) {
        var sliderCoords = getCoords(slider);
        var betweenCoords = getCoords(between);
        var buttonCoords1 = getCoords(button1);
        var buttonCoords2 = getCoords(button2);
        var shiftX2 = evt.pageX - buttonCoords2.left;
        var shiftX1 = evt.pageX - buttonCoords1.left;

        document.onmousemove = function(evt) {
            var left2 = evt.pageX - shiftX2 - sliderCoords.left;
            var right2 = slider.offsetWidth - button2.offsetWidth;
            if (left2 < 0) left2 = 0;
            if (left2 > right2) left2 = right2;
            button2.style.marginLeft = left2 + 'px';


            shiftX1 = evt.pageX - buttonCoords1.left;
            var left1 = evt.pageX - shiftX1 - sliderCoords.left;
            var right1 = slider.offsetWidth - button1.offsetWidth;
            if (left1 < 0) left1 = 0;
            if (left1 > right1) left1 = right1;

            var per_min = 0;
            var per_max = 0;

            if (left1 > left2)
            {
                between.style.width = (left1-left2) + 'px';
                between.style.marginLeft = left2 + 'px';
                per_min = left2*100/(slider.offsetWidth-button1.offsetWidth);
                per_max = left1*100/(slider.offsetWidth-button1.offsetWidth);
            }
            else
            {
                between.style.width = (left2-left1) + 'px';
                between.style.marginLeft = left1 + 'px';
                per_min = left1*100/(slider.offsetWidth-button1.offsetWidth);
                per_max = left2*100/(slider.offsetWidth-button1.offsetWidth);
            }
            inpt1.value= (parseInt(min)+Math.round((max-min)*per_min/100));
            inpt2.value= (parseInt(min)+Math.round((max-min)*per_max/100));
        };
        document.onmouseup = function() {
            updateResult(inpt1.value,inpt2.value);
            document.onmousemove = document.onmouseup = null;
        };
        return false;
    };

    button1.ondragstart = function() {
        return false;
    };
    button2.ondragstart = function() {
        return false;
    };

    function getCoords(elem) {
        var box = elem.getBoundingClientRect();
        return {
            top: box.top + pageYOffset,
            left: box.left + pageXOffset
        };
    }
    $(window).resize(function() {
        if (parseInt(inpt1.value) < min)
            inpt1.value = min;
        if (parseInt(inpt1.value) > max)
            inpt1.value = max;
        if (parseInt(inpt1.value) > parseInt(inpt2.value))
        {
            var temp = inpt1.value;
            inpt1.value = inpt2.value;
            inpt2.value = temp;
        }


        var sliderCoords = getCoords(slider);
        var per1 = parseInt(inpt1.value-min)*100/(max-min);
        var per2 = parseInt(inpt2.value-min)*100/(max-min);
        var left1 = per1*(slider.offsetWidth-button1.offsetWidth)/100;
        var left2 = per2*(slider.offsetWidth-button1.offsetWidth)/100;

        button1.style.marginLeft = left1 + 'px';
        button2.style.marginLeft = left2 + 'px';

        if (left1 > left2)
        {
            between.style.width = (left1-left2) + 'px';
            between.style.marginLeft = left2 + 'px';
        }
        else
        {
            between.style.width = (left2-left1) + 'px';
            between.style.marginLeft = left1 + 'px';
        }
    });

    $('#id66i1').on('input',function(e){
        updateResult(inpt1.value,inpt2.value);
    });
    $('#id66i2').on('input',function(e){
        updateResult(inpt1.value,inpt2.value);
    });
    $(".input-search-bar").on('input',function(e){
        //console.log($(".input-search-bar").val());
        pageINF["keyword"] = $(".input-search-bar").val();
        //console.log(ac);
        //updateSearchBarData();
        console.log(ac["data"]["src"]);
        restINF();
        updateSearchResult();
    });

}
function updateResult(min1,max2){
    _min= min1;
    _max= max2;
    restINF();
    updateSearchResult();
}

function updateSearchResult(){
    // console.log(getDataJson());
    // $('.item-row').css("visibility","hidden");
    // $('.pageINF').css("visibility","hidden");
    $('.item-row').finish();
    $('.pageINF').finish();
    $('.item-row').fadeOut();
    $('.pageINF').fadeOut();
    $.ajax({

        url: "/components/searchCategoryBlock/"+categoryId,
        data: JSON.stringify(getDataJson()),
        type: "POST",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        success: function(returnMessage){
            $('.item-row')[0].innerHTML=returnMessage;
            $('.item-row').fadeIn();
            // $('.item-row').css("visibility","visible");

            $.ajax({

                url: "/components/searchCategoryPageINF/"+categoryId,
                data: JSON.stringify(getDataJson()),
                type: "POST",
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                success: function(returnMessage2){
                    $('.pageINF')[0].innerHTML=returnMessage2;
                    $('.pageINF').fadeIn();
                    // $('.pageINF').css("visibility","visible");

                },
                error: function(jqXHR, ajaxOptions, thrownError){
                    //var errorMessage = JSON.parse(jqXHR.responseText);
                    Swal.fire(
                        "Search Fail", //title
                        jqXHR, //message
                        "error" //image --> success/info/warning/error/question
                    );
                }
            });
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            //var errorMessage = JSON.parse(jqXHR.responseText);
            Swal.fire(
                "Search Fail", //title
                jqXHR, //message
                "error" //image --> success/info/warning/error/question
            );
        }
    });
}


var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return typeof sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    return false;
};
function restINF(){
    pageINF["pageNumber"] = 1;
}

function page(pageNo){
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
    pageINF["pageNumber"]=parseInt(pageNo);
    updateSearchResult();
}

function switchOrderByBtn(){
    $("#btn-order-by1").css("display","none");
    $("#btn-order-by2").css("display","none");
    if($("#btn-order-by1").is(":checked")){
        $(".btn-order-by1").addClass("btn-primary");
        $(".btn-order-by1").removeClass("btn-outline-secondary");
        $(".btn-order-by2").addClass("btn-outline-secondary");
        $(".btn-order-by2").removeClass("btn-primary");
    }else {

        $(".btn-order-by2").addClass("btn-primary");
        $(".btn-order-by2").removeClass("btn-outline-secondary");
        $(".btn-order-by1").addClass("btn-outline-secondary");
        $(".btn-order-by1").removeClass("btn-primary");
    }
}

function updateSearchFilter() {

    $.each($('.search-checkbox'), function(i, field) {
        if($(this).val()===getUrlParameter("tab")){
            $(this).prop('checked', true);
        }
    });
    $('.search-checkbox').click(function(){
        if($(this).is(":checked")){
            tabs.push($(this).val());
            restINF();
            updateSearchResult();
        }
        else if($(this).is(":not(:checked)")){
            var removeItem = $(this).val();
            tabs = jQuery.grep(tabs, function(value) {
                return value != removeItem;
            });
            restINF();
            updateSearchResult();
        }
    });
}
function updateSearchBarData(){
    // console.log(getDataJson());
    let content = [];
    $.ajax({

        url: "/components/getSearchBarSuggestionList/"+categoryId,
        data: JSON.stringify(getDataJson()),
        type: "POST",
        dataType: "text",
        contentType: "application/json;charset=utf-8",
        success:function(returnData){
            content = returnData.split(',');
            // console.log("?????/"+content);
            // console.log(content);
            ac["data"]["src"] = content;
        },
        error: function(jqXHR, ajaxOptions, thrownError){
            //var errorMessage = JSON.parse(jqXHR.responseText);
            console.log(jqXHR);
            Swal.fire(
                "Search Fail", //title
                jqXHR["responseText"], //message
                "error" //image --> success/info/warning/error/question
            );
        }

    });
}
function getDataJson(){
    var dataJSON = {};
    dataJSON["id"] =$("#id").val();
    dataJSON["tabs"] =tabs;
    dataJSON["min"] =_min;
    dataJSON["max"] =_max;
    dataJSON["sortDirection"] = pageINF["sortDirection"];
    dataJSON["pageNumber"] = pageINF["pageNumber"];
    dataJSON["sortBy"] = pageINF["sortBy"];
    dataJSON["keyword"] = pageINF["keyword"];
    return dataJSON;
}
$(document).ready(function(){
    $(".navi-search-form").attr('style', 'display: none !important');
    if(getUrlParameter("tab"))
        tabs.push(getUrlParameter("tab"));
    updateSearchFilter();
    switchOrderByBtn();
    $(".order-by-btn-group").on('change',function (){
        switchOrderByBtn();
        restINF();
        if($("#btn-order-by1").is(":checked"))
            pageINF["sortDirection"] = "ASC";
        else
            pageINF["sortDirection"] = "DESC";
        updateSearchResult();
    });
    $(".Order-by-a").click(function (){
        $("#btnGroupDropOrderBy")[0].innerHTML=$(this).text();
        switch ($(this).text()) {
            case "Item Name":
                pageINF["sortBy"] = "name";break;
            case "Price":
                pageINF["sortBy"] = "price";break;
            case "Stock":
                pageINF["sortBy"] = "stock";break;
            case "Last Update":
                pageINF["sortBy"] = "update_time";break;
        }
        updateSearchResult();
    });
    words = updateSearchBarData();
    var keyword = getUrlParameter("keyword");
    if(keyword){
        $("#autoComplete").val(keyword);
        pageINF["keyword"] = keyword;
        restINF();
        updateSearchResult();
    }
});
let categoryId=getUrlParameter("categoryId");

let ac = new autoComplete({
    selector: "#autoComplete",
    placeHolder: "Search for Item...",
    data: {
        src: updateSearchBarData()
    },onSelection: (feedback) => {
        $("#autoComplete").val(feedback["selection"]["value"]);
        pageINF["keyword"] = feedback["selection"]["value"];
        restINF();
        updateSearchResult();
    },
    resultsList: {
        noResults: (list, query) => {
            // Create "No Results" message element
            const message = document.createElement("div");
            // Add class to the created element
            message.setAttribute("class", "no_result");
            // Add message text content
            message.innerHTML = `<span>Found No Results for "${query}"</span>`;
            // Append message element to the results list
            list.appendChild(message);
        },
    },
    resultItem: {
        highlight: {
            render: true
        }
    }
    // ,threshold: 2
});
