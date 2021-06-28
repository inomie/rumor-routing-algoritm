import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * Environment is the class that's reads in the the file with all the nodes and make new nodes for each position.
 */
public class Environment {

    public static final double agentProbability = 0.5;
    public static final double eventProbability = 0.0001;
    public static final int queryFrequency = 400;
    public static final int nodeReachRadius = 15;
    public static final int agentMaxSteps = 50;
    public static final int queryMaxSteps = 45;
    public final HashMap<Position, Node> sensorHashMap;
    private int timeStep;
    private int amountNodes;
    private LinkedList<Integer> id;

    /**
     * Constructor for the class Environment that creates a environment object.
     *
     * @param file A text file which contains number of nodes and coordinates for each node.
     */
    public Environment(Reader file) throws IOException {
        String array[];

        timeStep = 0;
        sensorHashMap = new HashMap<Position, Node>();
        id = new LinkedList<>();

        BufferedReader reader = new BufferedReader(file);
        try {
            // Reads the first line to get the number of nodes.
            amountNodes = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new IOException("Cant read file");
        }

        // Reads the position for each node and put them in a hashmap with the position as a key.
        for(int i = 0 ; i < amountNodes ; i++) {
            try {
                String string = reader.readLine();
                array = string.split(",");
                Position position = new Position(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
                Node node = new Node(position, this);
                sensorHashMap.put(position, node);
            } catch (IOException e) {
                throw new IOException("Cant read file");
            }

        }

        Random generator = new Random();
        Object[] values = sensorHashMap.values().toArray();

        //Set four random nodes to be query nodes.
        for(int i = 0; i < 4; i++) {
            Node node = (Node) values[generator.nextInt(values.length)];
            node.setQueryNode(true);
        }

        // Sets neighbour for each node.
        setNeighbours();
    }

    /**
     * Sets the neighbour nodes of each node in the environment.
     */
    public void setNeighbours() {
        Object[] values = sensorHashMap.values().toArray();
        int i = 0;
        // Loops through the array with all the nodes.
        while(i < values.length) {
            int j = 0;
            Node node = (Node)values[i];
            LinkedList<Node> neighbours = new LinkedList<>();
            // Loops through the array with all the nodes and see if they are neighbours.
            while(j < values.length) {
                Node node1 = (Node)values[j];
                // Compare so the nodes is not the same one and see so the distance between them is in the reach.
                if(!node.equals(node1) && node.getPosition().distance(node1.getPosition()) <= nodeReachRadius){
                    neighbours.add(node1);
                }
                j++;
            }
            // Sends in a linked list of neighbours to the node.
            node.setNeighbours(neighbours);
            i++;
        }

    }

    /**
     * Updates the time-step of the environment and updates each node in the environment.
     */
    public void timeStepUpdate() {
        timeStep++;

        for (Position key : sensorHashMap.keySet() ) {
            Node node = sensorHashMap.get(key);
            node.update();

        }

    }

    /**
     * Gets the current time_step of the environment.
     *
     * @return the current time-step of the environment.
     */
    public int getTime() {
        return timeStep;
    }

    /**
     * Generates a unique ID.
     *
     * @return an unique ID.
     */
    public int generateEventId() {
        int temp;
        if(id.isEmpty()) {
            temp = 1;
        }
        else {
            temp = id.getLast();
            temp++;
        }
        id.addLast(temp);
        return temp;
    }

    /**
     * Gets the list of all unhandled event ID:s.
     *
     * @return the list of all unhandled event ID:s.
     */
    public LinkedList getEventsId() {
        return id;
    }

    /**
     * Removes a specific ID from the list of unhandled ID:s
     *
     * @param id the ID that is to be removed.
     */
    public void removeEventId(int id) {
        this.id.removeFirstOccurrence(id);
    }
}

