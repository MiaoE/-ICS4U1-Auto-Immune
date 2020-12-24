import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

/**
 * [GameFrame.java]
 *
 * @author Ayden Gao
 * @author Eric Miao
 */
public class GameFrame extends JFrame {

    GameObject[][] matrix;
    final int tileWidth;
    final int size = 8;

    public GameFrame() {
        super("Demo Game");
        matrix = new GameObject[size][size];
        ArrayList<Warrior> enemyList = createEnemyList(3);

        tileWidth = 50;

        //map generation
        generateMap(matrix);
        generateObstacles(matrix, 6);
        generateEnemies(matrix, enemyList);
        generateVitals(matrix, 2);

        //player generation

        //j frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.add(new GamePanel(matrix, tileWidth, enemyList));
        this.setUndecorated(true);//no border
        this.addMouseListener(new MyMouseListener(tileWidth, size, matrix));
        this.setVisible(true);

        placePlayer();

    }

    private void placePlayer() {
        Soldier player = new Soldier(0, 0, 3, 1, 1);
        PlacePlayer place = new PlacePlayer(matrix, tileWidth, size, player);
        this.addMouseListener(place);
        while (true) {
            int playerCount = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] instanceof Soldier) {
                        playerCount++;
                    }
                }
            }
            if (playerCount == 1) {
                break;
            }
        }
        this.removeMouseListener(place);
    }

    /**
     * creates the game board
     *
     * @param board dude idk you made the method
     */
    private void generateMap(GameObject[][] board) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //matrix[i][j] = random.nextInt(10);
                matrix[i][j] = null;
            }
        }
    }

    /**
     * Adds obstacles to the game board
     *
     * @param board       the board
     * @param obstacleNum the number of obstacles to add
     */
    private void generateObstacles(GameObject[][] board, int obstacleNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < obstacleNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Obstruction(y, x, 3);
        }
    }

    /**
     * Adds vitals to the game board
     *
     * @param board    the board
     * @param vitalNum the number of vitals to add
     */
    private void generateVitals(GameObject[][] board, int vitalNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < vitalNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Vital(y, x, 2);
        }
    }

    /**
     * Adds enemies to the game board
     *
     * @param board     the board
     * @param enemyList the list of enemies to add
     */
    private void generateEnemies(GameObject[][] board, ArrayList<Warrior> enemyList) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < enemyList.size(); i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);

            //changes x and y of enemy objects
            enemyList.get(i).setX(y);
            enemyList.get(i).setY(x);
            board[y][x] = enemyList.get(i);
        }
    }

    /**
     * Creates an arrayList containing the enemies on the board
     *
     * @param enemyNum the number of enemies to be created
     * @return the arrayList of enemies
     */
    private ArrayList<Warrior> createEnemyList(int enemyNum) {
        ArrayList<Warrior> enemyList = new ArrayList<>();
        for (int i = 0; i < enemyNum; i++) {
            enemyList.add(new Warrior(0, 0, 3, 1, 1));
        }
        return enemyList;
    }
}
