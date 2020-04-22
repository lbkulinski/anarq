var connectedClientContainer = document.getElementById("connected-clients");
var blacklistContainer = document.getElementById("blacklist");
var requestQueueContainer = document.getElementById("current-requests");
var overrideContainer = document.getElementById("override-requests");
var sessionHeader = document.getElementById("session-header");
var quitSession = document.getElementById("quit-session");
var applyChanges = document.getElementById("apply-changes");
var qr = document.getElementById("qr");

sessionHeader.innerHTML = getCurrentHostSessionId();

setPreferences();
loadBlacklist();
loadQRCode();

function loadUserInfo() {

	loadCurrentUsers();
	loadBlacklist();
	loadCurrentSongQueue();
	loadOverrideQueue();
	
}

// Loads all the users currently connected
function loadQRCode() {

	var clientListRequest = new XMLHttpRequest();
	var clientListPath = '/get-qr-code?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	clientListRequest.open('GET', clientListPath);
	clientListRequest.onload = function() {
		
		if(clientListRequest.responseText.length > 3) {
		
			qr.src = 'data:image/png;base64,' + clientListRequest.responseText;
		
		}
			
	};
	clientListRequest.send();

}

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
		else {
			
			connectedClientContainer.innerHTML = "";
			
		}
			
	};
	clientListRequest.send();

}

// Loads the blacklist
function loadBlacklist() {

	var clientListRequest = new XMLHttpRequest();
	var clientListPath = '/blacklisted-users?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	clientListRequest.open('GET', clientListPath);
	clientListRequest.onload = function() {
		
		if(clientListRequest.responseText.length > 3) {
		
			var clientArray = JSON.parse(clientListRequest.responseText);
			renderBlacklistToHTML(clientArray);
		
		}
		else {
			
			blacklistContainer.innerHTML = "";
			
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
		else {
			
			requestQueueContainer.innerHTML = "";
			
		}
			
	};
	songQueueRequest.send();

}

// Loads the current list of songs 
function loadOverrideQueue() {

	var songQueueRequest = new XMLHttpRequest();
	var songQueuePath = '/override-requests?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	songQueueRequest.open('GET', songQueuePath);
	songQueueRequest.onload = function() {
		
		if(songQueueRequest.responseText.length > 3) {
		
			var songArray = JSON.parse(songQueueRequest.responseText);
			renderOverrideSongsToHTML(songArray);
		
		}
		else {
			
			overrideContainer.innerHTML = "";
			
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

// kicks a user
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

// Bans a user
function blacklistUser(userId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/blacklist-user?sessionId=' + getCurrentSessionId() + '&userId=' + userId;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		console.log("blacklisting User " + userId);
		
	};
	searchRequest.send();
	
}

// Bans a user
function unblacklistUser(userId) {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/unblacklist-user?sessionId=' + getCurrentSessionId() + '&userId=' + userId;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		// If it's successful, response goes here
		console.log("unblacklisting User " + userId);
		
	};
	searchRequest.send();
	
}

// Takes an array of song objects and renders them to the screen
function renderBlacklistToHTML(clientArray) {
	
	console.log("blacklist generating");
	
	// Clear container
	blacklistContainer.innerHTML = "";
	
	blacklistContainer.insertAdjacentHTML('beforeend', "<ul>");
	
	for (i = 0; i < clientArray.length; i++) {
		
		blacklistContainer.insertAdjacentHTML('beforeend', generateBlacklistHTML(clientArray[i]));
		
	}
	
	if (clientArray.length == 0) {
		
		blacklistContainer.insertAdjacentHTML('beforeend', "<h2>No Blacklisted Jammers...</h2>");
		
	}
	
	blacklistContainer.insertAdjacentHTML('beforeend', "</ul>");
	
}

// Generates a single song's HTML code
function generateBlacklistHTML(client) {
	
	var htmlString = "";
	
	htmlString += "<div class=\"client-div\">";
	htmlString += "<h2>" + client.name + "</h2>";
	htmlString += "<p>" + client.permissionLevel + "</p>";
	htmlString += "<button type=\"button\" onclick=\"unblacklistUser('" + client.id + "')\">Un-Blacklist User</button>";
	htmlString += "</div>";
	
	return htmlString;
	
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
		htmlString += "<button type=\"button\" onclick=\"blacklistUser('" + client.id + "')\">Ban/Blacklist User</button>";
	}
	htmlString += "</div>";
	
	return htmlString;
	
}

// Takes an array of song objects and renders them to the screen
function renderOverrideSongsToHTML(songArray) {
	
	// Clear container
	overrideContainer.innerHTML = "";
	
	overrideContainer.insertAdjacentHTML('beforeend', "<ul>");
	
	for (i = 0; i < songArray.length; i++) {
		
		overrideContainer.insertAdjacentHTML('beforeend', generateOverrideSongHTML(songArray[i]));
		
	}
	
	overrideContainer.insertAdjacentHTML('beforeend', "</ul>");
	
}

// Generates a single song's HTML code
function generateOverrideSongHTML(song) {
	
	var htmlString = "";
	
	htmlString += "<div class=\"song-div\">";
	htmlString += "<h1>" + song.name + "</h1>";
	htmlString += "<h2> " + song.album + "</h2>";
	htmlString += "<h3> " + song.artist + "</h3>";
	htmlString += "<p>Requested By: " + song.clientIp + "</p>";
	htmlString += "<p>Score: " + song.votes + "</p>";
	htmlString += "<button type=\"button\" onclick=\"approveOverrideRequest('" + song.id + "')\">Override Request</button>";
	htmlString += "<button type=\"button\" onclick=\"deleteOverrideRequest('" + song.id + "')\">Delete</button>";
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

function approveOverrideRequest(songId) {

	var searchRequest = new XMLHttpRequest();
	var searchPath = '/approve-override-request?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
}

function deleteOverrideRequest(songId) {

	var searchRequest = new XMLHttpRequest();
	var searchPath = '/delete-override-request?sessionId=' + getCurrentSessionId() + '&songId=' + songId + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.send();
	
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

var minBPM = document.getElementById("min-bpm");
var maxBPM = document.getElementById("max-bpm");
var dislikeThreshold = document.getElementById("dislike-threshold");


function setPreferences() {
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/get-preferences?sessionId=' + getCurrentHostSessionId()
	+ '&userId=' + getUserId();
	
	searchRequest.open('GET', searchPath);
	searchRequest.onload = function() {
		
		var data = JSON.parse(searchRequest.responseText);
		
		document.getElementById("max-bpm").value = data.maxBPM;
		document.getElementById("min-bpm").value = data.minBPM;
		document.getElementById("dislike-threshold").value = data.dislikeThreshold;
		document.getElementById("explicit").checked = data.explicit;
		document.getElementById("allowRequests").checked = data.requests;
		document.getElementById("isVisible").checked = data.visible;
		document.getElementById("pop").checked = data.pop;
		document.getElementById("rock").checked = data.rock;
		document.getElementById("country").checked = data.country;
		document.getElementById("jazz").checked = data.jazz;
		document.getElementById("rap").checked = data.rap;
		document.getElementById("metal").checked = data.metal;
		document.getElementById("rb").checked = data.rb;
		document.getElementById("hiphop").checked = data.hiphop;
		document.getElementById("electronic").checked = data.electronic;
		document.getElementById("christian").checked = data.christian;
		
	};
	searchRequest.send();
	
}


applyChanges.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/update-preferences?sessionId=' + getCurrentHostSessionId()
	+ '&userId=' + getUserId()
	+ '&minBPM=' + minBPM.value
	+ '&maxBPM=' + maxBPM.value
	+ '&dislikeThreshold=' + dislikeThreshold.value
	+ '&allowExplicit=' + document.getElementById("explicit").checked
	+ '&allowRequests=' + document.getElementById("allowRequests").checked
	+ '&isVisible=' + document.getElementById("isVisible").checked
	+ '&allowPop=' + document.getElementById("pop").checked
	+ '&allowRock=' + document.getElementById("rock").checked
	+ '&allowCountry=' + document.getElementById("country").checked
	+ '&allowJazz=' + document.getElementById("jazz").checked
	+ '&allowRap=' + document.getElementById("rap").checked
	+ '&allowMetal=' + document.getElementById("metal").checked
	+ '&allowRB=' + document.getElementById("rb").checked
	+ '&allowHipHip=' + document.getElementById("hiphop").checked
	+ '&allowElectronic=' + document.getElementById("electronic").checked
	+ '&AllowChristian=' + document.getElementById("christian").checked;
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
	};
	searchRequest.send();
	
});

// When the connect button is clicked, attempt to quit the session
quitSession.addEventListener("click", function(){
	
	var searchRequest = new XMLHttpRequest();
	var searchPath = '/terminate-session?sessionId=' + getCurrentHostSessionId() + '&userId=' + getUserId();
	searchRequest.open('PUT', searchPath);
	searchRequest.onload = function() {
		
		if (getIsLoggedIn() == "false") {
			setUsername("");
		}
		setUserId("");
		setCurrentSessionId("");
		setCurrentHostSessionId("");
		
	};
	searchRequest.send();
	
	window.location.href="/connect.html";
	
});