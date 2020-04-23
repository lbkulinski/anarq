package com.anarq.core;

import com.anarq.database.*;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * An instance of the AnarQ Application.
 *
 * @version April 23, 2020
 */
@SpringBootApplication(scanBasePackages={"com.anarq"})
public class CoreApplication {
    /**
     * The active sessions of this application.
     */
    private static final ArrayList<Session> activeSessions = new ArrayList<>();

    /**
     * Runs an instance of the AnarQ Application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);

        ConnectToDatabase c = new ConnectToDatabase();

        c.connect();

        EncryptionDecryptionAES a = new EncryptionDecryptionAES();
    } //main

    /**
     * Returns a newly created session.
     *
     * @return a newly created session
     */
    public static Session createNewSession() {
        Session newSession = new Session();

        activeSessions.add(newSession);

        return newSession;
    } //createNewSession

    /**
     * Attempts to terminate the session with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @return {@code true}, if the session with the specified session ID was terminated and {@code false} otherwise
     */
    public static boolean terminateSession(String sessionId) {
        for (int i = 0; i < activeSessions.size(); i++) {
            if (activeSessions.get(i).getSessionId().equals(sessionId)) {
                activeSessions.remove(i);

                return true;
            } //end if
        } //end for

        return false;
    } //terminateSession

    /**
     * Returns the session that is associated with the specified session ID.
     *
     * @param sessionId the session ID to be used in the operation
     * @return the session that is associated with the specified session ID
     */
    public static Session getSessionForSessionId(String sessionId) {
        for (Session activeSession : activeSessions) {
            if (activeSession.getSessionId().equals(sessionId)) {

                return activeSession;

            }
        }

        return null;
    } //getSessionForSessionId
}