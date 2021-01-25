package com.summative.game;

/**
 * [Point.java]
 * A class that stores a set of coordinate.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class Point {

    private int x, y;

    /**
     * Point Constructor
     * Sets the coordinate to the coordinate of a specified {@code Point}
     *
     * @param point the Point object
     */
    Point(Point point) {
        x = point.getX();
        y = point.getY();
    }

    /**
     * Point Constructor
     * Sets the coordinate to a specified coordinate
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * move
     * Update the coordinate
     *
     * @param x the updated x coordinate
     * @param y the updated y coordinate
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getX
     * Getter for x variable
     *
     * @return the x value
     */
    public int getX() {
        return x;
    }

    /**
     * getY
     * Getter for y variable
     *
     * @return the y value
     */
    public int getY() {
        return y;
    }

    /**
     * toString
     * For debugging: prints the coordinates to console
     *
     * @return String that contains the coordinates
     */
    @Override
    public String toString() {
        return (x + " " + y);
    }
}
