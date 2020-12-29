/**
 * [Entity.java]
 * Superclass of all movable objects
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
abstract class Entity extends GameObject implements Damageable, Movable {
    private int health;

    Entity(int x, int y, int health){
        super(x, y);
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void damageTaken(int damage){
        setHealth(getHealth() - damage);
    }

    /**
     * move
     *
     * @param x x destination
     * @param y y destination
     */
    @Override
    public void move(int x, int y) {
        setX(x);
        setY(y);
    }
}
