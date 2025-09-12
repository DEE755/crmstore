package view;

import java.util.List;
import java.util.Scanner;
import model.Employee;

public class ConsoleEmployeeDisplay  {
    private Scanner scanner = new Scanner(System.in);


    public void displayEmployeeDetails(Employee employee) {
        System.out.println("=== EMPLOYEE DETAILS ===");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Phone: " + employee.getPhoneNumber());
        System.out.println("=======================");
    }


    public void displayEmployeeList(List<Employee> employees) {
        System.out.println("=== EMPLOYEE LIST ===");
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId() + " Name: " + employee.getName() + " Username: " + employee.getUsername() + " Email: " + employee.getEmail() + " Phone: " + employee.getPhoneNumber());
        }
        System.out.println("=======================");
    }


   



    public Employee createNewEmployee() {
        String name;
        String email;
        String phoneNumber;
        String username;
        String password;

        System.out.println("Enter employee name:");
        name = scanner.nextLine().replace(" ", "-");

        do {
            System.out.println("Enter employee email:");
            email = scanner.nextLine();
            if (email.isEmpty() || !email.contains("@")) {
                System.out.println("Invalid email. Please try again.");
            }
        } while (email.isEmpty() || !email.contains("@"));

        do {
            System.out.println("Enter employee phone number:");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d+") || phoneNumber.length() < 7) {
                System.out.println("Invalid phone number. Please try again.");
            }
        } while (phoneNumber.isEmpty() || !phoneNumber.matches("\\d+") || phoneNumber.length() < 7);

        do {
            System.out.println("Enter employee username:");
            username = scanner.nextLine();
            if (username.isEmpty() || username.contains(" ")) {
                System.out.println("Invalid username. Please try again.");
            }
        } while (username.isEmpty() || username.contains(" "));

        do {
            System.out.println("Enter employee password:");
            password = scanner.nextLine();
            if (password.isEmpty() || password.contains(" ")) {
                System.out.println("Invalid password. Please try again.");
            }
        } while (password.isEmpty() || password.contains(" "));

        Employee newEmployee = new Employee(name, email, username, password, phoneNumber);
        return newEmployee;
    }

}

