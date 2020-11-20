package server.model.messagemodel;

public class NonElectricalItem extends Item {

    /**
     * Constructs a non-electrical item.
     *
     * @param id a unique ID as an integer
     * @param name String name of the item
     * @param quantity current item stock available as an integer
     * @param price price of an item (Example format: '12.43')
     */
    public NonElectricalItem(int id, String name, int quantity, double price) {
        super(id, name, quantity, price, "Non-Electrical");
    }
}
