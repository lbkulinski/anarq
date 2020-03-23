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
		
		if (result != null) {
		
			var data = JSON.parse(searchRequest.responseText);
		
			setUsername(username.value);
			setUserId(data.id);
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
		
		if (result != null) {
			
			var data = JSON.parse(searchRequest.responseText);
			
			setUsername(data.name);
			setUserId(data.id);
			setCurrentSessionId(data.hostSessionId);
			setCurrentHostSessionId(data.hostSessionId);
			
			window.location.href="/sessionhost.html";
		
		}
		else {
			
			// TODO: show alert to say session couldn't be created
			
		}
		
	};
	searchRequest.send();
	
});