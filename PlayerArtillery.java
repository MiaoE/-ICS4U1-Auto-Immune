/**
 * [PlayerArtillery.java]
 * An object representing a ranged unit of the {@code Player} side.
 * Ranged unit is able to attack other objects.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 2.0 21/01/07
 */
public class PlayerArtillery extends Player implements Attackable {
    private final int attackDamage;

    /**
     * Soldier Constructor
     * Contains all the essential information to create a unit.
     *
     * @param x            x position
     * @param y            y position
     * @param health       health
     * @param attackRange  attack range
     * @param attackDamage the damage of the attack
     * @param movementRange the movement range
     * @param knockback  the knockback
     */
    PlayerArtillery(int x, int y, int health, double movementRange, boolean attackRange, int attackDamage, int knockback) {
        super(x, y, health, movementRange, attackRange, knockback);
        this.attackDamage = attackDamage;
    }

    /**
     * attack
     * Attacks a tile.
     *
     * @return the damage of the attack
     */
    @Override
    public int attack() {
        return attackDamage;
    }
}

