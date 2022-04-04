package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    private static final String USER = "sa";
    private static final String PASS = "secret2022*";
    private static final String HOST = "localhost";
    private static final String PORT = "1433";
    private static final String DBNAME = "Dormily";
    private static final String SERVERNAME = "SQLEXPRESS";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL =
            String.format("jdbc:sqlserver://%s:%s\\%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true",
                    HOST, PORT, SERVERNAME, DBNAME, USER, PASS);

    private static DBConnection instance = null;
    private static Connection con = null;

    private DBConnection() throws DataAccessException {
        // Try loading the driver
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DataAccessException("Driver not found", e);
        }
        // Connection to the database
        try {
            con = java.sql.DriverManager.getConnection(URL, USER, PASS);
            // Turn on autocommit
            con.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error connecting to the database", e);
        }
    }

    // Close the connection
    public void closeConnection() throws DataAccessException {
        try {
            con.close();
            instance = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error closing the connection", e);
        }
    }

    // Get DB connection
    public Connection getDBCon() {
        return con;
    }

    public static synchronized DBConnection getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // start transaction
    public void startTransaction() throws DataAccessException {
        try {
            con.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error starting transaction", e);
        }
    }

    // commit transaction
    public void commitTransaction() throws DataAccessException {
        try {
            con.commit();
            con.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error committing transaction", e);
        }
    }

    // rollback transaction
    public void rollbackTransaction() throws DataAccessException {
        try {
            con.rollback();
            con.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error rolling back transaction", e);
        }
    }

    // Execute prepared statement and return the generated primary key
    public int executeStatement(PreparedStatement ps) throws DataAccessException {
        try {
            ps.executeUpdate();
            return ps.getGeneratedKeys().getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error executing statement", e);
        }

    }
    public static String getDriver() {
        return DRIVER;
    }

    public static String getURL() {
        return URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASS() {
        return PASS;
    }
}
