package messagemodel;

import java.io.Serializable;

public class Message implements Serializable {
    private String action;
    private String objectType;
    private Serializable objectToSend;

    static final long serialVersionUID = 1L;

    public Message(String action, String objectType, Serializable objectToSend) {
        this.action = action;
        this.objectType = objectType;
        this.objectToSend = objectToSend;
    }

    public String getAction() {
        return action;
    }

    public String getObjectType() {
        return objectType;
    }

    public Serializable getObject() {
        return objectToSend;
    }
}
