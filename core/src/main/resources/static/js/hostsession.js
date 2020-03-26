var connectedClientContainer = document.getElementById("connected-clients");
var requestQueueContainer = document.getElementById("current-requests");
var sessionHeader = document.getElementById("session-header");
var quitSession = document.getElementById("quit-session");

sessionHeader.innerHTML = getCurrentHostSessionId();

// Loads all the users currently connected
function loadCurrentUsers() {

	var clientListRequest = new XMLHttpRequest();
	var clientListPath = '/current-connected-users?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	clientListRequest.open('GET', clientListPath);
	clientListRequest.onload = function() {
		
		if(clientListRequest.responseText.length > 3) {
		
			var clientArray = JSON.parse(clientListRequest.responseText);
			renderUsersToHTML(clientArray);
		
		}
			
	};
	clientListRequest.send();

}

// Loads the current list of songs 
function loadCurrentSongQueue() {

	var songQueueRequest = new XMLHttpRequest();
	var songQueuePath = '/current-requests?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	songQueueRequest.open('GET', songQueuePath);
	songQueueRequest.onload = function() {
		
		if(songQueueRequest.responseText.length > 3) {
		
			var songArray = JSON.parse(songQueueRequest.responseText);
			renderSongsToHTML(songArray);
		
		}
			
	};
	songQueueRequest.send();

}

// Delets a song request
function deleteRequest(songId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/delete-request?sessionId=' + getCurrentSessionId() + '&songId=' + songId;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		
	};
	searchRequest.send();
	
}

// Delets a song request
function kickUser(userId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/kick-user?sessionId=' + getCurrentSessionId() + '&userId=' + userId;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		console.log("kicking user " + userId);
		
	};
	searchRequest.send();
	
}

// Takes an array of song objects and renders them to the screen
function renderUsersToHTML(clientArray) {
	
	// Clear container
	connectedClientContainer.innerHTML = "";
	
	connectedClientContainer.insertAdjacentHTML('beforeend', "<ul>");
	
	for (i = 0; i < clientArray.length; i++) {
		
		connectedClientContainer.insertAdjacentHTML('beforeend', generateUserHTML(clientArray[i]));
		
	}
	
	if (clientArray.length == 0) {
		
		connectedClientContainer.insertAdjacentHTML('beforeend', "<h2>No Connected Jammers...</h2>");
		
	}
	
	connectedClientContainer.insertAdjacentHTML('beforeend', "</ul>");
	
}

// Generates a single song's HTML code
function generateUserHTML(client) {
	
	var htmlString = "";
	
	htmlString += "<div class=\"client-div\">";
	htmlString += "<h2>" + client.name + "</h2>";
	htmlString += "<p>" + client.permissionLevel + "</p>";
	if (client.permissionLevel != "DJ") {
		htmlString += "<button type=\"button\" onclick=\"kickUser('" + client.id + "')\">Kick User</button>";
	}
	htmlString += "</div>";
	
	return htmlString;
	
}

// Takes an array of song objects and renders them to the screen
function renderSongsToHTML(songArray) {
	
	// Clear container
	requestQueueContainer.innerHTML = "";
	
	requestQueueContainer.insertAdjacentHTML('beforeend', "<ul>");
	
	for (i = 0; i < songArray.length; i++) {
		
		requestQueueContainer.insertAdjacentHTML('beforeend', generateSongHTML(songArray[i]));
		
	}
	
	requestQueueContainer.insertAdjacentHTML('beforeend', "</ul>");
	
}

// Generates a single song's HTML code
function generateSongHTML(song) {
	
	var htmlString = "";
	
	htmlString += "<div class=\"song-div\">";
	htmlString += "<h1>" + song.name + "</h1>";
	htmlString += "<h2> " + song.album + "</h2>";
	htmlString += "<h3> " + song.artist + "</h3>";
	htmlString += "<p>Requested By: " + song.clientIp + "</p>";
	htmlString += "<p>Score: " + song.votes + "</p>";
	htmlString += "<button type=\"button\" onclick=\"deleteRequest('" + song.id + "')\">Delete Request</button>";
	htmlString += "<button type=\"button\" onclick=\"likeSong('" + song.id + "')\">Like</button>";
	htmlString += "<button type=\"button\" onclick=\"dislikeSong('" + song.id + "')\">Dislike</button>";
	htmlString += "</div>";
	
	return htmlString;
	
}

function likeSong(songId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/like-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
}

function dislikeSong(songId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/dislike-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
}

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/terminate-session?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		if (getIsLoggedIn() != "true") {
			setUsername("");
		}
		setUserId("");
		setCurrentSessionId("");
		setCurrentHostSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});