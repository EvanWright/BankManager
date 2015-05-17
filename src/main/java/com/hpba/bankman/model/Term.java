package com.hpba.bankman.model;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Term deposit account type.
 * <p>
 * Bears interest over a fixed amount of time. In this time interval the client
 * has no access to the money.
 *
 * @author generalhpba
 */
public class Term extends InterestBearing {

    /**
     * The start of the term.
     */
    private LocalDate lockStart;
    /**
     * The end of the term.
     */
    private LocalDate lockEnd;
    /**
     * The lock state.
     */
    private boolean locked = false;
    /**
     * The amount of immutable money.
     */
    private double lockedAmount = 0;

    /**
     * Constructor for the term deposit account.
     *
     * @param interestRate the interest rate
     * @param number the account number
     * @param balance the amount of money
     */
    public Term(String number, double balance, double interestRate) {
        super(number, balance, interestRate);
        setType("Term Deposit");
    }

    /**
     * Update account information with the data of the given account.
     *
     * @param acc the account
     */
    public void update(Term acc) {
        setLocked(acc.isLocked());
        setLockStart(acc.getLockStart());
        setLockEnd(acc.getLockEnd());
    }

    /**
     * Returns the money stored by this account.
     *
     * @return the amount of money
     */
    @Override
    public double getBalance() {
        if (locked) {
            return lockedAmount + super.getBalance();
        } else {
            return super.getBalance();
        }
    }

    /**
     * Locks the account for given period of time.
     *
     * @param months the lock time in months
     * @return <code>true</code>if the account has been successfully
     * locked;<code>false</code>if the account is already locked
     */
    public boolean lock(int months) {
        if (!locked) {
            lockStart = LocalDate.now();
            lockEnd = lockStart.plusMonths(months);
            locked = true;
            lockedAmount = getBalance();
            setBalance(0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Unlocks the locked amount.
     * <p>
     * This process also pays the interest.
     */
    public void unlock() {
        addInterest();
        lockStart = lockEnd = null;
        locked = false;
    }

    /**
     * Returns whether this account is locked or not.
     *
     * @return the lock state
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the lock state.
     *
     * @param isLocked the lock state
     */
    public void setLocked(boolean isLocked) {
        this.locked = isLocked;
    }

    /**
     * Add the accumulated interest to the balance of the account.
     */
    private void addInterest() {
        double interest = Model.calcInterestPerMonth(this) * ChronoUnit.MONTHS.between(lockStart, lockEnd);
        setBalance(super.getBalance() + lockedAmount + interest);
        lockedAmount = 0;
    }

    /**
     * Returns the start of the term.
     *
     * @return the start of the term
     */
    public LocalDate getLockStart() {
        return lockStart;
    }

    /**
     * Sets the start of the term.
     *
     * @param lockStart the start of the term
     */
    public void setLockStart(LocalDate lockStart) {
        this.lockStart = lockStart;
    }

    /**
     * Returns the end of the term.
     *
     * @return the end of the term
     */
    public LocalDate getLockEnd() {
        return lockEnd;
    }

    /**
     * Sets the end of the term.
     *
     * @param lockEnd the end of the term
     */
    public void setLockEnd(LocalDate lockEnd) {
        this.lockEnd = lockEnd;
    }

}
