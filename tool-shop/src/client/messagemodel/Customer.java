package client.messagemodel;

import java.util.Map;

public abstract class Customer {
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

    protected Customer(int id, String firstName, String lastName, String address, String postalCode, String phoneNum, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.type = type;
    }

    protected Customer(Map<String, String> customerInfoMap) {
        setCustomerInfo(customerInfoMap);
    }

    public void setCustomerInfo(Map<String, String> customerInfoMap) {
        this.id = Integer.parseInt(customerInfoMap.get("customerId"));
        this.firstName = customerInfoMap.get("firstName");
        this.lastName = customerInfoMap.get("lastName");
        this.address = customerInfoMap.get("address");
        this.postalCode = customerInfoMap.get("postalCode");
        this.phoneNum = customerInfoMap.get("phoneNum");
        this.type = customerInfoMap.get("customerType");
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }
}
