var authenticationRequest = new XMLHttpRequest();
var authenticationPath = '/authenticate?sessionId=' + getCurrentSessionId() + '&username=' + getUsername();
authenticationRequest.open('GET', authenticationPath);
authenticationRequest.onload = function() {
		
	var isAuthenticated = authenticationRequest.responseText;
	if (isAuthenticated == "false") {
		window.location.href="/sessionexpired.html";
	}
		
};
authenticationRequest.send();