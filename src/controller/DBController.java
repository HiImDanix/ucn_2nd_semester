package controller;

import db.DBConnection;
import db.DBHelper;
import db.DataAccessException;

import java.io.IOException;
import java.sql.SQLException;

public class DBController {

    public void clear() throws DataAccessException, IOException {
        new DBHelper().clear();
    }

    public void addDemoData() throws DataAccessException {
        new DBHelper().addDemoData();
    }

    public boolean checkConnection() {
        try {
            DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT 1");
        } catch (SQLException | DataAccessException e) {
            return false;
        }
        return true;
    }
}
