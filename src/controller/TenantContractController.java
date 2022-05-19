package controller;

import dal.TenantContractDB;
import dal.TenantContractDBIF;
import db.DataAccessException;
import model.Contract;
import model.Tenant;

import java.time.LocalDate;
import java.util.List;

public class TenantContractController {

    private TenantContractDBIF tenantContractDB;

    public TenantContractController() {
        tenantContractDB = new TenantContractDB();
    }

    public List<Tenant> getTenantsByContractID(int contractID) throws DataAccessException {
        return tenantContractDB.getTenantsByContractID(contractID);
    }

    public List<Contract> getContractsByTenantID(int tenantID) throws DataAccessException {
        return tenantContractDB.getContractsByTenantID(tenantID);
    }

    public List<Integer> getTenantIDsByContractID(int contractID) throws DataAccessException {
        return tenantContractDB.getTenantIDsByContractID(contractID);
    }

    public List<Integer> getContractIDsByTenantID(int tenantID) throws DataAccessException {
        return tenantContractDB.getContractIDsByTenantID(tenantID);
    }

    public void add(Tenant tenant, Contract contract) throws DataAccessException {
        tenantContractDB.add(tenant.getID(), contract.getID());
    }

    public Contract getValidContract(Tenant tenant) {
        List<Contract> contracts = tenant.getContracts();
        for (Contract contract : contracts) {
            if (contract.getLeaveNotice() == null
                    ||  contract.getLeaveNotice().getNoticeGivenDate()
                    .plusDays(contract.getRoom().getRoomCategory().getLeaveNoticeDays())
                    .isAfter(LocalDate.now())) {
                return contract;
            }
        }
        return null;
    }

    public boolean tenantHasValidContract(Tenant tenant) {
        return getValidContract(tenant) != null;
    }

    public boolean tenantHasAnotherValidContract(Tenant tenant, Contract contract) {
        Contract validContract = getValidContract(tenant);
        // TODO: Override equals method?
        return validContract != null && validContract.getID() != contract.getID();

    }
}
