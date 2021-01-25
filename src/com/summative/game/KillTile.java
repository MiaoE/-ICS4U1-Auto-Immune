package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * [KillTile.java]
 * This class creates an instance of the KillTile object.
 * This object cannot be destroyed.
 * Any entity that moves on this object is instantly killed.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class KillTile extends Obstacle {

    /**
     * KillTile Constructor
     * Passes all the essential information to its super class.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    KillTile(int x, int y) {
        super(x, y, new Texture(Gdx.files.internal("KillTile.png")));
    }
}
