package com.hpba.bankman.model;

import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.*;

/**
 * Handles business logic operations and stores related data.
 * <p>
 * Stores accounts and the current user.
 *
 * @author generalhpba
 */
public class Model {

    /**
     * The logger.
     */
    static Logger LOGGER = LoggerFactory.getLogger(Model.class);
    /**
     * The currently selected account.
     * <p>
     * Must be set to use the following methods:
     * {@link #getNumber() getNumber},{@link #getAccount() getAccount} and
     * {@link #getBalance() getBalance()}
     */
    private Account acc;
    /**
     * The currently logged in client.
     * <p>
     * Username and user id in this data structure.
     */
    private Client client;
    /**
     * The logged in client.
     * <p>
     * Set when the user logs in.
     */
    private String currentClient = null;
    /**
     * The number of the currently selected account.
     */
    private String currentAccount = null;
    /**
     * Stores the accounts, which can be referenced by their account number.
     * <p>
     * The key is the account number and the value is the account.
     */
    private final HashMap<String, Account> accounts = new HashMap<>();

    /**
     * Transferring x amount of money from account A to account B.
     * <p>
     * Handles all types of accounts. Creates history logs to both A and B
     * accounts.
     *
     * @param from the account number of the sender
     * @param to the account number of the recipient
     * @param amount the money amount of the transfer
     * @param description the description of the transaction
     * @return status the status code of the transaction
     */
    public int transfer(Account from, Account to, double amount, String description) {
        try {
            if (from.getNumber().equals(to.getNumber())) {
                return 6;
            }
            if (from.getBalance() >= amount) {
                if (from instanceof Savings) {
                    if (!checkSavings((Savings) from)) {
                        return 2;
                    }
                } else if (from instanceof Term) {
                    if (!checkTerm((Term) from)) {
                        return 3;
                    }
                }
                exchange(from, to, amount);
                addHistory(from, to, amount, description);
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            LOGGER.error("Exception during transfer", e);
            return 5;
        }
    }

    /**
     * Calculates a month's worth of interest of an
     * {@link InterestBearing interest bearing} account.
     * <p>
     *
     * @param acc the interest bearing account
     * @return the interest
     */
    public static double calcInterestPerMonth(InterestBearing acc) {
        return acc.getBalance() * (acc.getInterestRate() / 100.0);
    }

    /**
     * Calculates a month's worth of interest of an
     * {@link InterestBearing interest bearing} account with given amount.
     * <p>
     * Does not calculate with the balance of the account, but instead uses
     * given money amount.
     *
     * @param acc the interest bearing account
     * @param amount the amount of interest
     * @return the interest
     */
    public static double calcInterestPerMonth(InterestBearing acc, double amount) {
        return amount * (acc.getInterestRate() / 100.0);
    }

    /**
     * Calculates interest for the given number of months of an
     * {@link InterestBearing interest bearing} account.
     *
     * @param acc the interest bearing account
     * @param months the number of months
     * @return the interest
     */
    public static double calcInterest(InterestBearing acc, int months) {
        return (acc.getBalance() * acc.getInterestRate() / 100) * months;
    }

    /**
     * Creates history entries and adds it to the accounts history.
     *
     * @param from the sender account
     * @param to the receiver account
     * @param amount the transfer amount
     * @param description the statement attached to the transfer
     */
    protected void addHistory(Account from, Account to, Double amount, String description) {
        LocalDateTime ldt = LocalDateTime.now();
        Entry eFrom = new Entry(from.getNumber(), to.getNumber(), "Sent", -amount, description, ldt);
        Entry eTo = new Entry(to.getNumber(), from.getNumber(), "Received", amount, description, ldt);
        from.addHistory(eFrom);
        to.addHistory(eTo);
    }

    /**
     * Updates the balance of the given accounts with the corresponding amount.
     * <p>
     * The money amount will be subtracted from the senders balance and added to
     * the recipient's.
     *
     * @param from the sender account
     * @param to the receiver account
     * @param amount the transfer amount
     */
    protected void exchange(Account from, Account to, Double amount) {
        to.setBalance(to.getBalance() + amount);
        from.setBalance(from.getBalance() - amount);
    }

    /**
     * Returns the quantity of the stored accounts.
     *
     * @return the number of accounts
     */
    public int getAccountCount() {
        return accounts.size();
    }

    /**
     * Returns the quantity of the stored transactional accounts.
     *
     * @return the number of accounts
     */
    public int getTransactionalCount() {
        int cnt = 0;
        for (String s : getClient().getAccounts()) {
            if (getAccount(s).getType().equals("Transactional Account")) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Returns the quantity of the stored savings accounts.
     *
     * @return the number of accounts
     */
    public int getSavingsCount() {
        int cnt = 0;
        for (String s : getClient().getAccounts()) {
            if (getAccount(s).getType().equals("Savings Account")) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Returns the quantity of the stored term deposit accounts.
     *
     * @return the number of accounts
     */
    public int getTermCount() {
        int cnt = 0;
        for (String s : getClient().getAccounts()) {
            if (getAccount(s).getType().equals("Term Deposit")) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Returns the sum of all stored accounts balance.
     *
     * @return the sum of all accounts balance
     */
    public double getBalanceSum() {
        double sum = 0;
        for (String s : getClient().getAccounts()) {
            sum += getAccount(s).getBalance();
        }
        return sum;
    }

    /**
     * Returns the current client's username.
     *
     * @return the username
     */
    public String getUsername() {
        return getClient().getUsername();
    }

    /**
     * Checks if the given term deposit account is eligible to initiate
     * transaction.
     * <p>
     * Term deposit account only eligible to initiate transaction if it isn't
     * locked.
     *
     * @param acc the term deposit account
     * @return <code>true</code>it the account is
     * eligible;<code>false</code>otherwise
     */
    public boolean checkTerm(Term acc) {
        if (acc.isLocked()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the given savings deposit account is eligible to initiate
     * transaction.
     * <p>
     * Savings account only eligible to initiate transaction if the number of
     * transfers this month is less than the accounts transfer limit.
     *
     * @param acc the savings account
     * @return <code>true</code>it the account is
     * eligible;<code>false</code>otherwise
     */
    public boolean checkSavings(Savings acc) {
        if (acc.getTransfersThisMonth() >= acc.getTransferLimit()) {
            return false;
        }
        acc.setTransfersThisMonth(acc.getTransfersThisMonth() + 1);
        return true;
    }

    /**
     * Removes all stored accounts from the model.
     */
    public void reset() {
        accounts.clear();
        client.getAccounts().clear();
    }

    /**
     * Returns the account number of the currently set account.
     * <p>
     * {@link #acc acc} has to be set, to call this method properly.
     *
     * @return the account number
     */
    public String getNumber() {
        if (acc != null) {
            return acc.getNumber();
        }
        return null;
    }

    /**
     * Returns the balance of the currently set account.
     * <p>
     * {@link #acc acc} has to be set, to call this method properly.
     *
     * @return the account balance
     */
    public String getBalance() {
        if (acc != null) {
            return String.valueOf(acc.getBalance());
        }
        return null;
    }

    /**
     * Get account which number matches the provided account number.
     *
     * @param number the account number
     * @return the account or null if there were no accounts with this number
     */
    public Account getAccount(String number) {
        return accounts.get(number);
    }

    /**
     * Returns the currently selected account.
     *
     * @return the current account
     */
    public Account getAccount() {
        return acc;
    }

    /**
     * Updates the account info with the information of the provided account.
     * <p>
     * The account stored with the same account number will be updated. Only
     * updates account balance.
     *
     * @param acc the account to update with
     */
    public void updateAccountInfo(Account acc) {
        accounts.get(acc.getNumber()).setBalance(acc.getBalance());
    }

    /**
     * Store the provided account.
     *
     * @param account the account which is to be stored
     */
    public void addAccount(Account account) {
        if (accounts.containsKey(account.getNumber())) {
            return;
        }
        accounts.put(account.getNumber(), account);
    }

    /**
     * Returns the client.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the client's id.
     *
     * @return the client's id
     */
    public String getClientId() {
        return client.getId();
    }

    /**
     * Sets current client with the provided client.
     *
     * @param client the new client
     */
    public void setClient(Client client) {
        this.client = client;
        setCurrentClient(client.getId());
    }

    /**
     * Returns the current client's id.
     *
     * @return the client id
     */
    public String getCurrentClient() {
        return currentClient;
    }

    /**
     * Sets the current client's id.
     *
     * @param currentClient the new client's id
     */
    public void setCurrentClient(String currentClient) {
        this.currentClient = currentClient;
    }

    /**
     * Returns the number of the current account.
     *
     * @return the account number
     */
    public String getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Sets the number of the current account.
     *
     * @param currentAccount the new account number
     */
    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }

    /**
     * Replaces the current account with the provided account.
     *
     * @param acc the new account
     */
    public void setAccount(Account acc) {
        this.acc = acc;
        currentAccount = acc.getNumber();
    }

}
