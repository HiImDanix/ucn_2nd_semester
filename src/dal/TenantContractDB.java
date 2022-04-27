package dal;

import controller.ContractController;
import controller.TenantController;
import db.DBConnection;
import db.DataAccessException;
import model.Contract;
import model.Tenant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/*
 * This class represents the DB access to the tenant-contract join table.
 */
// TODO: Improve this method using a JOIN & exposed buildObjects() methods
public class TenantContractDB implements TenantContractDBIF {

    TenantController tenantCtrl = new TenantController();
    ContractController contractCtrl = new ContractController();

    public static final String tableName = "tenant_contract";

    public enum Columns {
        ID,
        TENANT_ID,
        CONTRACT_ID;
        public String fieldName() {
            return this.name().toLowerCase();
        }
    }

    private static final String addQuery = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
            tableName, Columns.TENANT_ID.fieldName(), Columns.CONTRACT_ID.fieldName());
    private static final String getTenantIdsByContractIdQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
            Columns.TENANT_ID.fieldName(), tableName, Columns.CONTRACT_ID.fieldName());
    private static final String getContractIdsByTenantIdQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
            Columns.CONTRACT_ID.fieldName(), tableName, Columns.TENANT_ID.fieldName());

    @Override
    public List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Tenant> tenants = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(getTenantIdsByContractIdQuery);
            stmt.setInt(1, contractID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int tenantID = rs.getInt(Columns.TENANT_ID.fieldName());
                Tenant tenant = tenantCtrl.getTenantById(tenantID);
                tenants.add(tenant);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not get tenants by contract ID", e);
        }
        return tenants;
    }

    @Override
    public List<Contract> getContractsByTenantID(int tenantID) throws DataAccessException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Contract> contracts = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(getContractIdsByTenantIdQuery);
            stmt.setInt(1, tenantID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int contractID = rs.getInt(Columns.CONTRACT_ID.fieldName());
                Contract contract = contractCtrl.getContractById(contractID);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not get contracts by tenant ID", e);
        }
        return contracts;
    }

    @Override
    public void add(int tenantID, int contractID) throws DataAccessException {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(addQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, tenantID);
            stmt.setInt(2, contractID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not add tenant to contract", e);
        }
    }
}
