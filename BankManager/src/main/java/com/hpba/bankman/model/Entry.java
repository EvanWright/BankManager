package com.hpba.bankman.model;

import java.time.LocalDateTime;

/**
 * Entry which contains information about an operation.
 * <p>
 * The most usual operation is money transaction.
 *
 * @author generalhpba
 */
public class Entry {

    /**
     * The account number of this entry's owner.
     */
    final private String number;
    /**
     * The other party's account number.
     */
    final private String party;
    /**
     * The executed operation.
     */
    final private String operation;
    /**
     * The entry's statement.
     */
    final private String statement;
    /**
     * The date-time of the entry.
     */
    final private LocalDateTime date;
    /**
     * The money amount.
     */
    final private double amount;

    /**
     * Constructor without the {@link LocalDateTime} parameter.
     *
     * @param number the account number
     * @param party_number the other party's account number
     * @param operation the executed operation
     * @param amount the money amount
     * @param statement the entry's statement
     */
    public Entry(String number, String party_number, String operation, double amount, String statement) {
        this.number = number;
        this.party = party_number;
        this.statement = statement;
        this.operation = operation;
        this.amount = amount;
        date = LocalDateTime.now();
    }

    /**
     * Constructor with the {@link LocalDateTime} parameter.
     *
     * @param number the account number
     * @param party_number the other party's account number
     * @param operation the executed operation
     * @param amount the money amount
     * @param statement the entry's statement
     * @param date the date-time of the entry
     */
    public Entry(String number, String party_number, String operation, double amount, String statement, LocalDateTime date) {
        this.number = number;
        this.party = party_number;
        this.statement = statement;
        this.operation = operation;
        this.amount = amount;
        this.date = date;
    }

    /**
     * To string method.
     *
     * @return the entry's data as string
     */
    @Override
    public String toString() {
        return "Entry{" + "number=" + number + ", operation=" + operation + ", statement=" + statement + ", amount=" + amount + '}';
    }

    /**
     * Returns the account number which belongs to this entry.
     *
     * @return the account number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Returns the executed operation.
     * <p>
     * The most common is the money transaction operation.
     *
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Returns the entry's statement.
     *
     * @return the statement
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Returns the entry's date-time.
     * <p>
     * The entry's date-time is the moment when the operation was executed.
     *
     * @return the entry's date-time
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns the money amount.
     *
     * @return the money amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the other party's account number.
     *
     * @return the other party's account number
     */
    public String getParty() {
        return party;
    }

}
