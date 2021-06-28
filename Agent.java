import java.util.LinkedList;
import java.util.Random;
/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * The class agent is moving the agent to an random neighbour and saves the route it takes to each node.
 */
public class Agent extends Message{

    private RoutingTable routes;
    private Node currentNode;
    private double randomDouble;
    private int step;
    private int id;
    public int time;

    /**
     * Constructor for the class Agent that creates a agent object assigned to a event.
     *
     * @param id The event-ID.
     * @param maxStep The amount of steps this agent is allowed to take.
     * @param node The node this agent is going to spawn on.
     */
    public Agent(int id, int maxStep, Node node) {
        super(maxStep, node);
        currentNode = node;
        lifeTime = maxStep;
        routes = new RoutingTable();
        Route route = new Route(id);
        route.addNode(currentNode);
        routes.addRoute(route);
        step = 0;
        this.id = id;
        time = -1;
    }

    /**
     * Moves this agent and increments it's taken steps. Each time the agent takes a step it will add the new
     * current node to its own route and synchronize its routing-table with the current node's routing-table.
     * If it can't move to the chosen neighbour this time-step it will put itself in the current node's message-queue.
     */
    @Override
    public void move() {

        // Check so the agent haven't taken to many steps.
        if(step < lifeTime) {
            LinkedList<Node> neighbour = currentNode.neighbours();
            randomDouble = Math.random();
            randomDouble = randomDouble * neighbour.size();
            int randomInt = (int) randomDouble;
            // Get a random neighbour.
            Node node = currentNode.getNeighbour(randomInt);
            // Check so the node is not sending message and that it haven't done the same thing this timeStep.
            if(!sendingMessage(node) && time != currentNode.getTime()) {
                // Get the route from the node to the agent.
                for(int j = 0; j < currentNode.getRoutingTable().getNumRoutes(); j++) {
                    routes.addRoute(currentNode.getRoutingTable().getRoute(j));
                }
                // Put the route from the agent to the node.
                for(int j = 0; j < routes.getNumRoutes(); j++) {
                    currentNode.getRoutingTable().addRoute(routes.getRoute(j));
                }
                // Add current node to the routes.
                for(int j = 0; j < routes.getNumRoutes(); j++) {
                    routes.getRoute(j).addNode(currentNode);
                }
                // Take a step to the new node.
                this.currentNode = node;
                step++;

            }
            currentNode.addMessage(this);
            // Update the time this happened.
            time = currentNode.getTime();
        }

    }

    /**
     * Uses in test purpose.
     * @return this agents current node.
     */
    public Node getCurrentNode() {
        return this.currentNode;
    }

    /**
     * Uses in test purpose.
     * @return this agents routingTable.
     */
    public RoutingTable getRoutes() {
        return this.routes;
    }

}
