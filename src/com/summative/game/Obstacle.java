package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * [Obstacle.java]
 * This class is the super class of all obstacles.
 * All obstacles can be damaged.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
abstract class Obstacle extends GameObject {
    /**
     * Obstacle Constructor
     * The only constructor of the class.
     * In addition to {@link GameObject}, health value is also essential for this object.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate
     * @param texture the texture of the sprite
     */
    Obstacle(int x, int y, Texture texture) {
        super(x, y, texture);
    }

    /**
     * setCoordinate
     * As obstacles cannot be moved, overriding is required to restrict that.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    @Override
    public void setCoordinate(int x, int y) {
        System.out.println("You cannot move this object");
    }
}
