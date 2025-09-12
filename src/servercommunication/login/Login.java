package servercommunication.login;

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
            String response = this.serverCom.sendCommandAndGetResponse("Login " + enteredUsername + " " + enteredPassword, true);
            String employeeInfo = "";

            System.out.println("Response: " + response);
            if (!response.equals("SUCCESS")) {
                System.err.println("Login failed: " + response);
                return Optional.empty();
            }
            else {
                System.out.println("Login successful!");
                employeeInfo = serverCom.reader.readLine();
                serverCom.emptyBuffer();
            }
            

            //CONVERT THE JSON RAW TEXT TO EMPLOYEE OBJECT TODO(CONSIDER REPLACE WITH STRING PARSER THAT EXISTS ALREADY BUT NEED TO MATCH SERVER)
            
           
        
            
            //JSONObject json = new JSONObject(employeeInfo);
            //Employee employee = util.TypeConverter.JSONToEmployee(json);

            Employee employee = util.TypeConverter.stringToEmployee(employeeInfo);

            if (employee != null) {
                loggedIn = true;
                
                System.out.println(util.Utility.decorationLines("Logged in as: " + employee.getFirstName() + " " + employee.getFamilyName() + "\nRole: " + employee.getRole() + "\nFrom BRANCH: " + employee.getBranch().getName() + " BRANCH ID: " + employee.getBranch().getId()));
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
