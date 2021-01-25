package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * [PlayerArtillery.java]
 * An object representing a ranged unit of the {@link Player} side.
 * Ranged unit is able to attack other objects.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class PlayerArtillery extends Player implements Attackable {

    private final int attackDamage;

    /**
     * PlayerArtillery Constructor
     * Contains all the essential information to create a unit.
     *
     * @param x             x position
     * @param y             y position
     * @param health        health
     * @param movementRange the movement range
     * @param attackRange   attack range
     * @param attackDamage  the damage of the attack
     * @param knockback     the knockback
     */
    PlayerArtillery(int x, int y, int health, double movementRange, boolean attackRange, int attackDamage, int knockback) {
        super(x, y, new Texture(Gdx.files.internal("PlayerArtillary.png")), health, movementRange, attackRange, knockback);
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

