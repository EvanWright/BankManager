package com.hpba.bankman.view;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author generalhpba
 */
public abstract class AccountPanel extends JPanel {
    // Borderlayout, center for specific account type
    // bottom and top, for general account

    AccountInfoPanel accontInfoPanel = new AccountInfoPanel();
    SettingsPanel settingsPanel = new SettingsPanel();

    AccountPanel() {
        setLayout(new BorderLayout());
        add(accontInfoPanel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.SOUTH);
    }

    public void updateAccountInfoPanel(String number, double balance) {
        accontInfoPanel.number.setText(number);
        accontInfoPanel.balance.setText("$" + String.valueOf(balance));
    }

    public JButton getBackBtn() {
        return settingsPanel.backBtn;
    }

    public JButton getHistoryBtn() {
        return settingsPanel.historyBtn;
    }

    public JButton getTransferBtn() {
        return settingsPanel.transferBtn;
    }

    class AccountInfoPanel extends JPanel {

        JLabel number2 = new JLabel("Account Number:", SwingConstants.CENTER);
        JLabel balance2 = new JLabel("Current Balance:", SwingConstants.CENTER);
        JLabel number = new JLabel("00000000-00000000", SwingConstants.CENTER);
        JLabel balance = new JLabel("$0000000", SwingConstants.CENTER);

        public AccountInfoPanel() {
            setLayout(new MigLayout("gap 0, fill"));
            
            //setBorder(BorderFactory.createLineBorder(Color.BLACK));
            number2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            number.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            balance2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            balance.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            add(number2, "grow, align center");
            add(number, "wrap, grow, align center");
            add(balance2, "grow, align center");
            add(balance, "grow, align center");
        }

        public JLabel getNumber() {
            return number;
        }

        public JLabel getBalance() {
            return balance;
        }

    }

    class SettingsPanel extends JPanel {

        JButton backBtn = new JButton("Back");
        JButton historyBtn = new JButton("Transactional History");
        JButton transferBtn = new JButton("Transfer Money");

        public SettingsPanel() {
            setLayout(new MigLayout("fill"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(backBtn, "c, grow");
            add(transferBtn, "c, grow");
            add(historyBtn, "c, grow");
        }

    }
}
