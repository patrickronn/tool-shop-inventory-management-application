public class Tool {
    private int ID;
    private String name;
    private int quantity;
    private double price;
    private int supplierID;

    public Tool(int ID, String name, int quantity, double price, int supplierID) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierID = supplierID;
    }

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

    @Override
    public String toString() {
        return ID + " " + name + " " + quantity + " " + price + " " + supplierID;
    }
}
