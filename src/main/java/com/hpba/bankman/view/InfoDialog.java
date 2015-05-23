package com.hpba.bankman.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public final class InfoDialog extends JDialog {

    JPanel panel = new JPanel(new MigLayout("fill"));
    JLabel label = null;
    JButton okBtn = new JButton("OK");

    public InfoDialog(JFrame parent, String title, String str) {
        super(parent);
        setTitle(title);
        label = new JLabel(str);
        panel.add(label, "wrap, gapleft 10, gapright 10");
        panel.add(okBtn, "c, gapbottom 4, w 100!");
        add(panel);
        pack();
        setLocationRelativeTo(null);
        panel.setVisible(true);
    }

    public JButton getOkBtn() {
        return okBtn;
    }
}
