package dal;

import db.DataAccessException;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDB extends DAO<Employee> implements EmployeeDBIF {
    public static final String tableName = "employee";
    public enum Columns {
        ID,
        FIRST_NAME,
        LAST_NAME,
        EMAIL,
        PASSWORD_HASH;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public EmployeeDB() {
        // Passing table name and settable column names
        super(tableName, new String[] {
                Columns.ID.toString(),
                Columns.FIRST_NAME.toString(),
                Columns.LAST_NAME.toString(),
                Columns.EMAIL.toString(),
                Columns.PASSWORD_HASH.toString()
        });
    }

    @Override
    public void setValues(PreparedStatement stmt, Employee obj) throws SQLException {
        stmt.setString(1, obj.getFirstName());
        stmt.setString(2, obj.getLastName());
        stmt.setString(3, obj.getEmail());
        stmt.setString(4, obj.getPasswordHash());
    }

    @Override
    public Employee buildDomainObject(ResultSet rs) throws SQLException {
        Employee employee = new Employee(
                rs.getInt(Columns.ID.toString()),
                rs.getString(Columns.FIRST_NAME.toString()),
                rs.getString(Columns.LAST_NAME.toString()),
                rs.getString(Columns.EMAIL.toString()),
                rs.getString(Columns.PASSWORD_HASH.toString())
        );
        return employee;

    }

    @Override
    public int getId(Employee obj) {
        return obj.getId();
    }

    public Employee getByEmail(String email) throws DataAccessException {
        return super.getByField(Columns.EMAIL.toString(), email);
    }

}
