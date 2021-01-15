/**
 * [PlayerSupport.java]
 * An object representing a support unit of the {@code Player} side.
 * Support unit able to knockback moveable objects
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 21/01/13
 */
public class PlayerSupport extends Player {
    /**
     * PlayerSupport constructor
     * @param x the x coordinate
     * @param y teh y coordinate
     * @param health health of object
     * @param movementRange the movement range of object
     * @param attackRange the attack range of object
     * @param knockback the knockback
     */
    PlayerSupport(int x, int y, int health, double movementRange, boolean attackRange, int knockback){
        super(x, y, health, movementRange, attackRange, knockback);
    }
}
