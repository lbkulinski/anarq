package com.anarq.server;

import javax.net.ssl.SSLServerSocket;
import java.sql.Connection;
import java.util.Set;

/**
 * A server used to manage the back-end of the AnarQ application.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 16, 2020
 */
public final class Server {
    /**
     * The ID of this server.
     */
    private int id;

    /**
     * The server socket of this server.
     */
    private SSLServerSocket serverSocket;

    /**
     * The database connection of this server.
     */
    private Connection connection;

    /**
     * The client manager of this server.
     */
    private ClientManager clientManager;

    /**
     * The rules of this server.
     */
    private Rules rules;

    /**
     * The request handlers of this server.
     */
    private Set<RequestHandler> requestHandlers;

    /**
     * The ID of the next server instance to be created.
     */
    private static int nextId;
}