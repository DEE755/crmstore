package servercommunication.login;

import java.io.ObjectInputStream;
import java.util.Optional;
import java.util.Scanner;

import model.Employee;
import servercommunication.ServerCom;

public class Login implements LoginInterface {
    private ServerCom serverCom;
    private boolean loggedIn = false;

    private static  Login loginInstance;

    public Login(ServerCom serverCom){
        System.err.println("Created a Singleton instance of Login, delete later");
        this.serverCom = serverCom;
        }


    public Login getInstance(){
        return this.loginInstance;
        }

    @Override
    public Optional<Employee> authenticate() {
        Scanner scanner= new Scanner(System.in);

        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        try
        {
            String response = this.serverCom.sendCommand("Login " + enteredUsername + " " + enteredPassword);

            if (!response.equals("SUCCESS")) {
                System.err.println("Login failed: " + response);
                return Optional.empty();
            }
            
            ObjectInputStream inputObject = new ObjectInputStream(serverCom.getSocket().getInputStream());
            Employee employee = (Employee) inputObject.readObject();

            if (employee != null) {
                loggedIn = true;
                return Optional.of(employee);
            }

        } catch (Exception e) {
            System.err.println("Error communicating with server: " + e.getMessage());
            return null;
        } 

        

        return Optional.empty();
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
