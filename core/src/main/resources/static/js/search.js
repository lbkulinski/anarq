var backButton = document.getElementById("back-button");
var resultsContainer = document.getElementById("search-results");
var searchButton = document.getElementById("search");
var searchQuery = document.getElementById("query");


setBackButton();

// Generates a back button depending on if it's a host or a client
function setBackButton() {

	if(getCurrentSessionId() == getCurrentHostSessionId()) {
		backButton.insertAdjacentHTML('beforeend', "<button id=\"back-to-home\" type=\"button\" onclick=\"location.href = '/sessionhost.html'\">Back to Session</button>");
	}
	else {
		backButton.insertAdjacentHTML('beforeend', "<button id=\"back-to-home\" type=\"button\" onclick=\"location.href = '/session.html'\">Back to Session</button>");
	}

}

// When the search button is clicked, send a request for songs on the server side.
searchButton.addEventListener("click", function() {
    var username = getUsername();
    var cooldownRequest = new XMLHttpRequest();
    var cooldownPath = "/cooldown-over?sessionId=" + getCurrentSessionId();

    cooldownPath += "&username=";

    cooldownPath += username;

    cooldownRequest.open("GET", cooldownPath);

    cooldownRequest.onload = function() {
        var response = cooldownRequest.responseText;

        if ((response == "Cooldown Expired") || (response == "User Not Found")) {
            var searchRequest = new XMLHttpRequest();
            var searchPath = '/search?sessionId=' + getCurrentSessionId() + '&query=' + searchQuery.value + '&userId=' + getUserId();

            searchRequest.open('GET', searchPath);

            searchRequest.onload = function() {
                var songArray = JSON.parse(searchRequest.responseText);

                renderSongsToHTML(songArray);
            };

            searchRequest.send();
        } else {
            var output = "You are currently in a cooldown period. You may request songs again in ";

            output += response;

            output += ".";

            alert(output);
        } //end if
    };

    cooldownRequest.send();
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
	
	htmlString += "<div class=\"song-div-req\">";
	htmlString += "<h1>" + song.songName + "</h1>";
	htmlString += "<img src=\"" + song.albumCover + "\" alt=\"https://cdn3.iconfinder.com/data/icons/music-and-media-player-ui-filled-outline-s94/96/Music_Icon_Pack_-_Filled_Outline_vinyl-512.png\"" + " width=\"200\" height=\"200\"" + "></img>";
	htmlString += "<h2>" + song.albumName + "</h2>";
	htmlString += "<h3>by " + song.artistName + "</h3>";
	htmlString += "<p>Duration: " + song.duration + "</p>";
	htmlString += "<p>Explicit: " + song.isExplicit + "</p>";
	htmlString += "<button type=\"button\" onclick=\"requestSong('" + song.songId + "')\">Request song</button>";
	htmlString += "</div>"
	
	return htmlString;
	
}

// Request a song
function requestSong(songId) {
	
	console.log(songId);
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/request-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		
	};
	searchRequest.send();
	
}