package controller;

import dal.TenantContractDB;
import dal.TenantContractDBIF;
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
        tenantContractDB.add(tenant.getId(), contract.getID());
    }
}
