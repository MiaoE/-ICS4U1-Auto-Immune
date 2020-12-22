import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * [GamePanel.java]
 *
 * @author Eric Miao
 * @version
 */
public class GamePanel extends JPanel {
    GameObject[][] matrix;
    final int tileWidth;

    public GamePanel(GameObject[][] map, int tileWidth) {
        matrix = map;

        this.tileWidth = tileWidth;

        this.requestFocusInWindow();

        Thread t = new Thread(new Runnable() {
            public void run() {
                animate();
            }
        });
        t.start();

    }

    /**
     * animate
     * The main game loop - this is where the game state is updated
     */
    public void animate() {
        while (true) {
            this.repaint();
        }
    }

    public void paintComponent(Graphics g) {
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == null) {
                    g.setColor(Color.GREEN);
                } else if(matrix[i][j] instanceof Player) {
                    g.setColor(Color.PINK);
                } else if(matrix[i][j] instanceof Enemy) {
                    g.setColor(Color.RED);
                } else if(matrix[i][j] instanceof Obstruction){//obstacle
                    g.setColor(Color.BLUE);
                }else{//vital
                    g.setColor(Color.ORANGE);
                }

                g.fillRect(i*tileWidth, j*tileWidth, tileWidth, tileWidth);

                g.setColor(Color.BLACK);
                g.drawRect(i*tileWidth, j*tileWidth, tileWidth, tileWidth);
            }
        }
    }
}
