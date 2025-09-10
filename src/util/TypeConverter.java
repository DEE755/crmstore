package util;

import model.Employee;

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


public static JSONObject EmployeeToJSON(Employee employee) {
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
                            employee.getUsername() + " " +
                            employee.getPassword() + " " +
                            employee.getEmail() + " " +
                            employee.getPhoneNumber();
    return employeeString;  
}

public static Employee StringToEmployee(String employeeString) {
    String[] parts = employeeString.split(" ");
    if (parts.length != 1 && parts.length != 6) {
        throw new IllegalArgumentException("Invalid employee string: " + employeeString);
    }
    System.err.println("Parts length: " + parts.length);
    int id = Integer.parseInt(parts[0]);
    String name = parts[1];
    String username = parts[2];
    String password = parts[3];
    String email = parts[4];
    String phoneNumber = parts[5];
    return new Employee(id, name, username, password, email, phoneNumber);

}

}
