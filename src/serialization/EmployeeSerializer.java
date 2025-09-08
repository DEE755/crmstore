package serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Employee;
import servercommunication.ServerCom;
import util.*;

public class EmployeeSerializer {
    ServerCom serverCom;
    Socket socket;

    public EmployeeSerializer(ServerCom serverCom) {
        this.serverCom = serverCom;
        this.socket = serverCom.getSocket();
    }
    
    public void saveEmployeeToServer2(Employee employee) throws IOException, ClassNotFoundException {
        JSONObject employeeInfo = null;
        try {
            System.err.println("Converting employee to JSON: " + employee);
            //CONVERT OBJECT EMPLOYEE TO JSON
           employeeInfo = util.TypeConverter.EmployeeToJSON(employee);
        } catch (Exception e) {
           System.err.println("Error converting employee to JSON: " + e.getMessage());
        }

        //SEND JSON TO SERVER
        if (employeeInfo != null) {
            System.err.println("Sending employee data to server: " + employeeInfo.toString());
            socket.getOutputStream().write((employeeInfo.toString()+"\n").getBytes());
            socket.getOutputStream().flush();
        }

        if (serverCom.reader.readLine().equals("SUCCESS")) {
            System.out.println("Employee saved successfully.");
        } else {
            System.err.println("Failed to save employee.");
        }
    }



     public void employeeToString(Employee employee) throws IOException, ClassNotFoundException {
       String employeeInfo = null;
        try {
            System.err.println("Converting employee to JSON: " + employee);
            //CONVERT OBJECT EMPLOYEE TO JSON
           employeeInfo = util.TypeConverter.EmployeeToString(employee);
           
        } catch (Exception e) {
           System.err.println("Error converting employee to JSON: " + e.getMessage());
        }

        //SEND JSON TO SERVER
        if (employeeInfo != null) {
            System.err.println("Sending employee data to server: " + employeeInfo.toString());
            socket.getOutputStream().write((employeeInfo.toString()+"\n").getBytes());
            socket.getOutputStream().flush();
        }

        if (serverCom.reader.readLine().equals("SUCCESS")) {
            System.out.println("Employee saved successfully.");
        } else {
            System.err.println("Failed to save employee.");
        }
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
}