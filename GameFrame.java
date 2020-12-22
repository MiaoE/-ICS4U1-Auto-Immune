import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.util.Random;

/**
 * [GameFrame.java]
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version
 */
public class GameFrame extends JFrame {

    int[][] matrix;
    final int tileWidth;
    final int size = 8;

    public GameFrame() {
        super("Demo Game");
        matrix = new int[size][size];

        tileWidth = 50;

        generateMap(matrix);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.add(new GamePanel(matrix, tileWidth));

        this.setUndecorated(true);//no border

        this.addMouseListener(new MyMouseListener(tileWidth, size));

        this.setVisible(true);
    }

    private void generateMap(int[][] map) {
        Random random = new Random();

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
    }
}
