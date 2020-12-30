/**
 * [Vital.java]
 * This class is an instance of a vital object.
 * The main objective of the project is to protect the vital and prevent further harm to vitals.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
public class Vital extends Obstacle {

    /**
     * Vital Constructor
     * The only constructor of the class.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health the initial health value
     */
    Vital(int x, int y, int health) {
        super(x, y, health);
    }
}
