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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class ContractController {
    private ContractDBIF contractDB;

    public ContractController() {
        contractDB = new ContractDB();
    }

    /*
     * Creates a new contract.
     */
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

    public List<Integer> getAllContractIDsByRoomID(int roomID) throws DataAccessException {
        return contractDB.getAllContractIDsByRoomID(roomID);
    }

    /*
    Returns contract end date, or null
     */
    public LocalDate getEndDate(Contract contract) {
        if (contract.getLeaveNotice() != null) {
            return contract.getLeaveNotice().getNoticeGivenDate()
                    .plusDays(contract.getRoom().getRoomCategory().getLeaveNoticeDays());
        }
        return null;
    }

    /*
     * Returns contract's total cost per month including internet & extra tenants.
     */
    public BigDecimal getTotalPricePerMonth(Contract contract) {
        BigDecimal pricePerMonth = contract.getRoom().getRoomCategory().getPricePerMonth();
        BigDecimal internetCost = contract.isIncludeInternet() ?
                contract.getRoom().getRoomCategory().getPricePerMonthForInternet() : BigDecimal.ZERO;
        BigDecimal extraTenantCost = contract.getRoom().getRoomCategory().getPricePerMonthForExtraTenant();
        BigDecimal extraTenantCount = BigDecimal.valueOf(contract.getTenants().size() - 1);

        return pricePerMonth.add(internetCost).add(extraTenantCost.multiply(extraTenantCount));
    }
}
