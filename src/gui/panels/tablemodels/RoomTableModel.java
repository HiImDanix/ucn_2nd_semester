package gui.panels.tablemodels;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import controller.RoomController;
import db.DataAccessException;
import model.Contract;
import model.Room;

public class RoomTableModel extends MyAbstractTableModel<Room> {

    RoomController roomCtrl = new RoomController();

	private static final long serialVersionUID = -2367962812947993282L;

	protected static final String[] COLUMN_NAMES = {
        "ID", "category", "Is available"
    };

    private List<Room> rooms;
    private DataSupplier<List<Room>> roomsSupplier;


    public RoomTableModel(DataSupplier<List<Room>> supplier) throws DataAccessException {
        this.roomsSupplier = supplier;
        this.rooms = supplier.get();
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

        // is room available?
        String roomIsAvailable = room.isOutOfService() ? "Out of service"
                : roomCtrl.isRoomOccupied(room) ? "Occupied" : "Yes";

        switch (columnIndex) {
            case 0: return "#" + room.getID();
            case 1: return room.getRoomCategory().getName();
            case 2: return roomIsAvailable;
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

    @Override
    public void refreshData() throws DataAccessException {
        this.rooms = roomsSupplier.get();
        fireTableDataChanged();
    }

}