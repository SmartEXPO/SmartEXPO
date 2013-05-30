/**
 * @author Ben
 */
var area;
$(document).ready(function() {

    area = $("#area").html();
    if (!(parseInt(area) < 6)){
        area = "1";
        $("#area").html("1");
    }
        
    $('#footer').css({
        'bottom': -15
    }).delay(1000).animate({
        'bottom': 0
    }, 800);

    //blocksit define
    $(window).load(function() {
        $('#container').BlocksIt({
            numOfCol: 5,
            offsetX: 8,
            offsetY: 8
        });
    });

    //window resize
    var currentWidth = 1100;
    $(window).resize(function() {
        var winWidth = $(window).width();
        var conWidth;
        if (winWidth < 660) {
            conWidth = 440;
            col = 2
        } else if (winWidth < 880) {
            conWidth = 660;
            col = 3
        } else if (winWidth < 1100) {
            conWidth = 880;
            col = 4;
        } else {
            conWidth = 1100;
            col = 5;
        }

        if (conWidth != currentWidth) {
            currentWidth = conWidth;
            $('#container').width(conWidth);
            $('#container').BlocksIt({
                numOfCol: col,
                offsetX: 8,
                offsetY: 8
            });
        }
    });

    $("#loading").css({
        'display': 'none'
    });
    $(window).scroll(function() {
        if (!isWaiting)
            if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
                isWaiting = true;
                $("#loading").css({'display': 'block'});
                list_conn();
            }
    });

    //list_conn();在程序开始的时候加载
});

var isWaiting = false;