package messagemodel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;


public class CustomerList implements Serializable {

    static final long serialVersionUID = 1L;

    private LinkedHashSet<Customer> customers;

    public CustomerList(LinkedHashSet<Customer> customers) {
        setCustomers(customers);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void deleteCustomer(Map<String, String> customerInfoMap) {
        int customerId = Integer.parseInt(customerInfoMap.get("customerId"));
        Customer customer = searchCustomer(customerId);
        if (customer != null)
            customers.remove(customer);
    }

    public void updateCustomer(Map<String, String> customerInfoMap) {
        int customerId = Integer.parseInt(customerInfoMap.get("customerId"));
        Customer customer = searchCustomer(customerId);
        if (customer != null)
            customer.setCustomerInfo(customerInfoMap);
    }

    private Customer searchCustomer(int customerId) {
        for (Customer customer: customers) {
            if (customer.getId() == customerId)
                return customer;
        }
        return null;
    }

    public Map<String, String> getCustomerInfo(int customerId) {
        Customer customer = searchCustomer(customerId);
        if (customer != null)
            return customer.getCustomerInfoMap();
        else
            return null;
    }

    public ArrayList<String> getCustomerStringList() {
        ArrayList<String> customerStringList = new ArrayList<>();
        for (Customer customer: customers)
            customerStringList.add(customer.toString());

        return customerStringList;
    }

    public void setCustomers(LinkedHashSet<Customer> customers) {
        this.customers = customers;
    }
}
