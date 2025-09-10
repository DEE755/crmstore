package util;

import java.io.IOException;
import java.util.List;
import model.customer.Customer;
import serialization.CustomerSerializer;
import servercommunication.ServerCom;



public final class Utility {
    private Utility() {} // NO INSTANTATION HACK ("STATIC CLASS")

    //get a reference to the singleton ServerCom instance
private static ServerCom serverCom = ServerCom.getInstance();




private static CustomerSerializer customerSerializer = new CustomerSerializer(serverCom);
//private static EmployeeSerializer employeeSerializer = new EmployeeSerializer();

    public static int calculateLastId() {

        try {
            List<Customer> customers = customerSerializer.loadCustomerList();
            if (!customers.isEmpty()) {
                return customers.get(customers.size() - 1).getId() + 1;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No customer data found. Starting IDs from 1.");
        }

        return 1; 
    }
}
