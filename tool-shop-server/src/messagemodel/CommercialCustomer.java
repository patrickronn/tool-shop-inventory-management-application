package messagemodel;

import java.util.Map;

/**
 * A commercial customer represented by type "C". Extends Customer.
 *
 * @author Patrick Linang
 * @since November 25, 2020
 */
public class CommercialCustomer extends Customer {
    /**
     * Constructs a commercial customer with all customer information provided.
     *
     * @param id unique customer identifier number
     * @param firstName first name
     * @param lastName last name
     * @param address address (e.g. "My house St NW")
     * @param postalCode postal code (e.g. "A1B 2C3")
     * @param phoneNum phone number (e.g. "111-111-1111")
     */
    public CommercialCustomer(int id, String firstName, String lastName, String address, String postalCode, String phoneNum) {
        super(id, firstName, lastName, address, postalCode, phoneNum, "C");
    }

    /**
     * Constructs a commercial customer using a Map containing all required attributes of a customer.
     *
     * Primarily by a controller class to convert user input from a GUI into an object.
     *
     * @param customerInfoMap a Map containing the following keys with appropriate values for each attribute:
     *                        "customerId", "firstName", "lastName", "address", "postalCode", "phoneNum"
     */
    public CommercialCustomer(Map<String, String> customerInfoMap) {
        super(customerInfoMap);
    }
}