package db;

import gui.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

public class DBConnection {
    private static final Set<String> POSSIBLE_PROPERTIES = new HashSet<>(Arrays.asList(
            "USERNAME", "PASSWORD", "HOST", "PORT", "DBNAME"));
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL_PREFIX = "jdbc:sqlserver://";

    private static DBConnection instance = null;
    private static Connection con = null;
    private static final Properties properties = new Properties();

    private DBConnection() throws DataAccessException {

        // Read config file
        if (!new File("config.properties").exists()) {
            // If config file does not exist, create it with needed properties but no values
            createProperties();
            Messages.error("A new database config file has been created." +
                    " Please fill in the needed properties.", "Database config file");
            // Exit the app
            System.exit(0);

        } else {
            // Read properties from config file
            readProperties();
        }


        // Try loading the driver
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DataAccessException("Driver not found", e);
        }

        // set up url
        String url = String.format("%s%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true", URL_PREFIX,
                        properties.getProperty("HOST"), properties.getProperty("PORT"),
                        properties.getProperty("DBNAME"));

        // Try connecting to the database
        try {
            con = java.sql.DriverManager.getConnection(url,
                    properties.getProperty("USERNAME"), properties.getProperty("PASSWORD"));
            // Turn on autocommit
            con.setAutoCommit(true);
        }  catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error connecting to the database", e);
        }
    }

    // Read config properties from config file
    private static void readProperties() throws DataAccessException {
        // Read properties from config file
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataAccessException("Error reading config.properties", e);
        }
        // Check that all needed properties are set
        for (String property : POSSIBLE_PROPERTIES) {
            if (!properties.containsKey(property)) {
                throw new DataAccessException("Property " + property + " not set in config.properties");
            }
        }
    }

    // Create config file with default properties but no values
    private static void createProperties() throws DataAccessException {
        try {
            properties.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataAccessException("Error creating config.properties", e);
        }
        // Fill in the properties without values
        for (String property : POSSIBLE_PROPERTIES) {
            properties.setProperty(property, "");
        }
        // Save the properties to the file
        try {
            properties.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataAccessException("Error saving config.properties", e);
        }
    }

    // Close the connection
    public void closeConnection() throws DataAccessException {
        try {
            con.close();
            instance = null;
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error starting transaction", e);
        }
    }

    // commit transaction
    public void commitTransaction() throws DataAccessException {
        try {
            con.commit();
            con.setAutoCommit(true);
        }  catch (SQLException e) {
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

}
