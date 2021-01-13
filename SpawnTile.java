/**
 * [SpawnTile.java]
 * Instance of a SpawnTile
 * Enemies will spawn from these tiles ei, they will spawn with the same x and y coordinates as the spawn tile
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 21/01/13
 */
class SpawnTile extends Obstacle {
    private Enemy enemy;

    /**
     * SpawnTile
     * The only constructor for the object.
     * Passes all the essential information to its super class.
     *
     * @param x     the x coordinate of the object
     * @param y     the y coordinate of the object
     * @param enemy the enemy to be spawned
     */
    SpawnTile(int x, int y, Enemy enemy) {
        super(x, y);
        this.enemy = enemy;
    }

    /**
     * getEnemy
     * returns the enemy contained within the SpawnTile
     *
     * @return the enemy contained
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * setEnemy
     * I dont think this is used
     *
     * @param enemy the enemy
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
