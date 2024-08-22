
function getXML(){
    $.ajax({
        url: './task1.xml',
        datatype: 'xml',
        contentType: "text/xml",
        success: function(xml) {
            $(xml).find('timetable').each(function() {
                let name = $(this).find('name').text();
                name+=", due: " + $(this).find('leavingTime').text();
                $("#q1-name").append(name);
                let ul = "";
                $(this).find("schedule").each(function(){
                    let li = "<li>";
                    li +=$(this).find("place").text()+", ";
                    li +=$(this).find("time").text()+", ";
                    li +=$(this).find("Platform").text();
                    li +="</li>";
                    ul+=li;
                });
                $(".q1-ul").html(ul);
            });
        },
        error : function (xhr, ajaxOptions, thrownError){
            console.log(xhr.status);
            console.log(thrownError);
        }
    });
}

function getJSON(){
    $.ajax({
        url: './task1.json',
        datatype: 'json',
        contentType: "application/json;",
        success: function(json) {
            const timetable = json["timetable"];
            let name = timetable['name'];
            name+=", due: " + timetable['leavingTime'];
            $("#q2-name").append(name);
            let ul = "";
            timetable['schedule'].forEach(function (item, index) {
                let li = "<li>";
                li +=item["place"]+", ";
                li +=item["time"]+", ";
                li +=item["Platform"];
                li +="</li>";
                ul+=li;
            });
            $(".q2-ul").html(ul);
        },
        error : function (xhr, ajaxOptions, thrownError){
            console.log(xhr.status);
            console.log(thrownError);
        }
    });
}
