package dal;

import controller.TenantController;
import db.DBConnection;
import db.DataAccessException;
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
public class TenantContractDB implements TenantContractDBIF {

    TenantController tenantCtrl = new TenantController();

    public static final String tableName = "tenant_contract";

    public enum Columns {
        ID,
        tenant_id,
        contract_id;
        public String fieldName() {
            return this.name().toLowerCase();
        }
    }

    private static final String addQuery = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
            tableName, Columns.tenant_id.fieldName(), Columns.contract_id.fieldName());
    private static final String getTenantIdsByContractIdQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
            Columns.tenant_id.fieldName(), tableName, Columns.contract_id.fieldName());

    @Override
    public List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException {
        // TODO: Improve this method using a JOIN & exposed buildObjects() method for tenants
        Connection connection = DBConnection.getInstance().getConnection();
        List<Tenant> tenants = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(getTenantIdsByContractIdQuery);
            stmt.setInt(1, contractID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int tenantID = rs.getInt("tenant_id");
                Tenant tenant = tenantCtrl.getTenantById(tenantID);
                tenants.add(tenant);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not get tenants by contract ID", e);
        }
        return tenants;
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
