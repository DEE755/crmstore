package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.customer.Customer;
import servercommunication.ServerCom;

public class CustomerSerializer {
    ServerCom serverCom;
    Socket socket;

    public CustomerSerializer(ServerCom serverCom) {
        this.serverCom = serverCom;
        this.socket = serverCom.getSocket();
    }


   
    
    public Customer loadCustomer(int customer_id) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("customer_" + customer_id + ".ser"))) {
            return (Customer) in.readObject();
        }
    }



    public List<Customer> loadCustomerListFromServerText() throws IOException, ClassNotFoundException {
   
        try {
            System.out.println("Before readline");
            String customerListRawText = serverCom.reader.readLine();
            System.out.println("After readline: " + customerListRawText);

            List<Customer> customers = new ArrayList<Customer>();

            while (!customerListRawText.equals("ENDLIST")) {
                System.out.println("Received customer data: " + customerListRawText);
                Customer customer = util.TypeConverter.stringToCustomer(customerListRawText);
                customers.add(customer);
                System.err.println("Added customer: " + customer.toString());
                customerListRawText = serverCom.reader.readLine();
            }
            return customers;
        }
        catch (IOException e) {
            // If the file does not exist or is empty, return an empty list
            System.err.println("There is no customer data yet in the server");
            return new java.util.ArrayList<>();
        }
    }



    public void saveCustomerList(List<Customer> customers) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(util.Constants.CUSTOMER_DATA_FILE))) {
            out.writeObject(customers);
        }
    }
    
    
}