package server.model;

public class IntlSupplier extends Supplier {

    /**
     * The import tax charge required for international suppliers.
     */
    private double importTax;

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
    public IntlSupplier(int id, String companyName, String address, String salesContact) {
        super(id, companyName, address, salesContact, "International");
    }

    public void calculateImportTax() {
        // Arbitrarily set import tax to $100.00.
        setImportTax(100.0);
    }

    public double getImportTax() {
        return importTax;
    }

    public void setImportTax(double importTax) {
        this.importTax = importTax;
    }
}
