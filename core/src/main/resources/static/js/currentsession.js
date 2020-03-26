console.log(getUserId());

function setCurrentHostSessionId(value) {
	
	document.cookie = "anarq-host-session-id=" + value;
	
}

function getCurrentHostSessionId() {

	var name = "anarq-host-session-id=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "not-connected";
	
}

function setCurrentSessionId(value) {
	
	document.cookie = "anarq-connected-session-id=" + value;
	
}

function getCurrentSessionId() {

	var name = "anarq-connected-session-id=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "not-connected";
	
}

function setIsLoggedIn(value) {
	
	document.cookie = "anarq-logged-in=" + value;
	
}

function getIsLoggedIn() {

	var name = "anarq-logged-in=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "false";
	
}

function setUsername(value) {
	
	document.cookie = "anarq-username=" + value;
	
}

function getUsername() {

	var name = "anarq-username=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "no-username";
	
}

function setUserId(value) {
	
	document.cookie = "anarq-user-id=" + value;
	
}

function getUserId() {

	var name = "anarq-user-id=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "no-user-id";
	
}