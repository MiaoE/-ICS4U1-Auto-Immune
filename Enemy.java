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
    /*Attack damage and range is constant throughout, hence it is final.*/
    private int attackX = -1, attackY = -1;

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
    Enemy(int x, int y, int health, double movementRange, int attackRange) {
        super(x, y, health, movementRange, attackRange);
    }

    /**
     * getAttackY
     * Gets the y coordinate of the attack destination.
     *
     * @return the y coordinate
     */
    public int getAttackY() {
        return attackY;
    }

    /**
     * setAttackY
     * Sets the y coordinate of the attack location next turn.
     *
     * @param attackY the y coordinate of the next attack
     */
    public void setAttackY(int attackY) {
        this.attackY = attackY;
    }

    /**
     * getAttackX
     * Gets the x coordinate of the attack destination
     *
     * @return the x coordinate
     */
    public int getAttackX() {
        return attackX;
    }

    /**
     * setAttackX
     * Sets the x coordinate of the attack location next turn.
     *
     * @param attackX the x coordinate of the next attack
     */
    public void setAttackX(int attackX) {
        this.attackX = attackX;
    }
}
