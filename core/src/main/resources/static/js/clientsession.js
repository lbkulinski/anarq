var requestQueueContainer = document.getElementById("current-requests");
var sessionHeader = document.getElementById("session-header");
var quitSession = document.getElementById("quit-session");

sessionHeader.innerHTML = getCurrentSessionId();

loadCurrentSongQueue();

function loadCurrentSongQueue() {
	
	var songQueueRequest = new XMLHttpRequest();
	var songQueuePath = '/current-requests?sessionId=' + getCurrentSessionId();
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
	htmlString += "</div>";
	
	return htmlString;
	
}

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/disconnect?sessionId=' + getCurrentSessionId() + '&username=' + getUsername();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		setUsername("");
		setCurrentSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});