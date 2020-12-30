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
    private final int attackDamage, attackRange;
    private int attackX = -1, attackY = -1;

    /**
     * Enemy Constructor
     * Contains all the essential information to create an enemy object.
     *
     * @param x            the x coordinate
     * @param y            the y coordinate
     * @param health       the initial health value
     * @param attackDamage the attack damage
     * @param attackRange  the attack range
     */
    Enemy(int x, int y, int health, int attackDamage, int attackRange) {
        super(x, y, health);
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
    }

    /**
     * getAttackDamage
     * Returns the attack damage of the unit
     *
     * @return an integer that represents the attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
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
