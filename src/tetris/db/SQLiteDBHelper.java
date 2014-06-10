package tetris.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tetris.users.User;

public class SQLiteDBHelper implements DBHelper {

    private List<User> list;
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private final File file = new File("tetrisDB.sqlite");
    private final String resourcesPath = file.getAbsolutePath();
    private final File db = new File(resourcesPath);

    public SQLiteDBHelper() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:tetrisDB.sqlite");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect to DB", null, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            createdTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getUsersList() {

        list = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + Constants.USER_TABLE);
            while (rs.next()) {
                list.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs == null) {
                    rs.close();
                }
                if (stmt == null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void deleteUser(String name) {

        String query = "delete from " + Constants.USER_TABLE + " where name=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst == null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void addUser(User u) {
        String query = "INSERT into " + Constants.USER_TABLE
                + "(name,password,speed,score)"
                + " values(?,?,?,?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, u.getName());
            pst.setString(2, u.getPassword());
            pst.setString(3, u.getSpeed());
            pst.setInt(4, u.getScore());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pst == null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void changeUserAttribute(String name, String field, String newValue) {

        try {
            String query = "update " + Constants.USER_TABLE + " set " + field + " = ? where name = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, newValue);
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst == null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void createdTable() throws SQLException {

        if (db.exists() && db.length() == 0) {
            stmt = conn.createStatement();
            String userTable = "CREATE TABLE " + Constants.USER_TABLE
                    + " (Name VARCHAR UNIQUE, "
                    + " Password VARCHAR, "
                    + " Speed VARCHAR, "
                    + " Score INTEGER)";
            stmt.executeUpdate(userTable);;
            String query = "INSERT INTO " + Constants.USER_TABLE + " (name,password)" + " values('admin','admin')";
            stmt.executeUpdate(query);
            stmt.close();
        }
    }
}
