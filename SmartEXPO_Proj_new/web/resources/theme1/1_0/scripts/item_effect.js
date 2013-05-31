/**
 * @author Ben
 */

var isBottomBarDisplayed = false;
var isSearchBarDisplayed = false;
var isCommentBarDisplayed = false;

$(document).ready(function() {
//    $("#frame").click(function() {
//        console.log(document.getElementsByTagName("header")[0].getBoundingClientRect().top);
//    });

    $(window).scroll(function() {
        if (document.getElementsByTagName("header")[0].getBoundingClientRect().top <= -60) {
            $(".content_fixed").css("position", "fixed");
            $(".content_fixed").css("top", "40px");
            if (isBottomBarDisplayed == false) {
                isBottomBarDisplayed = true;
                $("footer").fadeIn('slow');
            }
        } else {
            $(".content_fixed").css("position", "");
            $(".content_fixed").css("top", "");
            if (isBottomBarDisplayed == true) {
                isBottomBarDisplayed = false;
                $("footer").fadeOut('slow');
            }
        }
    });

    $(".func_button").mouseenter(function() {
        if ($(this).data("display") === "on")
            return;
//        if (!$(this).is(":animated"))
        $(this).fadeTo('fast', 1);
    });

    $(".func_button").mouseleave(function() {
        if ($(this).data("display") === "on")
            return;
//        if (!$(this).is(":animated"))
        $(this).fadeTo('fast', 0.8);
    })

    $("#search_img").click(function() {
        $(this).clearQueue();
        hideCommentPane();
        if (isSearchBarDisplayed == false) {
            isSearchBarDisplayed = true;
            $("#search").animate({
                "width": "250px",
            }, 500);
            $("#search").data("display", "on");
            $("#search_box").css('display', 'inline');
            $("#search_box").animate({
                width: '200px',
            }, 500);
            $("#search_box").val("");
        } else {
            hideSearchPane();
        }
    });

    $(".inbox.username").attr("placeholder", "Username");
    $(".inbox.password").attr("placeholder", "Password");
    $(".func_input_box.name").attr("placeholder", "User");
    $(".func_input_box.content").attr("placeholder", "Content");


    function hideSearchPane() {
        isSearchBarDisplayed = false;
        $("#search").animate({
            "width": "40px",
            "height": "40px",
        }, 500);
        $("#search_box").animate({
            width: '0px',
        }, 500, function() {
            $("#search_box").css('display', 'none');
        });
        $("#search_result").css('display', 'none');
        $("#search").data("display", "off");
        $("#search").css({opacity: '0.8'});
    }


    $("#search_box").focus(function() {
        $(this).val("");
        $("#search").animate({
            height: '300px',
        }, 500, function() {
            $("#search_result").css('display', 'block');

        });
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
//                });
            });
            $("#comment").data("display", "on");
            $("#comment_form .content").val("");
            $("#comment_form .name").val("");
        } else {
            hideCommentPane();
        }
    });

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
        $("#comment").css({opacity: '0.8'});
    }


    // $('.inbox').focus(function() {
    // $(this).val("");
    // $(this).css('color', 'black');
    // if ($(this).attr('name') === "password") {
    // $(this).attr('type', 'password');
    // }
    // });

    var edit = false;
    var toEdit;
    $(".edit_description_div").click(function() {
        if (edit == false) {
            toEdit = $(this).next();
            var content = toEdit.html();
            toEdit.html("<textarea class='edit_description_box'>" + content + "</textarea>");
            edit = true;
            $(".edit_description_div").attr("value", "Done");
        } else {
            toEdit.html(toEdit.children().val());
            edit = false;
            $(".edit_description_div").attr("value", "Edit");
        }
    });
});

function popupLogin() {
    $('#overall_shade').fadeTo('fast', 0.4);
    $('#login_panel').fadeIn('fast');
    $('.inbox').val("");
}

function vanishLogin() {
    $('#overall_shade').fadeOut('fast');
    $('#login_panel').fadeOut('fast');
}

function finish(e) {
    $(".func_input_box.content").css({width: "235px"});
    $(".func_input_box.name").attr("placeholder", "User");
    $(".func_input_box.content").attr("placeholder", "Content");

    console.log(e);
}