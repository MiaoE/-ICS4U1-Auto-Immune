/**
 * Hole
 * This class is the instance of the Hole object
 * This object cannot be destroyed
 * Any non flying entity on this object will be instantly destroyed
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 21/01/13
 */
public class Hole extends Obstacle {
    /**
     * Hole constructor
     * The only constructor for the object.
     * Passes all the essential information to its super class.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    Hole(int x, int y){
        super(x, y);
    }
}
