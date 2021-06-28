import java.util.ArrayList;
import java.util.List;

/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * Route saves a path to the event.
 */

public class Route {

    private ArrayList<Node> route;
    private int eventId;
    private int length;

    /**
     * Constructor for the class Route that creates a route object assigned to a event.
     *
     * @param id The ID of the assigned event.
     */
    public Route(int id) {
        route = new ArrayList<Node>();
        this.eventId = id;
        length = route.size();

    }

    /**
     * Returns the length of this route.
     *
     * @return The amount of nodes in this route.
     */
    public int lengthOfRoute() {
        return this.length;
    }

    /**
     * Gets the event-ID assigned to this route.
     *
     * @return The event-ID.
     */
    public int getId() {
        return  this.eventId;
    }

    /**
     * Adds a node to the end of this route and increments the length of this route.
     *
     * @param node The node to be added.
     */
    public void addNode(Node node) {
        route.add(node);
        length = route.size();
    }

    /**
     * Gets the last node in this route.
     *
     * @return The last node in this route, returns null if the route is empty.
     */
    public Node getLast(){
        if(!route.isEmpty()) {
            return route.get(route.size() - 1);
        }
        return null;
    }
}
