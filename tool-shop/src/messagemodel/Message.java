package messagemodel;

import java.io.Serializable;

/**
 * Provides standardized communication between a client and the server. Contains a Serializable object that
 * gets sent via socket with a description the object being sent and the requested action to perform.
 *
 * @author Patrick Linang
 * @since November 25, 2020
 */
public class Message implements Serializable {
    /**
     * The requested action to perform. (e.g. "insert", "remove", "update")
     */
    private String action;

    /**
     * The type of object being sent (e.g. "customer", "itemparameters")
     */
    private String objectType;

    /**
     * The object to send (e.g. Customer, LinkedHashSet of Items)
     */
    private Serializable objectToSend;

    /**
     * Serialization version.
     */
    static final long serialVersionUID = 1L;

    /**
     * Constructs a message with requested action and an object.
     * @param action the requested action to perform
     * @param objectType description of the object being sent
     * @param objectToSend a Serializable object being sent
     */
    public Message(String action, String objectType, Serializable objectToSend) {
        this.action = action;
        this.objectType = objectType;
        this.objectToSend = objectToSend;
    }

    /**
     * Getter method
     * @return requested action
     */
    public String getAction() {
        return action;
    }

    /**
     * Getter method
     * @return object type
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Getter method
     * @return reference to the Object stored in the message
     */
    public Serializable getObject() {
        return objectToSend;
    }
}
