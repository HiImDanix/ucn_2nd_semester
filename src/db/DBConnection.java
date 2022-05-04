package db;

import gui.Messages;

import java.sql.*;
import java.util.*;
import java.io.*;

public class DBConnection {
    private static final Set<String> POSSIBLE_PROPERTIES = new HashSet<>(Arrays.asList(
            "USERNAME", "PASSWORD", "HOST", "PORT", "DBNAME"));
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL_PREFIX = "jdbc:sqlserver://";
    private static final String CONFIG_FILE = "config.properties";


    private static DBConnection instance = null;
    private static Connection con = null;
    private static final Properties properties = new Properties();

    private DBConnection() throws DataAccessException {

        // If properties already have been read, don't read them again
        if (properties.isEmpty()) {
            if (!new File(CONFIG_FILE).exists()) {
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
        }

        // Try loading the driver
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataAccessException("Driver not found", e);
        }

        // set up url
        String url = String.format("%s%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true", URL_PREFIX,
                        properties.getProperty("HOST"), properties.getProperty("PORT"),
                        properties.getProperty("DBNAME"));


        // print all properties
        for (String property : properties.stringPropertyNames()) {
            System.out.println(property + ": " + properties.getProperty(property));
        }

        // Try connecting to the database
        try {
            con = java.sql.DriverManager.getConnection(url,
                    properties.getProperty("USERNAME"), properties.getProperty("PASSWORD"));
            // Turn on autocommit
            con.setAutoCommit(true);
        }  catch (SQLException e) {
            throw new DataAccessException("Error connecting to the database", e);
        }
    }

    /*
     * Read properties from config file
     */
    private static void readProperties() throws DataAccessException {
        // Read properties from config file
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new DataAccessException("Error reading config.properties", e);
        }
        // Check that all needed properties are set
        for (String property : POSSIBLE_PROPERTIES) {
            if (!properties.containsKey(property)) {
                throw new DataAccessException("Property " + property + " not set in config.properties");
            }
        }
    }

    /*
     * Create a new config file with default properties but no values
     */
    private static void createProperties() throws DataAccessException {
        try {
            properties.store(new FileOutputStream(CONFIG_FILE), null);
        } catch (IOException e) {
            throw new DataAccessException("Error creating config.properties", e);
        }
        // Fill in the properties without values
        for (String property : POSSIBLE_PROPERTIES) {
            properties.setProperty(property, "");
        }
        // Save the properties to the file
        try {
            properties.store(new FileOutputStream(CONFIG_FILE), null);
        } catch (IOException e) {
            throw new DataAccessException("Error saving config.properties", e);
        }
    }

    /*
     * Close the connection to the database
     */
    public void closeConnection() throws DataAccessException {
        try {
            con.close();
            instance = null;
        } catch (SQLException e) {
            throw new DataAccessException("Error closing the connection", e);
        }
    }

    /*
     * Get the database's Connection object
     */
    public Connection getConnection() {
        return con;
    }

    /*
     * Singleton pattern
     */
    public static synchronized DBConnection getInstance() throws DataAccessException {
        if (instance == null || instance.getConnection() == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /*
     * Start transaction
     */
    public void startTransaction() throws DataAccessException {
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataAccessException("Error starting transaction", e);
        }
    }

    /*
     * commit transaction
     */
    public void commitTransaction() throws DataAccessException {
        try {
            con.commit();
            con.setAutoCommit(true);
        }  catch (SQLException e) {
            throw new DataAccessException("Error committing transaction", e);
        }
    }

    /*
     * Rollback transaction
     */
    public void rollbackTransaction() throws DataAccessException {
        try {
            con.rollback();
            con.setAutoCommit(true);
        }
        catch (SQLException e) {
            throw new DataAccessException("Error rolling back transaction", e);
        }
    }

    // Execute prepared statement and return the generated primary key

//    /**
//     * Execute a prepared statement and return the generated primary key
//     * @param ps Prepared statement that has been prepared with RETURN_GENERATED_KEYS enabled.
//     *
//     * @return The generated primary key
//     *
//     * @throws DataAccessException If an error occurs during the execution of the statement
//     */
//    public int executeStatementReturnID(PreparedStatement ps) throws DataAccessException {
//        try {
//            ps.executeUpdate();
//            return ps.getGeneratedKeys().getInt(1);
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("Error executing statement", e);
//        }
//    }

    // Get tables names for current database except system tables
    public List<String> getTables(Connection conn) throws DataAccessException {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = meta.getTables(null, null, "%", types);
            while (rs.next()) {
                // add if not a system table
                if (!rs.getString("TABLE_NAME").startsWith("trace_")) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error getting tables", e);
        }
        return tables;
    }

    // Drop foreign keys
    public void dropForeignKeys(Connection conn, String tableName) throws DataAccessException {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getImportedKeys(null, null, tableName);
            while (rs.next()) {
                String constraintName = rs.getString("FK_NAME");
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("ALTER TABLE " + tableName + " DROP CONSTRAINT " + constraintName);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error dropping foreign keys", e);
        }
    }

    // Drop table
    public void dropTable(Connection conn, String tableName) throws DataAccessException {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE " + tableName);
        } catch (SQLException e) {
            throw new DataAccessException("Error dropping table", e);
        }
    }

}
