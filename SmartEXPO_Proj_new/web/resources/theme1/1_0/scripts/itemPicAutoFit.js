/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function autoFit(w, h, target) {
    console.log(w + " " + h);
    resetPic(target);

    target.attr("style","");

    if (target.width() >= w && target.height() >= h) {
        var offset_x = (target.width() - w) / 2;
        var offset_y = (target.height() - h) / 2;
        target.css({
            left: "-" + offset_x + "px",
            top: "-" + offset_y + "px"
        });
        return;
    }

    fitFrame(w, h, target);

}

function resetPic(target) {
    target.attr("style","");
}

function fitFrame(frame_width, frame_height, target) {
    var height = target.height();
    var width = target.width();
    var th = frame_height;
    var tw = width / height * th;
    if (tw < frame_width) {
        tw = frame_width;
        th = height / width * tw;
    }
    var offset_x = (tw - frame_width) / 2;
    var offset_y = (th - frame_height) / 2;
    offset_x = (offset_x < 0) ? 0 : offset_x;
    offset_y = (offset_y < 0) ? 0 : offset_y;

    target.css({
        left: "-" + offset_x + "px",
        top: "-" + offset_y + "px",
        height: th,
        width: tw,
    });

}
