package messagemodel;

import server.model.Supplier;

import java.io.Serializable;

/**
 * This class represents order lines for an order which includes the item to order and the amount to order.
 *
 * @author Patrick Linang
 * @since November 25, 2020
 */
public class OrderLine implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * The item to order.
     */
    private Item itemToOrder;

    /**
     * The supplier to order from
     */
    transient private Supplier supplier;

    /**
     * The amount of an item to order.
     */
    private int quantityToOrder;

    /**
     * The order it belongs to.
     */
    private Order order;

    /**
     * Constructs an order line for a specified item and amount to order.
     * The Order reference is set to null and can be assigned using setter method.
     *
     * @param itemToOrder an Item object that needs to be ordered
     * @param quantityToOrder the quantity of Item object to order
     */
    public OrderLine(Item itemToOrder, int quantityToOrder) {
        setItemToOrder(itemToOrder);
        setSupplier(null);
        setQuantityToOrder(quantityToOrder);
        setOrder(null);
    }

    /**
     * Getter method.
     * @return reference to Item object to be ordered
     */
    public Item getItemToOrder() {
        return itemToOrder;
    }

    /**
     * Setter method.
     * @param itemToOrder set the Item object reference
     */
    public void setItemToOrder(Item itemToOrder) {
        this.itemToOrder = itemToOrder;
    }

    /**
     * Getter method.
     * @return the quantity to be ordered
     */
    public int getQuantityToOrder() {
        return quantityToOrder;
    }

    /**
     * Setter method.
     * @param quantityToOrder an updated quantity number to be ordered
     */
    public void setQuantityToOrder(int quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }

    /**
     * Getter method.
     * @return reference to the Order object
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Setter method.
     * @param order assigns the Order object
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Getter method.
     * @return reference to the Supplier object
     */
    public Supplier getSupplier() {
        return supplier;
    }
    /**
     * Setter method.
     * @param supplier assigns the Supplier object
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
