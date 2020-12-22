import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;

/**
 * [StartFrame.java]
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version
 */
public class StartFrame extends JFrame {
    public StartFrame() {
        super("Start Screen");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        this.add(startPanel);

        JButton startButton = new JButton("START");
        startButton.addActionListener(new StartButtonListener(this));

        this.add(startButton);

        this.setVisible(true);
    }
}
