package com.summative.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * [GameObject.java]
 * This class is the superclass of all objects in the project.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
abstract class GameObject {

    private Point coordinate;//position on the game board
    private Texture texture;

    /**
     * GameObject Constructor
     * The only constructor of the class.
     * All objects contain a valid coordinate on a plane.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    GameObject(int x, int y, Texture texture) {
        coordinate = new Point(x, y);
        this.texture = texture;
    }

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
