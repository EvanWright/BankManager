package com.hpba.bankman.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import net.miginfocom.swing.MigLayout;

public final class OpenAccountDialog extends JDialog {

    JComboBox<String> type = new JComboBox<>();
    JLabel typeLabel = new JLabel("Account type:");
    JButton openBtn = new JButton("Open");
    JButton cancelBtn = new JButton("Cancel");
    JFormattedTextField numberFTF = null;
    MaskFormatter mf = null;
    JLabel numberLabel = new JLabel("Account number:");
    JLabel invalidLabel = new JLabel("INVALID ACCOUNT NUMBER FORMAT");

    Runnable rInvalid = () -> {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
        invalidLabel.setVisible(false);
    };

    public OpenAccountDialog(JFrame parent) {
        super(parent);
        type.addItem("Transactional Account");
        type.addItem("Savings Account");
        type.addItem("Term Deposit");

        try {
            mf = new MaskFormatter("########-########");
            mf.setPlaceholderCharacter('X');
        } catch (Exception ex) {
        }

        numberFTF = new JFormattedTextField(mf);
        numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        invalidLabel.setHorizontalAlignment(SwingConstants.CENTER);
        invalidLabel.setFont(invalidLabel.getFont().deriveFont((float) 14));
        invalidLabel.setForeground(Color.red);
        invalidLabel.setVisible(false);

        String size = ", w 156!, h 24!";
        setLayout(new MigLayout("fill, ", "40[r][l]40", "40[][][][]18"));

        add(numberLabel, "grow" + size);
        add(numberFTF, "wrap, grow" + size);
        add(typeLabel, "grow" + size);
        add(type, "wrap, grow" + size);
        add(openBtn, "grow" + size);
        add(cancelBtn, "grow, wrap" + size);
        add(invalidLabel, "grow, h 22!, spanx 2");
        pack();
        setLocationRelativeTo(null);
        setTitle("Open New Account");
        setResizable(false);
    }

    public void showInvalidLabel() {
        if (!invalidLabel.isVisible()) {
            invalidLabel.setVisible(true);
            new Thread(rInvalid).start();
        }
    }

    public boolean isEditValid() {
        return numberFTF.isEditValid();
    }

    public ArrayList<String> getData() {
        ArrayList<String> al = new ArrayList<>();
        al.add(numberFTF.getText());
        al.add((String) type.getSelectedItem());
        return al;
    }

    public JButton getOpenBtn() {
        return openBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

}
