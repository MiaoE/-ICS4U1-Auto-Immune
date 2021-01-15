/**
 * [PlayerSupport.java]
 * An object representing a support unit of the {@code Player} side.
 * Support unit is able to
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 2.0 21/01/13
 */
public class EnemySupport extends Enemy{
    /**
     * EnemySupport constructor
     * Contains all the essential information to create a unit.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health health
     * @param movementRange the movement range
     * @param attackRange the attack range
     * @param weight the weight
     */
    EnemySupport(int x, int y, int health, double movementRange, boolean attackRange, int weight){
        super(x, y, health, movementRange, attackRange, weight);
    }
}
