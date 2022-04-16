package controller;

import dal.ContractDB;
import dal.ContractDBIF;
import dal.TenantDB;
import dal.TenantDBIF;
import db.DataAccessException;
import model.Contract;
import model.Room;
import model.Tenant;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class ContractController {
    private ContractDBIF contractDB;

    public ContractController() {
        contractDB = new ContractDB();
    }

    /*
     * Add contract with tenants
     */
    public Contract addRoom(boolean includeInternet, LocalDateTime startDateTime, Room room, List<Tenant> tenants) throws DataAccessException {
        Contract contract = new Contract(-1, includeInternet, startDateTime, room, tenants, Collections.emptyList(), Collections.emptyList(), null);
        // TODO: transaction
        contract.setID(contractDB.add(contract));
        for (Tenant tenant : tenants) {
            tenant.addContract(contract);
            new TenantController().addTenant(tenant);
        }
        // if fails, remove contracts from tenants
        // for (Tenant tenant : tenants) {
        //     tenant.removeContract(contract);
        // }

        return contract;

    }

    public List<Contract> getAllContracts() throws DataAccessException {
        return contractDB.getAll();
    }

    public Contract getContractById(int id) throws DataAccessException {
        return contractDB.getById(id);
    }

    public Contract updateIncludeInternet(boolean includeInternet, Contract contract) throws DataAccessException {
        // capture old object's value
        boolean oldIncludeInternet = contract.isIncludeInternet();
        contract.setIncludeInternet(includeInternet);
        try {
            contractDB.update(contract);
        } catch (DataAccessException e) {
            // rollback
            contract.setIncludeInternet(oldIncludeInternet);
            throw e;

        }
        return contract;
    }


}
