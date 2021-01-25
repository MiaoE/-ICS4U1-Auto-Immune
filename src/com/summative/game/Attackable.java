package com.summative.game;

/**
 * [Attackable.java]
 * An interface that is implemented by objects that is able to attack another object on a plane.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public interface Attackable {

    /**
     * attack
     *
     * @return an integer represents the damage that an object does
     */
    int attack();
}
