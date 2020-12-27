/**
 * [Warrior.java]
 * Subclass of enemy
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
public class Warrior extends Enemy implements Attackable {

    /**
     * Warrior Constructor
     * @param x x position
     * @param y y position
     * @param health health
     * @param attackDamage attack damage
     * @param attackRange attack range
     */
    Warrior(int x, int y, int health, int attackDamage, int attackRange){
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

    /**
     * move
     *
     * @param x x destination
     * @param y y destination
     */
    public void move(int x, int y) {
        //because setX setY are public in GameObject, we don't need super.
        //however, I'm not sure if those methods should be private.
        //If they are, then we need setX() setY() for each of its subclasses, including
        //the superclass for {@code this} class, which is {@code Enemy} class
        super.setX(x);
        super.setY(y);
    }
}
