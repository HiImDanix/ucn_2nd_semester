package dal;

import db.DBConnection;
import db.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {
    public abstract String getTableName();
    public abstract String[] getFields(); // all fields in the table

    // Fields for building SQL statements
    public String getInsertFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append(", ");
        }
        return sb.toString().substring(0, sb.length() - 2); // Remove last ", "
    }
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append("?, ");
        }
        return sb.toString().substring(0, sb.length() - 2); // Remove last ", "
    }
    public String getUpdateFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append(" = ?, ");
        }
        return sb.toString().substring(0, sb.length() - 2); // Remove last ", "
    }

    // SQL statements
    public String getQueryCreate() {
        return "INSERT INTO " + getTableName() + " (" + getInsertFields() + ") VALUES (" + getInsertValues() + ")";
    }
    public String getQueryUpdate() {
        return "UPDATE " + getTableName() + " SET " + getUpdateFields() + " WHERE id = ?";
    }
    public String getQueryDelete() {
        return "DELETE FROM " + getTableName() + " WHERE id = ?";
    }
    public String getQuerySelectById() {
        return "SELECT * FROM " + getTableName() + " WHERE id = ?";
    }
    public String getQuerySelectAll() {
        return "SELECT * FROM " + getTableName();
    }

    // Methods for setting values in prepared statements
    public abstract void setValues(PreparedStatement stmt, T obj) throws SQLException;
    public abstract void setValues(PreparedStatement stmt, int id) throws SQLException;

    // Methods for building objects from ResultSet
    public abstract T buildDomainObject(ResultSet resultSet) throws SQLException;
    public List<T> buildDomainObjects(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(buildDomainObject(resultSet));
        }
        return list;
    }

    // CRUD methods
    public void create(T obj) throws DataAccessException {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(getQueryCreate());
            setValues(stmt, obj);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not create " + getTableName(), e);
        }
    }

    public void update(T obj) throws DataAccessException {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(getQueryUpdate());
            setValues(stmt, obj);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not update " + getTableName(), e);
        }
    }

    public void delete(int id) throws DataAccessException {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(getQueryDelete());
            setValues(stmt, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not delete " + getTableName(), e);
        }
    }

    public T getById(int id) throws DataAccessException {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(getQuerySelectById());
            setValues(stmt, id);
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
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(getQuerySelectAll());
            ResultSet resultSet = stmt.executeQuery();
            return buildDomainObjects(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException("Could not get all " + getTableName(), e);
        }
    }
}
