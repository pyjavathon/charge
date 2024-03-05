var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msg").html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/public', function (message) {
			console.log("hey!!!!"+message);
            showMessage("받은 메시지: " + message.body); //서버에 메시지 전달 후 리턴받는 메시지
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    let message = $("#chat").val();
    console.log(typeof message);
    showMessage("보낸 메시지: " + message);
	console.log("메세지입니다:"+message);
    stompClient.send("/app/sendMessage", {}, JSON.stringify(message)); //서버에 보낼 메시지
    console.log("성공 여기까지 후후후");
}

function showMessage(message) {
    $("#communicate").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $(".form-inline").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $("#chat").keypress(function(e){
                if(e.keyCode == 13 && $(this).val().length){
					sendMessage();
                    var _val = $(this).val();
                    var _class = $(this).attr("class");
                    
                    $(this).val('');
                    var _tar = $(".chat_wrap .inner").append('<div class="item '+_class+'"><div class="box"><p class="msg">'+_val+'</p><span class="time">'+currentTime()+'</span></div></div>');

                    var lastItem = $(".chat_wrap .inner").find(".item:last");
                    setTimeout(function(){
                        lastItem.addClass("on");
                    },10);

                    var position = lastItem.position().top + $(".chat_wrap .inner").scrollTop();
                    console.log(position);

                    $(".chat_wrap .inner").stop().animate({scrollTop:position},500);
                }
            });
});


$(document).ready(function(){
	$("#bot").click(function(){
		$.ajax({
			url : "chat.html",
			success : function(result){
				$("#message1").html(result);
			}
		});
	});
});


        
        var currentTime = function(){
            var date = new Date();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var apm = hh >12 ? "오후":"오전";
            var ct = apm + " "+hh+":"+mm+"";
            return ct;
        }