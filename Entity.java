/**
 * [Entity.java]
 * Superclass of all movable objects
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
abstract class Entity extends GameObject implements Damageable {
    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    Entity(int x, int y, int health){
        super(x, y);
        this.health = health;
    }

    public void damageTaken(int damage){
        setHealth(getHealth() - damage);
    }
}
