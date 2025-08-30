package servercommunication.login;

import java.util.Optional;

import model.Employee;

public interface LoginInterface {
    Optional<Employee> authenticate();
    void logout();
    boolean isLoggedIn();
}
