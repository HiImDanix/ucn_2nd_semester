package gui.panels;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import controller.TenantController;
import db.DataAccessException;
import gui.Messages;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.TenantTableModel;
import gui.windows.WindowContract;
import gui.windows.WindowTenant;
import model.Room;
import model.Tenant;


public class CRUDTenants extends AbstractCRUDPanel {
	
	private JButton btnGotoContract;

	public CRUDTenants() throws DataAccessException {
		super("Tenants");
		
		btnGotoContract = new JButton("Genarate contract");
        btnGotoContract.setEnabled(false);
        btnGotoContract.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        GridBagConstraints gbc_btnGotoContract = new GridBagConstraints();
        gbc_btnGotoContract.insets = new Insets(0, 0, 0, 5);
        gbc_btnGotoContract.gridx = 0;
        gbc_btnGotoContract.gridy = 0;
        getBottomPanel().add(btnGotoContract, gbc_btnGotoContract);
        
        getTable().getSelectionModel().addListSelectionListener(e -> {
			if (getTable().getSelectionModel().isSelectionEmpty()) {
				// Not selected
				btnGotoContract.setEnabled(false);
			} else {
				// Selected
				btnGotoContract.setEnabled(true);
			}
		});
        
        btnGotoContract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGotoContract();
            }
        });
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
		Messages.error(this, "disabled");
		return;
//		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
//		Tenant tenant = (Tenant) getTableModel().getObj(row);
//		WindowTenant frame = new WindowTenant(tenant, WindowTenant.Mode.EDIT);
//		frame.setVisible(true);
//		getTableModel().fireTableRowsUpdated(row, row);
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

	private void btnGotoContract() {
		int row = getTable().convertRowIndexToModel(getTable().getSelectedRow());
		Tenant tenant = (Tenant) getTableModel().getObj(row);
		List<Tenant> tenants = new ArrayList<>();
		tenants.add(tenant);
		WindowContract frame = new WindowContract(tenants);
		frame.setVisible(true);
	}
}
