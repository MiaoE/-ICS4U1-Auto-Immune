import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [PlacePlayer.java]
 *
 * @author Eric Miao
 * @version
 */
public class PlacePlayer implements MouseListener {
    GameObject[][] matrix;
    int tileWidth;
    int size;
    Soldier player;

    PlacePlayer(GameObject[][] map, int tileWidth, int size, Soldier player) {
        matrix = map;
        this.tileWidth = tileWidth;
        this.size = size;
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / tileWidth, y = e.getY() / tileWidth;
        if(x >= size || y >= size) {
            System.out.println("Out of bound");
        } else if (matrix[x][y] != null) {
            System.out.println("Tile occupied");
        } else {
            matrix[x][y] = player;
            player.setX(y);
            player.setY(x);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
//do nothing
    }
}
