
function submit_changed_content(html) {
    var id = getURLParam("id");
    $.post('/SmartEXPO_Proj_new/ContentChange',
            {itemid: id, html: html});
}


var edit = false;
var toEdit;
function regEditBtn_old() {
    $(".edit_description_div").click(function() {
        if (edit == false) {
            toEdit = $(this).next();
            var content = toEdit.html();
            toEdit.html("<textarea class='edit_description_box'>" + content + "</textarea>");
            edit = true;
            $(".edit_description_div").attr("value", "Done");

        } else {
            var value = toEdit.children().val();
            toEdit.html(value);
            submit_changed_content(value);
            edit = false;
            $(".edit_description_div").attr("value", "Edit");
        }
    });
    $('#description').perfectScrollbar("update");

}

function regEditBtn() {
    $('.edit_description_link').magnificPopup({
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

    $(".edit_description_link").click(function() {
        var content = $("#information_content").html();
        $("#edit_content_textarea").val(content);
    });
}

function finishEditing() {
    var value = $("#edit_content_textarea").val();
    $("#edit_content_textarea").val("");
    value = value.replace(/&gt;/g,">");
    value = value.replace(/&lt;/g,"<");
    console.log(value);
    submit_changed_content(value);
    $("#information_content").html(value);
    $.magnificPopup.close();
}