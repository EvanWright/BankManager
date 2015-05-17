package com.hpba.bankman.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * Panel for the Transactional Account.
 * <p>
 * Shows basic account information and recent transaction history.
 *
 * @author generalhpba
 */
public class TransactionalPanel extends AccountPanel {

    TransactionalInfoPanel transactionalInfoPanel = new TransactionalInfoPanel(null);

    public TransactionalPanel() {
        setLayout(new MigLayout("", "[grow, fill]", "[][grow, fill][]"));
        settingsPanel.remove(settingsPanel.historyBtn);
        add(accontInfoPanel, "align center, wrap, gapbottom 10, w 360!, h 60!");
        add(transactionalInfoPanel, "align center, wrap, gapleft 20, gapright 20");
        add(settingsPanel, "align center, wrap, gaptop 10, w 400!, h 50!");
    }

    public void update(ArrayList<String[]> al) {
        transactionalInfoPanel.update(al);
    }

    class TransactionalInfoPanel extends JPanel {

        private JLabel titleLabel = new JLabel("Transactional Account");
        private JLabel historyLabel = new JLabel("Transaction history");
        private HistoryPanel hp = null;

        public TransactionalInfoPanel(ArrayList<String[]> al) {
            hp = new HistoryPanel(al);
            setLayout(new MigLayout("fill"));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, (float) 24));
            historyLabel.setFont(titleLabel.getFont().deriveFont((float) 14));
            add(titleLabel, "growx, wrap");
            add(historyLabel, "wrap");
            add(hp, "grow");
        }

        public void update(ArrayList<String[]> al) {
            hp.update(al);
        }
    }
}
