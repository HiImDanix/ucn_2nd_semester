package gui.panels.tablemodels;

import controller.ContractController;
import gui.Common;
import model.Contract;
import model.Tenant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractTableModel extends MyAbstractTableModel<Contract> {

    ContractController contractCtrl = new ContractController();

	private static final String[] COLUMN_NAMES = {"Contract ID", "Tenants", "Room ID", "Room category", "start date", "end date", "Total price/m", "Internet included"};

	private List<Contract> contracts;

	public ContractTableModel(List<Contract> contract) {
        // Copying array to prevent mutation
		this.contracts = new ArrayList<>(contract);
	}
	
	@Override
	public int getRowCount() {
		return contracts.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            default: return String.class;
        }
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Contract contract = contracts.get(rowIndex);

        // Tenant representation
        StringBuilder tenantRepresentation = new StringBuilder();
        for (Tenant tenant: contract.getTenants()){
            tenantRepresentation.append(
                    String.format(
                            "(%d) %s %s, ", tenant.getID(), tenant.getFirstName(), tenant.getLastName())
            );
        }
        if (tenantRepresentation.length() > 0) {
            tenantRepresentation.deleteCharAt(tenantRepresentation.length() - 2); // remove last space & comma
        }

        LocalDate contractEndDate = contractCtrl.getEndDate(contract);


        switch (columnIndex) {
            case 0: return "#" + contract.getID();
            case 1: return tenantRepresentation.toString();
            case 2: return contract.getRoom().getID();
            case 3: return contract.getRoom().getRoomCategory().getName();
            case 4: return Common.dateToString(contract.getStartDate());
            case 5: return contractEndDate != null ? Common.dateToString(contractEndDate) : "-";
            case 6: return Common.toCurrency(contractCtrl.getTotalPricePerMonth(contract));
            case 7: return contract.isIncludeInternet() ? "Yes" : "No";
            default: return "ERROR";
        }
	}

	// Make cells uneditable
    @Override
    public boolean isCellEditable(int row, int column) {       
        return false;
    }
    
    /**
     * Gets the Contract object by row
     *
     * @param row the row
     * @return the Tenant object
     */
    public Contract getObj(int row) {
    	return contracts.get(row);
    }
    
  
    /**
     * Adds a contract to the table
     *
     * @param contract the contract
     */
    public void add(Contract contract) {
        this.contracts.add(contract);
        fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }
    
    /**
     * Removes the contract from the table by row
     *
     * @param row the row
     */
    public void remove(int row) {
    	this.contracts.remove(row);
    	this.fireTableRowsDeleted(row, row);
    }
}
