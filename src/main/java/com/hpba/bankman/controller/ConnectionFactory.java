package com.hpba.bankman.controller;

import java.sql.*;

/**
 * Handles the connection to the database.
 * <p>
 * Remote connection is to the free web hosting servicer:
 * <a href="http://www.heliohost.org/">Heliohost</a>.
 *
 * @author generalhpba
 */
public class ConnectionFactory {

    /**
     * The actual host's URL. Set with {@link #setRemote(boolean) setRemote}
     * method.
     */
    private static String actDB_URL;
    /**
     * The actual user. Set with {@link #setRemote(boolean) setRemote} method.
     */
    private static String actUSER;
    /**
     * The actual user's password. Set with
     * {@link #setRemote(boolean) setRemote} method.
     */
    private static String actPASS;

    /**
     * Configures the connection.
     *
     * @param url the database's URL address
     * @param user the username
     * @param pass the password
     */
    public static void setConnection(String url, String user, String pass) {
        actDB_URL = url;
        actUSER = user;
        actPASS = pass;
    }

    /**
     * Create connection to the database.
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
     * Calls the createConnection method and returns the connection.
     *
     * @return the connection
     */
    public static Connection getConnection() {
        return createConnection();
    }

}
