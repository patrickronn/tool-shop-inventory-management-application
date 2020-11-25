package messagemodel;

import java.util.HashMap;
import java.util.Map;

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
    public ElectricalItem(int id, String name, int quantity, double price, int supplierId, String powerType) {
        super(id, name, quantity, price, supplierId, "Electrical");
        setPowerType(powerType);
    }

    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();
        map.put("powerType", powerType);
        return map;
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

    @Override
    public String toString() {
        return super.toString() + ", " + powerType;
    }
}
