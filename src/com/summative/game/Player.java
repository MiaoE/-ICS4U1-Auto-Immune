package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * [Player.java]
 * This class is the super class of all player units.
 * All units have an attack range; however, not all units will deal damage, as some units has other abilities.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
abstract class Player extends Entity {

    private final int knockback;
    private boolean moved;
    private boolean attacked;

    /**
     * Player Constructor
     * Contains all the essential information to create a player object.
     *
     * @param x             the x coordinate
     * @param y             the y coordinate
     * @param texture       the texture of the sprite
     * @param health        the initial health value
     * @param movementRange the range of movement
     * @param attackRange   the attack range
     * @param knockback     the tiles for knockback
     */
    Player(int x, int y, Texture texture, int health, double movementRange, boolean attackRange, int knockback) {
        super(x, y, texture, health, movementRange, attackRange);
        this.knockback = knockback;
    }

    /**
     * isMoved
     * Returns if the unit already moved in the same round.
     *
     * @return true if the unit has moved, false otherwise
     */
    public boolean isMoved() {
        return moved;
    }

    /**
     * setMoved
     * Sets the player movement status of the round.
     *
     * @param moved true if the player moves, false when the turn ends
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * isAttacked
     * Returns if the unit already attacked in the same round.
     *
     * @return true if the unit has attacked, false otherwise
     */
    public boolean isAttacked() {
        return attacked;
    }

    /**
     * setAttacked
     * Sets the player attack status of the round
     *
     * @param attacked true if the player attacks, false when the turn ends
     */
    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    /**
     * getKnockback
     * Gets the knockback value of the player object
     *
     * @return the knockback
     */
    public int getKnockback() {
        return knockback;
    }
}
