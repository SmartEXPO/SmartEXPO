/**
 * @author Ben
 */

$(document).ready(function() {

    //blocksit define
    $(window).load(function() {
        $('#container').BlocksIt({
            numOfCol : 5,
            offsetX : 8,
            offsetY : 8
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
                numOfCol : col,
                offsetX : 8,
                offsetY : 8
            });
        }
    });
// 
    // $("#container").click(function() {
        // $("#container").append('<div class="grid"><div class="imgholder"><img src="http://www.inwebson.com/demo/blocksit-js/demo2/images/img27.jpg" /></div><strong>Sunset Lake</strong><p>A peaceful sunset view...</p><div class="meta">by j osborn</div></div>'+'<div class="grid"><div class="imgholder"><img src="http://www.inwebson.com/demo/blocksit-js/demo2/images/img26.jpg" /></div><strong>Sunset Lake</strong><p>A peaceful sunset view...</p><div class="meta">by j osborn</div></div>'+'<div class="grid"><div class="imgholder"><img src="http://www.inwebson.com/demo/blocksit-js/demo2/images/img16.jpg" /></div><strong>Sunset Lake</strong><p>A peaceful sunset view...</p><div class="meta">by j osborn</div></div>');
        // $("#container").BlocksIt({});
    // });

});
