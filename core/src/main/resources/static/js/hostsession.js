var connectedClientContainer = document.getElementById("connected-clients");
var requestQueueContainer = document.getElementById("current-requests");
var sessionHeader = document.getElementById("session-header");
var quitSession = document.getElementById("quit-session");

sessionHeader.innerHTML = getCurrentHostSessionId();

loadCurrentUsers();
loadCurrentSongQueue();

function loadCurrentUsers() {

	var clientListRequest = new XMLHttpRequest();
	var clientListPath = '/current-connected-users?sessionId=' + getCurrentHostSessionId();
	clientListRequest.open('GET', clientListPath);
	clientListRequest.onload = function() {
			
		var clientArray = JSON.parse(clientListRequest.responseText);
		renderUsersToHTML(clientArray);
			
	};
	clientListRequest.send();

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
	
	console.log(client);
	
	htmlString += "<div class=\"client-div\">";
	htmlString += "<h2>" + client.name + "</h2>";
	htmlString += "<p>" + client.permissionLevel + "</p>";
	htmlString += "<button type=\"button\">Kick User</button>";
	htmlString += "</div>";
	
	return htmlString;
	
}

function loadCurrentSongQueue() {

	var songQueueRequest = new XMLHttpRequest();
	var songQueuePath = '/current-requests?sessionId=' + getCurrentHostSessionId();
	songQueueRequest.open('GET', songQueuePath);
	songQueueRequest.onload = function() {
			
		var songArray = JSON.parse(songQueueRequest.responseText);
		renderSongsToHTML(songArray);
			
	};
	songQueueRequest.send();

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
	htmlString += "<p>ID: " + song.id + "</p>";
	htmlString += "<button type=\"button\" onclick=\"deleteRequest('" + song.id + "')\">Delete Request</button>";
	htmlString += "</div>";
	
	return htmlString;
	
}

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/terminate-session?sessionId=' + getCurrentHostSessionId();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		setCurrentHostSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});

// Request a song
function deleteRequest(songId) {
	
	console.log(songId);
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/delete-request?sessionId=' + getCurrentSessionId() + '&songId=' + songId;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		
	};
	searchRequest.send();
	
}