package com.summative.game;

/**
 * [Damageable.java]
 * An interface that is implemented by objects that can take damage from an attack.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public interface Damageable {

    /**
     * damageTaken
     * This method is used to take away a certain damage from an object's health
     *
     * @param damage the damage taken
     */
    void damageTaken(int damage);

    /**
     * getHealth
     * Returns the remaining health of an object
     *
     * @return an integer represents the health of an object
     */
    int getHealth();
}
