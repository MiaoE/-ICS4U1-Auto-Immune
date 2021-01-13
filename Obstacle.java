/**
 * [Obstacle.java]
 * This class is the super class of all obstacles.
 * All obstacles can be damaged.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
abstract class Obstacle extends GameObject {
    /**
     * Obstacle Constructor
     * The only constructor of the class.
     * In addition to {@code GameObject}, health value is also essential for this object.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @see GameObject
     */
    Obstacle(int x, int y) {
        super(x, y);
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
