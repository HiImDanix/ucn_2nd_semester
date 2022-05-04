package test.db;

import db.DBConnection;
import db.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestDBConnection {

    static DBConnection dbCon = null;

    @BeforeAll
    static void setUp() throws DataAccessException {
        dbCon = DBConnection.getInstance();
    }


    @Test()
    public void connectAndReconnect() throws SQLException, DataAccessException {
        // Get the connection
        assertNotNull(dbCon.getConnection(), "Connection is null");

        // Retrieve data from the database
        dbCon.getConnection().createStatement().executeQuery("SELECT 1");

        // Disconnect
        dbCon.closeConnection();

        // Check that the connection is closed
        Assertions.assertThrows(SQLException.class,
                () -> dbCon.getConnection().createStatement().executeQuery("SELECT 1"));

        // Get the connection
        dbCon = DBConnection.getInstance();
        assertNotNull(dbCon.getConnection(), "Connection is null");

        // Retrieve data from the database
        dbCon.getConnection().createStatement().executeQuery("SELECT 1");
    }

    @AfterAll
    static void cleanUp() throws DataAccessException, SQLException {
        DBConnection.getInstance().closeConnection();
    }

}

