/**
 * [Soldier.java]
 * Subclass of player
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
public class Soldier extends Player implements Attackable {
    public int attack() {
        return 0;
    }

    Soldier(int x, int y, int health, int attackDamage, int attackRange){
        super(x, y, health, attackDamage, attackRange);
    }
}
