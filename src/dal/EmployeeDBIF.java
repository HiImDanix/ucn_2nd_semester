package dal;

import db.DataAccessException;
import model.Employee;

import java.util.List;

public interface EmployeeDBIF {
    /*
     * Get employee by id
     */
    Employee getById(int id) throws DataAccessException;

    /*
     * Get employee by email
     */
    Employee getByEmail(String email) throws DataAccessException;

    /*
     * Insert employee
     */
    void add(Employee employee) throws DataAccessException;

    /*
     * Update employee
     */
    void update(Employee employee) throws DataAccessException;

    /*
     * Delete employee
     */
    void delete(Employee employee) throws DataAccessException;

    /*
     * Get all employees
     */
    List<Employee> getAll() throws DataAccessException;

}
