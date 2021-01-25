package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * [SpawnTile.java]
 * Instance of a SpawnTile
 * Enemies will spawn from these tiles, they will spawn with the same x and y coordinates as the spawn tile.
 * {@link Player} is able to cover the spawn tile to prevent enemies from spawning, or able to knockback
 * other {@link Enemy} objects to land on top of the spawn tile to prevent spawning.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
class SpawnTile extends Obstacle {

    private final Enemy enemy;

    /**
     * SpawnTile
     * The only constructor for the object.
     * Passes all the essential information to its super class.
     *
     * @param x     the x coordinate of the object
     * @param y     the y coordinate of the object
     * @param enemy the type of enemy to be spawned
     */
    SpawnTile(int x, int y, Enemy enemy) {
        super(x, y, new Texture(Gdx.files.internal("SpawnTile.png")));
        this.enemy = enemy;
    }

    /**
     * getEnemy
     * returns the enemy contained within the SpawnTile
     *
     * @return the enemy to be spawned
     */
    public Enemy getEnemy() {
        return enemy;
    }
}
