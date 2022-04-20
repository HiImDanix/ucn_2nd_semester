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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TenantContractController {

    private TenantDBIF tenantDb;
//    private TenantContractDB tenantContractDB;

    public TenantContractController() {
        tenantDb = new TenantDB();
//        tenantContractDB = new TenantContractDB();
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

    public void add(Tenant tenant, Contract contract) throws DataAccessException {
//        tenantContractDB.add(tenant, contract);
        String query = "INSERT INTO tenant_contract (contract_id, tenant_id) VALUES (?, ?)";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, contract.getID());
            stmt.setInt(2, tenant.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not add tenant to contract", e);
        }
    }
}
