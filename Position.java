import java.util.Objects;

/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * Is the class that holds the position for the nodes.
 */
public class Position {

    private int x;
    private int y;


    /**
     * The position for the robot in the maze in x and y axis.
     *
     * @param x position in x-axis
     * @param y position in y-axis
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x axis position.
     *
     * @return x axis position.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y axis position.
     *
     * @return y axis position.
     */
    public int getY() {
        return y;
    }


    /**
     * Check if two positions are equals.
     *
     * @param o an object
     * @return true or false.
     */
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position temp = (Position) o;
            return x == temp.getX() && y == temp.getY();
        }
        return false;
    }

    /**
     * Make a hashcode from position x-axis and y-axis.
     *
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Calculate the distance between to positions.
     *
     * @param p The position that you want to see the distance too.
     * @return The distance too the position.
     */
    public double distance(Position p) {
        double distanceX = Math.abs(p.x - this.x);
        double distanceY = Math.abs(p.y - this.y);
        return Math.hypot(distanceX, distanceY);
    }

    /**
     * Generates and gets a string filled with the coordinates of this position.
     *
     * @return The generated string.
     */
    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
