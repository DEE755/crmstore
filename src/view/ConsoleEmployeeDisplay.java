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
            System.out.println("ID: " + employee.getId() + ", Name: " + employee.getName());
        }
        System.out.println("=======================");
    }


    public void promptToContinue()
    {
        System.out.println("Press Enter to return to Employee List...");
        scanner.nextLine();
    
    }



    public Employee createNewEmployee()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee name:");
        String name = scanner.nextLine();
        System.out.println("Enter employee email:");
        String email = scanner.nextLine();
        System.out.println("Enter employee phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter employee username:");
        String username = scanner.nextLine();
        System.out.println("Enter employee password:");
        String password = scanner.nextLine();
        
        Employee newEmployee = new Employee(name, email, username, password, phoneNumber);
        return newEmployee;
        
    }

}

