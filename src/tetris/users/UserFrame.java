package tetris.users;

import tetris.game.TetrisFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class UserFrame extends JFrame implements MouseListener {

    private final JFrame frame;
    private JLabel newGame, options, records, exit;
    private final static int WIDTH = 300;
    private final static int HEIGHT = 500;
    private User user;

    public UserFrame(JFrame frame, User user) {

        this.frame = frame;
        frame.setVisible(false);
        init();
        this.user = user;
    }
    
    private void init() {

        setLayout(new GridLayout(4, 1));

        newGame = new JLabel("New Game", JLabel.CENTER);
        options = new JLabel("Options", JLabel.CENTER);
        records = new JLabel("Records", JLabel.CENTER);
        exit = new JLabel("Exit", JLabel.CENTER);
        newGame.setFont(new Font("1", Font.BOLD, 25));
        options.setFont(new Font("1", Font.BOLD, 25));
        records.setFont(new Font("1", Font.BOLD, 25));
        exit.setFont(new Font("1", Font.BOLD, 25));

        newGame.addMouseListener(this);
        options.addMouseListener(this);
        records.addMouseListener(this);
        exit.addMouseListener(this);

        add(newGame);
        add(options);
        add(records);
        add(exit);

        setTitle("Main menu");
        setResizable(false);
        pack();
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == newGame) {
            new TetrisFrame(this, user);
        } else if (source == options) {
           new OptionsFrame(this);
        } else if (source == records) {
            new RecordsFrame(this);
        } else if (source == exit) {
            dispose();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();
        if (source == newGame) {
            newGame.setForeground(Color.red);
        } else if (source == options) {
            options.setForeground(Color.red);
        } else if (source == records) {
            records.setForeground(Color.red);
        } else if (source == exit) {
            exit.setForeground(Color.red);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();
        if (source == newGame) {
            newGame.setForeground(Color.black);
        } else if (source == options) {
            options.setForeground(Color.black);
        } else if (source == records) {
            records.setForeground(Color.black);
        } else if (source == exit) {
            exit.setForeground(Color.black);
        }
    }
}
