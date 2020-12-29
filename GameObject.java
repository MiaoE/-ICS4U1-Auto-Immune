/**
 * [GameObject.java]
 * Super class of all objects
 *
 * @author Ayden Gao
 * @version 1.0 20/12/29
 */
abstract class GameObject {

    private int x, y;//position on the game board

    GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }

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
