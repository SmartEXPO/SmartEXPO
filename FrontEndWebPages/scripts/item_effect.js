/**
 * @author Ben
 */

var isBottomBarDisplayed = false;

$(document).ready(function() {
    $("#frame").click(function() {
        console.log(document.getElementsByTagName("header")[0].getBoundingClientRect().top);
    });

    $(window).scroll(function() {
        if (document.getElementsByTagName("header")[0].getBoundingClientRect().top <= -60) {
            $("#content_fixed").css("position", "fixed");
            $("#content_fixed").css("top", "40px");
            if (isBottomBarDisplayed == false) {
                isBottomBarDisplayed = true;
                $("footer").fadeIn('slow');
            }
        } else {
            $("#content_fixed").css("position", "relative");
            $("#content_fixed").css("top", "0");
            if (isBottomBarDisplayed == true) {
                isBottomBarDisplayed = false;
                $("footer").fadeOut('slow');
            }

        }
    });
});
