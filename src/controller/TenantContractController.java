package controller;

import dal.TenantContractDB;
import dal.TenantContractDBIF;
import dal.TenantDB;
import dal.TenantDBIF;
import db.DataAccessException;
import model.Contract;
import model.Tenant;

import java.util.List;

public class TenantContractController {

    private TenantDBIF tenantDb;
    private TenantContractDBIF tenantContractDB;

    public TenantContractController() {
        tenantDb = new TenantDB();
        tenantContractDB = new TenantContractDB();
    }

    // TODO: Improve this using a JOIN & exposed buildObjects() method for tenants
    public List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException {
        return tenantContractDB.getTenantsByContractID(contractID);
    }

    public void add(Tenant tenant, Contract contract) throws DataAccessException {
        tenantContractDB.add(tenant.getID(), contract.getID());
    }
}
