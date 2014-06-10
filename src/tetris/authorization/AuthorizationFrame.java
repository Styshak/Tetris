package tetris.authorization;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import tetris.db.SQLiteDBHelper;
import tetris.users.AdminFrame;
import tetris.game.TetrisFrame;
import tetris.users.User;
import tetris.users.UserFrame;

public class AuthorizationFrame extends JFrame {

    private final static int WIDTH = 700;
    private final static int HEIGHT = 400;

    private JLabel loginLabel, passwordLabel, registration;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton enterBtn;
    private String login, password;
    SQLiteDBHelper helper = new SQLiteDBHelper();
    private User currentUser;

    public static String adminLogin = "admin";
    //private static final String adminPass = "admin";

    public AuthorizationFrame() {
        super("Sign in");
        init();

        registration.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                new RegistrationFrame(AuthorizationFrame.this);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                registration.setForeground(Color.black);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registration.setForeground(Color.blue);

            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
    }

    private void enterBtnAction() {

        login = loginField.getText().trim();
        password = passwordField.getText().trim();
        if (!login.isEmpty() && !login.trim().equals("")) {
            if (!password.isEmpty() && !password.trim().equals("")) {
                currentUser = getUser(login);
                if (currentUser != null) {
                    if (authentification(currentUser, password)) {
                        if (currentUser.getName().equals(adminLogin)) {
                            AdminFrame af = new AdminFrame(this);
                        } else {
                            UserFrame uf = new UserFrame(this,currentUser);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane,
                                "Wrong password");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane,
                            "This account does not exist - " + login);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Field 'Password' is empty");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Field 'Login' is empty");
        }
    }

    Action EnterButton = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            enterBtnAction();
        }
    };

    public void pushEnterButtonByMouse() {
        enterBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enterBtnAction();
            }
        });
    }

    public void pushEnterButtonByBtn() {

        InputMap im = loginField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("ENTER"), "EnterButton");
        loginField.getActionMap().put("EnterButton", EnterButton);

    }

    private User getUser(String login) {
        for (User u : helper.getUsersList()) {
            if (u.getName().equals(login)) {
                return u;
            }
        }
        return null;
    }

    public boolean authentification(User user, String password) {
        return user.getPassword().equals(password);
    }

    private void init() {

        setResizable(false);
        setLayout(null);

        registration = new JLabel("Registration");
        registration.setBounds(430, 200, 130, 30);
        loginLabel = new JLabel("Login");
        loginLabel.setBounds(180, 120, 35, 30);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(155, 170, 65, 30);

        loginField = new JTextField();
        loginField.setBounds(220, 120, 280, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(220, 170, 280, 30);

        enterBtn = new JButton("Enter");
        enterBtn.setBounds(420, 240, 80, 30);
        enterBtn.setFocusable(false);
        enterBtn.setToolTipText("Press for sign in");

        add(registration);
        add(loginLabel);
        add(passwordLabel);
        add(loginField);
        add(passwordField);
        add(enterBtn);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {

        AuthorizationFrame auth = new AuthorizationFrame();
        auth.pushEnterButtonByBtn();
        auth.pushEnterButtonByMouse();

    }
}
