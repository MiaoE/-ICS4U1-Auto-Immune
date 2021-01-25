package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * [EnemyArtillery.java]
 * An object representing a ranged unit of the {@code Enemy} side.
 * Ranged unit is able to attack other objects.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class EnemyArtillery extends Enemy implements Attackable {

    /**
     * EnemyArtillery Constructor
     * Contains all the essential information to create a unit.
     *
     * @param x            x position
     * @param y            y position
     * @param health       health
     * @param attackRange  attack range
     * @param attackDamage the damage of the attack
     */
    EnemyArtillery(int x, int y, int health, double movementRange, boolean attackRange, int weight, int attackDamage) {
        super(x, y, new Texture(Gdx.files.internal("EnemyArtillery.png")), health, movementRange, attackRange, weight, attackDamage);

    }
}
