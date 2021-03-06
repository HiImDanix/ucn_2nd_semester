package db;

import controller.*;
import gui.App;
import model.Room;
import model.RoomCategory;
import model.Tenant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DBHelper {
    // The SQL file to create tables. Statements are separated by semi-colons.
    private static final String TABLE_SETUP_SQL_FILENAME = "sql/table_setup.sql";

    // Note for classmates: it rebuilds the database, because we have to drop foreign keys.
    public void clear() throws DataAccessException, IOException {
        Connection conn = DBConnection.getInstance().getConnection();
        // Get all tables
        List<String> tables = DBConnection.getInstance().getTables(conn);

        // drop all foreign keys
        for (String table : tables) {
            DBConnection.getInstance().dropForeignKeys(conn, table);
        }
        // drop all tables
        for (String table : tables) {
            DBConnection.getInstance().dropTable(conn, table);
        }
        // create all tables
        for (String statement : getTableSetupStatements()) {
            try {
                DBConnection.getInstance().getConnection().createStatement().execute(statement);
            } catch (SQLException e) {
                throw new DataAccessException("Error executing SQL statements to create tables", e);
            }
        }
    }

    private String[] getTableSetupStatements() throws DataAccessException, IOException {
        // Read file from resources
        InputStream is = getClass().getClassLoader().getResourceAsStream(TABLE_SETUP_SQL_FILENAME);
        if (is == null) {
            throw new DataAccessException("Could not find SQL file: " + TABLE_SETUP_SQL_FILENAME);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString().split(";");
    }

    public void addDemoData() throws DataAccessException {
        // NOTE: SOME DATA IS STUBBED FOR NOW as there is no need to implement it yet due to current use case

        // Add default login
        new EmployeeController().addEmployee("Admin", "Admin", App.DEFAULT_EMAIL, App.DEFAULT_PASSWORD);

        // Add room categories
        RoomCategoryController roomCategoryCtrl = new RoomCategoryController();
        RoomCategory rc1 = roomCategoryCtrl.addRoomCategory( "S1", "Single room", BigDecimal.valueOf(4000), BigDecimal.valueOf(200), BigDecimal.valueOf(100), 2, 1);
        RoomCategory rc2 = roomCategoryCtrl.addRoomCategory( "D1", "Double room", BigDecimal.valueOf(4000), BigDecimal.valueOf(200), BigDecimal.valueOf(100), 2, 1);
        RoomCategory rc3 = roomCategoryCtrl.addRoomCategory( "P1", "Presidential suite ", BigDecimal.valueOf(12000), BigDecimal.valueOf(200), BigDecimal.valueOf(100), 2, 1);

        // Add rooms
        RoomController roomCtrl = new RoomController();
        Room room1 = roomCtrl.addRoom(rc1, false);
        Room room2 = roomCtrl.addRoom(rc1, true);
        Room room3 = roomCtrl.addRoom(rc2, false);
        Room room4 = roomCtrl.addRoom(rc3, false);

        // Add tenants
        TenantController tenantCtrl = new TenantController();
        Tenant tenant1 = tenantCtrl.addTenant("Daniels", "Kanepe", "danielskanepe@email.com", "0712665347", null);
        Tenant tenant2 = tenantCtrl.addTenant("Andras", "Varsanyi", "andrasvarsanyi@yahoo.com", "0542365211", null);
        Tenant tenant3 = tenantCtrl.addTenant("Ondrej", "Dobis", "ondrejdobis@aol.com", "+45573962625", null);

        // Add contracts
        ContractController contractCtrl = new ContractController();
        contractCtrl.addContract(LocalDate.now(), Arrays.asList(tenant1), room1, false);


    }

    public boolean checkConnection() {
        try {
            DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT 1");
        } catch (SQLException | DataAccessException e) {
            return false;
        }
        return true;
    }

    public void cacheAllData() {
        try {
            new EmployeeController().getAllEmployees();
            new RoomCategoryController().getAllRoomCategories();
            new RoomController().getAllRooms();
            new TenantController().getAllTenants();
            new ContractController().getAllContracts();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
