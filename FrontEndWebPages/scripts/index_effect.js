/**
 * @author Ben
 */
$(document).ready(function(){
    $('#handler').mouseenter(function(){
        $(this).attr("src","./images/handler_hover.png");
    });
    $('#handler').mouseleave(function(){
        $(this).attr("src","./images/handler.png");
    });
    $('#handler').click(function(){
        $(this).animate({
            'opacity':'0',
            'left':'-43px',
        },'fast');
        
        $('#expand_sidebar').animate({
            'left' : '0px',
        },500);
    });
    
    $('#handler_vanish').click(function() {
        $('#expand_sidebar').animate({
            'left' : '-677px',
        }, 500,function(){
            $('#handler').animate({
                'left':'0',
                'opacity':'1'
            }, 500);
        });
    });
});
