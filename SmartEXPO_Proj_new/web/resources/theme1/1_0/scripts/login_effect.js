$(document).ready(function() {
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
