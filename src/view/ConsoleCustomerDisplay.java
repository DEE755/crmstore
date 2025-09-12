package view;

import java.util.List;
import java.util.Scanner;
import model.customer.Customer;
import model.customer.NewCustomer;

public class ConsoleCustomerDisplay {
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




   
    public Customer createNewCustomer() {
        String firstName;
        String familyName;
        String email;
        String phoneNumber;

        do {
            System.out.println("Enter customer first name:");
            firstName = scanner.nextLine().replace(" ", "-");
            if (firstName.isEmpty()) {
            System.out.println("Invalid first name. Please try again:");
            }
        } while (firstName.isEmpty());

        do {
            System.out.println("Enter customer family name:");
            familyName = scanner.nextLine().replace(" ", "-");
            if (familyName.isEmpty()) {
            System.out.println("Invalid family name. Please try again:");
            }
        } while (familyName.isEmpty());

        do {
            System.out.println("Enter customer email:");
            email = scanner.nextLine();
            if (email.isEmpty() || !email.contains("@")) {
            System.out.println("Invalid email. Please try again:");
            }
        } while (email.isEmpty() || !email.contains("@"));

        do {
            System.out.println("Enter customer phone number:");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d+") || phoneNumber.length() < 7) {
            System.out.println("Invalid phone number. Please try again:");
            }
        } while (phoneNumber.isEmpty() || !phoneNumber.matches("\\d+") || phoneNumber.length() < 7);

        Customer newCustomer = new NewCustomer(firstName, familyName, email, phoneNumber);
        return newCustomer;
    }


    

}

