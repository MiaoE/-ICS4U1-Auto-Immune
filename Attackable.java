package com.summative.game;

/**
 * [Attackable.java]
 * An interface that is implemented by objects that is able to attack another object on a plane.
 *
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
public interface Attackable {

    /**
     * attack
     *
     * @return an integer represents the damage that an object does
     */
    int attack();
}
