package messagemodel;

import server.model.Supplier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Each object of this class represents a type of item sold by the shop.
 *
 * Item have a reference to the item's supplier and also has a method for generating new order line based on an item.
 *
 * @author Patick Linang
 * @since November 25, 2020
 */
public abstract class Item implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Unique ID of an item.
     */
    protected int id;

    /**
     * Item name or description.
     */
    protected String name;

    /**
     * Current stock available of an item.
     */
    protected int quantity;

    /**
     * Price of an item
     */
    protected double price;

    /**
     * Type of item
     */
    protected String type;

    /**
     * The supplier ID, which gets updated whenever a Supplier is assigned to the item.
     */
    protected int supplierId;


    /**
     * Constructs an item based on its description and availability.
     *
     * The supplier of the object remains null and can be assigned afterwards.
     *
     * @param id a unique ID as an integer
     * @param name String name of the item
     * @param quantity current item stock available as an integer
     * @param price price of an item (Example format: '12.43')
     * @param type type of item (e.g. 'Electrical' or 'Non-Electrical')
     */
    protected Item(int id, String name, int quantity, double price, int supplierId, String type) {
        setId(id);
        setName(name);
        setQuantity(quantity);
        setPrice(price);
        setSupplierId(supplierId);
        setType(type);
    }

    public Map<String, String> toMap() {
        Map<String, String> customerInfoMap = new HashMap<>();
        customerInfoMap.put("toolId", String.valueOf(id));
        customerInfoMap.put("name", name);
        customerInfoMap.put("quantity", String.valueOf(quantity));
        customerInfoMap.put("price", String.format("%.2f", price));
        customerInfoMap.put("toolType", type);
        customerInfoMap.put("supplierId", String.valueOf(supplierId));

        return customerInfoMap;
    }

    /**
     * Decreases the quantity of an Item by a specified amount.
     *
     * Reduces an Item's quantity variable and includes a check to ensure the resulting quantity won't be negative.
     *
     * @param quantityToRemove the amount to decrease quantity by (cannot be more than the quantity available)
     * @return the updated quantity after decreasing an item's quantity
     */
    public int decreaseQuantity(int quantityToRemove) {
        // Validation: quantity to remove shouldn't result in a negative quantity
        if (quantity - quantityToRemove < 0)
            throw new IllegalArgumentException("Existing quantity is less than quantity to remove");

        // Decrease quantity
        setQuantity(quantity - quantityToRemove);

        // Return the updated item quantity
        return quantity;
    }

    /**
     * Generates a new OrderLine for an Item with a specified amount to order.
     *
     * @param quantityToOrder the quantity amount to order for the item
     * @return a newly constructed OrderLine object
     */
    public OrderLine generateOrderLine(int quantityToOrder) {
        System.out.println("\nNew order line was made.");
        return new OrderLine(this, quantityToOrder);
    }

    /**
     * Provides a string representation of an item.
     * @return a String representing an Item's id, name, quantity, and price.
     */
    @Override
    public String toString() {
        String priceFormatted = String.format("%.2f", price);
        return id + ", " + name + ", " + type + ", $" + priceFormatted;
    }

    /**
     * Getter method.
     * @return item id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method.
     * @param id the new id value to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method.
     * @return item name or description
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method.
     * @param name the new String name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method.
     * @return item quantity that is currently in stock
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method.
     * @param quantity a new quantity to override the existing quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method
     * @return item price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter method
     * @param price the new price of this item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter method
     * @return item type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method
     * @param type the new price of this item
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter method
     * @param supplierId the new supplier Id of this item
     */
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * Getter method.
     * @return id of the item supplier
     */
    public int getSupplierId() {
        return this.supplierId;
    }

}
