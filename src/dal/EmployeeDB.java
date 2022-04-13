package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DBConnection;

import db.DataAccessException;
import model.Employee;

public class EmployeeDB implements EmployeeDBIF {
    // SQL statements
    public static final String SQL_SELECT_ALL = "SELECT * FROM employee";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM employee WHERE id = ?";
    public static final String SQL_INSERT = "INSERT INTO employee (first_name, last_name, email, password_hash) VALUES (?, ?, ?, ?)";
    public static final String SQL_UPDATE = "UPDATE employee SET first_name = ?, last_name = ?, email= ?, password_hash= ? WHERE id = ?";
    public static final String SQL_DELETE = "DELETE FROM employee WHERE id = ?";
    // Prepared statements
    private static PreparedStatement selectAll;
    private static PreparedStatement selectById;
    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement delete;


    /*
     * Constructor
     */
    public EmployeeDB() throws DataAccessException {
        // Get database connection
        DBConnection db = DBConnection.getInstance();

        // Initialize prepared statements
        try {
            selectAll = db.getConnection().prepareStatement(SQL_SELECT_ALL);
            selectById = db.getConnection().prepareStatement(SQL_SELECT_BY_ID);
            insert = db.getConnection().prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            update = db.getConnection().prepareStatement(SQL_UPDATE);
            delete = db.getConnection().prepareStatement(SQL_DELETE);
        } catch (SQLException e) {
            throw new DataAccessException("Could not prepare the statements", e);
        }
    }



    /*
     * Get all employees
     */
    public List<Employee> getAllEmployees() throws DataAccessException {
        List<Employee> employees = new ArrayList<>();
        try {
            ResultSet rs = selectAll.executeQuery();
            employees = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Could not get all employees", e);
        }
        return employees;
    }

    /*
     * Get employee by id
     */
    @Override
    public Employee getEmployeeById(int id) throws DataAccessException {
        Employee employee = null;
        try {
            selectById.setInt(1, id);
            ResultSet rs = selectById.executeQuery();
            employee = buildObject(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Could not get employee by id", e);
        }
        return employee;
    }

    /*
     * Get employee by email
     */
    @Override
    public Employee getEmployeeByEmail(String email) throws DataAccessException {
        Employee employee = null;
        try {
            selectById.setString(1, email);
            ResultSet rs = selectById.executeQuery();
            employee = buildObject(rs);
        } catch (SQLException e) {
            // empty on purpose
        }
        return employee;
    }

    /*
     * Insert employee
     */
    @Override
    public Employee addEmployee(Employee employee) throws DataAccessException {
        try {
            insert.setString(1, employee.getFirstName());
            insert.setString(2, employee.getLastName());
            insert.setString(2, employee.getEmail());
            insert.setString(3, employee.getPasswordHash());
            int id = DBConnection.getInstance().executeStatementReturnID(insert);
            employee.setId(id);
        } catch (SQLException e) {
            throw new DataAccessException("Could not insert employee", e);
        }
        return employee;
    }

    /*
     * Update employee
     */
    @Override
    public void updateEmployee(Employee employee) throws DataAccessException {
        try {
            update.setString(1, employee.getFirstName());
            update.setString(2, employee.getLastName());
            update.setString(2, employee.getEmail());
            update.setString(3, employee.getPasswordHash());
            update.setInt(4, employee.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not update employee", e);
        }
    }

    /*
     * Delete employee
     */
    @Override
    public void deleteEmployee(Employee employee) throws DataAccessException {
        try {
            delete.setInt(1, employee.getId());
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not delete employee", e);
        }
    }

    /*
     * Build employee objects from ResultSet
     */
    private List<Employee> buildObjects(ResultSet rs) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (rs.next()) {
            employees.add(buildObject(rs));
        }
        return employees;
    }

    /*
     * Build employee object from ResultSet
     */
    private Employee buildObject(ResultSet rs) throws SQLException {
        Employee employee = new Employee(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("passwordHash")
        );
        return employee;
    }
}
