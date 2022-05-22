package controller;

import dal.Container;
import db.DBConnection;
import db.DBHelper;
import db.DataAccessException;

import java.io.IOException;
import java.sql.SQLException;

public class DBController {

    DBHelper dbHelperCtrl = new DBHelper();

    public void clear() throws DataAccessException, IOException {
        dbHelperCtrl.clear();
    }

    public void addDemoData() throws DataAccessException {
        dbHelperCtrl.addDemoData();
    }

    public boolean checkConnection() {
        return dbHelperCtrl.checkConnection();
    }

    public void clearLocalContainer() throws DataAccessException {
        Container.clear();
    }

    public void cacheAllData() throws DataAccessException {
    	dbHelperCtrl.cacheAllData();
    }

}
