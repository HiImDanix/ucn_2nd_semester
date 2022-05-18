package dal;

import db.DataAccessException;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dal.EmployeeDB.Columns.*;

public class EmployeeDB extends DAO<Employee> implements EmployeeDBIF {
    public static final String tableName = "employee";
    public enum Columns {
        ID,
        FIRST_NAME,
        LAST_NAME,
        EMAIL,
        PASSWORD_HASH;

        public String fieldName() {
            return this.name().toLowerCase();
        }
    }

    public EmployeeDB() {
        // Passing table name and settable column names
        super(tableName, new String[] {
                ID.fieldName(),
                FIRST_NAME.fieldName(),
                LAST_NAME.fieldName(),
                EMAIL.fieldName(),
                PASSWORD_HASH.fieldName()
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
    protected Class<Employee> getDomainObjectClass() {
        return Employee.class;
    }

    @Override
    protected Employee buildDomainObjectWithoutAssociations(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt(ID.fieldName()),
                rs.getString(FIRST_NAME.fieldName()),
                rs.getString(LAST_NAME.fieldName()),
                rs.getString(EMAIL.fieldName()),
                rs.getString(PASSWORD_HASH.fieldName())
        );
    }

    @Override
    protected void setAssociatedObjects(Employee obj, ResultSet rs) throws DataAccessException, SQLException {
    }


    public Employee getByEmail(String email) throws DataAccessException {
        return super.getByField(EMAIL.fieldName(), email);
    }

}
