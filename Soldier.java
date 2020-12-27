/**
 * [Soldier.java]
 * Subclass of player
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
public class Soldier extends Player implements Attackable {

    /**
     * Soldier Constructor
     *
     * @param x x position
     * @param y y position
     * @param health health
     * @param attackDamage attack damage
     * @param attackRange attack range
     */
    Soldier(int x, int y, int health, int attackDamage, int attackRange){
        super(x, y, health, attackDamage, attackRange);
    }

    /**
     * attack
     *
     * @return the damage of the attack
     */
    public int attack() {
        return 0;
    }
}
