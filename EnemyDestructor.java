/**
 * [EnemyDestructor.java]
 * An object representing an destructor unit of the {@code Enemy} side.
 * Destructor unit will create holes on the tiles it attacks
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 21/01/14
 */
public class EnemyDestructor extends Enemy implements Attackable{
    private final int attackDamage;

    /**
     * EnemyDestructor
     * Contains all the essential information to create a unit.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health health of the unit
     * @param movementRange movement range
     * @param attackRange attack range
     * @param weight weight
     * @param attackDamage the attack damage
     */
    EnemyDestructor(int x, int y, int health, double movementRange, boolean attackRange, int weight, int attackDamage){
        super(x, y, health, movementRange, attackRange, weight);
        this.attackDamage = attackDamage;
    }

    /**
     * attack
     * Attacks a tile.
     *
     * @return the damage of the attack
     */
    @Override
    public int attack() {
        return attackDamage;
    }
}
