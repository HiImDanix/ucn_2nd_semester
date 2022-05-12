package test.db;

import db.DBConnection;
import db.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestDBConnection {

    @AfterAll
    static void cleanUp() throws DataAccessException {
        DBConnection.getInstance().closeConnection();
    }

    @Test
    public void connect() throws DataAccessException {
        // Get connection
        Connection con = DBConnection.getInstance().getConnection();

        // test that the connection is not null
        assertNotNull(con, "Connection is null");

        // Retrieve data from the database (should not throw an exception)
        Assertions.assertDoesNotThrow(() -> con.createStatement().executeQuery("SELECT 1"));
    }

    @Test
    public void disconnect() throws DataAccessException {
        // Get the connection
        Connection con = DBConnection.getInstance().getConnection();

        // Disconnect
        DBConnection.getInstance().closeConnection();

        // Check that the connection is closed
        Assertions.assertThrows(SQLException.class, () -> con.createStatement().executeQuery("SELECT 1"));
    }

    @Test()
    public void Reconnect() throws DataAccessException {
        // Get the connection
        DBConnection.getInstance().getConnection();

        // Disconnect
        DBConnection.getInstance().closeConnection();

        assertNotNull(DBConnection.getInstance().getConnection(), "Connection is null");

        // Try retrieving data from the database
        Assertions.assertDoesNotThrow(() -> DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT 1"));
    }

}

