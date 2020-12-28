/**
 * [Obstruction.java]
 * Sub class of obstacle
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/21
 */
class Obstruction extends Obstacle implements Damageable{
    private int health;

    Obstruction(int x, int y, int health){
        super(x, y);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void damageTaken(int damage){
        this.health = this.health - damage;
    }
}
