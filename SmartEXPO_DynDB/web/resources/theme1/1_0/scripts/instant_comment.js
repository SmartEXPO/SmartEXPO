/*Interaction*/

var _itemid = 1;
var valid = true;
var lastPopUp = -1;

function configureInstantComment(username, time, content, callback) {
    if (!valid)
        return;

    $("#instant_comment_username").html(username);
    $("#instant_comment_time").html(time);
    $("#instant_comment_content").html(content);

//    console.log(callback);
    if (callback) {
        callback();
    }
}

function showInstantComment() {
    if (!valid)
        return;
    $("#instant_comment").animate({
        opacity: 0.8,
        right: "3%"
    }).delay(2000).animate({
        opacity: 0,
        right: "0%"
    });
//    console.log(getURLParam('id'));
}

/*Function */

function getURLParam(name) {
    if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}

function initInstantComment() {
    _itemid = getURLParam("id");

    if (typeof(EventSource) !== "undefined") {
        var source = new EventSource("/SmartEXPO_Proj_new/InstantComment");
        source.onmessage = processMsg;
        source.onerror = function(event) {
//            console.log(event); //FF会异常
        }
    }
    else {
        valid = false;
    }
}

function processMsg(event) {
    
    if (_itemid !== event.lastEventId) return;
//    console.log(event);
    var msg = null;
    if (event.lastEventId === "#") {
        var tmp = JSON.parse(event.data);
        for (var info in tmp) {
            if (info.id === _itemid) {
                msg = info;
                break;
            }
        }
    } else if (event.lastEventId === _itemid) {
        msg = JSON.parse(event.data);
    }

	if (msg.num <= lastPopUp) return;
    lastPopUp = msg.num;
    
    if (isCommentBarDisplayed) return;
    if (msg != null)
        configureInstantComment(msg.username, msg.time, msg.content, showInstantComment);
}
