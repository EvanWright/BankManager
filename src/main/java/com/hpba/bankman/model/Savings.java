package com.hpba.bankman.model;

/**
 * Savings account type.
 * <p>
 * This account type can only make a limited amount of transfers each month. The
 * transfer counter resets the 1st day of each month.
 *
 * @author generalhpba
 */
public class Savings extends InterestBearing {

    /**
     * The quantity of transfers this month.
     */
    private int transfersThisMonth;
    /**
     * Maximum transfers per month.
     */
    private int transferLimit = 3;

    /**
     * Constructor of the Savings account type.
     *
     * @param interestRate the interest rate
     * @param number the account number
     * @param balance the current amount of money in account
     */
    public Savings(String number, double balance, double interestRate) {
        super(number, balance, interestRate);
        setType("Savings Account");
    }

    /**
     * Returns this month's transfer quantity.
     *
     * @return the transfer quantity
     */
    public int getTransfersThisMonth() {
        return transfersThisMonth;
    }

    /**
     * Sets this month's transfer quantity.
     *
     * @param transfersThisMonth the transfer quantity
     */
    public void setTransfersThisMonth(int transfersThisMonth) {
        this.transfersThisMonth = transfersThisMonth;
    }

    /**
     * Returns how many times can this account transfer money each month.
     *
     * @return the transfer limit
     */
    public int getTransferLimit() {
        return transferLimit;
    }

    /**
     * Sets how many times can this account transfer money each month.
     *
     * @param transferLimit the transfer limit
     */
    public void setTransferLimit(int transferLimit) {
        this.transferLimit = transferLimit;
    }

    /**
     * Update account information with the data of the given account.
     *
     * @param acc the account
     */
    public void update(Savings acc) {
        setInterestRate(acc.getInterestRate());
        setTransfersThisMonth(acc.getTransfersThisMonth());
        setTransferLimit(acc.getTransferLimit());
    }

}
