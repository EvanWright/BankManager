package com.hpba.bankman.controller;

import com.hpba.bankman.model.*;
import java.sql.*;
import java.util.*;
import org.slf4j.*;

/**
 * Data Access Object, which is used to get data from the remote/local database.
 * <p>
 * Methods get the connection from the
 * {@link ConnectionFactory ConnectionFactory} class.
 *
 * @author generalhpba
 * @version 1.0
 * @since 1.0
 */
public class DB_DAO {

    /**
     * The logger.
     */
    static Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    /**
     * Query all {@link com.hpba.bankman.model.Account accounts} which belongs
     * to the given user id.
     *
     * @param user_id a client's id number
     * @return the user's accounts
     */
    public static ArrayList<Account> getAccounts(String user_id) {
        ArrayList<Account> al = new ArrayList<>();
        Account acc = null;

        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM accounts WHERE user_id = '" + user_id + "';";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                switch (rs.getString("type")) {
                    case "Transactional Account":
                        acc = new Transactional(rs.getString("number"), rs.getDouble("balance"));
                        acc.setType("Transactional Account");
                        break;
                    case "Savings Account":
                        acc = new Savings(rs.getString("number"), rs.getDouble("balance"), 0);
                        acc.setType("Savings Account");
                        break;
                    case "Term Deposit":
                        acc = new Term(rs.getString("number"), rs.getDouble("balance"), 0);
                        acc.setType("Term Deposit");
                        break;
                }
                al.add(acc);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return al;
    }

    /**
     * Query all {@link com.hpba.bankman.model.Entry entries} which belongs to
     * the given account number.
     *
     * @param number account number
     * @return history entries
     */
    public static ArrayList<Entry> getEntries(String number) {
        ArrayList<Entry> al = new ArrayList<>();
        Entry entry = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM entries WHERE number = '" + number + "';";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entry = new Entry(rs.getString("number"), rs.getString("party"), rs.getString("operation"),
                        rs.getDouble("amount"), rs.getString("description"), rs.getTimestamp("datetime").toLocalDateTime());
                al.add(entry);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }
        return al;
    }

    /**
     * Query the {@link com.hpba.bankman.model.Account account} and the type
     * specific information too, which account number matches the given one.
     *
     * @param number account number
     * @return the queried account
     */
    public static Account getAccountFully(String number) {
        Account acc = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM accounts WHERE number = '" + number + "';";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                switch (rs.getString("type")) {
                    case "Transactional Account":
                        acc = new Transactional(rs.getString("number"), rs.getDouble("balance"));
                        acc.setType("Transactional Account");
                        break;
                    case "Savings Account":
                        acc = new Savings(rs.getString("number"), rs.getDouble("balance"), 0);
                        acc = getSavings(acc);
                        acc.setType("Savings Account");
                        break;
                    case "Term Deposit":
                        acc = new Term(rs.getString("number"), rs.getDouble("balance"), 0);
                        acc = getTerm(acc);
                        acc.setType("Term Deposit");
                        break;
                }
                return acc;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return acc;
    }

    /**
     * Query the {@link com.hpba.bankman.model.Account account} which account
     * number matches the provided one.
     *
     * @param number account number
     * @return the queried account
     */
    public static Account getAccount(String number) {
        Account acc = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM accounts WHERE number = '" + number + "';";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                switch (rs.getString("type")) {
                    case "Transactional Account":
                        acc = new Transactional(rs.getString("number"), rs.getDouble("balance"));
                        acc.setType("Transactional Account");
                        break;
                    case "Savings Account":
                        acc = new Savings(rs.getString("number"), rs.getDouble("balance"), 0);
                        acc.setType("Savings Account");
                        break;
                    case "Term Deposit":
                        acc = new Term(rs.getString("number"), rs.getDouble("balance"), 0);
                        acc.setType("Term Deposit");
                        break;
                }
                return acc;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return acc;
    }

    /**
     * Query Term Deposit type account.
     *
     * @param acc the base account
     * @return the term account
     */
    public static Term getTerm(Account acc) {
        Term term = new Term(acc.getNumber(), acc.getBalance(), 0);
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query = "SELECT * FROM terms WHERE number='" + acc.getNumber() + "';";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                term.setInterestRate(rs.getDouble("interest"));
                if (rs.getDate("start") != null) {
                    term.setLockStart(rs.getDate("start").toLocalDate());
                }
                if (rs.getDate("end") != null) {
                    term.setLockEnd(rs.getDate("end").toLocalDate());
                }
                term.setLocked(rs.getBoolean("is_locked"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return term;
    }

    /**
     * Query {@link com.hpba.bankman.model.Savings Savings account}.
     *
     * @param acc the base account
     * @return the savings account
     */
    public static Savings getSavings(Account acc) {
        Savings savings = new Savings(acc.getNumber(), acc.getBalance(), 0);
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query = "SELECT * FROM savings WHERE number='" + acc.getNumber() + "';";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                savings.setInterestRate(rs.getDouble("interest"));
                savings.setTransfersThisMonth(rs.getInt("transfers_this_month"));
                savings.setTransferLimit(rs.getInt("transfer_limit"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return savings;
    }

    /**
     * Update {@link com.hpba.bankman.model.Account account} in the database
     * using given {@link com.hpba.bankman.model.Account account}.
     *
     * @param acc the account
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean updateAccount(Account acc) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            if (acc instanceof Transactional) {
                st.execute(getUpdateAccountSQL(acc));
            } else if (acc instanceof Savings) {
                st.execute(getUpdateAccountSQL(acc) + getUpdateSavingsSQL((Savings) acc));
            } else if (acc instanceof Term) {
                st.execute(getUpdateAccountSQL(acc) + getUpdateTermSQL((Term) acc));
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Updates the given accounts, and also inserts the given entries.
     * <p>
     * Manual commit ensures that all database transactions is processed or none
     * if any error occurs during the operation.
     *
     * @param accs accounts to be updated
     * @param entries entries to be inserted
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean updateAccounts(Account[] accs, Entry[] entries) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            try {
                for (Account acc : accs) {
                    if (acc instanceof Transactional) {
                        st.execute(getUpdateAccountSQL(acc));
                    } else if (acc instanceof Savings) {
                        st.execute(getUpdateAccountSQL(acc) + getUpdateSavingsSQL((Savings) acc));
                    } else if (acc instanceof Term) {
                        st.execute(getUpdateAccountSQL(acc) + getUpdateTermSQL((Term) acc));
                    }
                }
                for (Entry e : entries) {
                    st.execute(getInsertEntrySQL(e));
                }
                conn.commit();
            } catch (SQLException e) {
                System.out.println("noes");
                conn.rollback();
                e.printStackTrace();
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Updates the given accounts.
     * <p>
     * Manual commit ensures that all database transactions is processed or none
     * if any error occurs during the operation.
     *
     * @param accs accounts to be updated
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean updateAccounts(Account[] accs) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            try {
                for (Account acc : accs) {
                    if (acc instanceof Transactional) {
                        st.execute(getUpdateAccountSQL(acc));
                    } else if (acc instanceof Savings) {
                        st.execute(getUpdateAccountSQL(acc) + getUpdateSavingsSQL((Savings) acc));
                    } else if (acc instanceof Term) {
                        st.execute(getUpdateAccountSQL(acc) + getUpdateTermSQL((Term) acc));
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                LOGGER.error("SQLException", e);
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Creates and returns the insertion SQL query as a {@link java.lang.String}
     * for given entry.
     *
     * @param e the history entry
     * @return the created SQL query as a string
     */
    public static String getInsertEntrySQL(Entry e) {
        return "INSERT INTO entries (number, operation, party, amount, description, datetime) "
                + "VALUES ('" + e.getNumber() + "', '" + e.getOperation() + "', "
                + "'" + e.getParty() + "', '" + e.getAmount() + "', '" + e.getStatement() + "'"
                + ", '" + e.getDate() + "')";
    }

    /**
     * Creates and returns the update SQL query as a {@link java.lang.String}
     * for given account.
     *
     * @param acc the account
     * @return the created SQL query as a String
     */
    public static String getUpdateAccountSQL(Account acc) {
        return "UPDATE accounts SET balance=" + acc.getBalance()
                + " WHERE number = '" + acc.getNumber() + "';";
    }

    /**
     * Creates and returns the update SQL query as a {@link java.lang.String}
     * for given Term Deposit account.
     *
     * @param acc the Term account
     * @return the created SQL query as a String
     */
    public static String getUpdateTermSQL(Term acc) {
        int res = acc.isLocked() ? 1 : 0;
        String start = acc.getLockStart() == null ? "null" : "'" + acc.getLockStart() + "'";
        String end = acc.getLockEnd() == null ? "null" : "'" + acc.getLockEnd() + "'";
        return "UPDATE terms SET start=" + start
                + ", end=" + end + ", is_locked=" + res
                + ", interest=" + acc.getInterestRate()
                + " WHERE number = '" + acc.getNumber() + "';";
    }

    /**
     * Creates and returns the update SQL query as a {@link java.lang.String}
     * for given Savings account.
     *
     * @param acc the Savings account
     * @return the created SQL query as a String
     */
    public static String getUpdateSavingsSQL(Savings acc) {
        return "UPDATE savings SET interest=" + acc.getInterestRate()
                + ", transfers_this_month=" + acc.getTransfersThisMonth()
                + " WHERE number = '" + acc.getNumber() + "';";
    }

    /**
     * Updates data in database using the given Savings account.
     *
     * @param acc the Savings account
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean updateSavings(Savings acc) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query = "UPDATE terms SET interest=" + acc.getInterestRate()
                    + " where number = '" + acc.getNumber() + "'";
            st.executeUpdate(query);
            query = "UPDATE accounts SET balance=" + acc.getBalance()
                    + " WHERE number = '" + acc.getNumber() + "'";
            st.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Updates data in database using the given Term account.
     *
     * @param acc the Term account
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean updateTerm(Term acc) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            int res = acc.isLocked() ? 1 : 0;
            String query = "UPDATE terms SET start='" + acc.getLockStart()
                    + "', end='" + acc.getLockEnd() + "', is_locked=" + res
                    + ", interest=" + acc.getInterestRate()
                    + " where number = '" + acc.getNumber() + "'";
            st.executeUpdate(query);
            query = "UPDATE accounts SET balance=" + acc.getBalance()
                    + " WHERE number = '" + acc.getNumber() + "'";
            st.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Close the given account.
     *
     * @param acc the account to be closed
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean closeAccount(Account acc) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            String query1 = "DELETE FROM accounts WHERE number = '" + acc.getNumber() + "';";
            String query2 = null;
            switch (acc.getType()) {
                case "Transactional Account":
                    query2 = "";
                    break;
                case "Savings Account":
                    query2 = "DELETE FROM savings WHERE number = '" + acc.getNumber() + "';";
                    break;
                case "Term Deposit":
                    query2 = "DELETE FROM terms WHERE number = '" + acc.getNumber() + "';";
                    break;
            }
            st.execute(query1 + query2);
            conn.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Insert given transactional account data to the database.
     * <p>
     * User id must be provided to distinguish account.
     *
     * @param acc the account
     * @param user_id the user id
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean insertTransactional(Account acc, String user_id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query = "INSERT INTO accounts (number, type, balance, user_id) " + "VALUES ('"
                    + acc.getNumber() + "', '" + acc.getType() + "', 0, " + user_id + ");";
            st.execute(query);
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Insert given savings account data to the database.
     * <p>
     * User id must be provided to distinguish account.
     *
     * @param acc the account
     * @param user_id the user id
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean insertSavings(Savings acc, String user_id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            String query1 = "INSERT INTO accounts (number, type, balance, user_id) " + "VALUES ('"
                    + acc.getNumber() + "', '" + acc.getType() + "', 0, " + user_id + ");";
            String query2 = "INSERT INTO savings (number, interest) " + "VALUES ('"
                    + acc.getNumber() + "', " + acc.getInterestRate() + ");";
            st.execute(query1);
            st.execute(query2);
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
        return true;
    }

    /**
     * Insert given term account data to the database.
     * <p>
     * User id must be provided to distinguish account.
     *
     * @param acc the account
     * @param user_id the user id
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean insertTerm(Term acc, String user_id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            String query1 = "INSERT INTO accounts (number, type, balance, user_id) " + "VALUES ('"
                    + acc.getNumber() + "', '" + acc.getType() + "', 0, " + user_id + ");";
            String query2 = "INSERT INTO terms (number, interest) " + "VALUES ('"
                    + acc.getNumber() + "', " + acc.getInterestRate() + ");";
            st.execute(query1);
            st.execute(query2);
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
        return true;
    }

    /**
     * Checks if the given username, password pair exists in the database.
     *
     * @param username the username
     * @param password the password
     * @return the user id and username or null if user with given password
     * doesn't exists in the database
     */
    public static String[] login(String username, String password) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query = "SELECT id, username FROM users "
                    + "WHERE username = '" + username
                    + "' AND password = '" + password + "';";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                return new String[]{String.valueOf(rs.getInt("id")), rs.getString("username")};
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return null;
    }

    /**
     * Inserts given username, password pair into the database.
     *
     * @param username the username
     * @param password the password
     * @return <code>true</code> if the update was successful;
     * <code>false</code> if it failed
     */
    public static boolean register(String username, String password) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query = "INSERT INTO users (username, password, last_login, email) VALUES "
                    + "('" + username + "', '" + password + "', '0000-00-00', 'none');";
            st.execute(query);
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return false;
        }
    }

    /**
     * Checks if the given username already exists in the database.
     *
     * @param username the username
     * @return <code>true</code> if the user already exists in the database;
     * <code>false</code> if the user doesn't exist in the database
     */
    public static boolean isUserExists(String username) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query
                    = "SELECT * "
                    + "FROM users "
                    + "WHERE username = '"
                    + username + "';";
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }

        return false;
    }

    /**
     * Checks if the given account already exists in the database.
     *
     * @param number the account number
     * @return <code>true</code> if the account already exists in the database;
     * <code>false</code> if the account doesn't exist in the database
     */
    public static boolean isAccountExists(String number) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            Statement st = conn.createStatement();
            String query
                    = "SELECT * "
                    + "FROM accounts "
                    + "WHERE number = '"
                    + number + "';";
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        }
        return false;
    }
}
