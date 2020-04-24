var requestQueueContainer = document.getElementById("current-requests");
var sessionHeader = document.getElementById("session-header");
var requestSong = document.getElementById("request-song");
var quitSession = document.getElementById("quit-session");
var connectSpotify = document.getElementById("connect-spotify");
var likedSongs = document.getElementById("liked-songs");

sessionHeader.innerHTML = getCurrentSessionId();

var connectedToSpot = false;

var clientListRequest = new XMLHttpRequest();
var clientListPath = '/connected-to-spotify-client?sessionId=' + getCurrentSessionId() + '&userId=' + getUserId();
clientListRequest.open('GET', clientListPath);
clientListRequest.onload = function() {
	
	if (clientListRequest.responseText == "true") {
		likedSongs.innerHTML = "";
		
		connectedToSpot = true;
		
		var savedTrackRequest = new XMLHttpRequest();
		var savedTracks = '/get-saved-tracks?sessionId=' + getCurrentSessionId() + '&userId=' + getUserId();
		savedTrackRequest.open('GET', savedTracks);
		savedTrackRequest.onload = function() {

			var songArray = JSON.parse(savedTrackRequest.responseText);

			likedSongs.innerHTML = "";
			likedSongs.insertAdjacentHTML('beforeend', "<ul>");
			
			for (i = 0; i < songArray.length; i++) {
				
				var htmlString = "";
	
				htmlString += "<div class=\"song-div\">";
				htmlString += "<h1>" + songArray[i].songName + "</h1>";
				htmlString += "<img src=\"" + songArray[i].albumCover + "\" alt=\"https://cdn3.iconfinder.com/data/icons/music-and-media-player-ui-filled-outline-s94/96/Music_Icon_Pack_-_Filled_Outline_vinyl-512.png\"" + " width=\"200\" height=\"200\"" + "/>";
				htmlString += "<h2> " + songArray[i].albumName + "</h2>";
				htmlString += "<h3> " + songArray[i].artistName + "</h3>";
				//htmlString += "<button type=\"button\" onclick=\"request('" + songArray[i].songId + "')\">Request</button>";
				
				likedSongs.insertAdjacentHTML('beforeend', htmlString);
				
			}
			
			likedSongs.insertAdjacentHTML('beforeend', "</ul>");

		};
		savedTrackRequest.send();
		
		
	}
	
};
clientListRequest.send();




function loadUserInfo() {

	loadCurrentSongQueue();
	getCurrentSongInfo();
	
}

function loadCurrentSongQueue() {
	
	var songQueueRequest = new XMLHttpRequest();
	var songQueuePath = '/current-requests?sessionId=' + getCurrentSessionId() + '&userId=' + getUserId();
	songQueueRequest.open('GET', songQueuePath);
	songQueueRequest.onload = function() {
		
		if(songQueueRequest.responseText.length > 3) {
		
			var songArray = JSON.parse(songQueueRequest.responseText);
			renderSongsToHTML(songArray);
		
		}
			
	};
	songQueueRequest.send();

}

// Loads all the users currently connected
function getCurrentSongInfo() {

	var currentSongRequest = new XMLHttpRequest();
	var path = '/get-current-song?sessionId=' + getCurrentSessionId();
	currentSongRequest.open('GET', path);
	currentSongRequest.onload = function() {
		
		var songInfo = JSON.parse(currentSongRequest.responseText);
		document.getElementById("current-song-name").innerHTML = songInfo.songName;
		document.getElementById("current-song-artist").innerHTML = songInfo.artistName;
		document.getElementById("current-song-album").innerHTML = songInfo.albumName;
		document.getElementById("current-song-cover").innerHTML = "<img src=\"" + songInfo.albumCover + "\" alt=\"https://cdn3.iconfinder.com/data/icons/music-and-media-player-ui-filled-outline-s94/96/Music_Icon_Pack_-_Filled_Outline_vinyl-512.png\"" + " width=\"200\" height=\"200\"" + "/>";
		
	};
	currentSongRequest.send();

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
	htmlString += "<img src=\"" + song.albumCover + "\" alt=\"https://cdn3.iconfinder.com/data/icons/music-and-media-player-ui-filled-outline-s94/96/Music_Icon_Pack_-_Filled_Outline_vinyl-512.png\"" + " width=\"200\" height=\"200\"" + "/>";
	htmlString += "<h2> " + song.album + "</h2>";
	htmlString += "<h3> " + song.artist + "</h3>";
	htmlString += "<p>Requested By: " + song.clientIp + "</p>";
	htmlString += "<p>Score: " + song.votes + "</p>";
	htmlString += "<button type=\"button\" onclick=\"likeSong('" + song.id + "')\">Like</button>";
	htmlString += "<button type=\"button\" onclick=\"dislikeSong('" + song.id + "')\">Dislike</button>";
	if (connectedToSpot) {
		htmlString += "<button type=\"button\" onclick=\"saveSong('" + song.id + "')\">Save Song</button>";
	}
	htmlString += "</div>";
	
	return htmlString;
	
}

function likeSong(songId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/like-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
}

function saveSong(songId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/save-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
}

function request(songId) {
	
	//var searchRequest = new XMLHttpRequest();
	//var searchPath = '/request-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	//console.log("requesting " + songId);
	//searchRequest.open('PUT', searchPath);
	//searchRequest.send();
	
}

function dislikeSong(songId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/dislike-song?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
}

requestSong.addEventListener("click", function() {
    var username = getUsername();
    var cooldownRequest = new XMLHttpRequest();
    var cooldownPath = "/cooldown-over?sessionId=" + getCurrentSessionId();

    cooldownPath += "&username=";

    cooldownPath += username;

    cooldownRequest.open("GET", cooldownPath);

    cooldownRequest.onload = function() {
        var response = cooldownRequest.responseText;

        if ((response == "Cooldown Expired") || (response == "User Not Found")) {
            window.location.href = "/search.html";
        } else {
            var output = "You are currently in a cooldown period. You may request songs again in ";

            output += response;

            output += ".";

            alert(output);
        } //end if
    };

    cooldownRequest.send();
});

connectSpotify.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/connect-spotify-client?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	searchRequest.open('GET', searchPath);
	searchRequest.onload = function() {
		
		// Redirect to Spotify
		window.location.href = searchRequest.responseText;
		
	};
	searchRequest.send();
	
});

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/disconnect?sessionId=' + getCurrentSessionId() + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		if (getIsLoggedIn() == "false") {
			setUsername("");
		}
		setUserId("");
		setCurrentSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});

