package gui.panels;


import controller.ContractController;
import controller.RoomController;
import db.DataAccessException;
import gui.Messages;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.ContractTableModel;
import gui.windows.WindowContract;
import model.Contract;


public class CRUDContracts extends AbstractCRUDPanel {

	public CRUDContracts() throws DataAccessException {
		super("Tenants");
	}

	@Override
	protected MyAbstractTableModel<Contract> createTableModel() throws DataAccessException {
		return new ContractTableModel(new ContractController().getAllContracts());
	}

	@Override
	protected void btnAddAction() {
		WindowContract frame = new WindowContract();
		frame.setVisible(true);
		if (frame.getSelectedObject() != null) {
			getTableModel().add(frame.getSelectedObject());
		}
	}

	@Override
	protected void btnViewAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Contract contract = (Contract) getTableModel().getObj(row);
		WindowContract frame = new WindowContract(contract, WindowContract.Mode.VIEW);
		frame.setVisible(true);
	}

	@Override
	protected void btnEditAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Contract contract = (Contract) getTableModel().getObj(row);
		WindowContract frame = new WindowContract(contract, WindowContract.Mode.EDIT);
		frame.setVisible(true);
		getTableModel().fireTableRowsUpdated(row, row);
	}

	@Override
	protected void btnDeleteAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Contract contract = (Contract) getTableModel().getObj(row);
		Messages.error(this, "Not implemented yet");
//		if (Messages.confirm(this, String.format("Are you sure you wish to delete the contract with id %d'?", contract.getID()))) {
//			try {
//				new ContractController().deleteContract(contract);
//				getTableModel().remove(row);
//			} catch (DataAccessException ex) {
//				Messages.error(this, "There was an error deleting the contract.", "error");
//			}
//		}
	}
}
