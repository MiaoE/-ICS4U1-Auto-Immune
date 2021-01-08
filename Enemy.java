/**
 * [Enemy.java]
 * This class is the super class of all enemy units.
 * All units have an attack range; however, not all units will deal damage, as some units can heal allies.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
abstract class Enemy extends Entity {
    /*
     * Attack damage and range is constant throughout, hence it is final.
     */
    private Point attack;
    private final int weight;

    /**
     * Enemy Constructor
     * Contains all the essential information to create a Enemy object.
     *
     * @param x            the x coordinate
     * @param y            the y coordinate
     * @param health       the initial health value
     * @param movementRange the range of movement
     * @param attackRange  the attack range
     */
    Enemy(int x, int y, int health, double movementRange, int attackRange, int weight) {
        super(x, y, health, movementRange, attackRange);
        this.weight = weight;
    }

    /**
     * getAttackY
     * Gets the y coordinate of the attack destination.
     *
     * @return the coordinates of the attack destination
     */
    public Point getAttack() {
        return attack;
    }

    /**
     * setAttackY
     * Sets the y coordinate of the attack location next turn.
     *
     * @param attack the coordinates of the next attack
     */
    public void setAttack(Point attack) {
        this.attack = attack;
    }

    /**
     * getWeight
     * Gets the weighing of the enemy unit. This is a variable that restricts
     * the spawning of enemies each round.
     *
     * @return the weight of the enemy unit
     */
    public int getWeight() {
        return weight;
    }
}
