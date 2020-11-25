package server.model;

import messagemodel.Inventory;
import messagemodel.Item;
import messagemodel.Order;

// TODO: convert these to return values and objects to the ModelController
/**
 * This class represents a shop consisting of an inventory of tool items for sale and a supplier list.
 *
 * This class provides the primary point of interaction with any front-end interfaces.
 * Shop's methods returns string values for query results and informative messages.
 * Also uses FileMgr for reading item and supplier instances.
 *
 * @author Patrick Linang
 * @since October 10, 2020
 */
public class ShopManager {
    /**
     * The shop inventory which contains items available for sale and manages any orders.
     */
    private Inventory inventory;

    /**
     * A list of suppliers who supply items for the shop.
     */
    private SupplierList supplierList;


    public ShopManager(Inventory inventory, SupplierList supplierList) { // add CustomerList argument later
        setInventory(inventory);
        setSupplierList(supplierList);
    }

//    /**
//     * Constructs a ShopManager object by loading a supplier list and inventory from specified text files.
//     *
//     * Uses FileMgr to handle file reading.
//     *
//     * @param supplierListFileName String name of file containing all suppliers (e.g. suppliers.txt)
//     * @param inventoryFileName String name of file containing all items (e.g. items.txt)
//     */
//    public ShopManager(String supplierListFileName, String inventoryFileName) {
//        SupplierList supplierList = FileMgr.loadSupplierList(supplierListFileName);
//        setSupplierList(supplierList);
//
//        Inventory inventory = FileMgr.loadInventory(inventoryFileName, supplierList);
//        setInventory(inventory);
//    }



    /**
     * Produces a list of all the shop's items.
     *
     * @return a String listing descriptions of all tools
     */
    public String listAllItems() {
        return inventory.toString();
    }

    /**
     * Searches for an item by name and returns the description of the item (if found).
     *
     * @param name String item name to search for (case-sensitive)
     * @return a String description of the item with matching name; else, nothing.
     */
    public String searchItemByName(String name) {
        Item item = inventory.searchItem(name);

        if (item != null)
            return item.toString();
        else
            return "No item found with name '" + name + "'.";
    }

    /**
     * Searches for an item by ID value and returns the description of the item (if found).
     *
     * @param id String item name to search for (case-sensitive)
     * @return a String description of the item with matching name; else, nothing.
     */
    public String searchItemById(int id) {
        Item item = inventory.searchItem(id);

        if (item != null)
            return item.toString();
        else
            return "No item found with id '" + id + "'.";
    }

    /**
     * Retrieves the current quantity of an item based on a given item name.
     *
     * @param name String name of the item (case-sensitive)
     * @return a String description of the current item quantity; else, nothing if item wasn't found.
     */
    public String getItemQuantity(String name) {
        Item item = inventory.searchItem(name);

        if (item != null)
            return "\nItem '" + name + "' - Current Quantity: " + item.getQuantity();
        else
            return "No item found with name '" + name + "'.";
    }

    /**
     * Decreases the quantity of an item based on a given item name and the amount to decrease.
     *
     * Includes a validation check to ensure that the desired amount to decrease is not greater than current stock.
     *
     * @param name String name of the item to decrease (case-sensitive)
     * @param quantityToRemove desired amount to remove (cannot be more than the current quantity of item)
     * @return String description for the updated quantity if item was found;
     *          or, notifies if quantityToRemove is illegal,
     *          or, nothing if item wasn't found.
     */
    public String decreaseItemQuantity(String name, int quantityToRemove) {
        Item item = inventory.searchItem(name);

        if (item != null) {
            if (quantityToRemove <= item.getQuantity()) {
                inventory.manageItem(item, quantityToRemove);
                return "\nItem '" + name + "' - Updated Quantity: " + item.getQuantity();
            }
            else
                return "\nItem '" + name + "' - Cannot decrease quantity by " +
                        quantityToRemove + " (Current Quantity: " + item.getQuantity() + ")";
        }
        else
            return "No item found with name '" + name + "'.";
    }

    /**
     * Produces a string representation of the order's current state.
     *
     * @return a String stating the order id and order date and any order lines;
     *          else, nothing if order doesn't exist.
     */
    public String getOrder() {
        Order order = inventory.getOrder();

        if (order != null)
            return order.toString();
        else
            return "No order exists";
    }

    /**
     * Getter method.
     * @return a reference to the shop's SupplierList object
     */
    public SupplierList getSupplierList() {
        return supplierList;
    }

    /**
     * Setter method.
     * @param supplierList a new SupplierList object to assign to the shop
     */
    public void setSupplierList(SupplierList supplierList) {
        this.supplierList = supplierList;
    }

    /**
     * Getter method.
     * @return a reference to the shop's Inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Setter method.
     * @param inventory a new Inventory object to assign to the shop
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
