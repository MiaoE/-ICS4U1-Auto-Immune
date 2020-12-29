/**
 * [Obstruction.java]
 * Sub class of obstacle
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
class Obstruction extends Obstacle implements Damageable {
    private int health;

    Obstruction(int x, int y, int health) {
        super(x, y);
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void damageTaken(int damage) {
        health -= damage;
    }
}
