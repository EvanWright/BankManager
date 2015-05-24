/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hpba.bankman.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author generalhpba
 */
public class SavingsPanel extends AccountPanel {

    private final SavingsInfoPanel savingsInfoPanel = new SavingsInfoPanel();

    public SavingsPanel() {
        setLayout(new MigLayout("", "[grow, fill]", "[][grow, fill][]"));
        add(accontInfoPanel, "align center, wrap, gapbottom 10, w 360!, h 60!");
        add(savingsInfoPanel, "align center, wrap, gaptop 40, gapleft 10, gapright 10");
        add(settingsPanel, "align center, wrap, gaptop 10, w 400!, h 50!");
    }

    public void updateSavingsInfo(double interest_rate, double interest, int transfer_limit, int transfers_left) {
        savingsInfoPanel.updateSavingsInfo(interest_rate, interest, transfer_limit, transfers_left);
    }

    class SavingsInfoPanel extends JPanel {

        JLabel titleLabel = new JLabel("Savings Account");
        JLabel interestRate1 = new JLabel("Interest rate");
        JLabel interestRate2 = new JLabel("2.5%");
        JLabel interest1 = new JLabel("Interest for next month");
        JLabel interest2 = new JLabel("$1240.132");
        JLabel transferLimit1 = new JLabel("Transfer limit");
        JLabel transferLimit2 = new JLabel("5");
        JLabel transfersLeft1 = new JLabel("Transfers left this month");
        JLabel transfersLeft2 = new JLabel("2");

        public SavingsInfoPanel() {
            setLayout(new MigLayout("fillx, gap 0"));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            for (JLabel label : new ArrayList<>(Arrays.asList(interestRate1, interestRate2,
                    interest1, interest2, transferLimit1, transferLimit2, transfersLeft1, transfersLeft2))) {
                label.setBorder(border);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
            String w = ", w 180!, h 40!";
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, (float) 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(titleLabel, "growx, spanx 2, gapbottom 20, wrap");
            add(interestRate1, "r" + w);
            add(interestRate2, "wrap" + w);
            add(interest1, "r" + w);
            add(interest2, "wrap" + w);
            add(transferLimit1, "r" + w);
            add(transferLimit2, "wrap" + w);
            add(transfersLeft1, "r" + w);
            add(transfersLeft2, "wrap" + w);
        }

        public void updateSavingsInfo(double interest_rate, double interest, int transfer_limit, int transfers_left) {
            interestRate2.setText(interest_rate + "%");
            interest2.setText("$" + (int) interest);
            transferLimit2.setText("" + transfer_limit);
            transfersLeft2.setText("" + transfers_left);
            if(transfers_left == 0) {
                getTransferBtn().setEnabled(false);
            } else {
                getTransferBtn().setEnabled(true);
            }
        }
    }
}
