package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.TenantDB;
import dal.TenantDBIF;
import db.DBConnection;
import db.DataAccessException;
import model.Contract;
import model.StudyProof;
import model.Tenant;

public class TenantController {

	private TenantDBIF tenantDb;
	
	public TenantController() {
		tenantDb = new TenantDB();
	}
	
	public Tenant getTenantById(int id) throws DataAccessException {
		return tenantDb.getById(id);
	}
	
	public Tenant addTenant(String fname, String lname, String email, String phone, StudyProof studyProof, List<Contract> contracts) throws DataAccessException{
		Tenant tenant = new Tenant(-1, fname, lname, email, phone, studyProof, contracts);
		tenant.setId(tenantDb.add(tenant));
		return tenant;
	}

	public Tenant addTenant(Tenant tenant) throws DataAccessException {
		tenant.setId(tenantDb.add(tenant));
		return tenant;
	}

	public List<Tenant> getAllTenants() throws DataAccessException{
		return tenantDb.getAll();
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
