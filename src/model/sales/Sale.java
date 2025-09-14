package model.sales;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.Branch;
import model.Employee;
import model.customer.Customer;
import model.inventory.StockItem;
import servercommunication.ServerCom;
import view.ConsoleCustomerDisplay;
import view.GeneralDisplay.Mode;

public class Sale {
    Customer buyer;
    StockItem itemSold;
    Employee seller;
    double salePrice;
    int quantitySold;
    double totalPrice;
    int saleId;
    java.time.LocalDate saleDate;
    Scanner scanner = new Scanner(System.in);
    Branch associatedBranch=Branch.getClientBranch();

    static ConsoleCustomerDisplay consoleCustomerDisplay = ConsoleCustomerDisplay.getInstance();
    static view.ConsoleInventoryDisplay consoleInventoryDisplay = view.ConsoleInventoryDisplay.getInstance();
    static servercommunication.Commands commands = servercommunication.Commands.getInstance();



Sale(Customer buyer, StockItem itemSold, Employee seller, double salePrice, int quantitySold){
    this.buyer=buyer;
    this.itemSold=itemSold;
    this.seller=seller;
    this.salePrice=salePrice;
    this.quantitySold=quantitySold;
    this.totalPrice=salePrice*quantitySold;
    this.saleDate=java.time.LocalDate.now();
}


public static Sale createSale(Employee seller) throws Exception{
    Scanner scanner = new Scanner(System.in);

    System.out.println("Select a Customer:");

    try{
    Optional<Customer> buyer =consoleCustomerDisplay.listCustomersFromServer(Mode.SELECT);
   

    if (!buyer.isPresent()) {
        System.out.println("No valid customer selected. Sale cannot proceed.");
        return null;
    }

    System.out.println("Select an item to sell:");
  
    List<StockItem> items = commands.getItemListFromServer();


    Optional<StockItem> itemSold = consoleInventoryDisplay.viewEditDeleAllItems(Mode.SELECT, items);

    if (!itemSold.isPresent()) {
        System.out.println("No valid item selected. Sale cannot proceed.");
        return null;
    }
    System.out.print("Enter quantity : ");
    int quantitySold = scanner.nextInt();

    int otherDiscount;
    System.out.print("Enter any additional discount percentage (0-100) for this sale: ");
    otherDiscount = scanner.nextInt();
    if (otherDiscount < 0 || otherDiscount > 100) {
        throw new IllegalArgumentException("Discount must be between 0 and 100.");
    }

    double saleTotal = calculateTotalPrice(buyer.get(), itemSold.get(), quantitySold, otherDiscount);
    

    Sale sale = new Sale(buyer.get(), itemSold.get(), seller, saleTotal, quantitySold);
    return sale;

}
catch (Exception e){
    throw new Exception("Error creating sale: " + e.getMessage());
}

}


public void submitSaleToServer(ServerCom serverCom, Sale sale) throws Exception
{

    String response=serverCom.sendCommandAndGetResponse("SubmitSale " + sale.toString() + "\n", true);
    if (response.equals("SUCCESS")) {
        System.out.println("Sale submitted successfully.");
    } else {
        throw new Exception("Failed to submit sale. Server response: " + response);
    }
}

@Override 
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(buyer.getId()).append(" ")
    .append(itemSold.getId()).append(" ")
    .append(seller.getId()).append(" ")
    .append(salePrice).append(" ")
    .append(quantitySold);
    return sb.toString();
}

private static double calculateTotalPrice(Customer buyer, StockItem itemSold, int quantitySold, int otherDiscount)
{
    return itemSold.getPrice() * quantitySold * buyer.getDiscount();
}


public String getTotalPrice() {
    return String.valueOf(this.totalPrice);
}


public String getQuantitySold() {
    return String.valueOf(this.quantitySold);
}


public StockItem getItemSold() {
    return this.itemSold;
}


public Customer getBuyer() {
    return this.buyer;
}

public Employee getSeller() {
    return this.seller;

}
}
