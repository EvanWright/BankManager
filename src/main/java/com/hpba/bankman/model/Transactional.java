package com.hpba.bankman.model;

/**
 * Transactional account type.
 * <p>
 * Used for everyday transactions. Has no limits between transactions. Bears no
 * interest.
 *
 * @author generalhpba
 * @version 1.0
 * @since 1.0
 */
public class Transactional extends Account {

    /**
     * Constructor of transactional account.
     *
     * @param number the account number
     * @param balance the amount of money
     */
    public Transactional(String number, double balance) {
        super(number, balance);
        setType("Transactional Account");
    }
}
