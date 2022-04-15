package controller;

import dal.TenantDB;
import dal.TenantDBIF;
import db.DataAccessException;
import model.Room;
import model.Tenant;

public class TenantController {

	private TenantDBIF tenantDb;
	
	public TenantController() {
		tenantDb = new TenantDB();
	}
	
	public Tenant getTenantById(int id) throws DataAccessException {
		return tenantDb.getById(id);
	}
	
	public Tenant addTenant(String fname, String lname, String email, String phone, Room room) throws DataAccessException{
		Tenant tenant = new Tenant(-1, fname, lname, email, phone, room);
		tenant.setId(tenantDb.add(tenant));
		return tenant;
	}

}
