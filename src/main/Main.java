package main;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import model.Branch;
import model.Employee;
import model.customer.Customer;
import model.inventory.StockItem;
import model.sales.Sale;
import serialization.CustomerSerializer;
import serialization.EmployeeSerializer;
import serialization.StockItemSerializer;
import servercommunication.Commands;
import servercommunication.ServerCom;
import servercommunication.login.Login;
import view.ConsoleCustomerDisplay;
import view.ConsoleEmployeeDisplay;
import view.ConsoleInventoryDisplay;
import view.ConsoleMenuDisplay;
import view.GeneralDisplay.Mode;

public class Main {
    // Singleton instances of display classes
    private static ConsoleCustomerDisplay consoleCustomerDisplay = ConsoleCustomerDisplay.getInstance();
    private static ConsoleEmployeeDisplay consoleEmployeeDisplay = ConsoleEmployeeDisplay.getInstance();
    private static ConsoleMenuDisplay menuDisplay = ConsoleMenuDisplay.getInstance();
    private static ConsoleInventoryDisplay consoleInventoryDisplay = ConsoleInventoryDisplay.getInstance();
    private static Commands commands = Commands.getInstance();

    private static Branch associatedBranch = new Branch();//automatic branch recognition according to client config
    
    private static ServerCom serverCom = ServerCom.getInstance();

    private static CustomerSerializer customerSerializer = CustomerSerializer.getInstance();
    private static EmployeeSerializer employeeSerializer = EmployeeSerializer.getInstance();
    private static StockItemSerializer stockItemSerializer = StockItemSerializer.getInstance();
    private static Login login = new Login(serverCom);
    
    private static Optional<Employee> currentEmployee = Optional.empty();
    

    private static String responseString;
    
    
    
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, Exception {
        try {
            System.err.println("Connecting to server as branch: " + associatedBranch.getName() + " (ID: " + associatedBranch.getId() + ")");
            serverCom.ServerConnection();

            String serverMessage=serverCom.reader.readLine();
            System.out.println("Message from server: " + serverMessage);
            if (serverMessage.equals("YOU ARE NOW CONNECTED")) {
                serverCom.revealBranchToServer();
                associatedBranch.setConnected(true);
            
            }

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
            {
                associatedBranch.setCurrentEmployee(currentEmployee.get());
                System.out.println("Login successful. Welcome, " + associatedBranch.getCurrentEmployee().getFirstName() + " " + associatedBranch.getCurrentEmployee().getFamilyName() + " (" + associatedBranch.getCurrentEmployee().getRole() + ") at " + associatedBranch.getName() + " branch.");
            break;
            }
            else 
            System.err.println("Login failed. Please try again.");
        }
        
        while (login.isLoggedIn()) {
            String choice;
            Sale newSale;
            if (associatedBranch.getCurrentEmployee().getRole() == Employee.Role.SELLER)
            {
            choice = menuDisplay.displayMainMenuSeller();}
            else{
                choice = menuDisplay.displayMainMenu();
            }
            
            
            switch (choice) {
                case "1":
                    customerManagement();
                    break;
                case "2":
                if (associatedBranch.getCurrentEmployee().getRole() == Employee.Role.ADMIN)
                    employeeManagement();
                    else {
                    
                    try{
                    newSale=Sale.createSale(associatedBranch.getCurrentEmployee());
                    newSale.submitSaleToServer(serverCom, newSale);
                    }
                    catch(Exception e)
                    {
                        System.err.println("Error submitting sale: " + e.getMessage());
                        break;
                    }

                    System.out.println("Sale submitted successfully. Total: " + newSale.getTotalPrice() + " for " + newSale.getQuantitySold() + " x " + newSale.getItemSold().getName() + " to customer " + newSale.getBuyer().getFirstName() + " " + newSale.getBuyer().getFamilyName());
                    menuDisplay.promptToContinue();
                    }


                    break;

                case "3":
                    inventoryManagement();
                    break;


                case "4":
                    System.out.println("=== CHAT FEATURE COMING SOON ===");
                    menuDisplay.promptToContinue();
                    break;

                case "5":
                    System.out.println("Logging out...");
                    login.logout();
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }

        }
    }

    

    



    private static void customerManagement() throws IOException, ClassNotFoundException {
        boolean inCustomerMenu = true;
        
        while (inCustomerMenu) {
            String choice = menuDisplay.displayCustomerManagementMenu();
            
            
            switch (choice) {
                
                case "1"://View all customers
                 
                try {
                    consoleCustomerDisplay.listCustomersFromServer(Mode.VIEW);
                 } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                 }
                    
                    break;

                case "2"://Add new customer
                    System.out.println("=== ADD NEW CUSTOMER ===");
                    Customer newCustomer = consoleCustomerDisplay.createNewCustomer();

                    System.out.println("Created new customer: " + newCustomer.toString());
                    responseString=serverCom.sendCommandAndGetResponse("AddCustomer " + util.TypeConverter.customerToString(newCustomer) + "\n", true);
                    serverCom.emptyBuffer();

                   if (responseString.equals("SUCCESS")) {
                       System.out.println("ed: " + newCustomer.getFirstName());
                   } else {
                       System.err.println("Failed to add new customer.");
                   }
                    menuDisplay.promptToContinue();
                    
                    break;

                    case "3": //Delete customer

                    responseString=serverCom.sendCommandAndGetResponse("ListCustomers", util.Constants.VERBOSE_OVERRIDE);

                    System.out.println("Response from server: " + responseString);

                    if (responseString.equals("SUCCESS")) 
                    {
                        System.out.println("Receiving customers data from server...");
                        List<Customer> customers = customerSerializer.loadCustomerListFromServerText();
                        consoleCustomerDisplay.viewAllCustomers(Mode.DELETE, customers);
                        menuDisplay.promptToContinue();
                    } 
                    else {
                        System.err.println("Failed to receive employee data.");
                        break;
                    }

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
                case "1"://View all employees
                    responseString=serverCom.sendCommandAndGetResponse("ListEmployees", util.Constants.VERBOSE_OVERRIDE);

                    System.out.println("Response from server: " + responseString);

                    if (responseString.equals("SUCCESS")) {
                        System.out.println("Receiving employee data from server...");

                        consoleEmployeeDisplay.viewAllEmployees(Mode.VIEW, employeeSerializer.loadEmployeeListFromText());
                        //consoleEmployeeDisplay.displayEmployeeList(employeeSerializer.loadEmployeeListFromText());

                    } else {
                        System.err.println("Failed to receive employee data.");
                        break;
                    }

                    break;

                case "2"://Add new employee
                    Employee newEmployee = consoleEmployeeDisplay.createNewEmployee();
                    System.err.println("Created new employee: " + newEmployee.toString());
                    responseString=serverCom.sendCommandAndGetResponse("AddEmployee " + util.TypeConverter.employeeToString(newEmployee) + "\n", true);
                    System.err.println("Response from server: " + responseString);
                    serverCom.emptyBuffer();
                
                    break;

                case "3"://Delete employee

                    responseString=serverCom.sendCommandAndGetResponse("ListEmployees", util.Constants.VERBOSE_OVERRIDE);

                    System.out.println("Response from server: " + responseString);

                    if (responseString.equals("SUCCESS")) {
                        System.out.println("Receiving employee data from server...");
                        List<Employee> employees = employeeSerializer.loadEmployeeListFromText();
                         consoleEmployeeDisplay.viewAllEmployees(Mode.DELETE, employees);

                         menuDisplay.promptToContinue();

                    } else {
                        System.err.println("Failed to receive employee data.");
                        break;
                    }

                    case "4"://Return to main menu
                    inEmployeeMenu = false;
                    break;

                    
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }


    static void inventoryManagement() throws IOException, ClassNotFoundException{
     String choice = menuDisplay.displayInventoryManagementMenu();

     switch (choice) {
         case "1" -> {//View all items
             System.out.println("Viewing inventory...");

             try {
                 List<StockItem> items = commands.getItemListFromServer();
                 consoleInventoryDisplay.viewEditDeleAllItems(Mode.VIEW, items);
             } catch (Exception e) {
                 System.err.println("Error retrieving inventory: " + e.getMessage());
             }

                   menuDisplay.promptToContinue();
                    break;

            }



        case "2" -> {//Add new item
            System.out.println("Adding new product...");
            StockItem newItem = consoleInventoryDisplay.createNewItem();
            System.err.println("Created new item: " + newItem.toString());
            responseString=serverCom.sendCommandAndGetResponse("AddItem " + util.TypeConverter.stockItemToString(newItem) + "\n", true);
            System.err.println("Response from server: " + responseString);
            serverCom.emptyBuffer();
            menuDisplay.promptToContinue();
            break;
        }

         case "3" -> {//Edit item quantity

                     try {
                 List<StockItem> items = commands.getItemListFromServer();
                 consoleInventoryDisplay.viewEditDeleAllItems(Mode.EDIT, items);
             } catch (Exception e) {
                 System.err.println("Error retrieving inventory: " + e.getMessage());
             }
                         menuDisplay.promptToContinue();

                    break;
        }
        case "4" -> {

                     try {
                 List<StockItem> items = commands.getItemListFromServer();
                 consoleInventoryDisplay.viewEditDeleAllItems(Mode.DELETE, items);
             } catch (Exception e) {
                 System.err.println("Error retrieving inventory: " + e.getMessage());
             }
                            menuDisplay.promptToContinue();
                    break;
        }

        

        case "5" -> {
             // Return to main menu
             return;
            }
         default -> System.out.println("Invalid option! Please try again.");
     }
    }

}