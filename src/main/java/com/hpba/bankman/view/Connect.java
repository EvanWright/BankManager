package com.hpba.bankman.view;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author generalhpba
 */
public class Connect extends JPanel {

    JLabel connectLabel = new JLabel("Connecting to server");
    JXBusyLabel busyLabel = new JXBusyLabel(new Dimension(150, 150));

    Connect() {
        connectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectLabel.setFont(connectLabel.getFont().deriveFont((float)24));
        busyLabel.setBusy(true);
        setLayout(new MigLayout("alignx center, aligny center", "[c]", "[c]20[c]"));
        add(busyLabel, "wrap");
        add(connectLabel, "growx");
    }
}
