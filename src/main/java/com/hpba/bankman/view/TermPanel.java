package com.hpba.bankman.view;

import java.awt.*;
import java.text.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author generalhpba
 */
public class TermPanel extends AccountPanel {

    TermInfoPanel termInfoPanel = new TermInfoPanel();

    public TermPanel() {
        setLayout(new MigLayout("", "[grow, fill]", "[][grow, fill][]"));
        add(accontInfoPanel, "align center, wrap, gapbottom 10, w 360!, h 60!");
        add(termInfoPanel, "align center, wrap, gapleft 10, gapright 10");
        add(settingsPanel, "align center, wrap, gaptop 10, w 400!, h 50!");
    }

    public void interestDialog(double value, LocalDate date) {
        JOptionPane.showMessageDialog(this, "Interest for " + getLockTimeTF().getText() + " months: "
                + value + "\nEnd of term: " + date.toString(), "Interest", JOptionPane.INFORMATION_MESSAGE);
    }

    public void lockDialog(double value, double balance, LocalDate date) {
        JOptionPane.showMessageDialog(this, "<html>Term Deposit Locked<br>Unlock date: " + date.toString()
                + "<br>Interest at end of term: " + value
                + "<br>Balance at end of term: " + (balance + value)
                + "</html>", "LOCK", JOptionPane.INFORMATION_MESSAGE);
    }

    public void lockDenyDialog() {
        JOptionPane.showMessageDialog(this, "Can't lock account for 0 months!", "LOCK DENIED", JOptionPane.INFORMATION_MESSAGE);
    }

    public TermInfoPanel getTermInfoPanel() {
        return termInfoPanel;
    }

    public JButton getCalcInterestBtn() {
        return termInfoPanel.calcInterestBtn;
    }

    public JButton getLockBtn() {
        return termInfoPanel.lockBtn;
    }

    public JTextField getLockTimeTF() {
        return termInfoPanel.lockTimeTF;
    }

    public void updateTermInfo(boolean status, double interest_rate, LocalDate start, LocalDate end) {
        termInfoPanel.interest.setText(String.valueOf(interest_rate) + "%");
        if (status) {
            termInfoPanel.termStatus.setText("LOCKED");
            getLockTimeTF().setText(String.valueOf(ChronoUnit.MONTHS.between(start, end)));
            getLockTimeTF().setEnabled(false);
            getLockBtn().setEnabled(false);
            getCalcInterestBtn().setEnabled(false);
            getTransferBtn().setEnabled(false);
        } else {
            termInfoPanel.termStatus.setText("UNLOCKED");
            getLockTimeTF().setText("0");
            getLockTimeTF().setEnabled(true);
            getLockBtn().setEnabled(true);
            getCalcInterestBtn().setEnabled(true);
            getTransferBtn().setEnabled(true);
        }
        if (start != null && end != null) {
            termInfoPanel.termStart.setText(start.toString());
            termInfoPanel.termEnd.setText(end.toString());
        } else {
            termInfoPanel.termStart.setText("N/A");
            termInfoPanel.termEnd.setText("N/A");
        }
    }

    public class TermInfoPanel extends JPanel {

        NumberFormat amountFormat = NumberFormat.getIntegerInstance();
        JLabel termHeader = new JLabel("Term Deposit Account");
        JLabel termStatusLabel = new JLabel("Status");
        JLabel termStatus = new JLabel("UNLOCKED");
        JLabel termStartLabel = new JLabel("Term start");
        JLabel termStart = new JLabel("0000.00.00");
        JLabel termEndLabel = new JLabel("Term end");
        JLabel termEnd = new JLabel("0000.00.00");
        JLabel interestLabel = new JLabel("Interest rate");
        JLabel interest = new JLabel("00.0%");
        JLabel lockTimeLabel = new JLabel("Term length (in months)");
        JFormattedTextField lockTimeTF = new JFormattedTextField(amountFormat); // in months
        JButton lockBtn = new JButton("Lock");
        JButton calcInterestBtn = new JButton("Calculate Interest");

        public TermInfoPanel() {
            lockTimeTF.setValue(0);

            int width = 160, height = 30;
            Dimension def = new Dimension(width, height);
            Dimension tfDef = new Dimension(width / 2, height);
            Dimension headerDef = new Dimension(width * 2, height);
            termHeader.setPreferredSize(headerDef);
            termHeader.setMinimumSize(headerDef);
            termHeader.setMaximumSize(headerDef);
            termStatusLabel.setPreferredSize(def);
            termStatusLabel.setMinimumSize(def);
            termStatusLabel.setMaximumSize(def);
            termStatus.setPreferredSize(def);
            termStatus.setMinimumSize(def);
            termStatus.setMaximumSize(def);
            termStartLabel.setPreferredSize(def);
            termStartLabel.setMinimumSize(def);
            termStartLabel.setMaximumSize(def);
            termStart.setPreferredSize(def);
            termStart.setMinimumSize(def);
            termStart.setMaximumSize(def);
            termEndLabel.setPreferredSize(def);
            termEndLabel.setMinimumSize(def);
            termEndLabel.setMaximumSize(def);
            termEnd.setPreferredSize(def);
            termEnd.setMinimumSize(def);
            termEnd.setMaximumSize(def);
            interestLabel.setPreferredSize(def);
            interestLabel.setMinimumSize(def);
            interestLabel.setMaximumSize(def);
            interest.setPreferredSize(def);
            interest.setMinimumSize(def);
            interest.setMaximumSize(def);
            lockTimeLabel.setPreferredSize(def);
            lockTimeLabel.setMinimumSize(def);
            lockTimeLabel.setMaximumSize(def);
            lockTimeTF.setPreferredSize(tfDef);
            lockTimeTF.setMinimumSize(tfDef);
            lockTimeTF.setMaximumSize(tfDef);
            lockBtn.setPreferredSize(def);
            lockBtn.setMinimumSize(def);
            lockBtn.setMaximumSize(def);
            calcInterestBtn.setPreferredSize(def);
            calcInterestBtn.setMinimumSize(def);
            calcInterestBtn.setMaximumSize(def);

            termHeader.setHorizontalAlignment(SwingConstants.CENTER);
            termStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
            termStatus.setHorizontalAlignment(SwingConstants.CENTER);
            termStartLabel.setHorizontalAlignment(SwingConstants.CENTER);
            termStart.setHorizontalAlignment(SwingConstants.CENTER);
            termEndLabel.setHorizontalAlignment(SwingConstants.CENTER);
            termEnd.setHorizontalAlignment(SwingConstants.CENTER);
            interestLabel.setHorizontalAlignment(SwingConstants.CENTER);
            interest.setHorizontalAlignment(SwingConstants.CENTER);
            lockTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lockTimeTF.setHorizontalAlignment(SwingConstants.CENTER);

            termHeader.setFont(termHeader.getFont().deriveFont(Font.BOLD, (float)24));
            lockTimeTF.setFont(lockTimeTF.getFont().deriveFont(Font.BOLD, (float)20));

            Border border = BorderFactory.createLineBorder(Color.black);
            termStatusLabel.setBorder(border);
            termStatus.setBorder(border);
            termStart.setBorder(border);
            termStartLabel.setBorder(border);
            termEnd.setBorder(border);
            termEndLabel.setBorder(border);
            interest.setBorder(border);
            interestLabel.setBorder(border);
            lockTimeLabel.setBorder(border);
            lockTimeTF.setBorder(border);

            Insets rightSpace = new Insets(0, 0, 0, 6);
            Insets rightBottomSpace = new Insets(0, 20, 0, 6);
            Insets bottomSpace = new Insets(0, 0, 20, 0);
            Insets topSpace = new Insets(20, 0, 0, 0);
            Insets none = new Insets(0, 0, 0, 0);

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 2;
            gbc.insets = bottomSpace;
            add(termHeader, gbc);
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.insets = none;
            add(termStatusLabel, gbc);
            gbc.gridx++;
            add(termStatus, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            add(termStartLabel, gbc);
            gbc.gridx++;
            add(termStart, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            add(termEndLabel, gbc);
            gbc.gridx++;
            add(termEnd, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            add(interestLabel, gbc);
            gbc.gridx++;
            add(interest, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = bottomSpace;
            add(lockTimeLabel, gbc);
            gbc.gridx++;
            add(lockTimeTF, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = none;
            add(calcInterestBtn, gbc);
            gbc.gridx++;
            add(lockBtn, gbc);
        }

    }

}
