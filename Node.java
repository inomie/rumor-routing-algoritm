import java.util.LinkedList;
import java.util.Random;

/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * The class node is updating every thing (events, query, agent).
 */
public class Node {

    private LinkedList<Node> neighbours;
    private RoutingTable routingTable;
    private LinkedList<Event> event;
    private boolean queryNode;
    private boolean sleeping;
    private Position position;
    private LinkedList<Message> messageList;
    private LinkedList<Query> sentQueries;
    private Environment environment;

    /**
     * Constructor for the class Node that creates a node object.
     *
     * @param position The position of the node.
     * @param environment The environment in which the node is to be placed.
     */
    public Node(Position position, Environment environment) {

        neighbours = new LinkedList<>();
        routingTable = new RoutingTable();
        event = new LinkedList<>();
        queryNode = false;
        sleeping = false;
        this.position = position;
        messageList = new LinkedList<>();
        sentQueries = new LinkedList<>();
        this.environment = environment;

    }

    /**
     * Sets this node's boolean attribute queryNode to be true or false.
     *
     * @param queryNode the boolean to represent if this node should be a query-node.
     */
    public void setQueryNode(boolean queryNode) {

        this.queryNode = queryNode;
    }

    /**
     * Gets this node's routingtable.
     *
     * @return the routingtable.
     */
    public RoutingTable getRoutingTable() {
        return this.routingTable;
    }

    /**
     * Updates this node and sets it be awake, which potentially includes detecting an event, resending or deleting queries, spawning an
     * agent, spawning a query, receiving or sending a message.
     */
    public void update() {
        // Se if an event happens on this node.
        Event temp = detectEvent();
        sleeping = false;
        // Only query nodes checks if they have got the message back or not.
        if(queryNode) {
            // Loop through the linked list of all the query the node have sent.
            for(int i = 0; i < sentQueries.size(); i++) {
                Query query = sentQueries.get(i);
                // Check if the time from the query is sent equal maxSteps times 8.
                if(getTime() - query.getTime() == (8 * Environment.queryMaxSteps)) {
                    // Check if the time from the query is sent equal (maxSteps * 8 * 2) to see if have gotten an answer otherwise send a new query.
                    if(getTime() - query.getTime() == (8 * Environment.queryMaxSteps)*2) {
                        deleteQuery(query.getEventId());
                    } else {
                        sendNewQuery(query.getEventId());
                    }
                }
            }
        }

        // If an event happened.
        if(temp != null) {
            // Add this event to the list of all the events for the node.
            event.add(temp);
            double random = Math.random();
            // Generate an random number to get 50% that an agent will span.
            if(random <= environment.agentProbability) {
                Agent agent = new Agent(temp.getId(), environment.agentMaxSteps, this);
                addMessage(agent);
            }

        }
        // If it's an queryNode and the (timeStep modulus queryFrequency) equals zero.
        if(queryNode == true && (environment.getTime() % environment.queryFrequency) == 0) {
            // Make a new query and add it to the list of all the query the node have sent.
            sentQueries.add(createQuery());
        }
        // Check so the message list is not empty.
        if(messageList.peekFirst() != null){
            Message message = messageList.getFirst();
            message.move();
            messageList.remove(message);
        }
    }



    /**
     * Gets this node's list of neighbours.
     *
     * @return the list of neighbours.
     */
    public LinkedList<Node> neighbours() {
        return neighbours;
    }

    /**
     * Sets this node's list of neighbours.
     *
     * @param neighbours The list of neighbours containing the neighbour-nodes.
     */
    public void setNeighbours(LinkedList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Gets a neighbour-node at a given index from this node's neighbours-list.
     *
     * @param i The index in the list.
     * @return The neighbour-node.
     */
    public Node getNeighbour(int i) {

        return neighbours.get(i);
    }

    /**
     * Creates a query for a random unhandled event.
     *
     * @return The query object created.
     */
    public Query createQuery() {
        sendToSleep();
        LinkedList<Integer> EventsIds = environment.getEventsId();
        Random random = new Random();
        int id = -1;
        while(!EventsIds.contains(id)) {
            id = random.nextInt(EventsIds.getLast() + 1);
        }

        Query query = new Query(id, environment.queryMaxSteps, this);
        addMessage(query);

        return query;
    }

    /**
     * Returns if this node is sleeping or not.
     *
     * @return A boolean representing if this node is sleeping.
     */
    public boolean isSleeping() {
        return sleeping;
    }

    /**
     * Adds a given message to this nodes message-list and puts this node to sleep.
     *
     * @param message The message to be added to this nodes message-list.
     */
    public void addMessage(Message message) {
        sendToSleep();
        messageList.add(message);
    }

    /**
     * Potentially makes this node detect an event, the probability is set by the eventProbability.
     *
     * @return The detected event, null if no event was detected.
     */
    public Event detectEvent() {
        Event temp = null;
        double random = Math.random();
        if(random <= environment.eventProbability) {
            temp = new Event(environment.generateEventId(), environment.getTime());
        }

        return temp;
    }

    /**
     * Gets this node's position.
     *
     * @return This node's position.
     */
    public Position getPosition() {

        return this.position;
    }

    /**
     * Sets this node to sleep.
     */
    public void sendToSleep() {
        this.sleeping = true;
    }

    /**
     * Invoke this node's environment to remove a specific event-ID from the list of unhandled Events.
     *
     * @param id The event-ID to be removed.
     */
    public void removeId(int id) {
        environment.removeEventId(id);
    }

    /**
     * Gets a specific event from this node's list of detected events.
     *
     * @param id The event-ID.
     * @return The Event with the specific event-ID, returns null if the event-list contains no such event.
     */
    public Event getEvent(int id){
        for (Event event: event) {
            if (event.getId() == id){
                return event;
            }
        }
        return  null;
    }

    /**
     * Gets the current time-step of this node's environment.
     *
     * @return The current time-step of this node's environment.
     */
    public int getTime() {
        return environment.getTime();
    }

    /**
     * Deletes a query with a specific event-ID from this node's list of sent queries.
     *
     * @param eventId The specific event-ID of the query to be removed.
     */
    public void deleteQuery(int eventId) {
        for(int i = 0; i < sentQueries.size(); i++) {
            Query query = sentQueries.get(i);
            if(query.getEventId() == eventId) {
                sentQueries.remove(i);
            }
        }
    }

    /**
     * Spawns a new iteration of a query with a specific event-ID.
     *
     * @param id The specific event-ID.
     */
    public void sendNewQuery(int id) {
        sendToSleep();

        Query query = new Query(id, environment.queryMaxSteps, this);
        addMessage(query);

    }

    /**
     * Uses in test purpose.
     * @param event the event to be added in to the list.
     */
    public void addEvent(Event event) {
        this.event.add(event);
    }

    /**
     * Function used for testing
     * @return list of sent messages
     */
    public LinkedList<Message> GetMessages(){
        return messageList;
    }
    /**
     * Function used for testing
     * @return list of sent queries
     */
    public LinkedList<Query> GetSentQueries(){
        return sentQueries;
    }
    /**
     * Function used for testing
     * Adds the given Query to the SentQueries list
     * @param query the given query
     */
    public void addToSentQueries(Query query) {
        sentQueries.add(query);
    }


}
