package gui.panels.tablemodels;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Contract;
import model.Room;

public class RoomTableModel extends MyAbstractTableModel<Room> {

	private static final long serialVersionUID = -2367962812947993282L;

	protected static final String[] COLUMN_NAMES = {
        "ID", "category", "out of service", "is occupied?"
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
        	case 2: return Boolean.class;
            case 3: return Boolean.class;
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Room room = rooms.get(rowIndex);

        boolean roomHasValidContract = false;
        for (Contract contract: room.getContracts()) {
            if (contract.getLeaveNotice() != null && contract.getLeaveNotice().getNoticeGivenDate().isAfter(
                    LocalDate.now().plusDays(room.getRoomCategory().getLeaveNoticeDays()))) {
                roomHasValidContract = true;
            }
        }
        boolean roomIsAvailable = !room.isOutOfService() && !roomHasValidContract;

        switch (columnIndex) {
            case 0: return "#" + room.getID();
            case 1: return room.getRoomCategory().getName();
            case 2: return room.isOutOfService();
            case 3: return roomIsAvailable;
            default: return "ERROR";
        }
    }
    
    // Make cells uneditable except checkbox (outOfService)
    @Override
    public boolean isCellEditable(int row, int column) {
         switch (column) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                return false;
            default:
                return false;
            }
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