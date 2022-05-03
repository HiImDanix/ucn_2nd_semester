package gui.panels;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import controller.RoomController;
import db.DataAccessException;
import gui.Messages;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.RoomTableModel;
import gui.windows.WindowRoom;
import model.Room;


public class CRUDRooms extends AbstractCRUDPanel{

	final JCheckBox checkBox = new JCheckBox();
	private RoomController roomCtrl;
	
	
	public CRUDRooms() throws DataAccessException {
		super("Rooms");
		roomCtrl = new RoomController();
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setBackground(Color.WHITE);
		getTable().getColumn("out of service").setCellRenderer(new DefaultTableCellRenderer(){
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		      checkBox.setSelected(((Boolean)value).booleanValue()) ;
		      return checkBox;
		    }
		});
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
				Room room = (Room) getTableModel().getObj(row);
				try {
					roomCtrl.updateRoomIsOutOfService(room, checkBox.isSelected());
				} catch (DataAccessException ex) {
					Messages.error("Error updating room", "error");
				}
				getTableModel().fireTableRowsUpdated(row, row);
			}
		});
	}

	@Override
	protected MyAbstractTableModel<Room> createTableModel() throws DataAccessException {
		return new RoomTableModel(new RoomController().getAllRooms());
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
		Messages.error(this, "disabled");
		return;
//		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
//		Room room = (Room) getTableModel().getObj(row);
//		WindowRoom frame = new WindowRoom(room, WindowRoom.Mode.EDIT);
//		frame.setVisible(true);
//		getTableModel().fireTableRowsUpdated(row, row);
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
