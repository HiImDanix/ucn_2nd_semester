package controller;

import dal.EmployeeDB;
import dal.EmployeeDBIF;
import db.DataAccessException;

import model.Employee;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class EmployeeController {
    private EmployeeDBIF employeeDB;

    public EmployeeController() throws DataAccessException {
        employeeDB = new EmployeeDB();
    }

    /*
     * Add employee
     */
    public Employee addEmployee(String firstName, String lastName, String email, String password) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Employee employee = new Employee(firstName, lastName, email, hashedPassword);
        employeeDB.addEmployee(employee);
        return employee;
    }

    /*
     * Get employee by email
     */
    public Employee getEmployeeByEmail(String email) throws DataAccessException {
        return employeeDB.getEmployeeByEmail(email);
    }

    /*
     * Get employee by id
     */
    public Employee getEmployeeById(int id) throws DataAccessException {
        return employeeDB.getEmployeeById(id);
    }

    /*
     * Update employee's first name
     */
    public void updateEmployeeFirstName(Employee employee, String firstName) throws DataAccessException {
        String oldFirstName = employee.getFirstName();
        try {
            employee.setFirstName(firstName);
            employeeDB.updateEmployee(employee);
        } catch (DataAccessException e) {
            employee.setFirstName(oldFirstName);
            throw new DataAccessException("Error updating employee's first name");
        }
    }

    /*
     * Update employee's last name
     */
    public void updateEmployeeLastName(Employee employee, String lastName) throws DataAccessException {
        String oldLastName = employee.getLastName();
        try {
            employee.setLastName(lastName);
            employeeDB.updateEmployee(employee);
        } catch (DataAccessException e) {
            employee.setLastName(oldLastName);
            throw new DataAccessException("Error updating employee's last name");
        }
    }

    /*
     * Update employee's email
     */
    public void updateEmployeeEmail(Employee employee, String email) throws DataAccessException {
        String oldEmail = employee.getEmail();
        try {
            employee.setEmail(email);
            employeeDB.updateEmployee(employee);
        } catch (DataAccessException e) {
            employee.setEmail(oldEmail);
            throw new DataAccessException("Error updating employee's email");
        }
    }

    /*
     * Update employee's password
     */
    public void updateEmployeePassword(Employee employee, String password) throws DataAccessException {
        String oldPasswordHash = employee.getPasswordHash();
        try {
            String newPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt());
            employee.setPasswordHash(newPasswordHash);
            employeeDB.updateEmployee(employee);
        } catch (DataAccessException e) {
            employee.setPasswordHash(oldPasswordHash);
            throw new DataAccessException("Error updating employee's password");
        }
    }

    /*
     * Delete employee
     */
    public void deleteEmployee(Employee employee) throws DataAccessException {
        employeeDB.deleteEmployee(employee);
    }

    /*
     * Get all employees
     */
    public List<Employee> getAllEmployees() throws DataAccessException {
        return employeeDB.getAllEmployees();
    }
}
