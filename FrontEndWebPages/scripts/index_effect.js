/**
 * @author Ben
 */
$(document).ready(function() {

    $("#item_chooser").html(pointsgen());
    $("#item_chooser .idle").mouseenter(function() {
        $(this).addClass("hover");
    });
    $("#item_chooser .idle").mouseleave(function() {
        $(this).removeClass("hover");
    });
    $("#item_chooser .idle").click(function() {
        
        t = parseInt($(this).attr("id"));
        t--;
        if (t == -1) t = 0;
        console.log(t);
        //display();
        //setInterval(display, 10000);
    });

    $('#handler').mouseenter(function() {
        $(this).attr("src", "./images/handler_hover.png");
    });
    $('#handler').mouseleave(function() {
        $(this).attr("src", "./images/handler.png");
    });
    $('#handler').click(function() {
        $(this).animate({
            'opacity' : '0',
            'left' : '-43px',
        }, 'fast');

        $('#expand_sidebar').animate({
            'left' : '0px',
        }, 500);
    });

    $('#handler_vanish').click(function() {
        $('#expand_sidebar').animate({
            'left' : '-677px',
        }, 500, function() {
            $('#handler').animate({
                'left' : '0',
                'opacity' : '1'
            }, 500);
        });
    });

    display();
    setInterval(display, 10000);
});

var t = 0;

//时间都是试出来的，具体为什么加起来不等于上面的interval我也不知道。。。
function display() {
    var dis = info[t];
    $("h1.detail_content").html(dis.title);
    $("p.detail_content").html(dis.description);

    $("h1.detail_content").animate({
        'right' : '0',
        'opacity' : '1',
    }, 350);
    $("h1.detail_content").queue(function() {
        $("p.detail_content").animate({
            'right' : '0',
            'opacity' : '1',
        }, 350);
        $("img#display_item").animate({
            'opacity' : '0'
        }, 500, function() {
            $("img#display_item").attr("src", dis.path);
        });
        $("img#display_item").animate({
            'opacity' : '1'
        }, 500);
        $(this).dequeue();
    });
    dechoose();
    choose(t);
    setTimeout(disappear, 9000);

}

function disappear() {
    $("h1.detail_content").animate({
        'right' : '50px',
        'opacity' : '0',
    }, 350);
    $("h1.detail_content").queue(function() {
        $("p.detail_content").animate({
            'right' : '50px',
            'opacity' : '0',
        }, 350);
        $(this).dequeue();
    });
    t++;
    if (t == info.length)
        t = 0;
}

function pointsgen() {
    var str = new Array();
    for (var i = 0; i < info.length; i++) {
        str.push("<div id='" + i + "' class='idle'></div>");
    }
    return str.join("");
}

function dechoose() {
    var kids = $("#item_chooser").children();
    kids.removeClass("chosen");
    kids.addClass("idle");
}

function choose(i) {
    $("#item_chooser #" + i).removeClass("idle").addClass("chosen");
}
