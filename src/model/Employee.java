package model;

import java.util.List;

public class Employee extends Participant implements java.io.Serializable {
    private String username;
    private String password;
    private Role role;
    private Branch branch;

    static int idCounter = 0;

    public Employee(String firstName, String familyName, String email, String username, String password, String phoneNumber, Role role, Branch branch) {
        super(firstName, familyName, email, ++idCounter, phoneNumber);
        this.username = username;
        this.password = password;
        this.role = role;
        this.branch = branch;
    }

    public Employee(int id, String firstName, String familyName, String email, String username, String password, String phoneNumber, Role role, Branch branch) {
        super(firstName, familyName, email, id, phoneNumber);
        if (id > idCounter) {
            idCounter = id; // Ensure idCounter is always the max assigned ID
        }
        this.username = username;
        this.password = password;
        this.role = role;
        this.branch = branch;
    }


        public enum Role {
            SHIFT_MANAGER,
            CASHIER,
            SELLER,
            ADMIN
        }

  
   

    public Role getRole() {
        return role;
    }
  

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Branch getBranch() {
        return branch;
    }

    public static int findEmployeeIndexById(List<Employee> employees, int searchId) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == searchId) {
                return i;
            }
        }
        return -1;
    }

}

