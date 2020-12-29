/**
 * [Player.java]
 * Superclass of all player objects
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
abstract class Player extends Entity {
    private int attackDamage, attackRange;
    private boolean moved;
    private boolean attacked;

    Player(int x, int y, int health, int attackDamage, int attackRange){
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

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }
}
