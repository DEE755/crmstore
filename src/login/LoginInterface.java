package login;

import java.util.List;

import model.Employee;

public interface LoginInterface {
    Employee authenticate(List<Employee> employees);
    void logout();
    boolean isLoggedIn();
}
