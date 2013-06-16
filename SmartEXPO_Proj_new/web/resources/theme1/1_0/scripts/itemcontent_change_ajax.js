
function submit_changed_content(html) {
    var id = getURLParam("id");
    $.post('/SmartEXPO_Proj_new/ContentChange',
            {itemid: id, html: html});
}


var edit = false;
var toEdit;
function regEditBtn() {
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