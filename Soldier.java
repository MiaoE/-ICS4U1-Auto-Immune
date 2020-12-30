/**
 * [Soldier.java]
 * An object representing an infantry unit of the {@code Player} side.
 * Infantry is able to attack other objects.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
public class Soldier extends Player implements Attackable {

    /**
     * Soldier Constructor
     * Contains all the essential information to create a unit.
     *
     * @param x            x position
     * @param y            y position
     * @param health       health
     * @param attackDamage attack damage
     * @param attackRange  attack range
     */
    Soldier(int x, int y, int health, int attackDamage, int attackRange) {
        super(x, y, health, attackDamage, attackRange);
    }

    /**
     * attack
     * Attacks a tile.
     *
     * @return the damage of the attack
     */
    @Override
    public int attack() {
        return getAttackDamage();
    }
}
