/**
 * [PlayerWarrior.java]
 * An object representing an infantry unit of the {@code Player} side.
 * Infantry is able to attack other objects.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
public class PlayerWarrior extends Player implements Attackable {
    private int attackDamage;

    /**
     * Soldier Constructor
     * Contains all the essential information to create a unit.
     *
     * @param x            x position
     * @param y            y position
     * @param health       health
     * @param attackRange  attack range
     * @param attackDamage the damage of the attack
     */
    PlayerWarrior(int x, int y, int health, double movementRange, int attackRange, int attackDamage) {
        super(x, y, health, movementRange, attackRange);
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
