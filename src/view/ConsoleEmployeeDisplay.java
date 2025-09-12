package view;

import java.util.List;
import java.util.Scanner;
import model.Branch;
import model.Employee;
import model.Employee.Role;

public class ConsoleEmployeeDisplay  {
    private Scanner scanner = new Scanner(System.in);


    public void displayEmployeeDetails(Employee employee) {
        System.out.println("=== EMPLOYEE DETAILS ===");
        System.out.println("ID: " + employee.getId());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Family Name: " + employee.getFamilyName());
        System.out.println("Role: " + employee.getRole());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Phone: " + employee.getPhoneNumber());
        System.out.println("=======================");
    }


    public void displayEmployeeList(List<Employee> employees) {
        System.out.println("=== EMPLOYEE LIST ===");
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId() + " Name: " + employee.getFirstName() + " " + employee.getFamilyName() + " Role: " + employee.getRole() + " Username: " + employee.getUsername() + " Email: " + employee.getEmail() + " Phone: " + employee.getPhoneNumber()+ " Branch: " + employee.getBranch().getName());
        }
        System.out.println("=======================");
    }


   



    public Employee createNewEmployee() {
        String firstName;
        String familyName;
        String email;
        String phoneNumber;
        String username;
        String password;
        Role role;
        

        do {
            System.out.println("Enter employee first name:");
            firstName = scanner.nextLine().replace(" ", "-");
            if (firstName.isEmpty()) {
                System.out.println("Invalid name. Please try again.");
            }
        } while (firstName.isEmpty());
     

        do {
            System.out.println("Enter employee last name:");
            familyName = scanner.nextLine().replace(" ", "-");
            if (familyName.isEmpty()) {
                System.out.println("Invalid name. Please try again.");
            }
        } while (familyName.isEmpty());
        

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


        do {
            System.out.println("Enter employee role --> 1:SHIFT_MANAGER, 2:CASHIER, 3:SELLER");
            int roleInput = scanner.nextInt();
            switch (roleInput) {
                case 1 -> role = Role.SHIFT_MANAGER;
                case 2 -> role = Role.CASHIER;
                case 3 -> role = Role.SELLER;
                default -> {
                    System.out.println("Invalid role. Please enter 1, 2, or 3.");
                    continue;
                }
            }
            break;
        } while (true);

        Employee newEmployee = new Employee(firstName, familyName, email, username, password, phoneNumber, role, Branch.getClientBranch());
        return newEmployee;
    }

}

