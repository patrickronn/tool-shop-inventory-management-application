package client.messagemodel;

import java.util.Map;

public class CommercialCustomer extends Customer {
    public CommercialCustomer(int id, String firstName, String lastName, String phoneNum, String address, String postalCode) {
        super(id, "Commercial", firstName, lastName, phoneNum, address, postalCode);
    }

    public CommercialCustomer(Map<String, String> customerInfoMap) {
        super(customerInfoMap);
    }
}