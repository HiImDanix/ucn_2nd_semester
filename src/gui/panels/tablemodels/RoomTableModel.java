package gui.panels.tablemodels;



import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Room;

/**
 * @author Daniels Kanepe
 *
 */
public class RoomTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2367962812947993282L;

	protected static final String[] COLUMN_NAMES = {
        "ID", "category", "is occupied", "out of service"
    };

    private List<Room> rooms;


    public RoomTableModel(List<Room> rooms) {
        // Prevent possible mutation
        this.rooms = new ArrayList<>(rooms);
    }
    

    @Override
    public int getRowCount() {
        return rooms.size();
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
    	Room room = rooms.get(rowIndex);
        switch (columnIndex) {
            case 0: return "#" + room.getId();
            case 1: return room.getRoomCategory().getName();
            case 2: return "NOT IMPLEMENTED";
            case 3: return room.isOutOfService();
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
    public Room getObj(int row) {
    	return rooms.get(row);
    }
    
  
    /**
     * Adds a room to the table
     *
     * @param room the room
     */
    public void add(Room room) {
        this.rooms.add(room);
        fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }
    
    /**
     * Removes the room from the table by row
     *
     * @param row the row
     */
    public void remove(int row) {
    	this.rooms.remove(row);
    	this.fireTableRowsDeleted(row, row);
    }

}