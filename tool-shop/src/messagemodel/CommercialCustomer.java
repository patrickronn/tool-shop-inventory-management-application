package messagemodel;

import java.util.Map;

public class CommercialCustomer extends Customer {
    public CommercialCustomer(int id, String firstName, String lastName, String address, String postalCode, String phoneNum) {
        super(id, firstName, lastName, address, postalCode, phoneNum, "C");
    }

    public CommercialCustomer(Map<String, String> customerInfoMap) {
        super(customerInfoMap);
    }
}