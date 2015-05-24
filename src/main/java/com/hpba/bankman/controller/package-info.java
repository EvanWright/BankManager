/**
 * Controller classes control the communication between the
 * {@link com.hpba.bankman.view View} and the
 * {@link com.hpba.bankman.model Model}.
 * <p>
 * This package also contains the {@link com.hpba.bankman.controller.DB_DAO DAO}
 * used to access data from a remote database, the
 * {@link com.hpba.bankman.controller.ConnectionFactory connection handler class}
 * and the entry point of the application in the
 * {@link com.hpba.bankman.controller.BankMan BankMan} class.
 * <p>
 * The {@link com.hpba.bankman.controller.DB_DAO Data Access Object} uses static
 * methods, so no instantiation needed.
 * <p>
 * {@link com.hpba.bankman.controller.ConnectionFactory ConnectionFactory} usage
 * example:
 * <pre>
 * <code>
 * try (Connection conn = ConnectionFactory.getConnection()) {
 *      PreparedStatement ps = conn.prepareStatement("SELECT * FROM entries;");
 *      ResultSet rs = ps.executeQuery();
 *      // process results
 * } catch (SQLException e) {
 *      e.printStackTrace();
 * }
 * </code>
 * </pre> {@link com.hpba.bankman.controller.DB_DAO Data Access Object} usage
 * example:
 * <pre>
 * <code>
 * DB_DAO.closeAccount("01234567-01234567");
 * </code>
 * </pre>
 *
 * @author generalhpba
 * @version 1.0
 * @since 1.0
 */
package com.hpba.bankman.controller;
