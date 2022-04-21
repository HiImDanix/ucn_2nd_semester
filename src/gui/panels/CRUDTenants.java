package gui.panels;


import controller.TenantController;
import db.DataAccessException;
import gui.Messages;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.TenantTableModel;
import gui.windows.WindowTenant;
import model.Room;
import model.Tenant;


public class CRUDTenants extends AbstractCRUDPanel {

	TenantController tenantCtrl = new TenantController();



	public CRUDTenants() throws DataAccessException {
		super("Tenants");
	}

	@Override
	protected MyAbstractTableModel<Tenant> createTableModel() throws DataAccessException {
		return new TenantTableModel(tenantCtrl.getAllTenants());
	}

	@Override
	protected void btnAddAction() {
		WindowTenant frame = new WindowTenant();
		frame.setVisible(true);
		if (frame.getTenant() != null) {
			getTableModel().add(frame.getTenant());
		}
	}

	@Override
	protected void btnViewAction() {
			int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
			Tenant tenant = (Tenant) getTableModel().getObj(row);
			WindowTenant frame = new WindowTenant(tenant, WindowTenant.Mode.VIEW);
			frame.setVisible(true);
	}

	@Override
	protected void btnEditAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Tenant tenant = (Tenant) getTableModel().getObj(row);
		WindowTenant frame = new WindowTenant(tenant, WindowTenant.Mode.EDIT);
		frame.setVisible(true);
		getTableModel().fireTableRowsUpdated(row, row);
	}

	@Override
	protected void btnDeleteAction() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Tenant tenant = (Tenant) getTableModel().getObj(row);
		Messages.info(this, "Not implemented yet");
//		if (Messages.confirm(this,
//				String.format("Are you sure you wish to delete the tenant '%s %s'?",
//						tenant.getFirstName(), tenant.getLastName()))) {
//			try {
////				tenantCtrl.d(room);
////				getTableModel().remove(row);
////				setTableModel(tableModel);
//			} catch (DataAccessException ex) {
//				Messages.error(this, "There was an error deleting the room.", "error");
//			}
//		}
//	});
	}


}

	/*
	 * *******************************************************
	 * *******************  Methods *******************
	 * *******************************************************
	 */
////
////	/**
////	 * Select a customer in the CRUD table.
////	 *
////	 * @param customer the customer
////	 * @return true, if successful
////	 */
////	public boolean selectCustomer(Customer customer) {
////		int rows = tableModel.getRowCount();
////		for (int i = 0; i < rows; i++) {
////			Customer foundCustomer = tableModel.getObj(i);
////			if (foundCustomer == customer) {
////				tableMain.getSelectionModel().setSelectionInterval(0, i);
////				return true;
////			}
////		}
////		return false;
////	}
//
//
