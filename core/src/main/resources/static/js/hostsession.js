var sessionHeader = document.getElementById("session-header");
var quitSession = document.getElementById("quit-session");

sessionHeader.innerHTML = "Connect with this ID: " + getCurrentHostSessionId() + "!";

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/terminate-session?sessionId=' + getCurrentHostSessionId();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		setCurrentHostSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});