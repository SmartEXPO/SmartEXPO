
function submit_changed_content(html) {
    var id = getURLParam("id");
    $.post('/SmartEXPO_Proj_new/ContentChange',
            {itemid: id, html: html});
}