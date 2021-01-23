package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * [Vital.java]
 * This class is an instance of a vital object.
 * The main objective of the project is to protect the vital and prevent further harm to vitals.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
public class Vital extends Obstacle implements Damageable{
    private int health;

    /**
     * Vital Constructor
     * The only constructor of the class.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health the initial health value
     */
    Vital(int x, int y, Texture texture, int health) {
        super(x, y, texture);
        this.health = health;
    }

    /**
     * getHealth
     * Returns the remaining health of an object
     *
     * @return an integer represents the health of an object
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * damageTaken
     * This method is used to take away a certain damage from the object's health
     *
     * @param damage the damage taken
     */
    @Override
    public void damageTaken(int damage) {
        health -= damage;
    }
}
