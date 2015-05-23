package com.hpba.bankman.view;

import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public final class CloseAccountDialog extends JDialog {

    JPanel panel = new JPanel(new MigLayout("fill"));
    JLabel label = null;
    JButton closeBtn = new JButton("Close Account");
    JButton cancelBtn = new JButton("Cancel");

    public CloseAccountDialog(JFrame parent, String str) {
        super(parent);
        label = new JLabel("<html><center>Do you really want to close account<br>" + str + "?</center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, "wrap, spanx 2");
        panel.add(closeBtn, "");
        panel.add(cancelBtn, "");
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Close New Account");
        setResizable(false);
    }

    @Override
    public void setModal(boolean value) {
        super.setModal(value);
        setVisible(true);
    }

    public JButton getCloseBtn() {
        return closeBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }
}
