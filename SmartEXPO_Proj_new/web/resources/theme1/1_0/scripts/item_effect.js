/**
 * @author Ben
 */

var isBottomBarDisplayed = false;
var isSearchBarDisplayed = 0;
var isCommentBarDisplayed = false;

$(document).ready(function() {
    $('#users_comment').perfectScrollbar();
    $('#description').perfectScrollbar();
    regEditBtn();


    $('.popup_link').magnificPopup({
        type: 'image'
    });
    initInstantComment();
//
//    $(window).scroll(function(e) {
//        if (isCommentBarDisplayed) {
//            e.preventDefault();
//            //console.log("Scroll");
//        }
//        if (document.getElementsByTagName("header")[0].getBoundingClientRect().top <= -60) {
//            $(".content_fixed").css("position", "fixed");
//            $(".content_fixed").css("top", "40px");
//            if (isBottomBarDisplayed == false) {
//                isBottomBarDisplayed = true;
//                $("footer").fadeIn('slow');
//            }
//        } else {
//            $(".content_fixed").css("position", "");
//            $(".content_fixed").css("top", "");
//            if (isBottomBarDisplayed == true) {
//                isBottomBarDisplayed = false;
//                $("footer").fadeOut('slow');
//            }
//        }
//    });

    function hideSearchPane() {
        isSearchBarDisplayed = 0;
        $("#search").animate({
            "width": "40px",
            "height": "40px",
        }, 500);
        $(".func_input_box.search").animate({
            width: '0px',
        }, 500, function() {
            $(".func_input_box.search").css('display', 'none');
        });
        $("#search_result").css('display', 'none');
        $("#search").data("display", "off");
        $("#search").css({
            opacity: '0.8'
        });
    }

    function hideCommentPane() {
        isCommentBarDisplayed = false;
        $("#comment_input").animate({
            width: "0px",
        }, 500, function() {
            $("#comment_input").css("display", "none");
        });

        $("#comment_form .content").animate({
            width: "0px",
        }, 360, function() {
            $("#comment_form .name").animate({
                width: "0px",
            }, 150);
        });

        $("#users_comment").css("display", "none");

        $("#comment").animate({
            "width": "40px",
            'height': '40px',
        }, 500);
        $("#comment").data("display", "off");
        $("#comment").css({
            opacity: '0.8'
        });
    }

    $(".func_button").mouseenter(function() {
        if ($(this).data("display") === "on")
            return;
//        if (!$(this).is(":animated"))
        $(this).fadeTo('fast', 1);
    });


    $(".func_button").mouseleave(function() {
        $(this).clearQueue();
        if ($(this).data("display") === "on")
            return;
//        if (!$(this).is(":animated"))
        $(this).fadeTo('fast', 0.8);
    });


    $("#search_img").click(function() {
        $(this).clearQueue();
        hideCommentPane();
        if (isSearchBarDisplayed == 0) {
            isSearchBarDisplayed = 1;
            $("#search").animate({
                "width": "250px",
            }, 500);
            $("#search").data("display", "on");
            $(".func_input_box.search").css('display', 'inline');
            $(".func_input_box.search").animate({
                width: '200px',
            }, 500);
            $(".func_input_box.search").val("");
        } else {
            hideSearchPane();
        }
    });

    $(".func_input_box.search").focus(function() {
        $(this).val("");

        if (isSearchBarDisplayed == 2)
            return;
        isSearchBarDisplayed = 2;
//        $("#search").animate({
//            height: '300px',
//        }, 500, function() {
//            $("#search_result").css('display', 'block');
//        });
    });


    $("#comment_img").click(function() {
        $(this).clearQueue();
        hideSearchPane();
        if (isCommentBarDisplayed == false) {
            isCommentBarDisplayed = true;
            $("#comment_input").css("display", "inline-block");
            $("#comment_input").animate({
                width: "360px",
            }, 500);
            $("#comment_form .name").animate({
                width: "100px",
            }, 150, function() {
                $("#comment_form .content").animate({
                    width: "235px",
                }, 320);
            });
            $("#comment").animate({
                "width": "400px",
                height: "400px",
            }, 500, function() {
//                $("#comment").animate({
//                    "height": "400px",
//                }, 500, function() {
                $("#users_comment").css("display", "block");
//                console.log($("#all_comments").height()-340);
                $("#users_comment").scrollTop($("#all_comments").height() - 340);
                $("#users_comment").perfectScrollbar('update');
//                });
            });
            $("#comment").data("display", "on");
            $("#comment_form .content").val("");
            $("#comment_form .name").val(trim($("#login_name").html()));
        } else {
            hideCommentPane();
        }
    });

    $(".func_input_box.name").attr("placeholder", "Username");
    $(".func_input_box.content").attr("placeholder", "Content");
    $(".func_input_box.search").attr("placeholder", "Search");

//    $("#frame_image").load(function() {
//        autoFit(480, 485, $("#frame_image"));
//    });
    $("#frame_image").autofit({height: 485, width: 480, cut: false});
});


function finish(e) {
    if (isCommentBarDisplayed)
        $(".func_input_box.content").css({width: "235px"});
    $(".func_input_box.name").attr("placeholder", "Username");
    $(".func_input_box.content").attr("placeholder", "Content");
    $("#comment_form .name").val(trim($("#login_name").html()));


    $("#users_comment").scrollTop($("#all_comments").height() - 340);
    $("#users_comment").perfectScrollbar('update');

    regLoginPanel();
    regEditBtn();
}

function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

function handleSearch(e) {
    console.log(e);
    if (e.status === "success") {
        $("#search_result").css('display', 'block');

        $("#search_result").clearQueue();
        var h = 250;
        if ($(".search_result_table").height() < 250)
            h = $(".search_result_table").height();
        if (h < 10) {
            $(".search_result_table").html("No Result");
            h = 24;
        }


        $("#search").animate({
            height: h + 50 + 'px',
        }, 'fast', function() {
            $("#search_result").height(h);
        });

    }
}