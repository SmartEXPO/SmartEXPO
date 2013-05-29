var area = new Array();
(function (lib, img, cjs) {

var p; // shortcut to reference prototypes

// stage content:
(lib.mapmain = function(mode,startPosition,loop) {
if (loop == null) { loop = false; } this.initialize(mode,startPosition,loop,{});

    // Square
    this.text = new cjs.Text("Square", "24px American Typewriter");
    this.text.lineHeight = 26;
    this.text.lineWidth = 84;
    this.text.setTransform(519.5,389.1);
    this.text.set({alpha:0});

    this.timeline.addTween(cjs.Tween.get(this.text).to({alpha:0},23).to({alpha:1},25).wait(1));

    // LAYER
    this.shape = new cjs.Shape();
    this.shape.graphics.f("#99CC66").s().p("AmAAdIMBjQIsBFng");
    this.shape.setTransform(395.6,361.1);

    this.shape_1 = new cjs.Shape();
    this.shape_1.graphics.f("#99CC66").s().p("AmhgIIAAAAIKbiTICpgEIhUA9Ip6DqIh2AYg");
    this.shape_1.setTransform(392.3,363.3);

    this.shape_2 = new cjs.Shape();
    this.shape_2.graphics.f("#99CC66").s().p("AnDCcIAAi8IAAgBILuiFICZApIhMBQIrQDUg");
    this.shape_2.setTransform(388.9,364);

    this.shape_3 = new cjs.Shape();
    this.shape_3.graphics.f("#99CC66").s().p("AnlCNIAAjQIAAAAINBh4ICKBWIhFBiIslC/g");
    this.shape_3.setTransform(385.5,365.7);

    this.shape_4 = new cjs.Shape();
    this.shape_4.graphics.f("#99CC66").s().p("AoHB+IAAjkIAAAAIOUhrIB7CFIg9ByIt8Csg");
    this.shape_4.setTransform(382.1,367.5);

    this.shape_5 = new cjs.Shape();
    this.shape_5.graphics.f("#99CC66").s().p("AooBwIAAj4IAAgBIPmhdIBsCyIg2CFIvRCWg");
    this.shape_5.setTransform(378.8,369.2);

    this.shape_6 = new cjs.Shape();
    this.shape_6.graphics.f("#99CC66").s().p("ApKBhIAAkMIAAAAIQ5hQIBcDfIgtCXIwoCBg");
    this.shape_6.setTransform(375.4,370.9);

    this.shape_7 = new cjs.Shape();
    this.shape_7.graphics.f("#99CC66").s().p("ApsBTIAAkgIAAgBISMhCIBNENIgmCpIx9Brg");
    this.shape_7.setTransform(372,372.6);

    this.shape_8 = new cjs.Shape();
    this.shape_8.graphics.f("#99CC66").s().p("AqOBEIAAk0IAAAAITfg2IA+E5IgfC+IzTBWg");
    this.shape_8.setTransform(368.6,374.3);

    this.shape_9 = new cjs.Shape();
    this.shape_9.graphics.f("#99CC66").s().p("AqvA1IAAlIIAAAAIUygoIAuFmIgXDQI0pBBg");
    this.shape_9.setTransform(365.3,376);

    this.shape_10 = new cjs.Shape();
    this.shape_10.graphics.f("#99CC66").s().p("ArRAnIAAlcIAAAAIWFgbIAeGUIgPDiI1/Arg");
    this.shape_10.setTransform(361.9,377.7);

    this.shape_11 = new cjs.Shape();
    this.shape_11.graphics.f("#99CC66").s().p("ArzAYIAAlwIAAAAIXYgNIAPHBIgID1I3UAVg");
    this.shape_11.setTransform(358.5,379.4);

    this.shape_12 = new cjs.Shape();
    this.shape_12.graphics.f("#99CC66").s().p("AsVF8IAAr3IYrAAIAAL3g");
    this.shape_12.setTransform(355.1,381.1);

    this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.shape}]},12).to({state:[{t:this.shape_1}]},1).to({state:[{t:this.shape_2}]},1).to({state:[{t:this.shape_3}]},1).to({state:[{t:this.shape_4}]},1).to({state:[{t:this.shape_5}]},1).to({state:[{t:this.shape_6}]},1).to({state:[{t:this.shape_7}]},1).to({state:[{t:this.shape_8}]},1).to({state:[{t:this.shape_9}]},1).to({state:[{t:this.shape_10}]},1).to({state:[{t:this.shape_11}]},1).to({state:[{t:this.shape_12}]},1).wait(6));
    area[0] = this.shape_12;
    // Layer 5
    this.shape_13 = new cjs.Shape();
    this.shape_13.graphics.f("#6699FF").s().p("AhUAFICphsIAABkIipBrg");
    this.shape_13.setTransform(241.5,352.7);

    this.shape_14 = new cjs.Shape();
    this.shape_14.graphics.f("#6699FF").s().p("AiPg+IEfhjIAADgIkfBjg");
    this.shape_14.setTransform(235.6,358.4);

    this.shape_15 = new cjs.Shape();
    this.shape_15.graphics.f("#6699FF").s().p("AjLiDIGXhXIAAFeImXBXg");
    this.shape_15.setTransform(229.6,364.1);

    this.shape_16 = new cjs.Shape();
    this.shape_16.graphics.f("#6699FF").s().p("AkGjHIINhNIAAHcIoNBNg");
    this.shape_16.setTransform(223.7,369.9);

    this.shape_17 = new cjs.Shape();
    this.shape_17.graphics.f("#6699FF").s().p("AlCkLIKFhDIAAJbIqFBCg");
    this.shape_17.setTransform(217.7,375.6);

    this.shape_18 = new cjs.Shape();
    this.shape_18.graphics.f("#6699FF").s().p("Al9lQIL7g3IAALYIr7A3g");
    this.shape_18.setTransform(211.8,381.4);

    this.shape_19 = new cjs.Shape();
    this.shape_19.graphics.f("#6699FF").s().p("Am5mVINzgsIAANWItzAsg");
    this.shape_19.setTransform(205.8,387.1);

    this.shape_20 = new cjs.Shape();
    this.shape_20.graphics.f("#6699FF").s().p("An0nZIPpghIAAPUIvpAhg");
    this.shape_20.setTransform(199.9,392.9);

    this.shape_21 = new cjs.Shape();
    this.shape_21.graphics.f("#6699FF").s().p("AowodIRhgXIAARSIxhAXg");
    this.shape_21.setTransform(193.9,398.6);

    this.shape_22 = new cjs.Shape();
    this.shape_22.graphics.f("#6699FF").s().p("AprpiITXgLIAATQIzXALg");
    this.shape_22.setTransform(188,404.4);

    this.shape_23 = new cjs.Shape();
    this.shape_23.graphics.f("#6699FF").s().p("AqnKnIAA1OIVOAAIAAVOg");
    this.shape_23.setTransform(182.1,410.1);

    this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.shape_13}]},4).to({state:[{t:this.shape_14}]},1).to({state:[{t:this.shape_15}]},1).to({state:[{t:this.shape_16}]},1).to({state:[{t:this.shape_17}]},1).to({state:[{t:this.shape_18}]},1).to({state:[{t:this.shape_19}]},1).to({state:[{t:this.shape_20}]},1).to({state:[{t:this.shape_21}]},1).to({state:[{t:this.shape_22}]},1).to({state:[{t:this.shape_23}]},1).wait(16));
    area[1] = this.shape_23;
    // Layer 4
    this.shape_24 = new cjs.Shape();
    this.shape_24.graphics.f("#6699FF").s().p("Ah3g/IDviNIAABvIjvEqg");
    this.shape_24.setTransform(288.1,301.6);

    this.shape_25 = new cjs.Shape();
    this.shape_25.graphics.f("#6699FF").s().p("AkbiMII3iAIAAEKIo3EPg");
    this.shape_25.setTransform(304.5,295.2);

    this.shape_26 = new cjs.Shape();
    this.shape_26.graphics.f("#6699FF").s().p("Am+jZIN9hzIAAGjIt9D2g");
    this.shape_26.setTransform(320.8,288.8);

    this.shape_27 = new cjs.Shape();
    this.shape_27.graphics.f("#6699FF").s().p("ApikmITFhmIAAI/IzFDag");
    this.shape_27.setTransform(337.2,282.4);

    this.shape_28 = new cjs.Shape();
    this.shape_28.graphics.f("#6699FF").s().p("AsGlzIYNhZIAALaI4NC/g");
    this.shape_28.setTransform(353.6,276);

    this.shape_29 = new cjs.Shape();
    this.shape_29.graphics.f("#6699FF").s().p("AupnAIdThMIAAN1I9TCkg");
    this.shape_29.setTransform(369.9,269.6);

    this.shape_30 = new cjs.Shape();
    this.shape_30.graphics.f("#6699FF").s().p("AxNoMMAibgBAIAAQRMgibACIg");
    this.shape_30.setTransform(386.3,263.1);

    this.shape_31 = new cjs.Shape();
    this.shape_31.graphics.f("#6699FF").s().p("AzwpZMAnhgAzIAASsMgnhABtg");
    this.shape_31.setTransform(402.6,256.7);

    this.shape_32 = new cjs.Shape();
    this.shape_32.graphics.f("#6699FF").s().p("A2UqmMAspgAmIAAVHMgspABSg");
    this.shape_32.setTransform(419,250.3);

    this.shape_33 = new cjs.Shape();
    this.shape_33.graphics.f("#6699FF").s().p("A44rzMAxxgAZIAAXjMgxxAA2g");
    this.shape_33.setTransform(435.4,243.9);

    this.shape_34 = new cjs.Shape();
    this.shape_34.graphics.f("#6699FF").s().p("A7btAMA23gAMIAAZ+Mg23AAbg");
    this.shape_34.setTransform(451.7,237.5);

    this.shape_35 = new cjs.Shape();
    this.shape_35.graphics.f("#6699FF").s().p("A9/OOIAA8aMA7/AAAIAAcag");
    this.shape_35.setTransform(468.1,231.1);

    this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.shape_24}]},7).to({state:[{t:this.shape_25}]},1).to({state:[{t:this.shape_26}]},1).to({state:[{t:this.shape_27}]},1).to({state:[{t:this.shape_28}]},1).to({state:[{t:this.shape_29}]},1).to({state:[{t:this.shape_30}]},1).to({state:[{t:this.shape_31}]},1).to({state:[{t:this.shape_32}]},1).to({state:[{t:this.shape_33}]},1).to({state:[{t:this.shape_34}]},1).to({state:[{t:this.shape_35}]},1).wait(12));
    area[2] = this.shape_35;
    // Layer 3
    this.shape_36 = new cjs.Shape();
    this.shape_36.graphics.f("#FF9900").s().p("AgYF8IAAr3IAxAAIAAL3g");
    this.shape_36.setTransform(278.6,84.1);

    this.shape_37 = new cjs.Shape();
    this.shape_37.graphics.f("#FF9900").s().p("AhXF8IAAr3ICvAAIAAL3g");
    this.shape_37.setTransform(284.9,84.1);

    this.shape_38 = new cjs.Shape();
    this.shape_38.graphics.f("#FF9900").s().p("AiWF8IAAr3IEtAAIAAL3g");
    this.shape_38.setTransform(291.2,84.1);

    this.shape_39 = new cjs.Shape();
    this.shape_39.graphics.f("#FF9900").s().p("AjVF8IAAr3IGrAAIAAL3g");
    this.shape_39.setTransform(297.5,84.1);

    this.shape_40 = new cjs.Shape();
    this.shape_40.graphics.f("#FF9900").s().p("AkUF8IAAr3IIpAAIAAL3g");
    this.shape_40.setTransform(303.8,84.1);

    this.shape_41 = new cjs.Shape();
    this.shape_41.graphics.f("#FF9900").s().p("AlTF8IAAr3IKnAAIAAL3g");
    this.shape_41.setTransform(310.1,84.1);

    this.shape_42 = new cjs.Shape();
    this.shape_42.graphics.f("#FF9900").s().p("AmSF8IAAr3IMlAAIAAL3g");
    this.shape_42.setTransform(316.4,84.1);

    this.shape_43 = new cjs.Shape();
    this.shape_43.graphics.f("#FF9900").s().p("AnQF8IAAr3IOhAAIAAL3g");
    this.shape_43.setTransform(322.6,84.1);

    this.shape_44 = new cjs.Shape();
    this.shape_44.graphics.f("#FF9900").s().p("AoPF8IAAr3IQfAAIAAL3g");
    this.shape_44.setTransform(328.9,84.1);

    this.shape_45 = new cjs.Shape();
    this.shape_45.graphics.f("#FF9900").s().p("ApOF8IAAr3ISdAAIAAL3g");
    this.shape_45.setTransform(335.2,84.1);

    this.shape_46 = new cjs.Shape();
    this.shape_46.graphics.f("#FF9900").s().p("AqNF8IAAr3IUbAAIAAL3g");
    this.shape_46.setTransform(341.5,84.1);

    this.shape_47 = new cjs.Shape();
    this.shape_47.graphics.f("#FF9900").s().p("ArMF8IAAr3IWZAAIAAL3g");
    this.shape_47.setTransform(347.8,84.1);

    this.shape_48 = new cjs.Shape();
    this.shape_48.graphics.f("#FF9900").s().p("AsLF8IAAr3IYXAAIAAL3g");
    this.shape_48.setTransform(354.1,84.1);

    this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.shape_36}]},9).to({state:[{t:this.shape_37}]},1).to({state:[{t:this.shape_38}]},1).to({state:[{t:this.shape_39}]},1).to({state:[{t:this.shape_40}]},1).to({state:[{t:this.shape_41}]},1).to({state:[{t:this.shape_42}]},1).to({state:[{t:this.shape_43}]},1).to({state:[{t:this.shape_44}]},1).to({state:[{t:this.shape_45}]},1).to({state:[{t:this.shape_46}]},1).to({state:[{t:this.shape_47}]},1).to({state:[{t:this.shape_48}]},1).wait(9));
    area[3] = this.shape_48;
    // Layer 2
    this.shape_49 = new cjs.Shape();
    this.shape_49.graphics.f("#0099CC").s().p("Ay+A3IAAhtMAl9AAAIAABtg");
    this.shape_49.setTransform(128.6,316.6);

    this.shape_50 = new cjs.Shape();
    this.shape_50.graphics.f("#0099CC").s().p("Ay2DKIAAmTMAltAAAIAAGTg");
    this.shape_50.setTransform(129.3,301.9);

    this.shape_51 = new cjs.Shape();
    this.shape_51.graphics.f("#0099CC").s().p("AyvFcIAAq3MAlfAAAIAAK3g");
    this.shape_51.setTransform(130.1,287.3);

    this.shape_52 = new cjs.Shape();
    this.shape_52.graphics.f("#0099CC").s().p("AynHvIAAvcMAlPAAAIAAPcg");
    this.shape_52.setTransform(130.8,272.6);

    this.shape_53 = new cjs.Shape();
    this.shape_53.graphics.f("#0099CC").s().p("AygKBIAA0BMAlBAAAIAAUBg");
    this.shape_53.setTransform(131.6,258);

    this.shape_54 = new cjs.Shape();
    this.shape_54.graphics.f("#0099CC").s().p("AyYMUIAA4mMAkxAAAIAAYmg");
    this.shape_54.setTransform(132.3,243.3);

    this.shape_55 = new cjs.Shape();
    this.shape_55.graphics.f("#0099CC").s().p("AyROmIAA9LMAkjAAAIAAdLg");
    this.shape_55.setTransform(133.1,228.7);

    this.shape_56 = new cjs.Shape();
    this.shape_56.graphics.f("#0099CC").s().p("AyJQ5MAAAghwMAkTAAAMAAAAhwg");
    this.shape_56.setTransform(133.8,214);

    this.shape_57 = new cjs.Shape();
    this.shape_57.graphics.f("#0099CC").s().p("AyCTLMAAAgmVMAkFAAAMAAAAmVg");
    this.shape_57.setTransform(134.6,199.4);

    this.shape_58 = new cjs.Shape();
    this.shape_58.graphics.f("#0099CC").s().p("Ax6VeMAAAgq6MAj1AAAMAAAAq6g");
    this.shape_58.setTransform(135.3,184.7);

    this.shape_59 = new cjs.Shape();
    this.shape_59.graphics.f("#0099CC").s().p("AxzXwMAAAgvfMAjnAAAMAAAAvfg");
    this.shape_59.setTransform(136.1,170.1);

    this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.shape_49}]},9).to({state:[{t:this.shape_50}]},1).to({state:[{t:this.shape_51}]},1).to({state:[{t:this.shape_52}]},1).to({state:[{t:this.shape_53}]},1).to({state:[{t:this.shape_54}]},1).to({state:[{t:this.shape_55}]},1).to({state:[{t:this.shape_56}]},1).to({state:[{t:this.shape_57}]},1).to({state:[{t:this.shape_58}]},1).to({state:[{t:this.shape_59}]},1).wait(11));
    area[4] = this.shape_59;
    // 外边框
    this.shape_60 = new cjs.Shape();
    this.shape_60.graphics.f().s("#000000").ss(5,1,0,3).p("AR00dMAkRAAAMAAAA7XMhesAAAIAA3cItdAAMAAAg2XMArxAAAIAADwIcHAAg");
    this.shape_60.setTransform(349.1,251.1);

    this.shape_61 = new cjs.Shape();
    this.shape_61.graphics.f().s("#000000").ss(5,1,0,3).p("Eg2Egm5MArxAAAIAADwIcHAAIAAOsMAkRAAAMAAAA7XMhesAAAIAA3cItdAAMAAAg2X");
    this.shape_61.setTransform(349.1,251.1);
    this.shape_61.set({alpha:0});

    //this.timeline.addTween(cjs.Tween.get({}).to({state:[]}).to({state:[{t:this.shape_60}]},23).to({state:[{t:this.shape_61}]},1).to({state:[{t:this.shape_61}]},1).to({state:[{t:this.shape_61}]},1).to({state:[{t:this.shape_61}]},1).to({state:[{t:this.shape_61}]},1).to({state:[{t:this.shape_60}]},1).wait(1));
    this.timeline.addTween(cjs.Tween.get(this.shape_61).to({alpha:0}, 23).to({alpha:1}, 5).to({state:[{t:this.shape_60}]},1).wait(1));
}).prototype = p = new cjs.MovieClip();
p.nominalBounds = new cjs.Rectangle(0,0,0,0);

})(lib = lib||{}, images = images||{}, createjs = createjs||{});
var lib, images, createjs;