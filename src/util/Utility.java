package util;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import model.customer.Customer;
import serialization.CustomerSerializer;
import servercommunication.ServerCom;



public final class Utility {
    private Utility() {} // NO INSTANTATION HACK ("STATIC CLASS")

    //get a reference to the singleton ServerCom instance
private static ServerCom serverCom = ServerCom.getInstance();
private static Scanner scanner = new Scanner(System.in);




private static CustomerSerializer customerSerializer = CustomerSerializer.getInstance();
//private static EmployeeSerializer employeeSerializer = new EmployeeSerializer();

    public static int calculateLastId() {
        String responseString;
        try {
            responseString = serverCom.sendCommandAndGetResponse("ListCustomers", util.Constants.VERBOSE_OVERRIDE);
            if (responseString.equals("EMPTY")) {
                System.err.println("No customers in the server yet. Starting IDs from 1.");
                return 1; //start IDs from 1 if there are no customers yet
            }

            if (responseString.equals("SUCCESS")) {
             
            List<Customer> customers = customerSerializer.loadCustomerListFromServerText();
            System.out.println("after checkpoint");

            if (!customers.isEmpty()) {
                return customers.get(customers.size() - 1).getId() + 1;
            }
        }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error calculating last ID: " + e.getMessage());
        }

        return 1; 
    }


    public static String decorationLines(String text) {
        StringBuilder decorated = new StringBuilder();
        String line = "=".repeat(text.length() + 4);
        decorated.append("\n").append(line).append("\n");
        decorated.append(text).append("\n");
        decorated.append(line).append("\n");
        return decorated.toString();
    }


    public static int promptAnInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + message);
            scanner.next(); 
        }
        return scanner.nextInt();
    }

}
