package dal;

import db.DataAccessException;
import model.Tenant;

import java.util.List;

/*
 * This is the interface for tenant-contract join table represented by TenantContractDB class.
 */
public interface TenantContractDBIF {

    List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException;

    // Join a tenant to a contract
    void add(int tenantID, int contractID) throws DataAccessException;
}
