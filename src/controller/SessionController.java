package controller;

import db.DataAccessException;
import model.Employee;

import org.mindrot.jbcrypt.BCrypt;

public class SessionController {

    private static SessionController instance = null;
    private Employee loggedInEmployee;

    private SessionController() {
        loggedInEmployee = null;
    }

    public static SessionController getInstance() {
        if (instance == null) {
            instance = new SessionController();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return loggedInEmployee != null;
    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }


    public void logout() {
        loggedInEmployee = null;
    }

    public boolean authenticate(String email, String password) throws DataAccessException {
        EmployeeController employeeController = new EmployeeController();

        // Check if user exists
        Employee employee = employeeController.getEmployeeByEmail(email);
        if (employee == null) {
            return false;
        }

        // Check if password is correct
        if (BCrypt.checkpw(password, employee.getPasswordHash())) {
            loggedInEmployee = employee;
            return true;
        }
        return false;
    }

}
