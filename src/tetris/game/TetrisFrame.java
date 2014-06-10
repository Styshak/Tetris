
package tetris.game;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import tetris.users.User;


public class TetrisFrame extends JFrame {

   private final JLabel statusbar;
   private final JFrame frame;
   private User user;

    public TetrisFrame(JFrame frame, User user) {

        this.frame = frame;
        frame.setVisible(false);
        this.user = user;
        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);
        TetrisPanel tetris = new TetrisPanel(this, user);
        add(tetris);
        tetris.start();

        setSize(400, 600);
        setTitle("Tetris");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }

   public JLabel getStatusBar() {
       return statusbar;
   }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }
   
}
