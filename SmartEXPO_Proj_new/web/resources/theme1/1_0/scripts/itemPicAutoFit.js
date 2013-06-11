/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function autoFit(w, h, target) {
    console.log(w + " " + h);
    var ratio = w / h;
    if (ratio >= 1)
        widthGreater(target, w, h);
    else
        heightGreater(target, w, h);
}

function widthGreater(target, frame_width, frame_height) {
    var height = target.height();
    var width = target.width();
    console.log("wid: " + width + " hei: " + height);
    if (width >= frame_width && height >= frame_height) {
        var offset_x = (width - frame_width) / 2;
        var offset_y = (height - frame_height) / 2;
        target.css({
            left: "-" + offset_x + "px",
            top: "-" + offset_y + "px"
        });
    } else if (width >= frame_width || width >= height) {
        var th = frame_height;
        var tw = width / height * th;
        var offset_x = (tw - frame_width) / 2;
        target.css({
            height: th,
            width: "auto !important",
            top: 0,
            left: "-" + offset_x + "px"
        });
    } else if (height >= frame_height || height >= width) {
        var tw = frame_width;
        var th = height / width * tw;
        var offset_y = (th - frame_height) / 2;
        target.css({
            height: "auto !important",
            width: tw,
            top: "-" + offset_y + "px",
            left: 0
        });
    }
}


function heightGreater(target, frame_width, frame_height) {
    var height = target.height();
    var width = target.width();
//    console.log(height+ " " + width);
    if (width >= frame_width && height >= frame_height) {
        var offset_x = (width - frame_width) / 2;
        var offset_y = (height - frame_height) / 2;
        target.css({
            left: "-" + offset_x + "px",
            top: "-" + offset_y + "px"
        });
    } else if (width >= frame_width || width >= height) {
        
        var th = frame_height;
        var tw = width / height * th;
        var offset_x = (tw - frame_width) / 2;
        target.css({
            height: th,
            width: "auto !important",
            top: 0,
            left: "-" + offset_x + "px"
        });


    } else if (height >= frame_height || height >= width) {
        var tw = frame_width;
        var th = height / width * tw;
        var offset_y = (th - frame_height) / 2;
        target.css({
            height: "auto !important",
            width: tw,
            top: "-" + offset_y + "px",
            left: 0
        });
        

    }
}