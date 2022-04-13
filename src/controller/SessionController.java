package controller;

import db.DataAccessException;
import model.Employee;

import org.mindrot.jbcrypt.BCrypt;

public class SessionController {

    private static SessionController instance = null;
    private Employee loggedInEmployee;

    private SessionController() {}

    /*
     * Singleton pattern
     */
    public static SessionController getInstance() {
        if (instance == null) {
            instance = new SessionController();
        }
        return instance;
    }

    /*
     * Checks if the user is logged in
     */
    public boolean isLoggedIn() {
        return loggedInEmployee != null;
    }

    /*
    * Gets the currently logged in employee
     */
    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }


    /*
    * Logs out the current user
     */
    public void logout() {
        loggedInEmployee = null;
    }

    /**
     * Logs in the user
     *
     * @param email The email of the user
     * @param password The password of the user
     *
     * @return true if the user is logged in, false otherwise
     *
     * @throws DataAccessException if there is a problem with the database
     */
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
