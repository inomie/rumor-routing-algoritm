
/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * The event class is storing the id of the event and the time it happened on.
 */
public class Event {

    private int id;
    private int timeStep;

    /**
     * Constructor for the class and write in the id-number and the timeStep.
     *
     * @param id Is the id-number of the Event.
     * @param timeStep
     */
    public Event(int id, int timeStep) {
        this.id = id;
        this.timeStep = timeStep;
    }

    /**
     * Get's the timeStep of the event.
     *
     * @return The timeStep of the event.
     */
    public int getTimeStep() {
        return this.timeStep;
    }

    /**
     * Compare two events to see if they are the same or not.
     *
     * @param event The event that's going too compare with the event on the node.
     * @return False or True.
     */
    public boolean equals(Object event) {
        if (event instanceof Event) {
            return this.id == ((Event) event).id;
        }
        return false;
    }

    /**
     * Get's the id-number of the event.
     *
     * @return The id-number.
     */
    public int getId() {
        return this.id;
    }

    /**
     * A to-string function which writes the time-step and ID of the event into a string.
     *
     * @return The string containing time-step and ID.
     */
    @Override
    public String toString() {
        String string = "timestep: " + timeStep + " ID: " + id + "\n";
        return string;
    }
}
