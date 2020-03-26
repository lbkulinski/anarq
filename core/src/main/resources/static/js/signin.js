var errorContainer = document.getElementById("error-message");
var signinButton = document.getElementById("signin");
var usernameQuery = document.getElementById("username");
var passwordQuery = document.getElementById("password");

signinButton.addEventListener("click", function(){
	
	logIn(usernameQuery.value, passwordQuery.value);
	
});

function setError(text) {
	
	errorContainer.innerHTML = "";
	errorContainer.insertAdjacentHTML('beforeend', text);
	
}
