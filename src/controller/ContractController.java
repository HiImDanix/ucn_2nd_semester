package controller;

import dal.ContractDB;
import dal.ContractDBIF;
import dal.TenantDB;
import dal.TenantDBIF;
import db.DataAccessException;
import gui.Messages;
import model.Contract;
import model.Room;
import model.Tenant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class ContractController {
    private ContractDBIF contractDB;

    public ContractController() {
        contractDB = new ContractDB();
    }

    public Contract addContract(LocalDate newStartDate, List<Tenant> tenants, Room room, boolean includeInternet)
            throws DataAccessException {
        Contract contract = new Contract(-1, includeInternet, newStartDate, room, tenants,
                Collections.emptyList(), Collections.emptyList(), null);
        try {
            contract.setID(contractDB.add(contract));
        } catch (DataAccessException e) {
            throw e;
        }
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

    public void updateContract(Contract contract, LocalDate newStartDate,
                               List<Tenant> tenants, Room room, boolean includeInternet) throws DataAccessException {

        // capture current values
        LocalDate oldStartDate = contract.getStartDate();
        List<Tenant> oldTenants = contract.getTenants();
        Room oldRoom = contract.getRoom();
        boolean oldIncludeInternet = contract.isIncludeInternet();

        // update values
        contract.setStartDate(newStartDate);
        contract.setTenants(tenants);
        contract.setRoom(room);
        contract.setIncludeInternet(includeInternet);

        // update database
        try {
            contractDB.update(contract);
        } catch (DataAccessException e) {
            // rollback
            contract.setStartDate(oldStartDate);
            contract.setTenants(oldTenants);
            contract.setRoom(oldRoom);
            contract.setIncludeInternet(oldIncludeInternet);
            throw e;
        }
    }
}
