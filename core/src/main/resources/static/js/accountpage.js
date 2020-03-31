var usernameField = document.getElementById("username");
var firstNameField = document.getElementById("first-name");
var lastNameField = document.getElementById("last-name");
var emailField = document.getElementById("email");
var bioField = document.getElementById("bio");
var profilePicture = document.getElementById("profilePicture");

var changeUsername = document.getElementById("edit-username");
var changePassword = document.getElementById("edit-password");

console.log(getIsLoggedIn());

var getAccountInfo = new XMLHttpRequest();
var path = '/get-account-info?userkey=' + getIsLoggedIn();
getAccountInfo.open('GET', path);
getAccountInfo.onload = function() {
	
	if(getAccountInfo.responseText.length > 3) {
	
		var info = JSON.parse(getAccountInfo.responseText);
		setUsername(info.username);
		usernameField.innerHTML = "Username: " + info.username;
		firstNameField.innerHTML = "First Name: " + info.firstName;
		lastNameField.innerHTML = "Last Name: " + info.lastName;
		emailField.innerHTML = "Email: " + info.email;
		bioField.innerHTML = "Bio: " + info.bio;
		profilePicture.src = 'data:image/png;base64,' + info.imageBytes;
	}
		
};
getAccountInfo.send();