package messagemodel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Represents a managed collection of Customer objects.
 *
 * @author Patrick Linang
 * @since November 25, 2020
 */
public class CustomerList implements Serializable {
    static final long serialVersionUID = 1L;

    /**
     * Collection of customers.
     */
    private LinkedHashSet<Customer> customers;

    /**
     * Constructs a customer list to manage a collection of customers
     * @param customers a LinkedHashSet of Customer objects
     */
    public CustomerList(LinkedHashSet<Customer> customers) {
        setCustomers(customers);
    }

    /**
     * Adds a new customer to customer list.
     * @param customer a Customer to add
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Uses a Map of customer info to search for an existing customer and removes it from the list if found.
     * @param customerInfoMap a Map of customer info which contains a "customerId" key
     */
    public void deleteCustomer(Map<String, String> customerInfoMap) {
        int customerId = Integer.parseInt(customerInfoMap.get("customerId"));
        Customer customer = searchCustomer(customerId);
        if (customer != null)
            customers.remove(customer);
    }

    /**
     * Uses a Map of customer info to search for an existing customer and updates customer info if found.
     * @param customerInfoMap a Map containing the following keys with appropriate values for each attribute:
     *                        "customerId", "firstName", "lastName", "address", "postalCode", "phoneNum"
     */
    public void updateCustomer(Map<String, String> customerInfoMap) {
        int customerId = Integer.parseInt(customerInfoMap.get("customerId"));
        Customer customer = searchCustomer(customerId);
        if (customer != null)
            customer.setCustomerInfo(customerInfoMap);
    }

    /**
     * Helper method to search for an existing customer
     * @param customerId customer ID to search
     * @return reference to the found Customer object; otherwise null
     */
    private Customer searchCustomer(int customerId) {
        for (Customer customer: customers) {
            if (customer.getId() == customerId)
                return customer;
        }
        return null;
    }

    /**
     * Searches for a customer.
     *
     * @param customerId customer ID to search
     * @return a Map containing customer info in key-value pairs
     */
    public Map<String, String> getCustomerInfoMap(int customerId) {
        Customer customer = searchCustomer(customerId);
        if (customer != null)
            return customer.toMap();
        else
            return null;
    }

    /**
     * Used to gather customer information as a collection of strings.
     *
     * @return returns an ArrayList of string representations of each customer in the list
     */
    public ArrayList<String> getCustomerStringList() {
        ArrayList<String> customerStringList = new ArrayList<>();
        for (Customer customer: customers)
            customerStringList.add(customer.toString());

        return customerStringList;
    }

    /**
     * Updates the managed collection of customers in the list.
     * @param customers a LinkedHashSet containing Customer objects
     */
    public void setCustomers(LinkedHashSet<Customer> customers) {
        this.customers = customers;
    }
}
