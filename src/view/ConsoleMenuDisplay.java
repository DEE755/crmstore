package view;

import java.util.Scanner;

public class ConsoleMenuDisplay 
{
    private Scanner scanner = new Scanner(System.in);

    public String displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Customer Management");
        System.out.println("2. Employee Management");
        System.out.println("3. Logout");
        System.out.print("Choose option (1-3): ");
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
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose option (1-3): ");
        return scanner.nextLine();
    }
    public void promptToContinue()
    {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    
    }
}
