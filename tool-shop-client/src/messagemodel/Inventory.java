package messagemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * This class represents the inventory of items and manages an order.
 * Inventory will automatically generate new order lines whenever an item has "low quantity".
 *
 * Includes methods for searching items and decreasing quantity of a specific item.
 *
 * @author Patrick Linang
 * @since November 25, 2020
 */
public class Inventory implements Serializable {
    static final long serialVersionUID = 1L;

    /**
     * A collection of all available items for sale.
     */
    private LinkedHashSet<Item> items;

    /**
     * Keeps track of items that must be ordered based on item quantity.
     */
    private Order order;

    /**
     * The minimum quantity allowed before the item will be automatically ordered.
     */
    private final static int MIN_QUANTITY = 40;

    /**
     * The amount to restock to when ordering an item automatically.
     */
    private final static int RESTOCK_QUANTITY = 50;


    /**
     * Constructs an inventory based on a given collection of items and an order.
     *
     * @param items a LinkedHashSet of Item objects to be tracked by the inventory
     * @param order an Order object where any new order lines can be added to
     */
    public Inventory(LinkedHashSet<Item> items, Order order) {
        setItems(items);
        setOrder(order);
    }

    /**
     * Adds a new item to the inventory.
     *
     * @param item a new Item object to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Search for an item based on item name and retrieve its reference.
     *
     * @param itemName the item name to search for (case-sensitive)
     * @return reference to the Item object with matching name; else, null.
     */
    public Item searchItem(String itemName) {
        for (Item item: items) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    /**
     * Search for an item based on item ID and retrieve its reference.
     *
     * @param id the item id to search for
     * @return reference to the Item object with matching id; else, null.
     */
    public Item searchItem(int id) {
        for (Item item: items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    /**
     * Searches for an item based on a set of item parameters and then returns a Map representation of
     * the object that was found.
     *
     * For example for key="toolId", value=1001 - this will search for item with toolId attribute equal to 1001.
     *
     * @param itemSearchParam a map containing key-value pairs for:
     *                        "paramType" corresponding to item.type attribute
     *                        "paramValue" corresponding to the value of the item.type attribute
     * @return a Map containing the found Item's attributes as key-value pairs; otherwise null
     */
    public Map<String, String> getItemStringMap(Map<String, String> itemSearchParam) {
        String paramType = itemSearchParam.get("paramType");
        String paramValue = itemSearchParam.get("paramValue");

        Item item = null;
        if (paramType.equals("toolId")) {
            item = searchItem(Integer.parseInt(paramValue));
        }
        else if (paramType.equals("name"))
            item = searchItem(paramValue);

        if (item != null) { return item.toMap(); }
        else { return null; }
    }

    /**
     * Searches for an item and returns its current quantity
     * @param id Item id to search
     * @return Item quantity if id was found; otherwise -1.
     */
    public int getItemQuantity(int id) {
        Item item = searchItem(id);
        if (item != null)
            return item.getQuantity();
        else
            return -1;
    }

    /**
     * Checks whether a specified quantity to remove is valid (i.e. ensure that item quantity never becomes negative).
     *
     * @param itemId id to search
     * @param quantityToRemove quantity to remove
     * @return true if quantityToRemove <= item.quantity; otherwise, false.
     */
    public boolean isQuantityToRemoveValid(int itemId, int quantityToRemove) {
        Item item = searchItem(itemId);
        return quantityToRemove <= item.getQuantity();
    }

    /**
     * Decreases the quantity of an item in the inventory and can update the order if required.
     *
     * This maintains the order such that if an item keeps getting reduced below 40, it will constantly
     * update the order line to always restock to 50.
     *
     * If the quantity of the item to decrease is less than MIN_QUANTITY, the Order object must be updated.
     * By default, it will place an order to restock up to RESTOCK_QUANTITY (as per project reqs/specification).
     *
     * @param item the Item object to decrease
     * @param quantityToRemove the amount to decrease an item's quantity by (cannot be more than current quantity)
     */
    public void manageItem(Item item, int quantityToRemove) {
        // Decrease item quantity
        int updatedQuantity = item.decreaseQuantity(quantityToRemove);

        // Generate or update order line if quantity is less than minimum amount
        if (updatedQuantity < MIN_QUANTITY) {
            updateOrder(item, RESTOCK_QUANTITY - updatedQuantity);
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
        order.updateOrder(item, quantityToOrder);
    }

    /**
     * Provides a list of all items stored in the inventory.
     * @return a String representation for all items stored in the inventory
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");

        for (Item item: items)
            sb.append(item).append('\n');

        // Remove the extra newline character '\n'
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

    /**
     * Used to gather item information as a collection of strings. Primarily used for GUI functionality.
     *
     * @return returns an ArrayList of string representations of each Item in the list
     */
    public ArrayList<String> getInventoryStringList() {
        ArrayList<String> inventoryStringList = new ArrayList<>();
        for (Item item: items)
            inventoryStringList.add(item.toString());

        return inventoryStringList;
    }

    /**
     * Getter method.
     * @return reference to the LinkedHashSet of Item objects
     */
    public LinkedHashSet<Item> getItems() {
        return items;
    }

    /**
     * Setter method.
     * @param items a new LinkedHashSet of Item objects to link with this inventory
     */
    public void setItems(LinkedHashSet<Item> items) {
        this.items = items;
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
     * @param order a new Order object to assign to the inventory
     */
    public void setOrder(Order order) {
        this.order = order;
    }
}
