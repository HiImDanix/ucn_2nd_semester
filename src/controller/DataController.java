package controller;

import db.DBConnection;
import db.DBHelper;
import db.DataAccessException;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class DataController {

    public void clear() throws DataAccessException, IOException {
        new DBHelper().clear();
    }

    public void addDemoData() throws DataAccessException {
        new DBHelper().addDemoData();
    }
}
