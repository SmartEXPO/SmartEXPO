/**
 * @author Ben
 */


var phase = 1;
var finished = false;

function list_conn() {
    if (!finished) {
        $.get('/SmartEXPO_Proj_new/listItemServlet',
                {area: area, phase: phase},
        function(data) {
            var obj = JSON.parse(data);
            $("#container").append(trans(obj));
//            $("#container").BlocksIt({});
        });
    } else {
        $("#loading").css({display: 'block'});
        $("#loading").html("<p>ALL ARTWORKS ARE LISTED ABOVE</p><br/><br/>");
    }
}

function trans(obj) {
    _allcount = obj.count;
    _upcount = 0;
    var rtrn = new Array();
    var tmp;
    if (obj && (obj.status == "0" || obj.status == "1")) { //正常接受
        for (var i = 0; i < obj.count; i++) {
            tmp = "<div class=\"grid\"><div class=\"imgholder\"><a href=\"" + obj.list[i].link + "\"><img src=\"/SmartEXPO_Proj_new" + obj.list[i].img + "\" /></a></div><a href=\"" + obj.list[i].link + "\"><strong>" + obj.list[i].title + "</strong></a><p>" + obj.list[i].description + "</p><div class=\"meta\">" + obj.list[i].author + "</div></div>";
            $("<img/>")
                    .load(function() {
                upcount();
            })
                    .error(function() {
                console.log("error loading image");
            })
                    .attr("src", "/SmartEXPO_Proj_new" + obj.list[i].img);


            rtrn.push(tmp);
        }
        if (obj.status == "1")
            finished = true;
        phase++;
    } else {
        console.log("ERROR: trans error");
    }
    return rtrn.join("");
}

var _upcount;
var _allcount;
function upcount() {
    _upcount++;
    if (_upcount == _allcount) {
        $("#container").BlocksIt({});
        $("#loading").css({display: 'none'});
        setTimeout("isWaiting = false", 1000);
    }
}