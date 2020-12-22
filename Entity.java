/**
 * [Entity.java]
 *
 *
 */
abstract class Entity extends GameObject implements Damageable {
    private int health;

    public void damageTaken(int damage) {
        health -= damage;

        if(health <= 0) {
            //death method
        }
    }
}
