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

    GameObject[][] matrix;
    final int tileWidth;
    final int size = 8;

    public GameFrame() {
        super("Demo Game");
        matrix = new GameObject[size][size];

        tileWidth = 50;

        generateMap(matrix);
        generateObstacles(matrix, 6);
        generateEnemies(matrix, 3);
        generateVitals(matrix, 2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.add(new GamePanel(matrix, tileWidth));

        this.setUndecorated(true);//no border

        this.addMouseListener(new MyMouseListener(tileWidth, size, matrix));

        this.setVisible(true);
    }

    private void generateMap(GameObject[][] map) {
        //Random random = new Random();

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                //matrix[i][j] = random.nextInt(10);
                matrix[i][j] = null;
            }
        }
    }

    private void generateObstacles(GameObject[][] map, int obstacleNum) {
        Random random = new Random();
        int x,y;
        for(int i = 0; i < obstacleNum; i++){
            do{
                x = random.nextInt(8);
                y = random.nextInt(8);
            }while(map[y][x] != null);
            map[y][x] = new Obstruction(y, x,3);
        }
    }

    private void generateVitals(GameObject[][] map, int vitalNum) {
        Random random = new Random();
        int x,y;
        for(int i = 0; i < vitalNum; i++){
            do{
                x = random.nextInt(8);
                y = random.nextInt(8);
            }while(map[y][x] != null);
            map[y][x] = new Vital(y, x,2);
        }
    }

    private void generateEnemies(GameObject[][] map, int enemyNum){
        Random random = new Random();
        int x,y;
        for(int i = 0; i < enemyNum; i++){
            do{
                x = random.nextInt(8);
                y = random.nextInt(8);
            }while(map[y][x] != null);
            map[y][x] = new Warrior(y, x, 3, 1, 5);
        }
    }
}
