package gui.panels.tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Room;
import model.Tenant;

public class TenantTableModel extends AbstractTableModel {

	private static final String[] columnNames = {"ID", "fname", "lname", "email", "phone", "living_in"};
	private List<Tenant> tenants;
	
	public TenantTableModel(List<Tenant> tenants) {
		this.tenants = tenants;
	}
	
	@Override
	public int getRowCount() {
		return tenants.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Tenant tenant = tenants.get(rowIndex);
        switch (columnIndex) {
            case 0: return tenant.getId();
            case 1: return tenant.getFirstName();
            case 2: return tenant.getLastName();
            case 3: return tenant.getEmail();
            case 4: return tenant.getPhone();
            case 5: return tenant.getRoom().getId();
            default: return "ERROR";
        }
	}

	// Make cells uneditable
    @Override
    public boolean isCellEditable(int row, int column) {       
        return false;
    }
    
    /**
     * Gets the Room object by row
     *
     * @param row the row
     * @return the Room object
     */
    public Tenant getObj(int row) {
    	return tenants.get(row);
    }
    
  
    /**
     * Adds a room to the table
     *
     * @param room the room
     */
    public void add(Tenant tenant) {
        this.tenants.add(tenant);
        fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }
    
    /**
     * Removes the room from the table by row
     *
     * @param row the row
     */
    public void remove(int row) {
    	this.tenants.remove(row);
    	this.fireTableRowsDeleted(row, row);
    }
	
	//fire cells edited/deleted?
}
