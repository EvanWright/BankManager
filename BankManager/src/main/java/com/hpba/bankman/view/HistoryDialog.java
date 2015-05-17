package com.hpba.bankman.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public final class HistoryDialog extends JDialog {

    HistoryPanel hp = null;

    public HistoryDialog(JFrame parent, String title, ArrayList<String[]> al) {
        super(parent);
        setTitle(title);
        hp = new HistoryPanel(al);
        setMinimumSize(new Dimension(900, 500));
        add(hp);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
