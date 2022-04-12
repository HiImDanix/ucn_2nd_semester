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
    public void addEmployee(String firstName, String lastName, String email, String password) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Employee employee = new Employee(firstName, lastName, email, hashedPassword);
        employeeDB.addEmployee(employee);
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
     * Update employee
     */
    public void updateEmployee(Employee employee) throws DataAccessException {
        employeeDB.updateEmployee(employee);
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
