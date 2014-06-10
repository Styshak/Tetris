package tetris.users;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class OptionsFrame extends JFrame {

    public static int speed = 400;
    public static String selectedSpeed;
    private final JFrame frame;
    private final JLabel speedL;
    private JComboBox speedCB;
    private final JButton okB;
    private static int WIDTH = 400;
    private static int HEIGHT = 200;

    public OptionsFrame(JFrame frame) {
        super("Options");
        setResizable(false);
        this.frame = frame;
        frame.setVisible(false);
        setLayout(null);

        speedL = new JLabel("Choose speed :");
        speedL.setBounds(20, 20, 120, 30);
        speedCB = new JComboBox(new String[]{"Low", "Medium", "High"});
        speedCB.setBounds(130, 20, 100, 30);
        speedCB.setSelectedIndex(1);
        okB = new JButton("OK");
        okB.setBounds(280, 130, 100, 30);

        add(speedCB);
        add(speedL);
        add(okB);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        okB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSpeed = (String) speedCB.getSelectedItem();
                switch (selectedSpeed) {
                    case "Low":
                        speed = 500;
                        break;
                    case "Medium":
                        speed = 400;
                        break;
                    case "High":
                        speed = 300;
                        break;
                }
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }
}
