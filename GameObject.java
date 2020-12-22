/**
 * [GameObject.java]
 * Super class of all objects
 *
 * @author Ayden Gao
 * @version 0.1 20/12/21
 */
abstract class GameObject {
    private int x, y;//position on the game board

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
