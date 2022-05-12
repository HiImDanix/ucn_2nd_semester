package gui.panels;


import controller.RoomCategoryController;
import db.DataAccessException;
import gui.Messages;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.RoomCategoryTableModel;
import gui.windows.WindowRoom;
import model.RoomCategory;


public class CRUDRoomCategory extends AbstractCRUDPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 878517353431220848L;

	public CRUDRoomCategory() throws DataAccessException {
		super("Categories");
	}

	@Override
	protected MyAbstractTableModel<RoomCategory> createTableModel() throws DataAccessException {
		return new RoomCategoryTableModel(new RoomCategoryController().getAllRoomCategories());
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
	protected void btnViewAction() {	}

	@Override
	protected void btnEditAction() {}

	@Override
	protected void btnDeleteAction() {}
}
