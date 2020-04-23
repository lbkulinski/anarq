function download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    //document.body.removeChild(element);
}

// Start file download.
document.getElementById("save-button").addEventListener("click", function(){
    
	var songQueueRequest = new XMLHttpRequest();
	var songQueuePath = '/current-requests?sessionId=' + getCurrentSessionId() + '&userId=' + getUserId();
	songQueueRequest.open('GET', songQueuePath);
	songQueueRequest.onload = function() {
		
		var text = songQueueRequest.responseText;
		var filename = "anarq-queue.txt";
		download(filename, text);
			
	};
	songQueueRequest.send();
	 
}, false);