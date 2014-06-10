package tetris.authorization;

import tetris.users.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import tetris.db.Constants;
import tetris.db.SQLiteDBHelper;

public class RegistrationFrame extends JFrame {

    private final static int WIDTH = 500;
    private final static int HEIGHT = 300;
    private final JFrame frame;
    private JLabel loginLabel, passwordLabel, confirmPasswordLabel;
    private JTextField loginField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerBtn;
    private String login, password, confirmPassword;
    SQLiteDBHelper helper = new SQLiteDBHelper();
   // List<User> list;

    public RegistrationFrame(JFrame frame) {

        super("Registration");
        this.frame = frame;
        frame.setVisible(false);
        init();
        //list = helper.getUsersList();
        registerBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                registrationAction();
            }
        });
    }

    private void init() {
        setResizable(false);
        setLayout(null);

        loginLabel = new JLabel("Login");
        loginLabel.setBounds(110, 50, 130, 30);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(85, 100, 60, 30);
        confirmPasswordLabel = new JLabel("Confirm pass");
        confirmPasswordLabel.setBounds(67, 150, 120, 30);

        loginField = new JTextField();
        loginField.setBounds(150, 50, 280, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 280, 30);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 150, 280, 30);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(320, 200, 110, 30);
        registerBtn.setToolTipText("Press for registration");

        add(confirmPasswordLabel);
        add(loginLabel);
        add(passwordLabel);
        add(loginField);
        add(passwordField);
        add(confirmPasswordField);
        add(registerBtn);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void registrationAction() {

        login = loginField.getText().trim();
        password = passwordField.getText().trim();
        confirmPassword = confirmPasswordField.getText().trim();

        if (!login.isEmpty() && !login.trim().equals("")) {
            if (!password.isEmpty() && !password.trim().equals("")) {
                if (!confirmPassword.isEmpty()
                        && !confirmPassword.trim().equals("")) {
                    if (Constants.isExist(login)) {
                        if (password.equals(confirmPassword)) {
                            User user = new User(login, password);
                            helper.addUser(user);
                            loginField.setText("");
                            passwordField.setText("");
                            confirmPasswordField.setText("");
                            JOptionPane.showMessageDialog(rootPane, "Registration success!");
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Passwords do not match!");
                            passwordField.setText("");
                            confirmPasswordField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "User with nickname '"+login+"' already exists! Choose another login");
                        loginField.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Field 'Confirm password' is empty");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Field 'Passwords' is empty");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Field 'Login' is empty");
        }
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }
}
