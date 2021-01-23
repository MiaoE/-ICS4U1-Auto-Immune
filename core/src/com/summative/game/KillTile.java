package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * KillTile
 * This class in the instance of the KillTile object
 * This object cannot be destroyed
 * Any entity that moves on this object is instantly killed
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 21/13/01
 */
public class KillTile extends Obstacle{
    /**
     * KillTile constructor
     * The only constructor for the object.
     * Passes all the essential information to its super class.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    KillTile(int x, int y, Texture texture){
        super(x, y, texture);
    }
}
