/**
 * [Player.java]
 * Superclass of all player objects
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
abstract class Player extends Entity {
    private final int attackDamage, attackRange;
    private boolean moved;

    Player(int x, int y, int health, int attackDamage, int attackRange){
        super(x, y, health);
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
