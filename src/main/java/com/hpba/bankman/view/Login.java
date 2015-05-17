package com.hpba.bankman.view;

import java.awt.*;
import java.awt.event.*;
import javafx.scene.input.KeyCode;
import javax.swing.*;

/**
 *
 * @author generalhpba
 */
public class Login extends JTabbedPane {
    
    CardLayout layout = new CardLayout();
    public LoginPanel login = new LoginPanel();
    public RegisterPanel register = new RegisterPanel();
    public ForgottenPanel forgotten = new ForgottenPanel();
    View gui;
    
    Login(View gui) {
        this.gui = gui;
        addTab("Login", login);
        addTab("Register", register);
        //add(new JToolBar("aloha", JToolBar.VERTICAL));
        //addTab("Forgotten Password", forgotten);
    }
    
    public String[] getRegisterData() {
        return register.getRegisterData();
    }
    
    public JButton getRegisterButton() {
        return register.registerBtn;
    }
    
    public JButton getLoginBtn() {
        return login.loginBtn;
    }
    
    public String getLoginUsername() {
        return login.usernameTF.getText();
    }
    
    public String getLoginPassword() {
        return new String(login.passwordPF.getPassword());
    }
    
    public class LoginPanel extends JPanel {
        
        JTextField usernameTF = new JTextField();
        JPasswordField passwordPF = new JPasswordField();
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel loginLabel = new JLabel("Bank Manager");
        JButton loginBtn = new JButton("LOGIN");
        
        private final int TFWidth = 200, TFHeight = 32;
        
        public LoginPanel() {           
            usernameTF.setPreferredSize(new Dimension(TFWidth, TFHeight));
            passwordPF.setPreferredSize(new Dimension(TFWidth, TFHeight));
            usernameLabel.setPreferredSize(new Dimension(80, TFHeight));
            passwordLabel.setPreferredSize(new Dimension(80, TFHeight));
            usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            loginLabel.setFont(new Font("Tele-Marines", 0, 22));
            loginLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
            
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 20, 0);
            add(loginLabel, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(usernameLabel, gbc);
            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(usernameTF, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(passwordLabel, gbc);
            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(passwordPF, gbc);
            gbc.gridy = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(loginBtn, gbc);
        }
        
    }
    
    public class RegisterPanel extends JPanel {
        
        JTextField usernameTF = new JTextField();
        JPasswordField passwordPF = new JPasswordField();
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel registerLabel = new JLabel("Registration");
        JButton registerBtn = new JButton("REGISTER");
        
        private final int TFWidth = 200, TFHeight = 32;
        
        public String[] getRegisterData() {
            return new String[]{usernameTF.getText(), new String(passwordPF.getPassword())};
        }
        
        public RegisterPanel() {
            usernameTF.setPreferredSize(new Dimension(TFWidth, TFHeight));
            passwordPF.setPreferredSize(new Dimension(TFWidth, TFHeight));
            usernameLabel.setPreferredSize(new Dimension(80, TFHeight));
            passwordLabel.setPreferredSize(new Dimension(80, TFHeight));
            usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            registerLabel.setFont(new Font("Tele-Marines", 0, 22));
            registerLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
            
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 20, 0);
            add(registerLabel, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(usernameLabel, gbc);
            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(usernameTF, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(passwordLabel, gbc);
            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(passwordPF, gbc);
            gbc.gridy += 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(registerBtn, gbc);
        }
    }
    
    public class ForgottenPanel extends JPanel {
        
        JTextField textField = new JTextField();
        JLabel textLabel = new JLabel("<html>Username/Email: </html>");
        JLabel forgottenLabel = new JLabel("<html>Forgotten<br>Password</html>");
        JButton sendBtn = new JButton("Send");
        
        private final int TFWidth = 200, TFHeight = 32;
        
        public ForgottenPanel() {
            textField.setPreferredSize(new Dimension(TFWidth, TFHeight));
            textLabel.setPreferredSize(new Dimension(116, TFHeight));
            textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            forgottenLabel.setFont(new Font("Tele-Marines", 0, 22));
            forgottenLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
            
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 20, 0);
            add(forgottenLabel, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(textLabel, gbc);
            gbc.gridx = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(textField, gbc);
            gbc.gridy = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(sendBtn, gbc);
        }
    }
}
