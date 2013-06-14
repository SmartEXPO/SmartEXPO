/**
 * jQuery plugin - auto fit the image to the frame
 * MIT licensed
 * Author: Ben
 * 
 */
( function($) {

        var setting = {
            height : $(this).parent().height(),
            width : $(this).parent().width(),
            cut : true,
        };

        $.fn.autofit = function(options) {

            settings = $.extend(setting, options);

            $(this).load(_autoFit);
        }
        
        function _autoFit() {
            var target = $(this);
            var w = setting.width;
            var h = setting.height;
            console.log("pic h: " + target.height() + " w: " +target.width());

            if (setting.cut && target.width() >= w && target.height() >= h) {
                var offset_x = (target.width() - w) / 2;
                var offset_y = (target.height() - h) / 2;
                target.css({
                    left : "-" + offset_x + "px",
                    top : "-" + offset_y + "px"
                });
                return;
            }

            _fitFrame(w, h, target);
        }

        function _fitFrame(frame_width, frame_height, target) {
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
                left : "-" + offset_x + "px",
                top : "-" + offset_y + "px",
                height : th,
                width : tw,
            });
        }

    }(jQuery));
