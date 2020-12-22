import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class GamePanel extends JPanel {
    int[][] matrix;

    public GamePanel(int[][] map) {
        matrix = map;

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
                if(matrix[i][j] < 6) {
                    g.setColor(Color.GREEN);
                } else if(matrix[i][j] == 7) {
                    g.setColor(Color.GRAY);
                } else if(matrix[i][j] == 8) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLUE);
                }

                g.fillRect(i*50, j*50, 50, 50);
                
                g.setColor(Color.BLACK);
                g.drawRect(i*50, j*50, 50, 50);
            }
        }
    }
}
