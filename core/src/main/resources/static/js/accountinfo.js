function logIn(username, password) {
	
	var searchRequest = new XMLHttpRequest();
		var searchPath = '/log-in?username='
		+ usernameQuery.value
		+ '&password=' + passwordQuery.value;
		searchRequest.open('PUT', searchPath);
		searchRequest.onload = function() {
			
			var response = searchRequest.responseText;
			
			console.log(response);
			
			if (response != "Login Failed." && getIsLoggedIn() == "false") {
				
				setIsLoggedIn(response);
				setUsername(username);
				setCurrentSessionId("");
				setUserId("");
				setCurrentHostSessionId("");
				
				window.location.href="/connect.html";
				
			}
			else {
				
				setError("The combination of Username and Password was not found.");
				
			}
			
			
		};
		searchRequest.send();
	
}

function logOut() {
	
	setIsLoggedIn("false");
	setUsername("");
	setCurrentSessionId("");
	setUserId("");
	setCurrentHostSessionId("");
	
	window.location.href="/connect.html";
	
}