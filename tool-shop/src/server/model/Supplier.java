package server.model;

import messagemodel.Item;

import java.util.LinkedHashSet;

/**
 * This class is used to represent the suppliers of items sold by the shop.
 *
 * Supplier objects have references to all Item objects they supply.
 *
 * @author Patrick Linang
 * @since October 10, 2020
 */
public abstract class Supplier {
    /**
     * Unique ID of a supplier.
     */
    protected int id;

    /**
     * Name of the supplier.
     */
    protected String companyName;

    /**
     * Address of the supplier.
     */
    protected String address;

    /**
     * Name of the point-of-contact with the supplier.
     */
    protected String salesContact;

    /**
     * A list of references to all Items supplied by supplier.
     */
    protected LinkedHashSet<Item> suppliedItems;

    /**
     * Phone number of supplier.
     */
    protected String phoneNum;

    /**
     * Description of the type of supplier (e.g. local supplier, international supplier)
     */
    protected String type;

    /**
     * Constructs a supplier based on their contact information.
     *
     * The items supplied are initialized as an empty collected; each item can be added afterwards.
     *
     * @param id unique ID integer
     * @param companyName supplier's name as a String
     * @param address supplier's address as a String
     * @param salesContact name of person to contact at company as a String
     * @param phoneNum supplier's phone number
     * @param type type of supplier (e.g. 'Local' or 'International')
     */
    protected Supplier(int id, String companyName, String address, String salesContact, String phoneNum, String type) {
        setId(id);
        setCompanyName(companyName);
        setAddress(address);
        setSalesContact(salesContact);
        setSuppliedItems(new LinkedHashSet<>());
        setPhoneNum(phoneNum);
        setType(type);
    }

    /**
     * Assigns reference to an Item.
     * @param item Item object supplied by the supplier
     */
    public void addSuppliedItem(Item item) {
        suppliedItems.add(item);
    }

    /**
     * Provides a description of the supplier.
     * @return a String representing an Supplier's id, company name, address, and sales contact.
     */
    @Override
    public String toString() {
        return "Supplier - ID: " + id +
                ", Company: " + companyName +
                ", Address: " + address +
                ", Sales Contact: " + salesContact +
                ", Type: " + type;
    }

    /**
     * Getter method.
     * @return supplier id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method.
     * @param id new id value to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method.
     * @return name of the supplier/company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter method.
     * @param companyName updated name of the supplier
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter method.
     * @return supplier's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method.
     * @param address updated address of the supplier
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method.
     * @return name of the supplier's point-of-contact
     */
    public String getSalesContact() {
        return salesContact;
    }

    /**
     * Setter method.
     * @param salesContact updated name of the sales contact.
     */
    public void setSalesContact(String salesContact) {
        this.salesContact = salesContact;
    }

    /**
     * Getter method.
     * @return LinkedHashSet of Item objects supplied by the supplier.
     */
    public LinkedHashSet<Item> getSuppliedItems() {
        return suppliedItems;
    }

    /**
     * Setter method.
     * @param itemsSupplied a new LinkedHashSet of Items to link to the supplier
     */
    public void setSuppliedItems(LinkedHashSet<Item> itemsSupplied) {
        this.suppliedItems = itemsSupplied;
    }

    /**
     * Getter method.
     * @return supplier phone number
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Setter method
     * @param phoneNum phone number of the suppleir
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Getter method.
     * @return type of supplier.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method.
     * @param type description of the supplier type.
     */
    public void setType(String type) {
        this.type = type;
    }


}
