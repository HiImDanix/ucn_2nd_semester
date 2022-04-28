package dal;

import db.DBConnection;
import db.DataAccessException;
import model.modelIF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T extends modelIF> {

    // Store table name and fields
    private String tableName;
    private String[] fields;

    // Constructor
    public DAO(String tableName, String[] fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    // Get table name
    public String getTableName() {
        return tableName;
    }

    // Get fields
    public String[] getFields() {
        return fields;
    }

    // Fields for building SQL statements
    protected String getInsertFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append(", ");
        }
        return sb.toString().substring(0, sb.length() - 2); // Remove last ", "
    }
    protected String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append("?, ");
        }
        return sb.toString().substring(0, sb.length() - 2); // Remove last ", "
    }
    protected String getUpdateFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append(" = ?, ");
        }
        return sb.toString().substring(0, sb.length() - 2); // Remove last ", "
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

    // Methods for setting values in prepared statements
    protected abstract void setValues(PreparedStatement stmt, T obj) throws SQLException;

    // Methods for building objects from ResultSet
    protected abstract T buildDomainObject(ResultSet resultSet) throws DataAccessException;
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
            System.out.println(e);
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
            List<T> objects = buildDomainObjects(resultSet);
            return objects;
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
}
