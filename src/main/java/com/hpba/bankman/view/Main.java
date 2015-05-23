package com.hpba.bankman.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author generalhpba
 */
public class Main extends JPanel {

    private final CardLayout layout = new CardLayout();
    private final MainPanel mainPanel = new MainPanel();
    private final TermPanel termPanel = new TermPanel();
    private final SavingsPanel savingsPanel = new SavingsPanel();
    private final TransactionalPanel transactionalPanel = new TransactionalPanel();
    private TransactionDialog transactionDialog = null;
    private OpenAccountDialog openAccountDialog = null;
    private CloseAccountDialog closeAccountDialog = null;
    private act actual = act.MAIN;

    private enum act {

        MAIN, TERM, SAVINGS, TRANSACTIONAL
    }

    public AccountPanel getAccountPanel() {
        switch (actual) {
            case TERM:
                return termPanel;
            case SAVINGS:
                return savingsPanel;
            case TRANSACTIONAL:
                return transactionalPanel;
            case MAIN:
            default:
                return null;
        }
    }

    Main() {
        setLayout(layout);
        add(mainPanel, "main");
        add(termPanel, "term");
        add(savingsPanel, "savings");
        add(transactionalPanel, "transactional");
    }

    public void createTransactionDialog(JFrame parent) {
        transactionDialog = new TransactionDialog(parent);
    }

    public void createOpenAccountDialog(JFrame parent) {
        openAccountDialog = new OpenAccountDialog(parent);
    }

    public void createCloseAccount(JFrame parent, String accNumber) {
        closeAccountDialog = new CloseAccountDialog(parent, accNumber);
    }

    public TransactionDialog getTransactionDialog() {
        return transactionDialog;
    }
    
    public void closeCloseAccountDialog() {
        closeAccountDialog = null;
    }

    public void closeTransactionDialog() {
        transactionDialog = null;
    }

    public void closeOpenAccountDialog() {
        openAccountDialog = null;
    }

    public OpenAccountDialog getOpenAccount() {
        return openAccountDialog;
    }

    public CloseAccountDialog getCloseAccountDialog() {
        return closeAccountDialog;
    }

    public void history() {
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel();
        panel.setLayout(new MigLayout("fill"));
        panel.add(scrollPane, "grow");

        scrollPane.setViewportView(table);
        table.setModel(dtm);
        table.setRowHeight(30);
        dtm.addColumn("Date");
        dtm.addColumn("Counterparty");
        dtm.addColumn("Amount");
        dtm.addColumn("Description");
        for (int i = 0; i < 1; ++i) {
            dtm.addRow(new Object[]{"2015-04-12", "Transfer", "01234567-01234567", "Monthly Rental Fee", "120000$", "8996525$"});
        }

        JDialog dialog = new JDialog();
        dialog.setTitle("Transaction history");
        dialog.setModal(true);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public TermPanel getTermPanel() {
        return termPanel;
    }
    
    

    public SavingsPanel getSavingsPanel() {
        return savingsPanel;
    }

    public TransactionalPanel getTransactionalPanel() {
        return transactionalPanel;
    }

    public void switchToMainPanel() {
        layout.show(this, "main");
        actual = act.MAIN;
    }

    public void switchToTerm() {
        layout.show(this, "term");
        actual = act.TERM;
    }

    public void switchToSavings() {
        layout.show(this, "savings");
        actual = act.SAVINGS;
    }

    public void switchToTransactional() {
        layout.show(this, "transactional");
        actual = act.TRANSACTIONAL;
    }

}
