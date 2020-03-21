var authenticationRequest = new XMLHttpRequest();
var authenticationPath = '/authenticate-host?sessionId=' + getCurrentHostSessionId();
authenticationRequest.open('GET', authenticationPath);
authenticationRequest.onload = function() {
		
	var isAuthenticated = authenticationRequest.responseText;
	if (isAuthenticated == "false") {
		window.location.href="/error.html";
	}
		
};
authenticationRequest.send();