var sessionHeader = document.getElementById("session-header");
var quitSession = document.getElementById("quit-session");

sessionHeader.innerHTML = "Connected to Session with ID " + getCurrentSessionId() + "!";

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/disconnect?sessionId=' + getCurrentSessionId() + '&username=' + getUsername();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		setUsername("");
		setCurrentSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});