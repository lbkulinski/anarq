function updateUserInformation() {

	// TODO: add timeout to end this loop if connection is lost

	loadCurrentUsers();
	loadCurrentSongQueue();

	setTimeout(updateUserInformation, 1000);
}

updateUserInformation();