package util;

import model.Employee;
import model.customer.Customer;
import model.customer.NewCustomer;
import model.customer.ReturningCustomer;
import model.customer.VIPCustomer;

public class TypeConverter {

    public static Employee JSONToEmployee(JSONObject json) {
    Employee employee = new Employee(
        json.getInt("id"),
        json.getString("name"),
        json.getString("username"),
        json.getString("password"),
        json.getString("email"),
        json.getString("phoneNumber")
    );

    return employee;
    }


public static JSONObject employeeToJSON(Employee employee) {
    JSONObject json = new JSONObject();
    json.put("id", employee.getId());
    json.put("name", employee.getName());
    json.put("username", employee.getUsername());
    json.put("password", employee.getPassword());
    json.put("email", employee.getEmail());
    json.put("phoneNumber", employee.getPhoneNumber());
    return json;
}


public static String employeeToString(Employee employee) {
    String employeeString = employee.getId() + " " +
                            employee.getName() + " " +
                             employee.getPassword() + " " +
                            employee.getEmail() + " " +
                            employee.getUsername() + " " +
                            employee.getPhoneNumber();
    return employeeString;  
}

public static Employee stringToEmployee(String employeeString) {
    String[] parts = employeeString.split(" ");
    if (parts.length != 1 && parts.length != 6) {
        throw new IllegalArgumentException("Invalid employee string: " + employeeString);
    }
    System.err.println("Parts length: " + parts.length);
    int id = Integer.parseInt(parts[0]);
    String name = parts[1];
    String password = parts[2];
    String email = parts[3];
    String username = parts[4];
    String phoneNumber = parts[5];
    return new Employee(id, name, email, username, password, phoneNumber);

}


public static Customer stringToCustomer(String customerInfoString) {
    Customer newCustomer=null;
    String[] parts = customerInfoString.split(" ");
    if (parts.length != 1 && parts.length != 7) {
        throw new IllegalArgumentException("Invalid customer string: " + customerInfoString);
    }
    System.err.println("Parts length: " + parts.length);
    int id = Integer.parseInt(parts[0]);
    String firstName = parts[1];
    String familyName = parts[2];
    String email = parts[3];
    String phoneNumber = parts[4];
    double discount = Double.parseDouble(parts[5]);

    switch (parts[6]) {
        case "NewCustomer":
            newCustomer = new NewCustomer(id, firstName, familyName, email, phoneNumber, discount);
            break;
        case "ReturningCustomer":
            newCustomer = new ReturningCustomer(id, firstName, familyName, email, phoneNumber, discount);
            break;
        case "VIPCustomer":
            newCustomer = new VIPCustomer(id, firstName, familyName, email, phoneNumber, discount);
            break;
        default:
            throw new IllegalArgumentException("Unknown customer type: " + parts[6]);
    }
    return newCustomer;

}

public static String customerToString(Customer customer) {
    String customerString = customer.getId() + " " +
                            customer.getFirstName() + " " +
                            customer.getFamilyName() + " " +
                            customer.getEmail() + " " +
                            customer.getPhoneNumber() + " " +
                            customer.getDiscount() + " " +
                            customer.getClass().getSimpleName();
    return customerString;
}


}
