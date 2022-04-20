package controller;

import dal.TenantDB;
import dal.TenantDBIF;
import db.DBConnection;
import db.DataAccessException;
import gui.Messages;
import model.Contract;
import model.Room;
import model.Tenant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TenantContractController {

    private TenantDBIF tenantDb;

    public TenantContractController() {
        tenantDb = new TenantDB();
    }

    // TODO: Improve this using a JOIN & exposed buildObjects() method for tenants
    public List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException {
        String query = "SELECT tenant_id FROM tenant_contract WHERE contract_id = " + contractID;
        Connection connection = DBConnection.getInstance().getConnection();
        List<Tenant> tenants = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                int tenantID = resultSet.getInt("tenant_id");
                Tenant tenant = tenantDb.getById(tenantID);
                tenants.add(tenant);
            }
            // tenants = tenantDb.buildDomainObjects(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException("Could not get tenants by contract ID", e);
        }
        return tenants;

    }
}
