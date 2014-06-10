
package tetris.users;

public class User {

    private String name;
    private String password;
    private String speed;
    private int score;

    public User(String name, String password, String speed, int score) {
        this.name = name;
        this.password = password;
        this.speed = speed;
        this.score = score;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public String getSpeed() {
        return speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "User("+name+" "+password+" "+speed+" "+score+ ")";
    }
}
