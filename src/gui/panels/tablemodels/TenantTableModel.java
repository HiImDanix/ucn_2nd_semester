package gui.panels.tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.Common;
import model.Room;
import model.Tenant;

public class TenantTableModel extends AbstractTableModel {

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
        switch (columnIndex) {
            case 0: return "#" + tenant.getId();
            case 1: return tenant.getFirstName();
            case 2: return tenant.getLastName();
            case 3: return tenant.getEmail();
            case 4: return tenant.getPhone();
            case 5: return tenant.getStudyProof() != null
                    ? "Until: " + Common.datetimeToString(tenant.getStudyProof().getStudentUntilDate())
                    : "Not provided";
            // TODO: Stubbed out for now
            case 6: return "(2) Room A, Room B";
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
