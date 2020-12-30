/**
 * [GameObject.java]
 * This class is the superclass of all objects in the project.
 *
 * @author Ayden Gao
 * @version 1.0 20/12/29
 */
abstract class GameObject {

    private int x, y;//position on the game board

    /**
     * GameObject Constructor
     * The only constructor of the class.
     * All objects contain a valid coordinate on a plane.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getX
     * Returns the x coordinate of an object
     *
     * @return an integer that represents the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * setX
     * Sets a new x coordinate of an object
     *
     * @param x the new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getY
     * Returns the y coordinate of an object
     *
     * @return an integer that represents the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * setY
     * Sets a new y coordinate of an object
     *
     * @param y the new y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
}
