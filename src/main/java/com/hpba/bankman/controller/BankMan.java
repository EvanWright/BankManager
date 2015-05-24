package com.hpba.bankman.controller;

import com.hpba.bankman.view.View;
import com.hpba.bankman.model.Model;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.logging.*;
import javax.swing.*;

/**
 * Banking management software.
 * <p>
 * This class contains the entry point. Also, the {@link Controller Controller}
 * is initialized and started from here.
 * <p>
 * Loads Open Sans font and sets it as the default font for all swing components
 * so there is no difference in the text size on varying operating systems.
 * <p>
 * <a href="https://www.google.com/fonts/specimen/Open+Sans">About Open Sans</a>
 * <p>
 * Sets the look and feel of the application.
 * <p>
 * <a href="http://www.jtattoo.net/">About JTatto</a>
 *
 * @author generalhpba
 * @version 1.0
 * @since 1.0
 */
public class BankMan {

    /**
     * Constructor which initializes and starts the application.
     *
     * @param args arguments for configuration
     */
    public BankMan(String... args) {
        init();
        start(args);
    }

    /**
     * The initialization method of this software.
     */
    private void init() {
        String fontName = "/OpenSans-Regular.ttf";
        InputStream is = this.getClass().getResourceAsStream(fontName);

        // Menu strings
        if (is == null) {
            try {
                throw new IOException("Cannot open " + fontName);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Font defaultFont, font = null;
        try {
            defaultFont = Font.createFont(Font.TRUETYPE_FONT, is);
            font = defaultFont.deriveFont(Font.PLAIN, 12);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1); // Resorces not found
        }

        Properties props = new Properties();
        props.put("logoString", "");
        MintLookAndFeel.setCurrentTheme(props);

        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
            java.util.Enumeration keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
                    UIManager.put(key, font);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BankMan.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("couldn't set look and feel");
        }
    }

    /**
     * The start method of the application.
     * <p>
     * This method gives the control of the application to the Controller.
     *
     * @param args arguments for configuration
     */
    private void start(String... args) {
        Controller controller = new Controller();
        Model model = new Model();
        View view = new View("Bank Manager", controller);
        controller.setModel(model);
        controller.setView(view);
        controller.start();
        controller.loadConfiguration(args);
    }

    /**
     * The entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BankMan bm = new BankMan(args);
    }

}
