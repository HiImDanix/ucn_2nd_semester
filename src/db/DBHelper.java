package db;

import controller.*;
import model.RoomCategory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBHelper {
    // The SQL file to create tables. Statements are separated by semi-colons.
    File file = new File("sql/table_setup.sql");

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
        // Read statements from file where each statement ends with ;
        String fileContent = Files.readString(file.toPath());
        return fileContent.split(";");
    }

    public void addDemoData() throws DataAccessException {
        // SOME DATA IS STUBBED FOR NOW (no controllers are used)

        // Add room categories
        RoomCategoryController roomCategoryCtrl = new RoomCategoryController();

        // Add rooms
        RoomController roomCtrl = new RoomController();
//        roomCtrl.addRoom(roomCategory1, false);
//        roomCtrl.addRoom(roomCategory1, true);

        // Add tenants
        TenantController tenantCtrl = new TenantController();
        tenantCtrl.addTenant("Daniels", "Kanepe", "danielskanepe@email.com", "0712665347", null);
        tenantCtrl.addTenant("Andras", "Varsanyi", "danielskanepe@email.com", "0542365211", null);
        tenantCtrl.addTenant("Ondrej", "Dobis", "danielskanepe@email.com", "+45573962625", null);

        // Add contracts
        ContractController contractCtrl = new ContractController();


    }
}