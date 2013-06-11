$(document).ready(function() {

    $('.popup_login').magnificPopup({
        type: 'inline',
        fixedContentPos: false,
        fixedBgPos: true,
//        overflowY: 'auto',
        closeBtnInside: true,
        preloader: false,
        midClick: true,
        removalDelay: 300,
        mainClass: 'my-mfp-slide-bottom'
    });

    $(".inbox.username").attr("placeholder", "Username");
    $(".inbox.password").attr("placeholder", "Password");
});

function popupLogin() {
    $('#overall_shade').fadeTo('fast', 0.4);
    $('#login_panel').fadeIn('fast');
    $('.inbox').val("");
}

function vanishLogin() {
    $('#overall_shade').fadeOut('fast');
    $('#login_panel').fadeOut('fast');
    $(".inbox.username").attr("placeholder", "Username");
    $(".inbox.password").attr("placeholder", "Password");
}
