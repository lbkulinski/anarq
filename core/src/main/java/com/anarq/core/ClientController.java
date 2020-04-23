package com.anarq.core;

import com.anarq.database.*;
import com.anarq.songrequests.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.time.Instant;
import java.time.Duration;

@RestController
public class ClientController {
	
	@PutMapping("/connect")
	public ConnectedClient attemptToConnectToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="username", defaultValue="no_username")String username,
	@RequestParam(value="isAccount", defaultValue="false")String isAccount) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: "
				+ sessionId + " Username: " + username + "...");
			return null;
		}
		
		if (NaughtyWords.isANaughtyWord(username)) {
			
			return null;
			
		}
		
		ConnectedClient newConnectedClient = new ConnectedClient(username, isAccount.equals("false"), Permission.JAMMER);
		session.addClient(newConnectedClient);
		
		// Redirect
		return newConnectedClient;
		
	}
	
	@PutMapping("/disconnect")
	public boolean attemptToCloseConnectionToSession(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Request for non-existant session...\n ID: "
				+ sessionId + " UserId: " + userId + "...");
			return false;
		}
		
		// TODO: Remove client from Session
		session.removeClient(userId);
		
		// Redirect
		return true;
		
	}
	
	@GetMapping("/authenticate")
	public boolean isSessionStillActive(
	@RequestParam(value="sessionId", defaultValue="default_session_id")String sessionId,
	@RequestParam(value="userId", defaultValue="no_user_id")String userId) {
		
		// Attempt to obtain the given session based on the sessionId provided
		Session session = CoreApplication.getSessionForSessionId(sessionId);
		if (session == null) {
			System.err.println("Error: Auth Failed.");
			return false;
		}
		// Check if that session has that user
		if (!session.hasUserForId(userId)) {
			return false;
		}			
		// Redirect
		return true;
		
	}

    @GetMapping("/cooldown-over")
    public String cooldownOver(@RequestParam(value="sessionId", defaultValue="default_session_id") String sessionId,
                               @RequestParam(value="username", defaultValue="default_username") String username) {
        Session session = CoreApplication.getSessionForSessionId(sessionId);

        if (session == null) {
            System.err.println("Error: Auth Failed.");

            return "Failure";
        } else {
            Map<ConnectedClient, Long> cooldownClients = session.getCooldownClients();
            String clientUsername;
            ConnectedClient foundClient = null;

            for (ConnectedClient client : cooldownClients.keySet()) {
                clientUsername = client.getName();

                if (clientUsername.equalsIgnoreCase(username)) {
                    foundClient = client;
                    
                    break;
                } //end if
            } //end for

            if (foundClient != null) {
                long currentTime = System.currentTimeMillis();
                long endTime = cooldownClients.get(foundClient);

                if (currentTime < endTime) {
                    Instant currentInstant;
                    Instant endInstant;
                    Duration remainingTime;
                    int remainingMinutes;
                    int remainingSeconds;
                    String format;

                    currentInstant = Instant.ofEpochMilli(currentTime);

                    endInstant = Instant.ofEpochMilli(endTime);

                    remainingTime = Duration.between(currentInstant, endInstant);

                    remainingMinutes = remainingTime.toMinutesPart();

                    remainingSeconds = remainingTime.toSecondsPart();

                    if (remainingMinutes == 0) {
                        format = "%d seconds";

                        return String.format(format, remainingSeconds);
                    } else if (remainingMinutes == 1) {
                        format = "1 minute and %d seconds";

                        return String.format(format, remainingSeconds);
                    } else {
                        format = "%d minutes and %d seconds";

                        return String.format(format, remainingMinutes, remainingSeconds);
                    } //end if
                } //end if

                session.uncooldownClient(username);

                return "Cooldown Expired";
            } //end if

            return "User Not Found";
        } //end if
    } //cooldownUser
}