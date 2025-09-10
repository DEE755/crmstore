package view;

import java.util.List;
import model.customer.Customer;

public interface CustomerDisplayInterface {
    void displayCustomerDetails(Customer customer);
    void displayCustomerList(List<Customer> customers);
    
}