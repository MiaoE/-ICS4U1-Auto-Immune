/**
 * [Entity.java]
 * This class is the superclass of all beings.
 * All beings are able to move and take damage, but not all can attack.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
abstract class Entity extends GameObject implements Damageable, Movable {
    private int health;

    /**
     * Entity Constructor
     * The only constructor of the class.
     * In addition to {@code GameObject}, health value is also essential for this object.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health the initial health value
     * @see GameObject
     */
    Entity(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    /**
     * getHealth
     * Returns the remaining health of the object
     *
     * @return an integer represents the health of an object
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * setHealth
     * Assigns a new health value to the object
     *
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * damageTaken
     * This method is used to take away a certain damage caused by {@code attack} method from {@code Attackable}
     *
     * @param damage the damage taken
     * @see Attackable#attack()
     */
    @Override
    public void damageTaken(int damage) {
        setHealth(getHealth() - damage);
    }

    /**
     * move
     * This method moves an object to a specified coordinate of a board.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    @Override
    public void move(int x, int y) {
        setX(x);
        setY(y);
    }
}
