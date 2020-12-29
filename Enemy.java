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
    private int attackX = -1, attackY = -1;

    Enemy(int x, int y, int health, int attackDamage, int attackRange){
        super(x, y, health);
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage){
        this.attackDamage = attackDamage;
    }

    public int getAttackY() {
        return attackY;
    }

    public void setAttackY(int attackY) {
        this.attackY = attackY;
    }

    public int getAttackX() {
        return attackX;
    }

    public void setAttackX(int attackX) {
        this.attackX = attackX;
    }
}
