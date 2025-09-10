package view;

import java.util.List;
import java.util.Scanner;
import model.customer.Customer;
import model.customer.NewCustomer;

public class ConsoleCustomerDisplay implements CustomerDisplayInterface {
    private Scanner scanner = new Scanner(System.in);
    
    
    public void displayCustomerDetails(Customer customer) {
        System.out.println("=== CUSTOMER DETAILS ===");
        System.out.println("ID: " + customer.getId());
        System.out.println("Name: " + customer.getFirstName() + " " + customer.getFamilyName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("=======================");
    }


    public void displayCustomerList(List<Customer> customers) {
        System.out.println("=== CUSTOMER LIST ===");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getId() + ", Name: " + customer.getFirstName() + " " + customer.getFamilyName());
        }
        System.out.println("=======================");
    }




   
    public Customer createNewCustomer()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer first name:");
        String firstName = scanner.nextLine().replace(" ", "-");
        System.out.println("Enter customer family name:");
        String familyName = scanner.nextLine().replace(" ", "-");
        System.out.println("Enter customer email:");
        String email = scanner.nextLine();
        System.out.println("Enter customer phone number:");
        String phoneNumber = scanner.nextLine();
        Customer newCustomer = new NewCustomer(firstName, familyName, email, phoneNumber);
        return newCustomer;

    }


    

}

