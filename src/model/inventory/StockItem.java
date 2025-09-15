package model.inventory;

import java.util.List;

public class StockItem implements java.io.Serializable {

    private String name;

    private int quantity;
    private double price;
    private int id;
    private Category category;
    private String branchID;
    

    public StockItem(String name, int quantity, double price, Category category) {
        this.name = name;
        this.id=generateItemId();
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public StockItem(String name, int quantity, double price, Category category, String branchID) {
        this.name = name;
        this.id=generateItemId();
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.branchID = branchID;
    }

    //FOR EXISTING ITEMS
    public StockItem(String name, int id, int quantity, double price, Category category) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    
     public enum Category {
        ELECTRONICS, CLOTHING, GROCERY, MISC
    }

   

    public String getName() {
        return name;
    }


    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    int generateItemId() {
        return (int) (Math.random() * 100000);
    }


    public Enum<Category> getCategory() {
        return category;
    }

    public static StockItem findStockItemById(List<StockItem> items, int id) {
        
        for (StockItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void displayStockItemDetails() {
        System.out.println("\n\n=== STOCK ITEM DETAILS ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Category: " + getCategory());
        System.out.println("=======================");
    }

}
