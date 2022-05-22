package gui.panels;


import controller.RoomController;
import db.DataAccessException;
import gui.Messages;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.RoomTableModel;
import gui.windows.WindowRoom;
import model.Room;

import java.util.List;


public class CRUDRooms extends AbstractCRUDPanel {

	public CRUDRooms() throws DataAccessException {
		super("Rooms");
	}

	@Override
	protected MyAbstractTableModel<Room> createTableModel() throws DataAccessException {
		return new RoomTableModel(new RoomController()::getAllRooms);
	}

	@Override
	protected void btnAddAction() {
		WindowRoom frame = new WindowRoom();
		frame.setVisible(true);
		if (frame.getRoom() != null) {
			getTableModel().add(frame.getRoom());
		}
	}

	@Override
	protected void btnViewAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Room room = (Room) getTableModel().getObj(row);
		WindowRoom frame = new WindowRoom(room, WindowRoom.Mode.VIEW);
		frame.setVisible(true);
	}

	@Override
	protected void btnEditAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Room room = (Room) getTableModel().getObj(row);
		WindowRoom frame = new WindowRoom(room, WindowRoom.Mode.EDIT);
		frame.setVisible(true);
		getTableModel().fireTableRowsUpdated(row, row);
	}

	@Override
	protected void btnDeleteAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Room room = (Room) getTableModel().getObj(row);
		if (Messages.confirm(this, String.format("Are you sure you wish to delete the room with id %d'?", room.getID()))) {
			try {
				new RoomController().deleteRoom(room);
				getTableModel().remove(row);
			} catch (DataAccessException ex) {
				Messages.error(this, "There was an error deleting the room.", "error");
			}
		}
	}
}
