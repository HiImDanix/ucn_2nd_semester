package db;

import controller.*;
import gui.App;
import model.Room;
import model.RoomCategory;
import model.Tenant;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper {
    // The SQL file to create tables. Statements are separated by semi-colons.
    private static final String TABLE_SETUP_SQL_FILENAME = "sql/table_setup.sql";

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
        RoomCategory roomCategory1 = new RoomCategory(-1, "A", "Single room", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BigDecimal.valueOf(100), 2, 1, null);

        // Add rooms
        RoomController roomCtrl = new RoomController();
        Room room1 = roomCtrl.addRoom(roomCategory1, false);
        Room room2 = roomCtrl.addRoom(roomCategory1, true);

        // Add tenants
        TenantController tenantCtrl = new TenantController();
        Tenant tenant1 = tenantCtrl.addTenant("Daniels", "Kanepe", "danielskanepe@email.com", "0712665347", null);
        Tenant tenant2 = tenantCtrl.addTenant("Andras", "Varsanyi", "danielskanepe@email.com", "0542365211", null);
        Tenant tenant3 = tenantCtrl.addTenant("Ondrej", "Dobis", "danielskanepe@email.com", "+45573962625", null);

        // Add contracts
        ContractController contractCtrl = new ContractController();
        contractCtrl.addContract(LocalDate.now(), Arrays.asList(tenant1), room1, false);


    }
}
