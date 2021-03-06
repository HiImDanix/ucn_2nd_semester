package gui.panels.tablemodels;


import controller.RoomController;
import db.DataAccessException;
import model.Room;
import model.RoomCategory;

import java.util.ArrayList;
import java.util.List;

public class RoomCategoryTableModel extends MyAbstractTableModel<RoomCategory> {

	private static final long serialVersionUID = -2367962812947993282L;

	protected static final String[] COLUMN_NAMES = {
        "ID", "Name", "Description", "Price / month", "Internet price / month",
            "Extra tenant price / month", "Max tenants", "Notice period (days)"
    };

    private List<RoomCategory> roomCategories;
    private DataSupplier<List<RoomCategory>> roomCategorySupplier;

    public RoomCategoryTableModel(DataSupplier<List<RoomCategory>> supplier) throws DataAccessException {
        this.roomCategorySupplier = supplier;
        this.roomCategories = supplier.get();
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
            case 6: return Integer.class;
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RoomCategory roomCategory = roomCategories.get(rowIndex);


        switch (columnIndex) {
            case 0: return "#" + roomCategory.getID();
            case 1: return roomCategory.getName();
            case 2: return roomCategory.getDescription();
            case 3: return roomCategory.getPricePerMonth() + " DKK";
            case 4: return roomCategory.getPricePerMonthForInternet() + " DKK";
            case 5: return roomCategory.getPricePerMonthForExtraTenant() + " DKK";
            case 6: return roomCategory.getMaxTenants();
            case 7: return roomCategory.getLeaveNoticeDays() + " days";
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

    @Override
    public void refreshData() throws DataAccessException {
        this.roomCategories = roomCategorySupplier.get();
        fireTableDataChanged();
    }
}