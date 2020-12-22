import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * [MyMouseListener.java]
 *
 * @author Eric Miao
 * @version
 */
public class MyMouseListener implements MouseListener {
    private final int tileWidth;
    private final int size;

    public MyMouseListener(int tileWidth, int boardSize) {
        this.tileWidth = tileWidth;
        size = boardSize;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / tileWidth, y = e.getY() / tileWidth;
        if(x >= size || y >= size) {
            System.out.println("Out of bound");
        } else {
            System.out.println("Clicked tile " + x + " " + y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / tileWidth, y = e.getY() / tileWidth;
        if(x >= size || y >= size) {
            System.out.println("Out of bound");
        } else {
            System.out.println("Pressed at " + x + " " + y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX() / tileWidth, y = e.getY() / tileWidth;
        if(x >= size || y >= size) {
            System.out.println("Out of bound");
        } else {
            System.out.println("Released at " + x + " " + y);
        }
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
