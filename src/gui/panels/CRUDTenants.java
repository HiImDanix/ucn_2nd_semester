package gui.panels;


import controller.TenantController;
import db.DataAccessException;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.TenantTableModel;
import gui.windows.WindowTenant;
import model.Tenant;


public class CRUDTenants extends AbstractCRUDPanel {



	public CRUDTenants() throws DataAccessException {
		super("Tenants");
	}

	@Override
	protected MyAbstractTableModel<Tenant> createTableModel() throws DataAccessException {
		return new TenantTableModel(new TenantController().getAllTenants());
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
		WindowTenant frame = new WindowTenant(tenant, WindowTenant.Mode.VIEW);
		frame.setVisible(true);
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
