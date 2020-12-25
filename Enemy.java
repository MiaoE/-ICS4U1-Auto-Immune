/**
 * [Enemy.java]
 * Superclass of all enemy objects
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
abstract class Enemy extends Entity {
    private int attackDamage, attackRange;

    Enemy(int x, int y, int health, int attackDamage, int attackRange){
        super(x, y, health);
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
    }
}
