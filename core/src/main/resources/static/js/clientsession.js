var requestQueueContainer = document.getElementById("current-requests");
var sessionHeader = document.getElementById("session-header");
var requestSong = document.getElementById("request-song");
var quitSession = document.getElementById("quit-session");

sessionHeader.innerHTML = getCurrentSessionId();

function loadUserInfo() {

	loadCurrentSongQueue();
	
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

