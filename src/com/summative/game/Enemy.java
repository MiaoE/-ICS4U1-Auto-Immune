package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * [Enemy.java]
 * This class is the super class of all enemy units.
 * All units have an attack range and an attack damage.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
abstract class Enemy extends Entity implements Attackable {

    private Point attack;
    private final int weight;
    private final int attackDamage;

    /**
     * Enemy Constructor
     * Contains all the essential information to create a Enemy object.
     *
     * @param x             the x coordinate
     * @param y             the y coordinate
     * @param texture       the texture of the sprite
     * @param health        the initial health value
     * @param movementRange the range of movement
     * @param attackRange   the attack range
     * @param weight        the weight
     * @param damage        the damage
     */
    Enemy(int x, int y, Texture texture, int health, double movementRange, boolean attackRange, int weight, int damage) {
        super(x, y, texture, health, movementRange, attackRange);
        this.weight = weight;
        attackDamage = damage;
    }

    /**
     * getAttackY
     * Gets the y coordinate of the attack destination.
     *
     * @return the coordinates of the attack destination
     */
    public Point getAttack() {
        return attack;
    }

    /**
     * setAttackY
     * Sets the y coordinate of the attack location next turn.
     *
     * @param attack the coordinates of the next attack
     */
    public void setAttack(Point attack) {
        this.attack = attack;
    }

    /**
     * getWeight
     * Gets the weighing of the enemy unit. This is a variable that restricts
     * the spawning of enemies each round.
     *
     * @return the weight of the enemy unit
     */
    public int getWeight() {
        return weight;
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
