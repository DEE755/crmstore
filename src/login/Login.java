package login;

import java.util.List;
import java.util.Scanner;

import model.Employee;

public class Login implements LoginInterface {
    private boolean loggedIn = false;

    @Override
    public Employee authenticate(List<Employee> employees) {
        Scanner scanner= new Scanner(System.in);

        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();


for (Employee employee : employees) {
            if (employee.getUsername().equals(enteredUsername) && 
                employee.getPassword().equals(enteredPassword)) {
                loggedIn = true;
                System.out.println("Login successful! Welcome " + employee.getName());
                return employee;
            }
        }

        return null;
    }

    @Override
    public void logout() {
        loggedIn = false;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
