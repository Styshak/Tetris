/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.users;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import tetris.db.Constants;
import tetris.db.SQLiteDBHelper;

/**
 *
 * @author Styshak Sergey
 */
public class AdminFrame extends JFrame {

    private final JFrame frame;

    private final JButton delete, change, add;
    private final JLabel nameL, passwordL, speedL, scoreL;
    private final JTextField nameTF, passwordTF, scoreTF;
    private final JComboBox cBox;
    private String name, password, speed, score;
    private final JTable usersTable;
    private static int WIDTH = 1000;
    private static int HEIGHT = 300;
    private static final String[] items = {"", "Low", "Medium", "High"};

    private final MyTableModel model;
    SQLiteDBHelper helper = new SQLiteDBHelper();

    public AdminFrame(JFrame frame) {
        super("View users");
        setResizable(false);
        this.frame = frame;
        frame.setVisible(false);
        setLayout(null);

        delete = new JButton("Delete");
        delete.setBounds(350, 230, 100, 30);
        change = new JButton("Change");
        change.setBounds(150, 230, 100, 30);
        add = new JButton("Add");
        add.setBounds(730, 230, 100, 30);

        nameL = new JLabel("Login");
        nameL.setBounds(635, 10, 70, 30);
        passwordL = new JLabel("Password");
        passwordL.setBounds(610, 60, 70, 30);
        speedL = new JLabel("Speed");
        speedL.setBounds(630, 110, 70, 30);
        scoreL = new JLabel("Score");
        scoreL.setBounds(630, 160, 70, 30);

        nameTF = new JTextField();
        nameTF.setBounds(680, 10, 180, 30);
        passwordTF = new JTextField();
        passwordTF.setBounds(680, 60, 180, 30);
        cBox = new JComboBox(items);
        cBox.setBounds(680, 110, 180, 30);
        scoreTF = new JTextField();
        scoreTF.setBounds(680, 160, 180, 30);

        model = new MyTableModel();
        usersTable = new JTable(model);
        JTableHeader header = usersTable.getTableHeader();
        header.setForeground(Color.black);
        header.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JScrollPane sp = new JScrollPane(usersTable);
        sp.setBounds(0, 0, 600, 191);

        add(delete);
        add(change);
        add(add);
        add(sp);
        add(nameL);
        add(passwordL);
        add(speedL);
        add(scoreL);
        add(cBox);
        add(nameTF);
        add(passwordTF);
        add(scoreTF);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameTF.getText().trim();
                password = passwordTF.getText().trim();
                speed = (String) cBox.getSelectedItem();
                score = scoreTF.getText().trim();
                User u;
                if (!name.isEmpty() && !password.isEmpty()) {
                    if (Constants.isExist(name)) {
                        if (score.isEmpty()) {
                            score = "0";
                            u = new User(name, password, speed, Integer.parseInt(score));
                            helper.addUser(u);
                            model.fireTableDataChanged();
                        } else {
                            if (isDigit(score)) {
                                u = new User(name, password, speed, Integer.parseInt(score));
                                helper.addUser(u);
                                model.fireTableDataChanged();
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Field 'Score' must be only contain integer value!");
                                scoreTF.setText("");
                                return;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane,
                                "User with nickname '" + name + "' already exists! Choose another login");
                        nameTF.setText("");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Fields 'Name' and 'Password' can't be not filling");
                    return;
                }
                JOptionPane.showMessageDialog(null, "User added successfully !", null, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = usersTable.getSelectedRow();
                if (isSelectedUser(row)) {
                    if (helper.getUsersList().get(row).getName().equals("admin")) {
                        JOptionPane.showMessageDialog(rootPane, "You can not delete the admin account!");
                    } else {
                        if (getSelectedUser().getName() != null) {
                            String name = getSelectedUser().getName();
                            helper.deleteUser(name);
                            model.fireTableDataChanged();
                            JOptionPane.showMessageDialog(null, "User was deleted successfully !", null, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
        change.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int column = usersTable.getSelectedColumn();
                if (column == 0) {
                    JOptionPane.showMessageDialog(null, "Field can't be changed !", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    if (column > 0) {
                        String id = getSelectedUser().getName();
                        changeField(column, id);
                    } else {
                        JOptionPane.showMessageDialog(null, "Select data for change", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    private void changeField(int column, String id) {
        String name = null;
        if (column == 3) {
            name = (String) JOptionPane.showInputDialog(frame,
                    "Select speed",
                    null,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    items,
                    items[0]);
        } else {
            name = JOptionPane.showInputDialog("On what to change?");
        }
        if (name != null) {
            if (!name.isEmpty() || (column == 3 && name.isEmpty())) {
                switch (column) {
                    case 1:
                        helper.changeUserAttribute(id, "name", name);
                        break;
                    case 2:
                        helper.changeUserAttribute(id, "password", name);
                        break;
                    case 3:
                        helper.changeUserAttribute(id, "speed", name);
                        break;
                    case 4:
                        if (isDigit(name)) {
                            helper.changeUserAttribute(id, "score", name);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid format. Must be contain only a integer value!", null, JOptionPane.ERROR_MESSAGE);
                            changeField(column, id);
                            return;
                        }
                }
                model.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, "Data changed successfully !", null, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Field for change is empty !", null, JOptionPane.ERROR_MESSAGE);
                changeField(column, id);
            }
        }
    }

    public User getSelectedUser() {
        int index = usersTable.getSelectedRow();
        if (isSelectedUser(index)) {
            String name = (String) usersTable.getValueAt(index, 1);
            return getUser(name);
        } else {
            return null;
        }
    }

    public User getUser(String name) {
        for (User u : helper.getUsersList()) {
            if (name.equals(u.getName())) {
                return u;
            }
        }
        return null;
    }

    public boolean isSelectedUser(int index) {
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "User is not selected. Please, choose user", null, JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    private static boolean isDigit(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }

    private class MyTableModel extends DefaultTableModel {

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return "#";
                case 1:
                    return "Login";
                case 2:
                    return "Password";
                case 3:
                    return "Speed";
                case 4:
                    return "Score";
            }
            return "";
        }

        @Override
        public int getRowCount() {
            return helper.getUsersList().size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            User user = helper.getUsersList().get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return ++rowIndex;
                case 1:
                    return user.getName();
                case 2:
                    return user.getPassword();
                case 3:
                    return user.getSpeed();
                case 4:

                    return user.getScore();
            }
            return "";
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
        }
    }
}
