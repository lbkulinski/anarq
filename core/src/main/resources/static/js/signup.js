var resultsContainer = document.getElementById("error-message");
var signupButton = document.getElementById("signup");
var usernameQuery = document.getElementById("username");
var emailQuery = document.getElementById("email");
var passwordQuery = document.getElementById("password");
var passwordConfirmQuery = document.getElementById("password-confirm");

// When the search button is clicked, send a request for songs on the server side.
signupButton.addEventListener("click", function(){
	
	if (passwordQuery.value == passwordConfirmQuery.value) {
	
		var searchRequest = new XMLHttpRequest();
		var searchPath = '/sign-up?username='
		+ usernameQuery.value 
		+ '&email=' + emailQuery.value
		+ '&password=' + CryptoJS.AES.encrypt(passwordQuery.value);
		searchRequest.open('PUT', searchPath);
		searchRequest.onload = function() {
			
			var songArray = JSON.parse(searchRequest.responseText);
			renderSongsToHTML(songArray);
			
		};
		searchRequest.send();
	
	}
	
});
