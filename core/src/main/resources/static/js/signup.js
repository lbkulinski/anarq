var errorContainer = document.getElementById("error-message");
var signupButton = document.getElementById("signup");
var usernameQuery = document.getElementById("username");
var emailQuery = document.getElementById("email");
var firstNameQuery = document.getElementById("first-name");
var lastNameQuery = document.getElementById("last-name");
var dayQuery = document.getElementById("day");
var monthQuery = document.getElementById("month");
var yearQuery = document.getElementById("year");
var passwordQuery = document.getElementById("password");
var passwordConfirmQuery = document.getElementById("password-confirm");

// When the search button is clicked, send a request for songs on the server side.
signupButton.addEventListener("click", function(){
	
	console.log("Attemping to sign up......");
	
	if (passwordQuery.value == passwordConfirmQuery.value) {
	
		var searchRequest = new XMLHttpRequest();
		var searchPath = '/sign-up?username='
		+ usernameQuery.value 
		+ '&email=' + emailQuery.value
		+ '&firstname=' + firstNameQuery.value
		+ '&lastname=' + lastNameQuery.value
		+ '&day=' + dayQuery.value
		+ '&month=' + monthQuery.value
		+ '&year=' + yearQuery.value
		+ '&password=' + passwordQuery.value;
		searchRequest.open('PUT', searchPath);
		searchRequest.onload = function() {
			
			var response = searchRequest.responseText;
			
			console.log(response);
			
			if (response == "Sign Up Success!") {
				
				window.location.href="/signupsuccess.html";
				
			}
			else {
				
				setError(response);
				
			}
			
			
		};
		searchRequest.send();
	
	}
	else {
	
		
		setError("Error: Passwords do not match!");
		
	}
	
});

function setError(text) {
	
	errorContainer.innerHTML = "";
	errorContainer.insertAdjacentHTML('beforeend', text);
	
}
