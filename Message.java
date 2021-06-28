
/**
 * @author CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * Abstract class for all types of messages in this network.
 */
public abstract class Message {

    protected boolean isAlive;
    protected int lifeTime;
    private RoutingTable routes;
    protected Node position;
    public int age;

    /**
     * Constructor for the class Message that creates a message object.
     *
     * @param lifeTime The amount of steps this message is allowed to take.
     * @param node     The node this message is going to spawn on.
     */
    public Message(int lifeTime, Node node) {
        this.position = node;
        this.lifeTime = lifeTime;
        age = 0;
        routes = new RoutingTable();
    }

    /**
     * Is abstract
     */
    public abstract void move();

    /**
     * Sets this message to sleep.
     */
    protected void die() {
        isAlive = false;
    }

    /**
     * Returns whether a node is busy or not.
     *
     * @param node The node to look at if it's busy.
     * @return A boolean representing if the given node is busy.
     */
    protected boolean sendingMessage(Node node) {
        return node.isSleeping();
    }
}
