package db;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 * Author: Daniels Kanepe
 */
public class DBConnectionTest {

    private DBConnection dbCon;

    @BeforeEach
    public void init() throws DataAccessException {
        dbCon = DBConnection.getInstance();

    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        DBConnection.getInstance().closeConnection();
    }

    @Test
    public void connect() {
        // Get connection
        Connection con = dbCon.getConnection();

        // test that the connection is not null
        assertNotNull(con, "Connection is null");

        // Retrieve data from the database (should not throw an exception)
        Assertions.assertDoesNotThrow(() -> con.createStatement().executeQuery("SELECT 1"));
    }

    @Test
    public void disconnect() throws DataAccessException {
        // Get the connection
        Connection con = dbCon.getConnection();

        // Disconnect
        dbCon.closeConnection();

        // Check that the connection is closed
        Assertions.assertThrows(SQLException.class, () -> con.createStatement().executeQuery("SELECT 1"));
    }

    @Test()
    public void reconnect() throws DataAccessException {
        // Get the connection
        // Already done in init()

        // Disconnect
        dbCon.closeConnection();

        // use getInstance() to re-establish the connection
        dbCon = DBConnection.getInstance();

        assertNotNull(dbCon, "Connection is null");

        // Try retrieving data from the database
        Assertions.assertDoesNotThrow(() -> dbCon.getConnection().createStatement().executeQuery("SELECT 1"));
    }

}

