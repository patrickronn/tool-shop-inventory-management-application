package messagemodel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an abstract customer which contains customer information.
 * To clarify, a "customer" entity is the same as a "client" entity from the project problem description.
 *
 * @author Patrick Linang
 * @since Nov 25, 2020
 */
public abstract class Customer implements Serializable {
    static final long serialVersionUID = 1L;

    /**
     * Unique ID of a customer
     */
    protected int id;

    /**
     * Customer's first name
     */
    protected String firstName;

    /**
     * Customer's last name
     */
    protected String lastName;

    /**
     * Customer's address.
     */
    protected String address;

    /**
     * Customer's postal code
     */
    protected String postalCode;

    /**
     * Customer's phone number
     */
    protected String phoneNum;

    /**
     * Type of customer (e.g. residential customer)
     */
    protected String type;

    /**
     * Constructs a customer with all customer information provided. Only subclasses can invoke this method.
     *
     * @param id unique customer identifier number
     * @param firstName first name
     * @param lastName last name
     * @param address address (e.g. "My house St NW")
     * @param postalCode postal code (e.g. "A1B 2C3")
     * @param phoneNum phone number (e.g. "111-111-1111")
     * @param type type of customer specified by each subclasses
     */
    protected Customer(int id, String firstName, String lastName, String address, String postalCode, String phoneNum, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.type = type;
    }

    /**
     * Constructs a customer using a Map containing all required attributes of a customer.
     *
     * Primarily by a controller class to convert user input from a GUI into an object.
     *
     * @param customerInfoMap a Map containing the following keys with appropriate values for each attribute:
     *                        "customerId", "firstName", "lastName", "address", "postalCode",
     *                        "phoneNum", "customerType"
     */
    protected Customer(Map<String, String> customerInfoMap) {
        setCustomerInfo(customerInfoMap);
    }

    /**
     * Updates customer info based on a Map containing all required attributes of a customer.
     *
     * Primarily by a controller class to convert user input from a GUI into an object.
     *
     * @param customerInfoMap a Map containing the following keys with appropriate values for each attribute:
     *                        "customerId", "firstName", "lastName", "address", "postalCode",
     *                        "phoneNum", "customerType"
     */
    public void setCustomerInfo(Map<String, String> customerInfoMap) {
        this.id = Integer.parseInt(customerInfoMap.get("customerId"));
        this.firstName = customerInfoMap.get("firstName");
        this.lastName = customerInfoMap.get("lastName");
        this.address = customerInfoMap.get("address");
        this.postalCode = customerInfoMap.get("postalCode");
        this.phoneNum = customerInfoMap.get("phoneNum");
        this.type = customerInfoMap.get("customerType");
    }

    /**
     * Getter method.
     * @return customer id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method.
     * @param id customer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Converts the customer's attributes into a Hash Map.
     * Each key is the attribute name and value is attribute value.
     *
     * This primary use of this method is to display item information on GUI string-based functionalities.
     *
     * @return a Map containing item attributes
     */
    public Map<String, String> toMap() {
        Map<String, String> customerInfoMap = new HashMap<>();
        customerInfoMap.put("customerId", String.valueOf(this.id));
        customerInfoMap.put("firstName", this.firstName);
        customerInfoMap.put("lastName", this.lastName);
        customerInfoMap.put("address", this.address);
        customerInfoMap.put("postalCode", this.postalCode);
        customerInfoMap.put("phoneNum", this.phoneNum);
        customerInfoMap.put("customerType", this.type);

        return customerInfoMap;
    }

    /**
     * Getter method
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter method
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter method
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter method
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter method
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method
     * @param address address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter method
     * @param postalCode postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter method
     * @return phone num
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Setter method
     * @param phoneNum phone num
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Getter method
     * @return customer type
     */
    public String getType() {
        return type;
    }

    /**
     * @return overrides toString() to display customer id, name, and type info
     */
    @Override
    public String toString() {
        return (id + " " + firstName + " " + lastName + " " + type);
    }
}
