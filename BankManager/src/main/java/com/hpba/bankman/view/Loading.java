package com.hpba.bankman.view;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author generalhpba
 */
public class Loading extends JPanel {

    JLabel connectLabel = new JLabel("LOADING");
    JXBusyLabel busyLabel = new JXBusyLabel(new Dimension(150, 150));

    Loading() {
        connectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectLabel.setFont(connectLabel.getFont().deriveFont((float)28));
        busyLabel.setBusy(true);
        setLayout(new MigLayout("alignx center, aligny center", "[c]", "[c]20[c]"));
        add(busyLabel, "wrap");
        add(connectLabel, "growx");
    }
}
