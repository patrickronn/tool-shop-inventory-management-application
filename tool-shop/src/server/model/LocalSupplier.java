package server.model;

public class LocalSupplier extends Supplier {

    /**
     * Constructs a local supplier based on their contact information.
     *
     * The items supplied are initialized as an empty collected; each item can be added afterwards.
     *
     * @param id unique ID integer
     * @param companyName supplier's name as a String
     * @param address supplier's address as a String
     * @param salesContact name of person to contact at company as a String
     */
    public LocalSupplier(int id, String companyName, String address, String salesContact) {
        super(id, companyName, address, salesContact, "Local");
    }
}
