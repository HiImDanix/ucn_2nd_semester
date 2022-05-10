package gui.panels.tablemodels;


import controller.RoomController;
import model.Room;
import model.RoomCategory;

import java.util.ArrayList;
import java.util.List;

public class RoomCategoryTableModel extends MyAbstractTableModel<RoomCategory> {

	private static final long serialVersionUID = -2367962812947993282L;

	protected static final String[] COLUMN_NAMES = {
        "ID", "Name", "Price / month", "Internet price / month",
            "Extra tenant price / month", "Max tenants", "Notice period (days)"
    };

    private List<RoomCategory> roomCategories;


    public RoomCategoryTableModel(List<RoomCategory> RoomCategory) {
        // Prevent possible mutation
        this.roomCategories = new ArrayList<>(roomCategories);
    }

    @Override
    public int getRowCount() {
        return roomCategories.size();
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
        RoomCategory roomCategory = roomCategories.get(rowIndex);


        switch (columnIndex) {
            case 0: return "#" + roomCategory.getID();
            case 1: return roomCategory.getName();
            case 2: return roomCategory.getPricePerMonth();
            case 3: return roomCategory.getPricePerMonthForInternet();
            case 4: return roomCategory.getPricePerMonthForExtraTenant();
            case 5: return roomCategory.getMaxTenants();
            case 6: return roomCategory.getLeaveNoticeDays();
            default: return "ERROR";
        }
    }
    
    // Make cells uneditable
    @Override
    public boolean isCellEditable(int row, int column) {       
        return false;
    }
    
    /**
     * Gets the RoomCategory object by row
     *
     * @param row the row
     * @return the RooMCategory object
     */
    public RoomCategory getObj(int row) {
    	return roomCategories.get(row);
    }
    
  
    /**
     * Adds a RoomCategory to the table
     *
     * @param roomCategory the room category
     */
    public void add(RoomCategory roomCategory) {
        this.roomCategories.add(roomCategory);
        fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }
    
    /**
     * Removes the RoomCategory from the table by row
     *
     * @param row the row
     */
    public void remove(int row) {
    	this.roomCategories.remove(row);
    	this.fireTableRowsDeleted(row, row);
    }
}