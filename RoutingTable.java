import java.util.ArrayList;
import java.util.List;

/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * The class saves the routes to an array list and can make the routes shorter if possible.
 */

public class RoutingTable {

    private ArrayList<Route> table;
    private int routs;

    /**
     * Constructor for the class RoutingTable that creates a routing-table object.
     */
    public RoutingTable() {
        table = new ArrayList<Route>();
        routs = table.size();
    }

    /**
     * Replaces a rout at a given index in this routing-table with another rout, if the given route is shorter.
     *
     * @param index The index of the route in this routing-table to be replaced.
     * @param shortRoute The rout that is to replace the current route.
     */
    public void shortenRoute(int index, Route shortRoute) {
        if ((shortRoute.lengthOfRoute() < getRoute(index).lengthOfRoute())){
            table.set(index, shortRoute);
        }
    }

    /**
     * Gets the route at a given index in this routing-table.
     *
     * @param i The index of the route.
     * @return The route at the given index.
     */
    public Route getRoute(int i) {
        return table.get(i);
    }

    /**
     * Searches and returns a route assigned with a specific event-ID from this routing-table.
     *
     * @param id The specific event-ID.
     * @return The route assigned with the given ID. Returns null if this routing-table does not contain a route
     * assigned with the specific event-ID.
     */
    public Route searchRoute(int id) {
        if(!table.isEmpty()){
            for (int i = 0; i < table.size(); i++){
                if (table.get(i).getId() == id){
                    return table.get(i);
                }
            }
        }

        return null;
    }

    /**
     * Gets the number of routes this routing-table contains.
     *
     * @return The number of routes.
     */
    public int getNumRoutes() {
        return table.size();
    }

    /**
     * Adds a route to the end of this routing-table. If this routing-table already contains a route assigned with the
     * same event-ID and that route is longer than the route to be added, replace the existing route with the shorter
     * route.
     *
     * @param r The route to be added.
     */
    public void addRoute(Route r){
        boolean same = false;
        for (int i = 0; i < table.size(); i++){
            if (table.get(i).getId() == r.getId()){
                shortenRoute(i, r);
                same = true;
                break;
            }
        }
        if (!same){
            table.add(r);
        }

    }
}
