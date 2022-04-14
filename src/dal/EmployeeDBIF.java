package dal;

import db.DataAccessException;
import model.Employee;

import java.util.List;

public interface EmployeeDBIF {
    /*
     * Get employee by id
     */
    Employee getEmployeeById(int id) throws DataAccessException;

    /*
     * Get employee by email
     */
    Employee getEmployeeByEmail(String email) throws DataAccessException;

    /*
     * Insert employee
     */
    void addEmployee(Employee employee) throws DataAccessException;

    /*
     * Update employee
     */
    void updateEmployee(Employee employee) throws DataAccessException;

    /*
     * Delete employee
     */
    void deleteEmployee(Employee employee) throws DataAccessException;

    /*
     * Get all employees
     */
    List<Employee> getAllEmployees() throws DataAccessException;

}
