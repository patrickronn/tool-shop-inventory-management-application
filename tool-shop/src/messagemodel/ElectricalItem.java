package messagemodel;

public class ElectricalItem extends Item {
    /**
     * Description of the power type requirements for an electrical tool.
     */
    private String powerType;

    /**
     * Constructs an ElectricalItem which includes info about the item's power type.
     *
     * The supplier of the object remains null and can be assigned afterwards.
     *
     * @param id a unique ID as an integer
     * @param name String name of the item
     * @param quantity current item stock available as an integer
     * @param price price of an item (Example format: '12.43')
     * @param powerType a description about the power type required for the item
     */
    public ElectricalItem(int id, String name, int quantity, double price, String powerType) {
        super(id, name, quantity, price, "Electrical");
        setPowerType(powerType);
    }

    /**
     * Getter method.
     * @return power type information
     */
    public String getPowerType() {
        return powerType;
    }

    /**
     * Setter method.
     * @param powerType description of the power type requirements.
     */
    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }
}
