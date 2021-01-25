package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * [GameObject.java]
 * This class is the superclass of all objects in the application.
 * All objects have a coordinate on the plane, and their own texture.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
abstract class GameObject {

    private Point coordinate;//position on the game board
    private final Texture texture;

    /**
     * GameObject Constructor
     * The only constructor of the class.
     * All objects contain a valid coordinate on a plane.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param texture the texture of the sprite
     */
    GameObject(int x, int y, Texture texture) {
        coordinate = new Point(x, y);
        this.texture = texture;
    }

    /**
     * getTexture
     * Gets the texture of the sprite
     *
     * @return texture
     */
    public Texture getTexture(){
        return texture;
    }

    /**
     * getX
     * Returns the x coordinate of an object
     *
     * @return an integer that represents the x coordinate
     */
    public int getX() {
        return coordinate.getX();
    }

    /**
     * getY
     * Returns the y coordinate of an object
     *
     * @return an integer that represents the y coordinate
     */
    public int getY() {
        return coordinate.getY();
    }

    /**
     * getCoordinate
     * Returns the coordinates of the object
     *
     * @return a Point object
     */
    public Point getCoordinate() {
        return new Point(coordinate);
    }

    /**
     * setCoordinate
     * Sets a new set of coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setCoordinate(int x, int y) {
        coordinate.move(x, y);
    }
}
