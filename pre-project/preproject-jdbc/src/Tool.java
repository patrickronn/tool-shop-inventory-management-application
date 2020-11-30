/**
 * This class represents a tool with fields matching from the items.txt file.
 * This is used for preproject exercise on using prepared statements and JDBC.
 *
 * @author Patrick Linang
 * @since November 24, 2020
 */
public class Tool {
    /**
     * Tool ID number
     */
    private int ID;

    /**
     * Tool name
     */
    private String name;

    /**
     * Tool quantity in the inventory stock
     */
    private int quantity;

    /**
     * Unit price of a tool
     */
    private double price;

    /**
     * ID of the supplier of the tool
     */
    private int supplierID;

    /**
     * Constructs the tool with all attributes specified.
     * @param ID tool id
     * @param name tool name
     * @param quantity tool quantity
     * @param price tool price
     * @param supplierID supplier id who supplies the tool
     */
    public Tool(int ID, String name, int quantity, double price, int supplierID) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierID = supplierID;
    }

    // GETTERS AND SETTERS BELOW
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * Overrides toString() method to provide ID, name, quantity, price, and supplier id of a tool.
     * @return String representation of all the tool's attributes.
     */
    @Override
    public String toString() {
        return ID + " " + name + " " + quantity + " " + price + " " + supplierID;
    }
}
