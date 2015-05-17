package com.hpba.bankman.view;

import com.hpba.bankman.controller.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Graphical User Interface for Banking Manager
 * <p>
 * All interaction with the user handled here.
 *
 * @author generalhpba
 */
public final class View extends JFrame {

    private Controller controller;
    private final int width = 960;
    private final int height = 580;
    BorderLayout layout = new BorderLayout();
    public JMenuBar menuBar = new JMenuBar();
    private Login login;
    private Main main;
    private Offline offline;
    private Connect connect;
    private boolean hasFocus = true;
    private final Loading loading = new Loading();
    JMenuItem logoutMenuItem = new JMenuItem("Logout");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JPanel glassPane = new JPanel();
    InfoDialog infoDialog = null;
    HistoryDialog historyDialog = null;

    public void setLostFocus(boolean value) {
        glassPane.setVisible(value);
        hasFocus = !value;
    }

    public View(String title, Controller controller) {

        glassPane.setBackground(new Color(128, 128, 128, 128));
        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
        });
        setGlassPane(glassPane);

        ImageIcon img = new ImageIcon(getClass().getResource("/coins.png"));
        setIconImage(img.getImage());
        this.controller = controller;
        initMenuBar();
        setTitle(title);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setResizable(false);
        setLayout(layout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        KeyStroke escStroke = KeyStroke.getKeyStroke("ESCAPE");
        Action actionEscape = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasFocus) {
                    System.exit(0);
                }
            }
        };
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(escStroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", actionEscape);

        registerMenuActions();
        setVisible(true);
    }

    public void updateAccountInfo(String number, double amount) {
        getAccountPanel().updateAccountInfoPanel(number, amount);
    }

    public void updateTermInfo(boolean status, double interest_rate, LocalDate start, LocalDate end) {
        getTermPanel().updateTermInfo(status, interest_rate, start, end);
    }

    public void updateSavingsInfo(double interest_rate, double interest, int transfer_limit, int transfers_left) {
        getSavingsPanel().updateSavingsInfo(interest_rate, interest, transfer_limit, transfers_left);
    }

    public void updateTransactionalHistory(ArrayList<String[]> al) {
        getTransactionalPanel().update(al);
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void registerAccountActions() {
        getTermPanel().getBackBtn().addActionListener(e -> {
            controller.switchToMain("main");
        });
        getTermPanel().getTransferBtn().addActionListener(e -> {
            controller.transaction();
        });
        getTermPanel().getHistoryBtn().addActionListener(e -> {
            controller.history();
        });
        getSavingsPanel().getBackBtn().addActionListener(e -> {
            controller.switchToMain("main");
        });
        getSavingsPanel().getTransferBtn().addActionListener(e -> {
            controller.transaction();
        });
        getSavingsPanel().getHistoryBtn().addActionListener(e -> {
            controller.history();
        });
        getTransactionalPanel().getBackBtn().addActionListener(e -> {
            controller.switchToMain("main");
        });
        getTransactionalPanel().getTransferBtn().addActionListener(e -> {
            controller.transaction();
        });
        getTransactionalPanel().getHistoryBtn().addActionListener(e -> {
            controller.history();
        });
    }

    public void registerCloseAccountActions() {
        getMain().getCloseAccountDialog().getCloseBtn().addActionListener(e -> {
            controller.aCloseAccount();
            closeCloseAccountDialog();
        });
        getMain().getCloseAccountDialog().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeCloseAccountDialog();
            }
        });
        getMain().getCloseAccountDialog().getCancelBtn().addActionListener(e -> {
            closeCloseAccountDialog();
        });
    }

    public void registerLoginActions() {
        login.getLoginBtn().addActionListener(e -> {
            controller.aLogin();
        });
        login.getRegisterButton().addActionListener(e -> {
            controller.aRegister();
        });
    }

    public String[] getRegisterData() {
        return login.getRegisterData();
    }

    public void registerOpenAccountActions() {
        getMain().getOpenAccount().getOpenBtn().addActionListener(e -> {
            if (controller.aOpenAccount() == 0) {
                closeOpenAccountDialog();
            }
        });
        getMain().getOpenAccount().cancelBtn.addActionListener(e -> {
            closeOpenAccountDialog();
        });
        getMain().getOpenAccount().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeOpenAccountDialog();
            }
        });
    }

    private void closeCloseAccountDialog() {
        getMain().getCloseAccountDialog().dispose();
        getMain().closeCloseAccountDialog();
        setLostFocus(false);
    }

    public void closeOpenAccountDialog() {
        getMain().getOpenAccount().dispose();
        getMain().closeOpenAccountDialog();
        setLostFocus(false);
    }

    public void closeTransactionDialog() {
        getMain().getTransactionDialog().dispose();
        getMain().closeTransactionDialog();
        setLostFocus(false);
    }

    public void registerMainPanelActions() {
        getMainPanel().getSelectBtn().addActionListener(e -> {
            controller.aSelectCurrent();
        });
        getMainPanel().getLogoutBtn().addActionListener(e -> {
            controller.aBackToLogin();
        });
        getMainPanel().getExitBtn().addActionListener(e -> {
            System.exit(0);
        });
        getMainPanel().getOpenBtn().addActionListener(e -> {
            controller.aOpenAccountDisplay();
        });
        getMainPanel().getCloseBtn().addActionListener(e -> {
            controller.aCloseAccountDisplay();
        });
        registerTermPanelActions();
        registerAccountActions();
    }

    private void registerMenuActions() {
        logoutMenuItem.addActionListener(e -> {
            controller.aBackToLogin();
        });
        exitMenuItem.addActionListener(e -> {
            System.exit(0);
        });
    }

    public void registerOfflineActions() {
        getOffline().getRetryBtn().addActionListener(e -> {
            controller.aRetry();
        });
    }

    public void registerTermPanelActions() {
        getTermPanel().getCalcInterestBtn().addActionListener(e -> {
            setLostFocus(true);
            controller.aTermCalcInterest();
            setLostFocus(false);
        });
        getTermPanel().getLockBtn().addActionListener(e -> {
            setLostFocus(true);
            controller.aTermLock();
            setLostFocus(false);
        });
    }

    public ArrayList<Object> getTransactionData() {
        return getMain().getTransactionDialog().getData();
    }

    public void initCloseAccount() {
        if (main.getCloseAccountDialog() == null) {
            main.createCloseAccount(this, getSelectedAccount());
            registerCloseAccountActions();
            setLostFocus(true);
            main.getCloseAccountDialog().setVisible(true);
        }
    }

    public void initOpenAccount() {
        if (main.getTransactionDialog() == null) {
            main.createOpenAccountDialog((JFrame) this);
            registerOpenAccountActions();
            setLostFocus(true);
            main.getOpenAccount().setVisible(true);
        }
    }

    private void closeInfoDialog() {
        infoDialog.dispose();
        infoDialog = null;
    }

    public void initHistory(String title, ArrayList<String[]> al) {
        if (historyDialog == null) {
            historyDialog = new HistoryDialog((JFrame) this, title, al);
            setLostFocus(true);
            historyDialog.setVisible(true);
            historyDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    historyDialog.dispose();
                    historyDialog = null;
                    setLostFocus(false);
                }
            });
        }
    }

    public void initInfo(String title, String str) {
        if (infoDialog == null) {
            infoDialog = new InfoDialog((JFrame) this, title, str);
            setLostFocus(true);
            infoDialog.setVisible(true);
            infoDialog.getOkBtn().addActionListener(e -> {
                closeInfoDialog();
                setLostFocus(false);
            });
        }
    }

    public void initTransaction() {
        if (main.getTransactionDialog() == null) {
            main.createTransactionDialog((JFrame) this);
            registerTransactionActions();
            setLostFocus(true);
            main.getTransactionDialog().setVisible(true);
        }
    }

    private void registerTransactionActions() {
        getMain().getTransactionDialog().getTransferBtn().addActionListener(e -> {
            controller.aTransfer();
        });
        getMain().getTransactionDialog().getCancelBtn().addActionListener(e -> {
            closeTransactionDialog();
        });
        main.getTransactionDialog().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeTransactionDialog();
            }
        });
    }

    private void initMenuBar() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(logoutMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void showMenubar(boolean value) {
        for (Component c : menuBar.getComponents()) {
            c.setVisible(value);
        }
        menuBar.setVisible(value);
    }

    public void showRegister() {
        getLogin().setSelectedIndex(1);
    }

    public ArrayList<String> getOpenAccountData() {
        return main.getOpenAccount().getData();
    }

    public void showInvalidLabel() {
        main.getOpenAccount().showInvalidLabel();
    }

    public void updateAccountList(ArrayList<String> accs, ArrayList<String> types, ArrayList<Integer> balances) {
        getMainPanel().updateList(accs, types, balances);
    }

    public void interestDialog(double value, LocalDate date) {
        getTermPanel().interestDialog(value, date);
    }

    public void switchToLogin() {
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        login = new Login(this);
        registerLoginActions();
        add(login, BorderLayout.CENTER);
        remove(menuBar);
        showMenubar(false);
        revalidate();
        repaint();
    }

    public void updateUserInfo(String user, int accountCount, int transactionalCount,
            int savingsCount, int termCount, double sum) {
        getMainPanel().updateUserInfo(user, accountCount, transactionalCount,
                savingsCount, termCount, sum);
    }

    /**
     * Switches to main panel and which subpanel of main should be shown.
     * <p>
     * Subpanel selected through provided string. If an empty string is given
     * then simply shows the main panel.
     * <p>
     * Subpanels:
     * <ul style="list-style-type:none">
     * <li>main</li>
     * <li>term</li>
     * <li>savings</li>
     * <li>transactional</li>
     * </ul>
     *
     * @param subPanel the desired subPanel
     */
    public void switchToMain(String subPanel) {
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        if (main == null) {
            main = new Main();
            registerMainPanelActions();
        } else {
            switch (subPanel) {
                case "main":
                    main.switchToMainPanel();
                    break;
                case "term":
                    main.switchToTerm();
                    break;
                case "savings":
                    main.switchToSavings();
                    break;
                case "transactional":
                    main.switchToTransactional();
                    break;
                default:
                    break;
            }
        }
        add(main, BorderLayout.CENTER);
        showMenubar(true);
        revalidate();
        repaint();
    }

    public void switchToConnect() {
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        connect = new Connect();
        add(connect, BorderLayout.CENTER);
        showMenubar(false);
        revalidate();
        repaint();
    }

    public void switchToLoading() {
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        add(loading, BorderLayout.CENTER);
        showMenubar(false);
        revalidate();
        repaint();
    }

    public void switchToOffline() {
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        offline = new Offline();
        registerOfflineActions();
        add(offline, BorderLayout.CENTER);
        showMenubar(false);
        revalidate();
        repaint();
    }

    public void switchToTerm() {
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
    }

    public String getSelectedAccount() {
        return main.getMainPanel().getActualAccount();
    }

    public Main getMain() {
        return main;
    }

    public Login getLogin() {
        return login;
    }

    public Offline getOffline() {
        return offline;
    }

    public MainPanel getMainPanel() {
        return main.getMainPanel();
    }

    public TermPanel getTermPanel() {
        return main.getTermPanel();
    }

    public SavingsPanel getSavingsPanel() {
        return main.getSavingsPanel();
    }

    public TransactionalPanel getTransactionalPanel() {
        return main.getTransactionalPanel();
    }

    public AccountPanel getAccountPanel() {
        return main.getAccountPanel();
    }

}
