import java.util.LinkedList;
import java.util.Stack;

/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * The class is moving the query and check's if the nodes is what is searching for.
 */

public class Query extends Message{

    private int eventTime;
    private boolean finalized;
    private Node currentNode;
    private Stack<Node> ownRoute;
    private Event eventMessage;
    private Node eventPosition;
    private int eventId;
    private double randomDouble;
    private int time;
    private int maxStep;
    private int step;

    /**
     * Constructor for the class Query that creates a query object assigned to a event.
     *
     * @param id The event-ID.
     * @param maxStep The amount of steps this query is allowed to take.
     * @param currentNode The node this query is going to spawn on.
     */
    public Query(int id, int maxStep, Node currentNode) {
        super(maxStep, currentNode);
        this.maxStep = maxStep;
        this.eventId = id;
        this.currentNode = currentNode;
        ownRoute = new Stack<Node>();
        ownRoute.push(this.currentNode);
        time = -1;
        step = 0;
        eventMessage = null;
        isAlive = true;
        eventTime = 0;
    }

    /**
     * Makes the query move a step back towards its origin or if it's at its origin, print the information it possesses
     * and kills this query.
     */
    private void returnToOrigin() {

        if (ownRoute.empty()) {
            if(eventMessage != null){
                currentNode.removeId(eventMessage.getId());
                currentNode.deleteQuery(eventId);
            }
            String string = toString();
            System.out.println(string);
            die();
        } else {
            currentNode = ownRoute.pop();
        }
    }

    /**
     * Generates and gets a string filled with the information of the event this query possesses.
     *
     * @return The generated string.
     */
    @Override
    public String toString() {
        return "EventId: " + eventId + " EventPosition: " + eventPosition.getPosition().toString() +
                " EventTidsSteg: " + eventTime + "\n";
    }

    /**
     * Moves this query and increments it's taken steps. Each time before the query takes a step it will check if it
     * has been finalized, if not, it will check if the current node's routing table contains the route to the query's
     * assigned event-ID. If the table contains a route to the event-ID, look if the next node in this route is
     * sleeping, if not, move to that node. If the current node's routing-table contains no route with the query's
     * assigned event-ID, look up a random neighbour and look if the neighbour is sleeping, if not, move to the
     * neighbour. Finally, if the query can't be sent because it's not finalized, can't move along a route with the
     * assigned event-ID or move to a random neighbour, then the query will add itself to the current node's message-
     * list and update the current time-step.
     */
    public void move() {
        // Check that it's alive and not have taken more then maxStep.
        if(step < (maxStep) && isAlive) {
            // Check if it's on the event node and if it's not happened this timeStep already.
            if (finalized && time != currentNode.getTime()){
                returnToOrigin();
            // Check if the nodes routingTable have the event and if it's not happened this timeStep already.
            }else if (currentNode.getRoutingTable().searchRoute(eventId) != null && time != currentNode.getTime()){
                // Check if the node is sending message.
                if(!sendingMessage(currentNode.getRoutingTable().searchRoute(eventId).getLast())) {
                    this.currentNode = currentNode.getRoutingTable().searchRoute(eventId).getLast();
                    ownRoute.push(currentNode);
                    // Check if the node have the event in it.
                    if(currentNode.getEvent(eventId) != null) {
                        finalized = true;
                        eventPosition = currentNode;
                        eventMessage = currentNode.getEvent(eventId);
                        eventTime = eventMessage.getTimeStep();
                    }
                }
            // Check if it's not happened this timeStep already.
            }else if(time != currentNode.getTime()){
                randomDouble = Math.random();
                randomDouble = (randomDouble * currentNode.neighbours().size());
                int randomInt = (int) randomDouble;
                Node node = currentNode.getNeighbour(randomInt);
                // Check if the node have the event in it.
                if(node.getEvent(eventId) != null) {
                    finalized = true;
                    eventPosition = node;
                    eventMessage = node.getEvent(eventId);
                }
                // Check if node is sending message and so the new node is not in query's ownRoute.
                if(!sendingMessage(node) && !ownRoute.contains(node)) {
                    this.currentNode = node;
                    step++;
                    ownRoute.push(currentNode);
                } else {
                    for(int i = 0; i < currentNode.neighbours().size(); i++) {
                        if(!ownRoute.contains(currentNode.getNeighbour(i))) {
                            break;
                        } else if(!sendingMessage(node)){
                            this.currentNode = node;
                            step++;
                            ownRoute.push(currentNode);
                            break;
                        }
                    }

                }


            }

            currentNode.addMessage(this);
            time = currentNode.getTime();
        }
    }


    /**
     * Gets the time-step of when the query's assigned event occurred.
     *
     * @return The time-step .
     */
    public int getTime() {
        return eventTime;
    }

    /**
     * Gets the event-ID of the query's assigned event.
     *
     * @return The event-ID.
     */
    public int getEventId() {
        return eventId;
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
     * @return boolean representing if this query is finalized.
     */
    public boolean isFinalized() {
        return finalized;
    }

}



