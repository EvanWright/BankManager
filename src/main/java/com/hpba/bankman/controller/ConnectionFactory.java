package com.hpba.bankman.controller;

import java.sql.*;

/**
 * Handles the connection to the database.
 * <p>
 * Remote connection is to the free web hosting servicer:
 * <a href="http://www.heliohost.org/">Heliohost</a>.
 *
 * @author generalhpba
 * @version 1.0
 * @since 1.0
 */
public class ConnectionFactory {

    /**
     * The actual host's URL.
     * <p>
     * Set with
     * {@link #setConnection(java.lang.String, java.lang.String, java.lang.String) setConnection}
     * setRemote} method.
     */
    private static String actDB_URL;
    /**
     * The actual user.
     * <p>
     * Set with
     * {@link #setConnection(java.lang.String, java.lang.String, java.lang.String) setConnection}
     * method.
     */
    private static String actUSER;
    /**
     * The actual user's password.
     * <p>
     * Set with
     * {@link #setConnection(java.lang.String, java.lang.String, java.lang.String) setConnection}
     * method.
     */
    private static String actPASS;

    /**
     * Configures the connection to the database.
     *
     * @param url the database's address
     * @param user the username
     * @param pass the password
     */
    public static void setConnection(String url, String user, String pass) {
        actDB_URL = url;
        actUSER = user;
        actPASS = pass;
    }

    /**
     * Creates a connection to the database.
     *
     * @return the created connection
     */
    private static Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(actDB_URL, actUSER, actPASS);
        } catch (SQLException e) {
            // Connection error
            if (e.getErrorCode() == 0) {
                return null;
            }
        }
        return connection;
    }

    /**
     * Calls the {@link #createConnection() createConnection} method and returns
     * the {@link Connection Connection}.
     *
     * @return the connection
     */
    public static Connection getConnection() {
        return createConnection();
    }

}
