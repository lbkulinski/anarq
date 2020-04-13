function updateUserInformation() {

	// TODO: add timeout to end this loop if connection is lost

	loadUserInfo();
	auth();

	setTimeout(updateUserInformation, 1000);
}

updateUserInformation();