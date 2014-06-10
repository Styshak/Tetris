package tetris.users;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import tetris.db.SQLiteDBHelper;

public class RecordsFrame extends JFrame {

    private final JFrame frame;
    private final MyTableModel model;
    private final JTable usersTable;
    private static int WIDTH = 600;
    private static int HEIGHT = 297;
    SQLiteDBHelper helper = new SQLiteDBHelper();

    public RecordsFrame(JFrame frame) {
        super("Records");
        setResizable(false);
        this.frame = frame;
        frame.setVisible(false);
        setLayout(null);

        model = new MyTableModel();
        usersTable = new JTable(model);
        JTableHeader header = usersTable.getTableHeader();
        header.setForeground(Color.black);
        header.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JScrollPane sp = new JScrollPane(usersTable);
        sp.setBounds(0, 0, 600, 271);

        add(sp);
        if (helper.getUsersList().size() > 15) {
            WIDTH = 610;
        }
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }

    private class MyTableModel extends DefaultTableModel {

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return "#";
                case 1:
                    return "Login";
                case 2:
                    return "Speed";
                case 3:
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
                    return user.getSpeed();
                case 3:
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
