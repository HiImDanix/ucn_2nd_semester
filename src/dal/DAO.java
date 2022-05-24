package dal;

import db.DBConnection;
import db.DataAccessException;
import model.modelIF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DAO<T extends modelIF> {

    private String tableName;
    private String[] fields;
    private String IDFieldName;

    /*
    *The first field should be the primary key (ID)
     */
    public DAO(String tableName, String[] fields) {
        this.tableName = tableName;
        this.fields = fields;
        this.IDFieldName = fields[0];
    }

    // Get table name
    public String getTableName() {
        return tableName;
    }

    // Get fields excluding the primary key (ID)
    public String[] getFields() {
        return Arrays.copyOfRange(fields, 1, fields.length);
    }

    // Fields for building SQL statements
    protected String getInsertFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append(", ");
        }
        return sb.substring(0, sb.length() - 2); // Remove last ", "
    }
    protected String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("?, ".repeat(getFields().length));
        return sb.substring(0, sb.length() - 2); // Remove last ", "
    }
    protected String getUpdateFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append(" = ?, ");
        }
        return sb.substring(0, sb.length() - 2); // Remove last ", "
    }

    // SQL statements
    protected String getQueryCreate() {
        return "INSERT INTO " + getTableName() + " (" + getInsertFields() + ") VALUES (" + getInsertValues() + ")";
    }
    protected String getQueryUpdate() {
        return "UPDATE " + getTableName() + " SET " + getUpdateFields() + " WHERE id = ?";
    }
    protected String getQueryDelete() {
        return "DELETE FROM " + getTableName() + " WHERE id = ?";
    }
    protected String getQuerySelectById() {
        return "SELECT * FROM " + getTableName() + " WHERE id = ?";
    }
    protected String getQuerySelectAll() {
        return "SELECT * FROM " + getTableName();
    }
    protected String getQuerySelectByField(String field) {
        return "SELECT * FROM " + getTableName() + " WHERE " + field + " = ?";
    }

    /*
     * Returns the object from container, if exists, or builds a new object and adds it to the container.
     */
    protected T buildDomainObject(ResultSet resultSet) throws DataAccessException {
        try {
            // return from container if exists
            int id = resultSet.getInt(IDFieldName);
            if (Container.contains(getDomainObjectClass(), id)) {
                return (T) Container.get(getDomainObjectClass(), id);
            }

            // build object
            T obj = buildDomainObjectWithoutAssociations(resultSet);

            // put object in container
            Container.put(obj);

            // set associated objects
            setAssociatedObjects(obj, resultSet);

            return obj;
        } catch (SQLException e) {
            throw new DataAccessException("Error building object: " + getTableName(), e);
        }
    }
    public List<T> buildDomainObjects(ResultSet resultSet) throws DataAccessException {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(buildDomainObject(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error building domain objects", e);
        }
        return list;
    }

    // add & return id
    public int add(T obj) throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQueryCreate(), PreparedStatement.RETURN_GENERATED_KEYS);
            setValues(stmt, obj);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException("Could not add " + obj.getClass().getSimpleName() + " to database", e);
        }
    }

    public void update(T obj) throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQueryUpdate());
            setValues(stmt, obj);
            stmt.setInt(getFields().length + 1, obj.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not update " + getTableName(), e);
        }
    }

    public void delete(T obj) throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQueryDelete());
            stmt.setInt(1, obj.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not delete " + getTableName(), e);
        }
    }

    public T getById(int id) throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQuerySelectById());
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return buildDomainObject(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Could not get " + getTableName(), e);
        }
    }

    public T getByField(String field, String value) throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQuerySelectByField(field));
            stmt.setString(1, value);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return buildDomainObject(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Could not get " + getTableName(), e);
        }
    }

    public List<T> getAll() throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQuerySelectAll());
            ResultSet resultSet = stmt.executeQuery();
            return buildDomainObjects(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException("Could not get all " + getTableName(), e);
        }
    }

    public List<T> getAllByField(String field, String value) throws DataAccessException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(getQuerySelectByField(field));
            stmt.setString(1, value);
            ResultSet resultSet = stmt.executeQuery();
            return buildDomainObjects(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException("Could not get all " + getTableName(), e);
        }
    }

    /*
     * This method should set the prepared statement parameters using the given object
     * in the order that the columns were provided in constructor
     */
    protected abstract void setValues(PreparedStatement stmt, T obj) throws SQLException;

    /*
     * This method should return the model object's class type
     */
    protected abstract Class<T> getDomainObjectClass();

    /*
     * This method should create the model object with fields from the result set, but without any associated objects.
     */
    protected abstract T buildDomainObjectWithoutAssociations(ResultSet rs) throws SQLException;

    /*
     * This method should set the associated objects for the given model object,
     * as well as reference the object back from the associated object to the model object.
     */
    protected abstract void setAssociatedObjects(T obj, ResultSet rs) throws DataAccessException, SQLException;
}
