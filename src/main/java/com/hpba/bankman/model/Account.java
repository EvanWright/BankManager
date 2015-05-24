package com.hpba.bankman.model;

import java.util.*;

/**
 * Default account type.
 * <p>
 * Base of all other account types.
 * <p>
 * Stores account number, type, balance and history entries.
 *
 * @see Transactional
 * @see Savings
 * @see Term
 *
 * @author generalhpba
 */
public class Account {

    /**
     * The account number.
     */
    private final String number;
    /**
     * The balance of the account.
     */
    private double balance;
    /**
     * The type of the account.
     */
    private String type;
    /**
     * The ArrayList of entries which serves as the account history.
     */
    private final ArrayList<Entry> history = new ArrayList<>();

    /**
     * Constructor for account.
     *
     * @param number the account number
     * @param balance the balance
     */
    public Account(String number, double balance) {
        this.number = number;
        this.balance = balance;
    }

    /**
     * Adds a history entry to the history.
     *
     * @param entry history entry
     */
    public void addHistory(Entry entry) {
        history.add(entry);
    }

    /**
     * Returns the type of the account.
     *
     * @return the account type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the last inserted entry from the entries {@link ArrayList}.
     *
     * @return the last entry
     */
    public Entry getLastEntry() {
        return history.get(history.size() - 1);
    }

    /**
     * Sets the type of the account.
     *
     * @param type the account type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the account number.
     *
     * @return the account number.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     *
     * @param balance the balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}
