package util;

import model.Branch;
import model.Employee;
import model.customer.Customer;
import model.customer.NewCustomer;
import model.customer.ReturningCustomer;
import model.customer.VIPCustomer;

public class TypeConverter {


public static String employeeToString(Employee employee) {
    String employeeString = employee.getId() + " " +
                            employee.getFirstName() + " " +
                            employee.getFamilyName() + " " +
                            employee.getPassword() + " " +
                            employee.getEmail() + " " +
                            employee.getUsername() + " " +
                            employee.getPhoneNumber() + " " +
                            employee.getRole().name() + " " +
                            employee.getBranch().getName();

    return employeeString;
}

public static Employee stringToEmployee(String employeeString) {
    String[] parts = employeeString.split(" ");
    if (parts.length != 1 && parts.length != 9) {
        throw new IllegalArgumentException("Invalid employeekkk string: " + employeeString);
    }
    //System.err.println("Parts length: " + parts.length);
    int id = Integer.parseInt(parts[0]);
    String firstName = parts[1];
    String familyName = parts[2];
    String password = parts[3];
    String email = parts[4];
    String username = parts[5];
    String phoneNumber = parts[6];
    Employee.Role role = Employee.Role.valueOf(parts[7]);
    Branch branch = new Branch(parts[8]);
    return new Employee(id, firstName, familyName, email, username, password, phoneNumber, role, branch);
    }




public static Customer stringToCustomer(String customerInfoString) {
    Customer newCustomer=null;
    String[] parts = customerInfoString.split(" ");
    if (parts.length != 1 && parts.length != 7) {
        throw new IllegalArgumentException("Invalid customer string: " + customerInfoString);
    }
    //System.err.println("Parts length: " + parts.length);
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
