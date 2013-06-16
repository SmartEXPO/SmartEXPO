/**
 * @author Ben
 *
 * 关于area的定义在MapNavig.js当中，用于保存每一块area的状态
 */
function initMapInteraction() {
    shadow = new createjs.Shadow("#6e6e6e", 3, 3, 10);
    $("#areaname").css('display','none');
    for (var i = 0; i < area.length; i++) {
        area[i].set({id: (i + 1)}); //为每个area设置id，编号从1开始
        area[i].addEventListener("mouseover", function(e) {
            e.target.set({
                shadow: shadow,
            });
            document.getElementById("areaname").innerHTML = "No." + e.target.id + " Area";
            $("#areaname").clearQueue().fadeIn('fast');
        });
        area[i].addEventListener("mouseout", function(e) {
            e.target.set({
                shadow: null,
            });
            $("#areaname").css('display', 'none');
        });
        area[i].addEventListener("click", function(e) {
            //alert(e.target.id);
            window.location.href = "list" + e.target.id;
        });
    }
}
