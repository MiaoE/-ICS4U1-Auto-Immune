/**
 * [Obstruction.java]
 * This class is an instance of an obstruction object.
 * This obstacle can be destroyed.
 * But when this obstacle is on the plane, no {@code Entity} can traverse to or on the obstacle.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
class Obstruction extends Obstacle {

    /**
     * Obstruction Constructor
     * The only constructor for the object.
     * Passes all the essential information to its super class.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health the initial health value
     */
    Obstruction(int x, int y, int health) {
        super(x, y, health);
    }
}
