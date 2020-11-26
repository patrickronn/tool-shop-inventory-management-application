package messagemodel;

import server.model.Supplier;
import server.model.SupplierList;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * This class represents an order consisting of order lines for items that need to be ordered.
 *
 * Includes methods to add new order lines update existing order lines. Each order has a randomized order ID
 * and keep track of order date.
 *
 * @author Patrick Linang
 * @since November 25, 2020
 */
public class Order implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Accumulates order lines for any item that should be ordered.
     */
    private LinkedHashSet<OrderLine> orderLines;

    /**
     * A randomized ID value that falls within the range of MIN_ID and MAX_ID (inclusive)
     */
    private int id;

    /**
     * The order date (e.g. date order was created).
     */
    private String date;

    /**
     * Constant integer representing the smallest ID value possible.
     */
    private final static int MIN_ID = 10000;

    /**
     * Constant integer representing the largest ID value possible
     */
    private final static int MAX_ID = 99999;

    /**
     * Constructs an order with a randomized id number and assigned date and an empty order.
     * The orderLines collection is initialized as empty; new order lines are assigned via other methods.
     */
    public Order() {
        setOrderLines(new LinkedHashSet<OrderLine>());
        setId(generateId());
        setDate(generateDate());
    }

    public boolean orderIsEmpty() {
        if (orderLines.size() == 0) { return true; }
        else { return false; }
    }

    /**
     * Adds a new order line to the order.
     * @param orderLine a new OrderLine object to add
     */
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    /**
     * Assigns supplier object references to order lines based on a list of suppliers
     *
     * @param supplierList a list of Suppliers to search from
     */
    public void addSuppliersToOrderLines(SupplierList supplierList) {
        for (OrderLine orderLine: orderLines) {
            int supplierId = orderLine.getItemToOrder().getSupplierId();
            Supplier supplier = supplierList.searchSupplier(supplierId);
            if (supplier != null)
                orderLine.setSupplier(supplier);
        }
    }

    /**
     * Updates the order with an amount to order of a specified item.
     *
     * If an order line already exists for the item, it will just overwrite the existing order quantity.
     *
     * @param item the Item object to order for
     * @param quantityToOrder the amount of the item to order
     */
    public void updateOrder(Item item, int quantityToOrder) {
        // Check whether an order line for the item already exists
        OrderLine orderLine = getOrderLine(item);
        if (orderLine == null) {
            // Generate new order line and add it to the order
            orderLine = item.generateOrderLine(quantityToOrder);
            addOrderLine(orderLine);    // create association between
            orderLine.setOrder(this);   // order and order line
        }
        else
            // Else, update quantity of existing order line
            orderLine.setQuantityToOrder(quantityToOrder);
    }

    /**
     * Helper method to retrieve any order lines matching a specified item.
     *
     * @param item the Item object to search for
     * @return an existing OrderLine object with matching Item reference; else, null if not found.
     */
    private OrderLine getOrderLine(Item item) {
        // Traverse each order line and return any matches
        if (orderLines != null) {
            for (OrderLine orderLine : orderLines) {
                if (orderLine.getItemToOrder() == item)
                    return orderLine;
            }
        }
        // Null if no match found
        return null;
    }

    /**
     * Helper method to generate a randomized ID value between MAX_ID and MIN_ID (inclusive).
     *
     * @return an randomized ID value as an integer
     */
    private int generateId() {
        return (int) (Math.random() * (MAX_ID - MIN_ID + 1) + MIN_ID);
    }

    /**
     * Helper method to retrieve local date.
     *
     * @return the local date (today's current date).
     */
    private String generateDate() {
        return java.time.LocalDate.now().toString();
    }

    /**
     * Getter method.
     * @return a reference to the LinkedHashSet of OrderLine objects
     */
    public LinkedHashSet<OrderLine> getOrderLines() {
        return orderLines;
    }

    /**
     * Setter method.
     * @param orderLines a new LinkedHashSet of OrderLines
     */
    public void setOrderLines(LinkedHashSet<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    /**
     * Getter method.
     * @return order id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method
     * @param id a new id to assign to the order
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter method which generates a randomized ID.
     */
    public void setRandomizedId() {
        setId(generateId());
    }

    /**
     * Getter method.
     * @return the date of the order
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter method.
     * @param date a new String date to assign to the order
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Provides a description of the order.
     *
     * @return s String representation of the order including id, name, and any order lines;
     *          else, nothing if the order is empty.
     */
    @Override
    public String toString() {
        if (orderLines.isEmpty())
            return "No items ordered.\n";

        // Pad id with zeros if required (e.g. id = 11 becomes idString = "00011")
        String idStr = String.format("%05d", id);

        StringBuilder sb = new StringBuilder();
        sb.append("ORDER ID: ").append(idStr).append('\n');
        sb.append("Date Ordered: ").append(date).append('\n').append('\n');

        int i = 1;
        for (OrderLine orderLine : orderLines) {
            // Append each order line's information
            sb.append(i).append(". Name:").append(orderLine.getItemToOrder().getName()).append('\n');
            sb.append("(Amount ordered: ").append(orderLine.getQuantityToOrder()).append(")\n");
            i++;
        }
        // Remove the extra newline character
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
