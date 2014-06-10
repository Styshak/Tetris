/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.db;

import tetris.users.User;

/**
 *
 * @author Styshak Sergey
 */
public class Constants {

    public static final String USER_TABLE = "User";
    static SQLiteDBHelper helper = new SQLiteDBHelper();
    
    private Constants() {
    }
    
    public static boolean isExist(String login) {

        for (User u : helper.getUsersList()) {
            if (u.getName().equals(login)) {
                return false;
            }
        }
        return true;
    }
}
