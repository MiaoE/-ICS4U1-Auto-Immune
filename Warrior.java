/**
 * [Warrior.java]
 * Subclass of enemy
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 0.1 20/12/22
 */
public class Warrior extends Enemy implements Attackable {
    public int attack() {
        return 0;
    }

    Warrior(int x, int y, int health, int attackDamage, int attackRange){
        super(x, y, health, attackDamage, attackRange);
    }

    public void move(int x, int y){
        super.setX(x);
        super.setY(y);
    }
}
