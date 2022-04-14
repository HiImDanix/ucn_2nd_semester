package dal;

import db.DataAccessException;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDB extends DAO<Employee> implements EmployeeDBIF {
    public static final String tableName = "employee";
    public static final String[] columnNames = new String[]
            {"id", "first_name", "last_name", "email", "password_hash"}; // order matters (for down the code)

    public EmployeeDB() {
        super(tableName, columnNames);
    }

    @Override
    public void setValues(PreparedStatement stmt, Employee obj) throws SQLException {
        stmt.setString(1, obj.getFirstName());
        stmt.setString(2, obj.getLastName());
        stmt.setString(3, obj.getEmail());
        stmt.setString(4, obj.getPasswordHash());
    }

    @Override
    public void setValues(PreparedStatement stmt, int id) throws SQLException {
        stmt.setInt(1, id);
    }

    @Override
    public Employee buildDomainObject(ResultSet rs) throws SQLException {
        Employee employee = new Employee(
                rs.getInt(columnNames[0]),
                rs.getString(columnNames[1]),
                rs.getString(columnNames[2]),
                rs.getString(columnNames[3]),
                rs.getString(columnNames[4])
        );
        return employee;

    }

    public Employee getByEmail(String email) throws DataAccessException {
        return super.getByField(columnNames[3], email);
    }

    public void delete(Employee employee) throws DataAccessException {
        super.delete(employee.getId());
    }

}
