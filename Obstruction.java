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
class Obstruction extends Obstacle implements Damageable{
    private int health;

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
        super(x, y);
        this.health = health;
    }

    /**
     * getHealth
     * Returns the remaining health of an object
     *
     * @return an integer represents the health of an object
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * damageTaken
     * This method is used to take away a certain damage from the object's health
     *
     * @param damage the damage taken
     */
    @Override
    public void damageTaken(int damage) {
        health -= damage;
    }
}
