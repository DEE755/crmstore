package main;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.Employee;
import model.customer.Customer;
import serialization.CustomerSerializer;
import serialization.EmployeeSerializer;
import servercommunication.ServerCom;
import servercommunication.login.Login;
import view.ConsoleCustomerDisplay;
import view.ConsoleEmployeeDisplay;
import view.ConsoleMenuDisplay;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ConsoleCustomerDisplay consoleCustomerDisplay = new ConsoleCustomerDisplay();

    private static ServerCom serverCom = new ServerCom();
    private static CustomerSerializer customerSerializer = new CustomerSerializer();
    private static EmployeeSerializer employeeSerializer = null;
    private static Login login = new Login(serverCom);
    
    private static Optional<Employee> currentEmployee = Optional.empty();
    private static ConsoleEmployeeDisplay employeeDisplay = new ConsoleEmployeeDisplay();
    private static ConsoleMenuDisplay menuDisplay = new ConsoleMenuDisplay();

    
    
    
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            serverCom.ServerConnection();

             employeeSerializer = new EmployeeSerializer(serverCom);
            String serverMessage=serverCom.reader.readLine();
            System.out.println("Message from server: " + serverMessage);
            

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            return;
        }

        System.out.println("=== WELCOME TO MANAGEMENT SYSTEM ===\nPLEASE LOGIN TO CONTINUE");
        
        boolean running = true;

        while(running)
        {
            currentEmployee = login.authenticate();
            //System.out.println(currentEmployee);
            if(currentEmployee.isPresent()) 
            break;
            
            else 
            System.err.println("Login failed. Please try again.");
        }
        
        while (login.isLoggedIn()) {
            String choice = menuDisplay.displayMainMenu();
            
            switch (choice) {
                case "1":
                    customerManagement();
                    break;
                case "2":
                    employeeManagement();
                    break;

                case "3":
                    System.out.println("Logging out...");
                    login.logout();
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }

        }
    }
    
    private static void viewAllCustomers(boolean viewOrDelete) throws IOException, ClassNotFoundException {
        System.out.println("\n=== ALL CUSTOMERS ===");
        
        List<Customer> customers = customerSerializer.loadCustomerList("customers.ser");
        consoleCustomerDisplay.displayCustomerList(customers);
        
        if (viewOrDelete){
            System.out.println("\nWrite the ID of the Customer you want to see and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }
        else {System.out.println("\nWrite the ID of the Customer you want to delete and press Enter to continue... Or cancel by pressing Enter without typing anything.");}
        String input = scanner.nextLine();
        if (!input.trim().isEmpty()) {
            try {
                int customerId = Integer.parseInt(input);

                try{
                    if (viewOrDelete)
                    {
                consoleCustomerDisplay.displayCustomerDetails(customers.get(customerId-1));
                
                    }
                    else{
                        System.out.println("Customer deleted: " + customers.get(customerId).getFullname());
                        customerSerializer.deleteCustomer(customerId);
                        
                    
                    }
                    menuDisplay.promptToContinue();
                }
                catch(IndexOutOfBoundsException e)
                {
                    System.out.println("No customer found with the given ID.");
                    
                }

                

            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        }
        menuDisplay.promptToContinue();



    }

    private static void customerManagement() throws IOException, ClassNotFoundException {
        boolean inCustomerMenu = true;
        
        while (inCustomerMenu) {
            String choice = menuDisplay.displayCustomerManagementMenu();
            
            switch (choice) {
                case "1":
                    viewAllCustomers(true);
                    break;
                case "2":
                   Customer newCustomer = consoleCustomerDisplay.createNewCustomer();
                   customerSerializer.saveCustomer(newCustomer, "customers.ser");
                   
                   System.out.println("New customer added: " + newCustomer.getFullname());
                    break;

                    case "3":
                    viewAllCustomers(false);

                case "4":
                    inCustomerMenu = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }


    private static void employeeManagement() throws IOException, ClassNotFoundException {
        boolean inEmployeeMenu = true;
        
        while (inEmployeeMenu) {
            String choice = menuDisplay.displayEmployeeManagementMenu();
            
            switch (choice) {
                case "1":
                    String responseString=serverCom.sendCommandAndGetResponse("ListEmployees");
                    
                    
                    System.out.println("Response from server: " + responseString);

                    if (responseString.equals("SUCCESS")) {
                        System.out.println("Receiving employee data from server...");
                        employeeDisplay.displayEmployeeList(employeeSerializer.loadEmployeeListFromText());

                    } else {
                        System.err.println("Failed to receive employee data.");
                        break;
                    }

                    menuDisplay.promptToContinue();

                    break;
                case "2":
                    Employee newEmployee = employeeDisplay.createNewEmployee();
                    System.err.println("Created new employee: " + newEmployee.toString());
                    serverCom.sendCommandAndGetResponse("AddEmployee " + util.TypeConverter.employeeToString(newEmployee) + "\n");
                    serverCom.emptyBuffer();
                
                    break;
                case "3":
                    inEmployeeMenu = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    

}