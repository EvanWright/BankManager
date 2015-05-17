package com.hpba.bankman.view;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXBusyLabel;

/**
 * Displays all accounts owned by the current user.
 * <p>
 * It's main component is a JTable, where the account number, balance and type
 * are displayed for every account.
 * <p>
 * A button panel serves as the main interaction interface for operations with
 * the accounts.
 * <p>
 *
 *
 * @author generalhpba
 */
public final class MainPanel extends JPanel {
    
    JLabel userLabel = new JLabel("User: None");
    JLabel selectedLabel = new JLabel("Selected account: None");
    JScrollPane scrollPane = new JScrollPane();
    JTable table = new JTable();
    TableModel dtm = new TableModel();
    JButton selectBtn = new JButton("Select");
    ButtonPanel buttonPanel = new ButtonPanel();
    InfoPanel infoPanel = new InfoPanel();
    String actualAccount = "";
    
    protected MainPanel() {
        initLayout();
        initTable();
        scrollPane.setViewportView(table);
        selectBtn.setEnabled(false);
        updateSelected(-1);
    }
    
    public void updateList(ArrayList<String> accs, ArrayList<String> types, ArrayList<Integer> balances) {
        // sorting miatt szinkronban kell h legyunk swinggel
        SwingUtilities.invokeLater(() -> {
            dtm.setRowCount(0);
            for (int i = 0; i < accs.size(); ++i) {
                dtm.addRow(new Object[]{accs.get(i), types.get(i), balances.get(i)});
            }
        });
    }
    
    private void updateSelected(int r) {
        if (r != -1) {
            actualAccount = String.valueOf(table.getValueAt(r, 0));
            selectedLabel.setText(("Selected account: " + dtm.getValueAt(r, 0)));
            selectBtn.setEnabled(true);
            buttonPanel.closeAccountBtn.setEnabled(true);
        } else {
            actualAccount = "";
            selectedLabel.setText(("Selected account: None"));
            selectBtn.setEnabled(false);
            buttonPanel.closeAccountBtn.setEnabled(false);
        }
    }
    
    private void initTable() {
        dtm.addColumn("Account Number");
        dtm.addColumn("Account Type");
        dtm.addColumn("Account Balance");
        table.setModel(dtm);
        table.setAutoCreateRowSorter(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 2) {
                    setText("$" + getText());
                }
                return this;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelected(table.getSelectedRow());
            }
        });
    }
    
    private void initLayout() {
        setLayout(new MigLayout("gap 0, fill", "[][grow, fill][]", ""));
        add(buttonPanel, "r, t, gaptop 10");
        add(scrollPane, "grow, gaptop 10");
        add(infoPanel, "t, gaptop 10, gapright 10, gapleft 10");
        add(selectBtn, "growx, newline, skip, t");
        add(selectedLabel, "growx, newline, skip, t");
    }
    
    class TableModel extends DefaultTableModel {
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:
                    return Integer.class;
                default:
                    return String.class;
            }
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
    
    public void updateUserInfo(String user, int accountCount,
            int transactionalCount, int savingsCount, int termCount, double sum) {
        infoPanel.updateUserInfo(user, accountCount, transactionalCount,
                savingsCount, termCount, sum);
    }
    
    public JButton getSelectBtn() {
        return selectBtn;
    }
    
    public JButton getLogoutBtn() {
        return buttonPanel.logoutBtn;
    }
    
    public JButton getExitBtn() {
        return buttonPanel.exitBtn;
    }
    
    public JButton getOpenBtn() {
        return buttonPanel.openAccountBtn;
    }
    
    public JButton getCloseBtn() {
        return buttonPanel.closeAccountBtn;
    }
    
    public String getActualAccount() {
        return actualAccount;
    }
    
    public void setLoading(boolean value) {
        buttonPanel.setLoading(value);
    }
    
    final class ButtonPanel extends JPanel {
        
        JButton openAccountBtn = new JButton("<html>Open New</html>");
        JButton closeAccountBtn = new JButton("<html>Close Selected</html>");
        JButton logoutBtn = new JButton("Logout");
        JButton exitBtn = new JButton("Exit");
        JXBusyLabel busyAnim = new JXBusyLabel(new Dimension(60, 60));
        JLabel busyLabel = new JLabel("LOADING");
        
        ButtonPanel() {
            setLoading(false);
            busyAnim.setBusy(true);
            busyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            String size = "w 120!, h 42!";
            setLayout(new MigLayout("gap 0, fill"));
            add(openAccountBtn, "wrap, " + size);
            add(closeAccountBtn, "wrap, " + size);
            add(logoutBtn, "wrap, " + size);
            add(exitBtn, "wrap, " + size);
            add(busyAnim, "wrap, c, gaptop 30");
            add(busyLabel, "c, " + size);
        }
        
        void setLoading(boolean value) {
            busyAnim.setVisible(value);
            busyLabel.setVisible(value);
            table.setEnabled(!value);
            if (value) {
                table.setBackground(Color.lightGray);
            } else {
                table.setBackground(Color.white);
            }
        }
    }
    
    class InfoPanel extends JPanel {
        
        JLabel user = new JLabel("<html>User<br># user comes here</html>");
        JLabel accCount = new JLabel("<html>Account Count<br># accCount</html>");
        JLabel taCount = new JLabel("<html>Transactional Account<br># accCount</html>");
        JLabel saCount = new JLabel("<html>Savings Account<br># accCount</html>");
        JLabel tdCount = new JLabel("<html>Term Deposit<br># accCount</html>");
        JLabel sumBalance = new JLabel("<html>Balance Sum<br># allMoney</html>");
        
        public void updateUserInfo(String user, int accountCount, int transactionalCount,
                int savingsCount, int termCount, double sum) {
            this.user.setText("<html>User<br>#  " + user + "</html>");
            accCount.setText("<html>Account Count<br>#  " + accountCount + "</html>");
            taCount.setText("<html>Transactional Account<br>#  " + transactionalCount + "</html>");
            saCount.setText("<html>Savings Account<br>#  " + savingsCount + "</html>");
            tdCount.setText("<html>Term Deposit<br>#  " + termCount + "</html>");
            sumBalance.setText("<html>Balance Sum<br>#  $" + sum + "</html>");
        }
        
        InfoPanel() {
            setLayout(new MigLayout("gap 0", "[]"));
            String w = ", w ::200";
            add(user, "wrap" + w);
            add(accCount, "wrap" + w);
            add(taCount, "wrap" + w);
            add(saCount, "wrap" + w);
            add(tdCount, "wrap" + w);
            add(sumBalance, "" + w);
        }
    }
}
