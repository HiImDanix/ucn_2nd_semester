package gui.panels.tablemodels;

import java.util.ArrayList;
import java.util.List;

import controller.TenantContractController;
import db.DataAccessException;
import gui.Common;
import model.Contract;
import model.Tenant;

public class TenantTableModel extends MyAbstractTableModel<Tenant> {

	private static final String[] COLUMN_NAMES = {"ID", "First name", "Last name", "Email", "Phone", "Study proof?", "Contracts"};

	private List<Tenant> tenants;
	
	public TenantTableModel(List<Tenant> tenants) {
        // Copying array to prevent mutation
		this.tenants = new ArrayList<>(tenants);
	}
	
	@Override
	public int getRowCount() {
		return tenants.size();
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
		Tenant tenant = tenants.get(rowIndex);

        TenantContractController tenantContractCtrl = new TenantContractController();
        StringBuilder contractRepr = new StringBuilder("");
        for (Contract contract: tenant.getContracts()) {
            contractRepr.append(String.format("(%d) Room %s, ",
                    contract.getRoom().getID(), contract.getRoom().getRoomCategory().getName()));
        }
        if (contractRepr.length() > 0) {
            contractRepr.delete(contractRepr.length() - 2, contractRepr.length()); // remove last comma
        }

        switch (columnIndex) {
            case 0: return "#" + tenant.getID();
            case 1: return tenant.getFirstName();
            case 2: return tenant.getLastName();
            case 3: return tenant.getEmail();
            case 4: return tenant.getPhone();
            case 5: return tenant.getStudyProof() != null
                    ? "Until: " + Common.dateToString(tenant.getStudyProof().getStudentUntilDate())
                    : "Not provided";
            case 6: return contractRepr.toString();
            default: return "ERROR";
        }
	}

	// Make cells uneditable
    @Override
    public boolean isCellEditable(int row, int column) {       
        return false;
    }
    
    /**
     * Gets the Tenant object by row
     *
     * @param row the row
     * @return the Tenant object
     */
    public Tenant getObj(int row) {
    	return tenants.get(row);
    }
    
  
    /**
     * Adds a tenant to the table
     *
     * @param tenant the tenant
     */
    public void add(Tenant tenant) {
        this.tenants.add(tenant);
        fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }
    
    /**
     * Removes the tenant from the table by row
     *
     * @param row the row
     */
    public void remove(int row) {
    	this.tenants.remove(row);
    	this.fireTableRowsDeleted(row, row);
    }
}
