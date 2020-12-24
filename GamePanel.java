import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * [GamePanel.java]
 *
 * @author Eric Miao
 * @version 1.0
 */
public class GamePanel extends JPanel {
    GameObject[][] matrix;
    final int tileWidth;
    ArrayList<Warrior> enemyList;

    public GamePanel(GameObject[][] map, int tileWidth, ArrayList<Warrior> enemyList) {
        matrix = map;
        this.tileWidth = tileWidth;
        this.enemyList = enemyList;

        this.requestFocusInWindow();

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    animate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * animate
     * The main game loop - this is where the game state is updated
     */
    public void animate() throws InterruptedException {
        while (true) {
            this.repaint();
            //Moves the contents of the enemyList
            for (int i = 0; i < enemyList.size(); i++) {
                TimeUnit.SECONDS.sleep(1);
                this.repaint();//board must be updated to show the movement
                enemyMove(matrix, enemyList.get(i));
            }
        }
    }

    /**
     * enemyMove
     * moves the inputted enemy on the game board
     *
     * @param board the game board
     * @param enemy the enemy to move
     */
    private void enemyMove(GameObject[][] board, Warrior enemy) {
        int[] ans = findEnemyXY(board, enemy);

        board[enemy.getX()][enemy.getY()] = null;//makes prev position null
        enemy.move(ans[0], ans[1]);//changes x and y
        board[ans[0]][ans[1]] = enemy;//changes position on the game board

    }

    //this method will be changed to get actual good x and y cords for enemy
    /**
     * findEnemyXY
     * gets the x and y coordinates of the inputted enemy
     * (currently random x and y)
     *
     * @param board the game board
     * @param enemy the enemy to find x and y for
     * @return the x and y coordinates
     */
    private int[] findEnemyXY(GameObject[][] board, Warrior enemy) {
        int[] ans = new int[2];
        Random random = new Random();
        do {//finds random x and y for enemy
            ans[0] = random.nextInt(8);
            ans[1] = random.nextInt(8);
        } while (board[ans[0]][ans[1]] != null);
        return ans;
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == null) {
                    g.setColor(Color.GREEN);
                } else if (matrix[i][j] instanceof Player) {
                    g.setColor(Color.PINK);
                } else if (matrix[i][j] instanceof Enemy) {
                    g.setColor(Color.RED);
                } else if (matrix[i][j] instanceof Obstruction) {//obstacle
                    g.setColor(Color.BLUE);
                } else {//vital
                    g.setColor(Color.ORANGE);
                }

                g.fillRect(i * tileWidth, j * tileWidth, tileWidth, tileWidth);

                g.setColor(Color.BLACK);
                g.drawRect(i * tileWidth, j * tileWidth, tileWidth, tileWidth);
            }
        }
    }
}
