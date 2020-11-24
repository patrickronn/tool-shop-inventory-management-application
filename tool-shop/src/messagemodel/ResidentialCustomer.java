package messagemodel;

import java.util.Map;

public class ResidentialCustomer extends Customer {
    public ResidentialCustomer(int id, String firstName, String lastName, String address, String postalCode, String phoneNum) {
        super(id, firstName, lastName, address, postalCode, phoneNum,"R");
    }

    public ResidentialCustomer(Map<String, String> customerInfoMap) {
        super(customerInfoMap);
    }
}