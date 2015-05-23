/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hpba.bankman.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.embed.swing.JFXPanel;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author generalhpba
 */
public class Offline extends JPanel {

    JLabel connectionLabel = new JLabel("The connection has failed");
    JButton retryBtn = new JButton("Retry connection to database");
    JButton exitBtn = new JButton("EXIT");

    public Offline() {
        connectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectionLabel.setFont(connectionLabel.getFont().deriveFont((float)24));
        retryBtn.setFont(retryBtn.getFont().deriveFont((float)18));
        exitBtn.setFont(exitBtn.getFont().deriveFont((float)18));
        setLayout(new MigLayout("alignx center, aligny center", "[c]", "[c]10[c]10[c]"));
        add(connectionLabel, "wrap, h 40!");
        add(retryBtn, "wrap, growx");
        add(exitBtn, "growx");

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JButton getRetryBtn() {
        return retryBtn;
    }

}
