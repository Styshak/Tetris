

package tetris.db;

import java.util.List;
import tetris.users.User;


public interface DBHelper {
    
    List<User> getUsersList();

    void deleteUser(String name);

    void addUser(User u);

    void changeUserAttribute(String name, String field, String newValue);
}
