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
        System.out.println("Name: " + customer.getFullname());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("=======================");
    }


    public void displayCustomerList(List<Customer> customers) {
        System.out.println("=== CUSTOMER LIST ===");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getId() + ", Name: " + customer.getFullname());
        }
        System.out.println("=======================");
    }




   
    public Customer createNewCustomer()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer name:");
        String name = scanner.nextLine().replace(" ", "-");
        System.out.println("Enter customer email:");
        String email = scanner.nextLine();
        System.out.println("Enter customer phone number:");
        String phoneNumber = scanner.nextLine();
        Customer newCustomer = new NewCustomer(name, email, phoneNumber);
        return newCustomer;

    }


    @Override
    public boolean addCustomer(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCustomer'");
    }

    

}

