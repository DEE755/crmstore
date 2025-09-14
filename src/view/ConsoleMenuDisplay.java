package view;

public class ConsoleMenuDisplay extends GeneralDisplay
{
    

    private static ConsoleMenuDisplay instance;

    public static ConsoleMenuDisplay getInstance() {
        if (instance == null){
            instance = new ConsoleMenuDisplay();
        }
        return instance;
    }

    private ConsoleMenuDisplay() {}

    public String displayMainMenu() {
        System.out.println("\n=== MAIN MENU === for Administrator");
        System.out.println("1. Customer Management");
        System.out.println("2. Employee Management");
        System.out.println("3. Inventory Management");
        System.out.println("4. Chat with another branch");
        System.out.println("5. Logout");
        System.out.print("Choose option (1-5): ");
        return scanner.nextLine();
    }



    public String displayMainMenuSeller() {
        System.out.println("\n=== MAIN MENU - SELLER === for Seller");
        System.out.println("1. Customer Management");
        System.out.println("2. Sell a product");
        System.out.println("3. Inventory Management");
        System.err.println("4. Chat with another branch");
        System.out.println("5. Logout");
        System.out.print("Choose option (1-5): ");
        return scanner.nextLine();
    }


    public String displayCustomerManagementMenu() {
        System.out.println("\n=== CUSTOMER MANAGEMENT ===");
        System.out.println("1. View All Customers");
        System.out.println("2. Add New Customer");
        System.out.println("3. Delete a Customer");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose option (1-4): ");
        return scanner.nextLine();
    }


    public String displayEmployeeManagementMenu() {
        System.out.println("\n=== EMPLOYEE MANAGEMENT ===");
        System.out.println("1. View All Employees");
        System.out.println("2. Add New Employee");
        System.out.println("3. Delete an Employee");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose option (1-4): ");
        return scanner.nextLine();
    }

    public String displayInventoryManagementMenu() {
        System.out.println("\n=== INVENTORY MANAGEMENT ===");
        System.out.println("1. View Inventory");
        System.out.println("2. Add New Product");
        System.out.println("3. Update Product Quantity");
        System.out.println("4. Delete Product");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose option (1-5): ");
        return scanner.nextLine();
    }


    

    public void promptToContinue()
    {
        System.out.println("\nPress Enter to continue...\n");
        scanner.nextLine();
    }
}
