var connectButton = document.getElementById("connect");
var hostButton = document.getElementById("host");
var sessionId = document.getElementById("sessionId");
var username = document.getElementById("username");

// When the connect button is clicked, attempt to connect to the session
connectButton.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/connect?sessionId=' + sessionId.value + '&username=' + username.value;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// Check if the connection was successful
		var result = searchRequest.responseText;
		
		if (result == "true") {
		
			setUsername(username.value);
			setCurrentSessionId(sessionId.value);
			
			window.location.href="/session.html";
		
		}
		else {
			
			// TODO: show alert to say session doesn't exist
			
		}
		
	};
	searchRequest.send();
	
});

// When the connect button is clicked, attempt to host a new session
hostButton.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/host-new-session';
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// Check if the connection was successful
		var result = searchRequest.responseText;
		
		console.log(result);
		
		if (result != "true") {
			
			setCurrentHostSessionId(result);
			window.location.href="/sessionhost.html";
		
		}
		else {
			
			// TODO: show alert to say session doesn't exist
			
		}
		
	};
	searchRequest.send();
	
});