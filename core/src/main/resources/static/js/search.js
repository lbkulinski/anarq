var resultsContainer = document.getElementById("search-results");
var searchButton = document.getElementById("search");
var searchQuery = document.getElementById("query");

// When the search button is clicked, send a request for songs on the server side.
searchButton.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/search?sessionId=' + getCurrentSessionId() + '&query=' + searchQuery.value;
	searchRequest.open('GET', searchPath);
	searchRequest.onload = function() {
		
		var songArray = JSON.parse(searchRequest.responseText);
		renderSongsToHTML(songArray);
		
	};
	searchRequest.send();
	
});

// Takes an array of song objects and renders them to the screen
function renderSongsToHTML(songArray) {
	
	// Clear container
	resultsContainer.innerHTML = "";
	
	resultsContainer.insertAdjacentHTML('beforeend', "<ul>");
	
	for (i = 0; i < songArray.length; i++) {
		
		resultsContainer.insertAdjacentHTML('beforeend', generateSongHTML(songArray[i]));
		
	}
	
	resultsContainer.insertAdjacentHTML('beforeend', "</ul>");
	
}

// Generates a single song's HTML code
function generateSongHTML(song) {
	
	var htmlString = "";
	
	htmlString += "<div class=\"song-div\">";
	htmlString += "<h1>" + song.songName + "</h1>";
	htmlString += "<h2>on " + song.albumName + "</h2>";
	htmlString += "<h3>by " + song.artistName + "</h3>";
	htmlString += "<p>Duration: " + song.duration + "</p>";
	htmlString += "<p>Explicit: " + song.isExplicit + "</p>";
	htmlString += "<button type=\"button\" onclick=\"requestSong('" + song.songId + "')\">Request this Song</button>";
	htmlString += "</div>";
	
	return htmlString;
	
}

// Request a song
function requestSong(songId) {
	
	console.log(songId);
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/request-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		
	};
	searchRequest.send();
	
}