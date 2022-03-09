
let ws = null;

var messageCounter = 0;


function wsconnect() {
    ws = new WebSocket('ws://localhost:8080/user');
    ws.onmessage = function(data) {
        display_message(data.data);
    };

    ws.onopen = function (ev) {
        console.log("on open");
    };

    ws.onclose = function (ev) {
        wsdisconnect();
    }
}

function wsdisconnect() {
    if (ws != null) {
        ws.close();
    }
    console.log("Websocket is in disconnected state");
}

// function sendData() {
//     var data = JSON.stringify({
//         'user' : $("#user").val()
//     });
//     ws.send(data);
// }

function display_message(message) {
    if (typeof (parseInt(message)) == 'number') {
        var i = parseInt(sessionStorage.getItem("messageCounter")) + parseInt(message);
        sessionStorage.setItem("messageCounter", i);
        $("#message-box").text(i);
        // $("#message-box").show();
    }
}


// $(function() {
//     $("form").on('submit', function(e) {
//         e.preventDefault();
//     });
//     $("#connect").click(function() {
//         connect();
//     });
//     $("#disconnect").click(function() {
//         disconnect();
//     });
//     $("#send").click(function() {
//         sendData();
//     });
// });

window.onbeforeunload = function(event) {
    console.log("关闭WebSocket连接！");
    wsdisconnect();
};

$(document).ready(function () {
    console.log(sessionStorage.getItem('status'));
    if (sessionStorage.getItem('status') != null && ws==null) {
        wsconnect();
    }

    display_message(0);
});
