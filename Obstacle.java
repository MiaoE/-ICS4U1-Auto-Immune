/**
 * [Obstacle.java]
 * This class is the super class of all obstacles.
 * All obstacles can be damaged.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
abstract class Obstacle extends GameObject implements Damageable {
    private int health;

    /**
     * Obstacle Constructor
     * The only constructor of the class.
     * In addition to {@code GameObject}, health value is also essential for this object.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health the initial health value
     * @see GameObject
     */
    Obstacle(int x, int y, int health) {
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
