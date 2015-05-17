package com.hpba.bankman.view;

import java.text.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.text.*;
import net.miginfocom.swing.MigLayout;

public final class TransactionDialog extends JDialog {

    private JLabel numberLabel = new JLabel("Account Number:", SwingConstants.RIGHT);
    private JLabel amountLabel = new JLabel("Transfer Amount:", SwingConstants.RIGHT);
    private JLabel descriptionLabel = new JLabel("Description", SwingConstants.LEFT);
    MaskFormatter mf = null;
    private JFormattedTextField numberFTF = null;
    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
    private DecimalFormat df = (DecimalFormat) nf;
    private NumberFormatter formatter = new NumberFormatter(df);
    private JFormattedTextField amountFTF = new JFormattedTextField(df);
    private JTextArea descriptionTA = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(descriptionTA);
    private JButton transferBtn = new JButton("Transfer");
    private JButton cancelBtn = new JButton("Cancel");

    public JButton getTransferBtn() {
        return transferBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JTextField getNumberTF() {
        return numberFTF;
    }

    public JTextField getAmountTF() {
        return amountFTF;
    }

    public JTextArea getDescriptionTA() {
        return descriptionTA;
    }

    /*
     * 1. String - Account number
     * 2. double - Amount
     * 3. String - Description
     */
    public ArrayList<Object> getData() {
        ArrayList<Object> al = new ArrayList<>();
        al.add(numberFTF.getText());
        al.add(amountFTF.getText());
        al.add(descriptionTA.getText());
        return al;
    }

    public TransactionDialog(JFrame parent) {
        super(parent);
        setTitle("Money Transaction");
         
        try {
            mf = new MaskFormatter("########-########");
            mf.setPlaceholderCharacter('X');
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        numberFTF = new JFormattedTextField(mf);
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(10);

        String size1 = ", w 160!, h 20!", size2 = ", w 100!, h 20!";
        setLayout(new MigLayout("fill", "[r][l]"));
        add(numberLabel, "r" + size2);
        add(numberFTF, "wrap, l" + size1);
        add(amountLabel, "r" + size2);
        add(amountFTF, "wrap, l" + size1);
        add(descriptionLabel, "wrap, l, gaptop 4" + size2);
        add(scrollPane, "wrap, spanx 2, c, w 260!, h 70!");
        add(transferBtn, "");
        add(cancelBtn, "");
        pack();
        setLocationRelativeTo(null);
    }

}
