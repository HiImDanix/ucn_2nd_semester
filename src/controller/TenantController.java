package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
	
	public Tenant addTenant(String fname, String lname, String email, String phone, StudyProof studyProof) throws DataAccessException{
		Tenant tenant = new Tenant(-1, fname, lname, email, phone, studyProof, new ArrayList<>());
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

	public List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException {
		return new TenantContractController().getTenantsByContractID(contractID);
	}

    public void updateTenant(Tenant tenant, String firstName, String lastName, String email, String phone)
			throws DataAccessException {
		// capture old values
		String oldFirstName = tenant.getFirstName();
		String oldLastName = tenant.getLastName();
		String oldEmail = tenant.getEmail();
		String oldPhone = tenant.getPhone();

		// update new values
		tenant.setFirstName(firstName);
		tenant.setLastName(lastName);
		tenant.setEmail(email);
		tenant.setPhone(phone);

		// update database
		try {
			tenantDb.update(tenant);
		} catch (DataAccessException e) {
			// revert to old values
			tenant.setFirstName(oldFirstName);
			tenant.setLastName(oldLastName);
			tenant.setEmail(oldEmail);
			tenant.setPhone(oldPhone);
			throw e;
		}
	}

	public boolean tenantHasValidContract(Tenant tenant) {
		List<Contract> contracts = tenant.getContracts();
		for (Contract contract : contracts) {
			if (contract.getLeaveNotice() == null
					||  contract.getLeaveNotice().getNoticeGivenDate()
					.plusDays(contract.getRoom().getRoomCategory().getLeaveNoticeDays())
					.isAfter(LocalDate.now())) {
				return true;
			}
		}
		return false;
	}

}
