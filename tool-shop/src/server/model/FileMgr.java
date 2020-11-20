//package server.model;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import server.model.messagemodel.*;
//
///**
// * This class is used to read items for inventory and suppliers for supplier list from text files.
// * All methods are class methods.
// *
// * @author Patrick Linang
// * @since October 10, 2020
// */
//public class FileMgr {
//    /**
//     * Reads lines directly from a specified file and stores it into an array list.
//     *
//     * Uses BufferedReader for file reading.
//     *
//     * Terminates program if there are issues reading from file.
//     *
//     * @param filename String name of the file to open (e.g. "items.txt" or "suppliers.txt")
//     * @return an ArrayList of String objects representing each line
//     */
//    public static ArrayList<String> readLines(String filename) {
//        ArrayList<String> lines = new ArrayList<String>();
//        BufferedReader br = null;
//
//        try {
//            File file = new File(filename); // throws NullPointerException if file doesn't exist
//
//            // Instantiate reader objects
//            FileReader fr = new FileReader(file);
//            br = new BufferedReader(fr);
//
//            // Read through all lines in file and add it to the array list
//            String line = br.readLine();
//            while (line != null) {
//                lines.add(line);
//                line = br.readLine();
//            }
//
//        } catch (Exception e) {
//            // Print error and abort program
//            e.printStackTrace();
//            System.exit(0);
//        } finally {
//            // Properly close connection
//            try {
//                if (br != null)
//                    br.close();
//            } catch (Exception e) {
//                System.out.println("Error in closing the BufferedReader: " + e);
//            }
//        }
//
//        // Returns all lines read
//        return lines;
//    }
//
//
//    /**
//     * Converts a text file containing supplier information into a SupplierList object.
//     *
//     * @param filename String name of file (e.g. "suppliers.txt")
//     * @return a new SupplierList object with data parsed from text file
//     */
//    public static SupplierList loadSupplierList(String filename) {
//        return new SupplierList(readSuppliers(filename));
//    }
//
//    /**
//     * Reads lines from a text file and creates Supplier objects from each line.
//     *
//     * @param filename String name of file (e.g. "suppliers.txt")
//     * @return a LinkedHashSet of Supplier objects parsed from each line of text file
//     */
//    private static LinkedHashSet<Supplier> readSuppliers(String filename) {
//        // Read lines from filename into an arraylist
//        ArrayList<String> lines = readLines(filename);
//
//        if (lines.isEmpty()) {
//            System.out.println("No lines read were read from " + filename);
//            return null;
//        }
//        else {
//            // Store each converted line into a list of Suppliers
//            LinkedHashSet<Supplier> mySupplierList = new LinkedHashSet<>();
//
//            // Convert each line into a Supplier object
//            for (String line: lines) {
//                // Parse line into separated supplier info
//                String[] supplierInfo = line.split(";");
//                int id = Integer.parseInt(supplierInfo[0]);
//                String companyName = supplierInfo[1];
//                String address = supplierInfo[2];
//                String salesContact = supplierInfo[3];
//
//                // Construct supplier and add it to list
//                mySupplierList.add(new Supplier(id, companyName, address, salesContact));
//            }
//
//            return mySupplierList;
//        }
//    }
//
//    /**
//     * Converts a text file containing item information into an Inventory object.
//     *
//     * Uses a SupplierList to associate Items in the loaded Inventory with Suppliers in SupplierList.
//     *
//     * @param filename String name of file (e.g. "items.txt")
//     * @param mySupplierList a SupplierList object containing Suppliers who supply the loaded Items
//     * @return a new Inventory object with data parsed from text file
//     */
//    public static Inventory loadInventory(String filename, SupplierList mySupplierList) {
//        LinkedHashSet<Item> items = readItems(filename, mySupplierList.getSuppliers());
//        return new Inventory(items, new Order());
//    }
//
//    /**
//     * Reads lines from a text file and creates Item objects for each line.
//     *
//     * Uses a LinkedHashSet of Supplier objects to create associations between Item and Supplier objects
//     * (if no match was found, the reference remains null).
//     *
//     * @param filename String name of file (e.g. "suppliers.txt")
//     * @param suppliers a LinkedHashSet of Supplier objects used to assign suppliers to items, and vice versa
//     * @return a LinkedHashSet of Item objects parsed from each line of text file
//     */
//    private static LinkedHashSet<Item> readItems(String filename, LinkedHashSet<Supplier> suppliers) {
//        // Read lines from filename into an arraylist
//        ArrayList<String> lines = readLines(filename);
//
//        if (lines.isEmpty()) {
//            System.out.println("No lines read were read from " + filename);
//            return null;
//        }
//        else {
//            // Store each converted line into a list of Items
//            LinkedHashSet<Item> items = new LinkedHashSet<>();
//
//            // Convert each line into an Item object
//            for (String line: lines) {
//                // Parse line into separated item info
//                String[] itemInfo = line.split(";");
//                int id = Integer.parseInt(itemInfo[0]);
//                String itemName = itemInfo[1];
//                int quantity = Integer.parseInt(itemInfo[2]);
//                double price = Double.parseDouble(itemInfo[3]);
//                int supplierId = Integer.parseInt(itemInfo[4]);
//
//                // Construct item
//                Item item = new Item(id, itemName, quantity, price);
//
//                // Find the Supplier object with matching supplierId
//                for (Supplier supplier: suppliers) {
//                    if (supplier.getId() == supplierId) {
////                        item.setSupplier(supplier);         // create association
//                        supplier.addSuppliedItem(item);     // between item and supplier
//                        break;
//                    }
//                }
//
//                // Add item to list
//                items.add(item);
//            }
//            return items;
//        }
//    }
//}
