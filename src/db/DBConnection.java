package db;

import gui.App;

import java.sql.*;
import java.util.*;
import java.io.*;

public class DBConnection {
    private static final Set<String> POSSIBLE_PROPERTIES = new HashSet<>(Arrays.asList(
            "DB_USERNAME", "DB_PASSWORD", "DB_HOST", "DB_PORT", "DB_NAME"));
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL_PREFIX = "jdbc:sqlserver://";
    private static final String CONFIG_FILENAME = "config.properties";


    private static DBConnection instance = null;
    private static Connection con = null;
    private static final Properties properties = new Properties();

    private DBConnection() throws DataAccessException {

        InputStream configFileStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILENAME);
        // If properties already have been read, don't read them again
        if (properties.isEmpty()) {
            if (configFileStream == null) {
                    if (App.DEBUG) {
                        System.out.println("DEBUG: Config file not found. Please create a  + " + CONFIG_FILENAME + " file in the resources folder that contains the following properties: " + POSSIBLE_PROPERTIES);
                    }
                    throw new DataAccessException("Config file " + CONFIG_FILENAME + " not found");
            } else {
                readProperties(configFileStream);
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
                        properties.getProperty("DB_HOST"), properties.getProperty("DB_PORT"),
                        properties.getProperty("DB_NAME"));


        // Try connecting to the database
        try {
            con = java.sql.DriverManager.getConnection(url,
                    properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"));
            // Turn on autocommit
            con.setAutoCommit(true);
        }  catch (SQLException e) {
            throw new DataAccessException("Error connecting to the database", e);
        }
    }

    /*
     * Read properties from config file
     */
    private static void readProperties(InputStream configFileStream) throws DataAccessException {
        // Read properties from config file
        try {
            properties.load(configFileStream);
        } catch (IOException e) {
            throw new DataAccessException("Error reading config.properties", e);
        }
        // Check that all needed properties are set
        List<String> missingProperties = new ArrayList<>();
        for (String property : POSSIBLE_PROPERTIES) {

            if (!properties.containsKey(property)) {
                missingProperties.add(property);
            }
        }
        if (!missingProperties.isEmpty()) {
            if (App.DEBUG) {
                System.out.println("DEBUG: Please set the following properties in " + CONFIG_FILENAME + ": " + missingProperties);
            }
            throw new DataAccessException("Missing properties: " + String.join(", ", missingProperties));
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
        try {
            if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed()) {
                instance = new DBConnection();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error getting instance of DBConnection", e);
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
