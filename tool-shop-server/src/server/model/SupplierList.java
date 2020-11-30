package server.model;

import java.util.LinkedHashSet;

/**
 * This class represents a list of all suppliers that supply items for the shop.
 *
 * @author Patrick Linang
 * @since October 10, 2020
 */
public class SupplierList {
    /**
     * Tracks the references to all suppliers in the list.
     */
    private LinkedHashSet<Supplier> suppliers;

    /**
     * Constructs a list based on a specified collection of suppliers
     * @param suppliers a LinkedHashSet of Supplier objects
     */
    public SupplierList(LinkedHashSet<Supplier> suppliers) {
        setSuppliers(suppliers);
    }

    /**
     * Adds a new supplier to the list.
     *
     * @param supplier a new Supplier object to add
     */
    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    /**
     * Search for a supplier based on supplier ID and retrieve its reference.
     *
     * @param id the supplier id to search for
     * @return reference to the Supplier object with matching id; else, null.
     */
    public Supplier searchSupplier(int id) {
        for (Supplier supplier: suppliers) {
            if (supplier.getId() == id)
                return supplier;
        }
        return null;
    }

    /**
     * Provides a description for all suppliers listed.
     * @return a String representation of all suppliers currently referenced by the list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        for (Supplier item: suppliers)
            sb.append(item).append('\n');

        // Remove the extra newline character
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

    /**
     * Getter method.
     * @return a reference to the LinkedHashSet of Supplier objects
     */
    public LinkedHashSet<Supplier> getSuppliers() {
        return suppliers;
    }

    /**
     * Setter method.
     * @param suppliers a new LinkedHashSet of Supplier objects to assign
     */
    public void setSuppliers(LinkedHashSet<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
