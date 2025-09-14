package view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import model.customer.Customer;
import model.customer.NewCustomer;
import serialization.CustomerSerializer;
import servercommunication.ServerCom;

public class ConsoleCustomerDisplay extends GeneralDisplay {
    
    private static ConsoleMenuDisplay consoleMenuDisplay=ConsoleMenuDisplay.getInstance();
    private static ServerCom serverCom=ServerCom.getInstance();
    private static CustomerSerializer customerSerializer=CustomerSerializer.getInstance();

    private static ConsoleCustomerDisplay instance;

    public static ConsoleCustomerDisplay getInstance() {
        if (instance == null) {
            instance = new ConsoleCustomerDisplay();
        }
        return instance;
    }

    private ConsoleCustomerDisplay() {}


    public Optional<Customer> viewAllCustomers(Mode viewMode, List<Customer> customers) throws IOException  {
        System.out.println("\n=== ALL CUSTOMERS ===");
         
        displayCustomerList(customers);

        if (viewMode == Mode.SELECT) {
            System.out.println("\nWrite the ID of the Customer you want to sell to and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }

        else if (viewMode == Mode.VIEW) {
            System.out.println("\nWrite the ID of the Customer you want to see and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }
        else {System.out.println("\nWrite the ID of the Customer you want to delete and press Enter to continue... Or cancel by pressing Enter without typing anything.");}
        String input = scanner.nextLine();
        
        
        if (!input.trim().isEmpty()) {
    
                try {
                    int customerId = Integer.parseInt(input);
                    try {
                        if (viewMode != Mode.DELETE) {
                            Customer selectedCustomer = customers.get(Customer.findCustomerIndexById(customers, customerId));

                            if (viewMode == Mode.SELECT) {
                                return Optional.of(selectedCustomer);
                            }

                            if (viewMode == Mode.VIEW) {
                                displayCustomerDetails(selectedCustomer);
                                return Optional.empty();
                            }
                        } else { // DELETE MODE
                            String responseString = serverCom.sendCommandAndGetResponse("ListCustomers DELETEMODE " + customerId + "\n", util.Constants.VERBOSE_OVERRIDE);

                            if (responseString.equals("SUCCESS")) {
                                System.out.println("Customer with ID " + customerId + " has been deleted successfully.");
                            } else {
                                System.err.println("Failed to delete customer with ID " + customerId + ". Please ensure the ID is correct.");
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("No customer found with the given ID.");
                    } finally {
                        consoleMenuDisplay.promptToContinue();
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid ID format.");
                }
        
            }
                    return Optional.empty();
    }

    
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



    public Optional<Customer> listCustomersFromServer(Mode viewMode) throws Exception {
        String responseString;
        responseString=serverCom.sendCommandAndGetResponse("ListCustomers", util.Constants.VERBOSE_OVERRIDE);

            if (responseString.equals("SUCCESS")) {
                System.out.println("Receiving customers data from server...");
                List<Customer> customers = customerSerializer.loadCustomerListFromServerText();
                System.out.println("Loaded " + customers.size() + " customers.");
                Optional<Customer> selectedCustomer = viewAllCustomers(viewMode, customers);

                if(selectedCustomer.isPresent())
                {return selectedCustomer;}
                
            }
                    
            else if (responseString.equals("EMPTY")) {
                System.err.println("No customer data in the server yet.");
                throw new Exception("No customer data in the server yet.");
            }

            return Optional.empty();
        }

}

