package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * [PlayerSupport.java]
 * An object representing a support unit of the {@link Player} side.
 * Support unit is able to knockback {@link Enemy}
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class PlayerSupport extends Player {

    /**
     * PlayerSupport constructor
     * Contains all the essential information to create a unit.
     *
     * @param x             the x coordinate
     * @param y             teh y coordinate
     * @param health        health of object
     * @param movementRange the movement range of object
     * @param attackRange   the attack range of object
     * @param knockback     the knockback
     */
    PlayerSupport(int x, int y, int health, double movementRange, boolean attackRange, int knockback) {
        super(x, y, new Texture(Gdx.files.internal("PlayerSupport.png")), health, movementRange, attackRange, knockback);
    }
}
