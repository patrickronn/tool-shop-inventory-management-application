package server.model;

public class IntlSupplier extends Supplier {

    /**
     * The import tax rate required for international supplier.
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
     * @param phoneNum supplier's phone number
     * @param importTax the import tax rate for the supplier
     */
    public IntlSupplier(int id, String companyName, String address, String salesContact, String phoneNum, double importTax) {
        super(id, companyName, address, salesContact, phoneNum, "International");
        setImportTax(importTax);
    }

    public void calculateImportTax() {
        System.out.println("This method calculates import tax using the rate: " + importTax);
    }

    public double getImportTax() {
        return importTax;
    }

    public void setImportTax(double importTax) {
        this.importTax = importTax;
    }

    @Override
    public String toString() {
        return super.toString() + ", Import Tax Rate: " + importTax;
    }
}
