package com.hpba.bankman.model;

/**
 * Serves as a super class for all account types which are interest specific.
 * <p>
 * The purpose of this class is to store the interest rate of the account.
 * <p>
 * If the interest rate is not specified in the constructor then it's value will
 * be set to 0.0%.
 *
 * @author generalhpba
 * @version 1.0
 * @since 1.0
 */
public abstract class InterestBearing extends Account {

    /**
     * The interest rate of the account.
     */
    private double interestRate;

    /**
     * Constructor with interest rate parameter.
     *
     * @param number the account number
     * @param balance the current balance
     * @param interestRate the interest rate
     */
    public InterestBearing(String number, double balance, double interestRate) {
        super(number, balance);
        this.interestRate = interestRate;
    }

    /**
     * Constructor without interest rate parameter.
     * <p>
     * The interest rate will be set to the default 0.0%.
     *
     * @param number the account number
     * @param balance the current balance
     */
    public InterestBearing(String number, double balance) {
        super(number, balance);
        this.interestRate = 0;
    }

    /**
     * Returns the interest rate.
     *
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate.
     *
     * @param interestRate the interest rate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

}
