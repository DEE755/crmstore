package serialization;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
import servercommunication.ServerCom;

public class EmployeeSerializer {
    ServerCom serverCom = ServerCom.getInstance();
    Socket socket=serverCom.getSocket();

    private static EmployeeSerializer instance;
    public static EmployeeSerializer getInstance() {
        if (instance == null) {
            instance = new EmployeeSerializer();
        }
        return instance;
    }

    private EmployeeSerializer() {
        
        
    }
    
    


    public Employee loadEmployee(int employee_id) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee_" + employee_id + ".ser"))) {
            return (Employee) in.readObject();
        }
    }

    public void saveEmployeeList(List<Employee> employees) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(employees);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Employee> loadEmployeeList() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            return (List<Employee>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            // If the file does not exist or is empty, return an empty list
            System.err.println("There is no employee data available. Please add employees first.");
            return new java.util.ArrayList<>();
        }
    }

    public List<Employee> loadEmployeeListFromText() throws IOException, ClassNotFoundException {
        BufferedReader reader = serverCom.reader;
        try {
            String employeeListRawText;


            List<Employee> employees = new ArrayList<Employee>();
            while ((employeeListRawText = reader.readLine()) != null && !employeeListRawText.isEmpty()) {
                Employee employee = util.TypeConverter.stringToEmployee(employeeListRawText);
                employees.add(employee);
            }
            return employees;
        }
        catch (IOException e) {
            // If the file does not exist or is empty, return an empty list
            System.err.println("There is no employee data available. Please add employees first.");
            return new java.util.ArrayList<>();
        }
    }

    
}