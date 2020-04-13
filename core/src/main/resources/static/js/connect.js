var connectButton = document.getElementById("connect");
var userData = document.getElementById("userdata");
var usernameField = document.getElementById("username-field");
var hostButton = document.getElementById("host");
var sessionId = document.getElementById("sessionId");
var username = document.getElementById("username");
var error = document.getElementById("error-message");

if (getIsLoggedIn() != "false") {
	userData.innerHTML = "Welcome, " + getUsername()
	+ "!<button id=\"logout\" type=\"button\" onclick=\"location.href = '/logout.html'\">Log Out</button>"
	 + "<button id=\"account-page\" type=\"button\" onclick=\"location.href = '/accountpage.html'\">My Account</button>";
	usernameField.innerHTML = "";
}

// When the connect button is clicked, attempt to connect to the session
connectButton.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/connect?sessionId=' + sessionId.value;
	if (getIsLoggedIn() != "false") {
		searchPath += '&username=' + getUsername();
	}
	else {
		searchPath += '&username=' + username.value;
	}
	searchPath += '&isAccount=' + getIsLoggedIn();
	
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// Check if the connection was successful
		var result = searchRequest.responseText;
		
		if (result.length > 4) {
		
			var data = JSON.parse(searchRequest.responseText);
		
			if (getIsLoggedIn() == "false") {
				setUsername(username.value);
			}
			setUserId(data.id);
			setCurrentSessionId(sessionId.value);
			
			window.location.href="/session.html";
		
		}
		else {
			
			// TODO: show alert to say session doesn't exist
			error.innerHTML = "There was an error connecting to this session, make sure it exists or you don't have a bad word in your name!";
			
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
			
			if (getIsLoggedIn() == "false") {
				setUsername(data.name);
			}
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