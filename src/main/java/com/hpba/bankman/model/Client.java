package com.hpba.bankman.model;

import java.util.*;

/**
 * This class holds information about a client's data.
 * <p>
 * Stored information:
 * <ul>
 * <li>the client's id</li>
 * <li>the client's username</li>
 * <li>the account numbers of accounts owned by this client</li>
 * </ul>
 *
 * @author generalhpba
 */
public class Client {

    /**
     * The client's id.
     */
    private String id;
    /**
     * The client's username.
     */
    private String username;
    /**
     * The account numbers of the accounts owned by this client.
     */
    ArrayList<String> accounts = new ArrayList<>();

    /**
     * Constructor with only an id parameter.
     *
     * @param id the id of the client
     */
    public Client(String id) {
        this.id = id;
    }

    /**
     * Constructor with id and username parameters.
     *
     * @param id the id of the client
     * @param username the username of the client
     */
    public Client(String id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Add an account to the client.
     *
     * @param acc the account
     */
    public void add(String acc) {
        accounts.add(acc);
    }

    /**
     * Returns the client's username.
     *
     * @return the client's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the client's username.
     *
     * @param username the client's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the client's id.
     *
     * @return the client's id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the client's id.
     *
     * @param id the client's id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns all account numbers of accounts owned by this client.
     *
     * @return the account numbers
     */
    public ArrayList<String> getAccounts() {
        return accounts;
    }

}
