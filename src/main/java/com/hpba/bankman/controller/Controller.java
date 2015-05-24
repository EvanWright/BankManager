package com.hpba.bankman.controller;

import com.hpba.bankman.view.*;
import com.hpba.bankman.model.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import org.slf4j.*;

/**
 * This class handles all interaction between the
 * {@link com.hpba.bankman.model model} and the
 * {@link com.hpba.bankman.view view}.
 * <p>
 * The heart of the application, everything is controlled by this class,
 * including the database connection handling.
 *
 * @author generalhpba
 */
public final class Controller {

    /**
     * The logger.
     */
    Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    /**
     * The pointer to the actual {@link com.hpba.bankman.model model}.
     */
    Model model;
    /**
     * The pointer to the actual {@link com.hpba.bankman.view view}.
     */
    View view;
    /**
     * State which represents whether the application is currently loading.
     */
    private static boolean isLoading = false;

    /**
     * The parameterless constructor.
     */
    public Controller() {
    }

    /**
     * Constructor with {@link com.hpba.bankman.model model} and
     * {@link com.hpba.bankman.view view} parameters.
     *
     * @param model the current model
     * @param view the current view
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * The starter method of the controller.
     */
    public void start() {
        view.switchToConnect();
        new Thread(rCon).start();
    }

    /**
     * Configures the connection to the database using the configuration file.
     *
     * @param args connection specifying switches
     */
    public void loadConfiguration(String... args) {
        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");

        if (is == null) {
            LOGGER.error("Configuration file error!");
        }

        try {
            prop.load(is);
        } catch (Exception ex) {
            LOGGER.error("Configuration file error!", ex);
        }

        if (args.length > 0) {
            for (String s : args) {
                if (s.equals("-l")) {
                    ConnectionFactory.setConnection(prop.getProperty("URL_l"),
                            prop.getProperty("USER_l"), prop.getProperty("PASS_l"));
                }
                if (s.equals("-s")) {
                    ConnectionFactory.setConnection(prop.getProperty("URL_s"),
                            prop.getProperty("USER_s"), prop.getProperty("PASS_s"));
                }
            }
        } else {
            ConnectionFactory.setConnection(prop.getProperty("URL"),
                    prop.getProperty("USER"), prop.getProperty("PASS"));
        }
    }

    /**
     * Connects to the database.
     */
    Runnable rCon = () -> {
        connectToDatabase(0);
    };

    /**
     * Loads data from the database into the
     * {@link com.hpba.bankman.model model}.
     */
    Runnable rLoad = () -> {
        if (!isLoading) {
            isLoading = true;
            view.getMainPanel().setLoading(true);
            loadModelFromDB(model);
            view.getMainPanel().setLoading(false);
            isLoading = false;
        }
    };

    /**
     * Handles the login process.
     */
    public void aLogin() {
        view.switchToLoading();
        Runnable rLogin = () -> {
            String[] userData = DB_DAO.login(view.getLogin().getLoginUsername(), view.getLogin().getLoginPassword());
            if (userData != null) {
                model.setClient(new Client(userData[0], userData[1]));
                LOGGER.info("User: " + userData[1] + " has logged in");
                switchToMain("main");
            } else {
                view.switchToLogin();
                view.initInfo("Warning", "<html>Invalid username / password!<html>");
            }
        };
        new Thread(rLogin).start();
    }

    /**
     * Registers the new user.
     */
    public void aRegister() {
        view.switchToLoading();
        Runnable rRegister = () -> {
            try {
                String[] userData = view.getRegisterData();
                if (userData[0].trim().equals("") || userData[0].length() < 3) {
                    view.initInfo("Warning", "<html>Selected username is invalid!</html>");
                    view.switchToLogin();
                    view.showRegister();
                } else if (userData[0].length() > 0) {
                    for (char c : userData[0].toCharArray()) {
                        if (Character.isWhitespace(c)) {
                            view.initInfo("Warning", "<html>Usernames can't contain any whitespaces!</html>");
                            view.switchToLogin();
                            view.showRegister();
                        }
                    }
                } else if (userData[1].trim().equals("")) {
                    view.initInfo("Warning", "<html>Selected password is invalid!</html>");
                    view.switchToLogin();
                    view.showRegister();
                }
                if (!DB_DAO.isUserExists(userData[0])) {
                    if (DB_DAO.register(userData[0], userData[1])) {
                        view.initInfo("Success", "<html>User registration successful!</html>");
                        LOGGER.info("User: " + userData[1] + " has successfully registered");
                        view.switchToLogin();
                        view.showRegister();
                    } else {
                        LOGGER.warn("Database error");
                        view.initInfo("Warning", "<html>Database error, please try again.</html>");
                        view.switchToLogin();
                        view.showRegister();
                    }
                } else {
                    view.initInfo("Warning", "<html>This username is already taken!</html>");
                    view.switchToLogin();
                    view.showRegister();
                }
            } catch (Exception e) {
                view.switchToLogin();
            }
        };
        new Thread(rRegister).start();
    }

    /**
     * Retries connection to the database.
     */
    public void aRetry() {
        switchToConnect();
        new Thread(rCon).start();
    }

    /**
     * Switches back to the login screen.
     */
    public void aBackToLogin() {
        view.switchToLogin();
        model.reset();
    }

    /**
     * Updates the currently selected account in the
     * {@link com.hpba.bankman.model model} from the
     * {@link com.hpba.bankman.view view}.
     */
    public void aSelectCurrent() {
        model.setCurrentAccount(view.getSelectedAccount());
        switchToAccount();
    }

    /**
     * Handles the open account process.
     *
     * @return status code of the operation
     */
    public int aOpenAccount() {
        if (view.getMain().getOpenAccount().isEditValid()) {
            ArrayList<String> al = view.getOpenAccountData();
            view.switchToLoading();
            new Thread() {
                @Override
                public void run() {
                    if (!DB_DAO.isAccountExists(al.get(0))) {
                        switch (al.get(1)) {
                            case "Transactional Account":
                                new Thread() {
                                    @Override
                                    public void run() {
                                        if (DB_DAO.insertTransactional(new Transactional(al.get(0), 0), model.getClientId())) {
                                            LOGGER.info("New transactional account with number " + al.get(0) + " has been created");
                                            switchToMain("main");
                                        } else {
                                            switchToMain("main");
                                            LOGGER.warn("Database error");
                                            view.initInfo("Warning", "<html>INSERT ERROR<br>Account insertion into database has failed.</html>");
                                        }
                                    }
                                }.start();
                                break;
                            case "Savings Account":
                                new Thread() {
                                    @Override
                                    public void run() {
                                        if (DB_DAO.insertSavings(new Savings(al.get(0), 0, 2.3), model.getClientId())) {
                                            LOGGER.info("New savings account with number " + al.get(0) + " has been created");
                                            switchToMain("main");
                                        } else {
                                            switchToMain("main");
                                            LOGGER.warn("Database error");
                                            view.initInfo("Warning", "<html>INSERT ERROR<br>Account insertion into database has failed.</html>");
                                        }
                                    }
                                }.start();
                                break;
                            case "Term Deposit":
                                new Thread() {
                                    @Override
                                    public void run() {
                                        if (DB_DAO.insertTerm(new Term(al.get(0), 0, 4.5), model.getClientId())) {
                                            LOGGER.info("New term deposit account with number " + al.get(0) + " has been created");
                                            switchToMain("main");
                                        } else {
                                            switchToMain("main");
                                            LOGGER.warn("Database error");
                                            view.initInfo("Warning", "<html>INSERT ERROR<br>Account insertion into database has failed.</html>");
                                        }
                                    }
                                }.start();
                                break;
                            default:
                                view.initInfo("Error", "Account type not supported");
                        }
                    } else {
                        switchToMain("main");
                        view.initInfo("Warning", "Account with this number already exits!");
                    }
                }
            }.start();
            return 0;
        } else {
            view.showInvalidLabel();
            return 1;
        }
    }

    /**
     * Initializes the open account process.
     */
    public void aOpenAccountDisplay() {
        view.initOpenAccount();
    }

    /**
     * Handles the close account process.
     */
    public void aCloseAccount() {
        view.switchToLoading();
        new Thread() {
            @Override
            public void run() {
                String accountNumber = view.getSelectedAccount();
                if (DB_DAO.closeAccount(model.getAccount(accountNumber))) {
                    LOGGER.info("Account with number " + accountNumber + " has been closed");
                    switchToMain("main");
                } else {
                    switchToMain("main");
                    LOGGER.warn("Database error");
                    view.initInfo("Error", "Database error, operation failed!");
                }
            }
        }.start();
    }

    /**
     * Initializes the close account process.
     */
    public void aCloseAccountDisplay() {
        view.initCloseAccount();
    }

    /**
     * Connects to the database using the
     * {@link ConnectionFactory ConnectionFactory}.
     *
     * @param retries number of failed connections
     */
    void connectToDatabase(int retries) {
        if (ConnectionFactory.getConnection() != null) {
            LOGGER.info("Successfully connected to the database.");
            view.switchToLogin();
        } else {
            if (retries == 3) {
                LOGGER.warn("Couldn't connect to the database.");
                switchToOffline();
            } else {
                retries++;
                connectToDatabase(retries);
            }
        }
    }

    /**
     * Switches to the main display.
     *
     * @param str specifies the sub display
     */
    public void switchToMain(String str) {
        view.switchToMain(str);
        if (str.equals("main")) {
            model.setCurrentAccount(null);
            new Thread(rLoad).start();
            updateMain();
        }
    }

    /**
     * Updates the main display with data from the
     * {@link com.hpba.bankman.model model}.
     */
    void updateMain() {
        ArrayList<String> accs = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Integer> balances = new ArrayList<>();
        for (String str : model.getClient().getAccounts()) {
            accs.add(model.getAccount(str).getNumber());
            types.add(model.getAccount(str).getType());
            balances.add((int) model.getAccount(str).getBalance());
        }
        view.updateAccountList(accs, types, balances);
        view.updateUserInfo(model.getUsername(), model.getAccountCount(), model.getTransactionalCount(),
                model.getSavingsCount(), model.getTermCount(), model.getBalanceSum());
    }

    /**
     * Updates the term display.
     */
    void updateTerm() {
        Term act = (Term) model.getAccount();
        view.updateTermInfo(
                act.isLocked(),
                act.getInterestRate(),
                act.getLockStart(),
                act.getLockEnd());
    }

    /**
     * Updates the savings display.
     */
    void updateSavings() {
        Savings act = (Savings) model.getAccount();
        view.updateSavingsInfo(
                act.getInterestRate(),
                Model.calcInterestPerMonth(act),
                act.getTransferLimit(),
                act.getTransferLimit() - act.getTransfersThisMonth());
    }

    /**
     * Calculates the term deposit account interest.
     */
    public void aTermCalcInterest() {
        Term act = (Term) model.getAccount();
        String months = view.getTermPanel().getLockTimeTF().getText();
        int length = Integer.valueOf(months.replaceAll("[^\\d.]", ""));
        double value = Model.calcInterest(act, length);
        view.interestDialog(value, LocalDate.now().plusMonths(length));
    }

    /**
     * Locks the currently selected term deposit account.
     */
    public void aTermLock() {
        try {
        int length = Integer.valueOf(view.getTermPanel().getLockTimeTF().getText());
        if (length > 0) {
            Term act = (Term) model.getAccount();
            act.lock(length);
            updateTerm();
            DB_DAO.updateTerm(act);
            view.getTermPanel().lockDialog(Model.calcInterest(act, length), act.getBalance(), act.getLockEnd());
        } else {
            view.getTermPanel().lockDenyDialog();
        }
        } catch(NumberFormatException e) {
            view.initInfo("Warning", "<html>Term length must be between 1 - 999!</html>");
        }
    }

    /**
     * Transfers money between two accounts.
     * <p>
     * This is an action method, called from the
     * {@link com.hpba.bankman.view view} as a result of an interaction.
     * <p>
     * The transaction data retrieved from the
     * {@link com.hpba.bankman.view view}:
     * <ul style="list-style-type:none">
     * <li>0 = Account number</li>
     * <li>1 = The desired amount</li>
     * <li>2 = description about the transaction</li>
     * </ul>
     */
    public void aTransfer() {
        view.switchToLoading();
        new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<Object> al = view.getTransactionData();
                    view.closeTransactionDialog();
                    if (DB_DAO.isAccountExists((String) al.get(0))) {
                        if (al.get(1).equals("")) {
                            switchToMain("");
                            view.initInfo("Warning", "<html>Transfer amount field can't be empty!</html>");
                            return;
                        } else if (Double.parseDouble((String) al.get(1)) < 1.0) {
                            switchToMain("");
                            view.initInfo("Warning", "<html>Minimum transfer amount is $1.0!</html>");
                            return;
                        }
                        Account to = DB_DAO.getAccountFully((String) al.get(0));
                        model.setAccount(DB_DAO.getAccountFully(view.getSelectedAccount()));
                        int res = model.transfer(model.getAccount(), to,
                                Double.parseDouble((String) al.get(1)),
                                (String) al.get(2));
                        switch (res) {
                            case 0:
                                if (DB_DAO.updateAccounts(new Account[]{model.getAccount(), to},
                                        new Entry[]{model.getAccount().getLastEntry(), to.getLastEntry()})) {
                                    LOGGER.info("Successful transaction between " + model.getCurrentAccount() + " and " + to.getNumber());
                                    view.initInfo("Success", "<html>Transfer has been completed successfully!</html>");
                                } else {
                                    view.initInfo("Error", "<html>Database error, transfer was unsuccessful!</html>");
                                }
                                break;
                            case 1:
                                view.initInfo("Warning", "<html>Insufficient funds!</html>");
                                break;
                            case 2:
                                view.initInfo("Warning", "<html>Savings account has reached maximum transfer count!"
                                        + "<br>Transfer count reset is at the 1st day of each month.</html>");
                                break;
                            case 3:
                                view.initInfo("Warning", "<html>Term locked!</html>");
                                break;
                            case 4:
                                view.initInfo("Error", "<html>Database error, transfer was unsuccessful!</html>");
                                break;
                            case 5:
                                view.initInfo("Error", "<html>Unknown exception, pls contact administrator!</html>");
                                break;
                            case 6:
                                view.initInfo("Warning", "<html>Can't transfer money to the same account!</html>");
                                break;
                        }
                        if (model.getAccount() instanceof Savings) {
                            updateSavings();
                        }
                        view.updateAccountInfo(model.getCurrentAccount(), Double.valueOf(model.getBalance()));
                        view.updateTransactionalHistory(getHistoryData(model.getCurrentAccount()));
                        switchToMain("");
                    } else {
                        switchToMain("");
                        view.initInfo("Warning", "<html>Given account doesn't exists!</html>");
                    }
                } catch (Exception e) {
                    LOGGER.error("Exception during transfer", e);
                    switchToMain("");
                }
            }
        }.start();
    }

    /**
     * Switches the display to the currently selected account.
     */
    private void switchToAccount() {
        String acc = view.getSelectedAccount();
        if (model.getAccount(acc) != null) {
            view.switchToLoading();
            new Thread() {
                @Override
                public void run() {
                    switch (model.getAccount(acc).getType()) {
                        case "Term Deposit":
                            model.setAccount(DB_DAO.getTerm(model.getAccount(model.getCurrentAccount())));
                            model.updateAccountInfo(DB_DAO.getAccount(model.getNumber())); // in case of change
                            view.switchToMain("term");
                            updateTerm();
                            break;
                        case "Savings Account":
                            model.setAccount(DB_DAO.getSavings(model.getAccount(model.getCurrentAccount())));
                            model.updateAccountInfo(DB_DAO.getAccount(model.getNumber())); // in case of change
                            view.switchToMain("savings");
                            updateSavings();
                            break;
                        case "Transactional Account":
                            model.setAccount(model.getAccount(acc));
                            model.updateAccountInfo(DB_DAO.getAccount(model.getNumber())); // in case of change
                            view.updateTransactionalHistory(getHistoryData(model.getCurrentAccount()));
                            view.switchToMain("transactional");
                            break;
                    }
                    view.updateAccountInfo(model.getCurrentAccount(), Double.valueOf(model.getBalance()));
                }
            }.start();
        }
    }

    /**
     * Switches to the offline display.
     */
    private void switchToOffline() {
        view.switchToOffline();
    }

    /**
     * Switches to the connection display.
     */
    private void switchToConnect() {
        view.switchToConnect();
    }

    /**
     * Initializes the transaction display.
     */
    public void transaction() {
        view.initTransaction();
    }

    /**
     * Retrieves the history of the owner of the provided account number.
     *
     * @param number account number
     * @return history contained in an ArrayList
     */
    private ArrayList<String[]> getHistoryData(String number) {
        ArrayList<String[]> al = new ArrayList<>();
        for (Entry e : DB_DAO.getEntries(number)) {
            al.add(new String[]{e.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                e.getOperation(), e.getParty(), String.valueOf(e.getAmount()), e.getStatement()});
        }
        return al;
    }

    /**
     * Displays the history of the currently selected account.
     */
    public void history() {
        view.switchToLoading();
        new Thread() {
            @Override
            public void run() {
                view.initHistory("Transaction History", getHistoryData(model.getCurrentAccount()));
                switchToMain("");
            }
        }.start();
    }

    /**
     * Loads the {@link com.hpba.bankman.model model} from the database.
     *
     * @param model target model
     */
    void loadModelFromDB(Model model) {
        model.reset();
        ArrayList<Account> al = DB_DAO.getAccounts(model.getClientId());
        for (Account a : al) {
            model.addAccount(a);
            model.getClient().add(a.getNumber());
        }
        updateMain();
    }

    /**
     * Sets the currently active {@link com.hpba.bankman.model model}.
     *
     * @param model model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Sets the currently active {@link com.hpba.bankman.view view}.
     *
     * @param view view
     */
    public void setView(View view) {
        this.view = view;
    }
}
