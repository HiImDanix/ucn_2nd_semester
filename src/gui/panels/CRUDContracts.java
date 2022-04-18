package gui.panels;

import controller.ContractController;
import controller.TenantController;
import db.DataAccessException;
import gui.Images;
import gui.JLink;
import gui.Messages;
import gui.Palette;
import gui.panels.tablemodels.ContractTableModel;
import gui.panels.tablemodels.TenantTableModel;
import model.Tenant;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;


public class CRUDContracts extends JPanel {

	private ContractController contractCtrl;

	private JButton btnAdd;
	private static final long serialVersionUID = -8329527605114016878L;
	private JTable tableMain;
	private ContractTableModel tableModel;
	private JLink btnView;
	private JLink btnEdit;
	private JLink btnDelete;
	private JTextField txtSearch;
	private TableRowSorter<TableModel> rowSorter;

	public CRUDContracts() throws DataAccessException {
		contractCtrl = new ContractController();
		setLayout(new BorderLayout(0, 0));
		tableModel = new ContractTableModel(contractCtrl.getAllContracts());

		// ***** TOP PANEL *****
		JPanel topPanel = new JPanel();
		this.add(topPanel, BorderLayout.NORTH);
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_topPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_topPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_topPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);

		// ***** Title *****
		JLabel lblTitle = new JLabel(
				"Contracts"
		);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 3;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		topPanel.add(lblTitle, gbc_lblTitle);

		// ***** Search bar *****
		txtSearch = new JTextField();
		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSearch.gridx = 0;
		gbc_txtSearch.gridy = 1;
		topPanel.add(txtSearch, gbc_txtSearch);
		txtSearch.setColumns(10);

		// ***** button: Add tenant  *****
		btnAdd = new JButton("Add contract");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 1;
		topPanel.add(btnAdd, gbc_btnAdd);
		btnAdd.setIcon(Images.ADD_ITEM.getImageIcon(btnAdd));

		// ***** Middle panel: Scroll panel *****
		JScrollPane scrollPanel = new JScrollPane();
		add(scrollPanel, BorderLayout.CENTER);

		// ***** Table *****
		tableMain = new JTable();
		tableMain.setModel(tableModel);
		tableMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanel.setViewportView(tableMain);

		// ***** Bottom panel *****
		JPanel bottomPanel = new JPanel();
		this.add(bottomPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[]{271, 0, 0, 0, 0};
		gbl_bottomPanel.rowHeights = new int[]{21, 0};
		gbl_bottomPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		bottomPanel.setLayout(gbl_bottomPanel);

		// ***** View button *****
		btnView = new JLink("View", Palette.SUCCESS.getColor());
		GridBagConstraints gbc_btnView = new GridBagConstraints();
		gbc_btnView.insets = new Insets(0, 0, 0, 5);
		gbc_btnView.gridx = 1;
		gbc_btnView.gridy = 0;
		bottomPanel.add(btnView, gbc_btnView);

		// ***** Edit button *****
		btnEdit = new JLink("Edit", Palette.PRIMARY.getColor());
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
		gbc_btnEdit.gridx = 2;
		gbc_btnEdit.gridy = 0;
		bottomPanel.add(btnEdit, gbc_btnEdit);

		// ***** Disable button *****
		btnDelete = new JLink("Delete", Palette.DANGER.getColor());
		GridBagConstraints gbc_btnDisable = new GridBagConstraints();
		gbc_btnDisable.gridx = 3;
		gbc_btnDisable.gridy = 0;
		bottomPanel.add(btnDelete, gbc_btnDisable);

		// By default: all selection buttons disabled
		btnView.setEnabled(false);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);

		// Add filtering
		rowSorter = new TableRowSorter<>(tableModel);
		tableMain.setRowSorter(rowSorter);

		// Attach event handler
		this.addEventHandlers();
	}

	/*
	 * *******************************************************
	 * *******************  Methods *******************
	 * *******************************************************
	 */

	//	/**
//	 * @return JTable tableMain
//	 */
//	public JTable getTable() {
//		return tableMain;
//	}
//
//	/**
//	 * @return CustomerTableModel tableModel
//	 */
//	public CustomerTableModel getTableModel() {
//		return tableModel;
//	}
//
	public void setTableModel(ContractTableModel tableModel) {
		this.tableMain.setModel(tableModel);
		this.tableModel = tableModel;
		// Update table row sorter
		rowSorter = new TableRowSorter<>(tableMain.getModel());
		tableMain.setRowSorter(rowSorter);
	}
//
//	/**
//	 * Select a customer in the CRUD table.
//	 *
//	 * @param customer the customer
//	 * @return true, if successful
//	 */
//	public boolean selectCustomer(Customer customer) {
//		int rows = tableModel.getRowCount();
//		for (int i = 0; i < rows; i++) {
//			Customer foundCustomer = tableModel.getObj(i);
//			if (foundCustomer == customer) {
//				tableMain.getSelectionModel().setSelectionInterval(0, i);
//				return true;
//			}
//		}
//		return false;
//	}


	/*
	 * *******************************************************
	 * *******************  EVENT HANDLERS *******************
	 * *******************************************************
	 */
	private void addEventHandlers() {
		// Table row selection
		tableMain.getSelectionModel().addListSelectionListener(e -> {
			if (tableMain.getSelectionModel().isSelectionEmpty()) {
				// Not selected
				btnView.setEnabled(false);
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(false);
			} else {
				// Selected
				btnView.setEnabled(true);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		});

		/*
		 * 'Delete' button
		 */
//		btnDelete.addActionListener(e -> {
//			int row = tableMain.convertRowIndexToModel(tableMain.getSelectedRow());
//			Tenant tenant = tableModel.getObj(row);
//			if (Messages.confirm(this, String.format("Are you sure you wish to delete the tenant with id %d'?'", tenant.getId()))) {
//				// TODO: STUB - delete tenant]
//				Messages.info(this, "Not implemented yet");
////				try {
//////					tenantCtrl.deleteTenant(tenant);
////					tableModel.remove(row);
////					setTableModel(tableModel);
////				} catch (DataAccessException ex) {
////					Messages.error(this, "There was an error deleting the room.", "error");
////				}
//			}
//		});

		/*
		 * 'View' button
		 */
//		btnView.addActionListener(e -> {
//			int row = tableMain.convertRowIndexToModel(tableMain.getSelectedRow());
//			Room room = tableModel.getObj(row);
//			WindowRoom frame = new WindowRoom(room, WindowRoom.Mode.VIEW);
//			frame.setVisible(true);
//		});

		/*
		 * 'Edit' button
		 */
//		btnEdit.addActionListener(e -> {
//			int row = tableMain.convertRowIndexToModel(tableMain.getSelectedRow());
//			Room room = tableModel.getObj(row);
//			WindowRoom frame = new WindowRoom(room, WindowRoom.Mode.EDIT);
//			frame.setVisible(true);
//			tableModel.fireTableRowsUpdated(row, row);
//			setTableModel(tableModel);
//		});

		/*
		 * 'Add new' button
		 */
//		btnAddCustomer.addActionListener(e -> {
//			WindowRoom frame = new WindowRoom();
//			frame.setVisible(true);
//			if (frame.getRoom() != null) {
//				tableModel.add(frame.getRoom());
//				setTableModel(tableModel);
//			}
//		});

		/*
		 * Search
		 */
		txtSearch.getDocument().addDocumentListener(new DocumentListener(){

			private void search() {
				String text = txtSearch.getText();
				if(text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					// (?i) matches case-insensitive & Pattern.quote() escapes special characters
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text)));
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void  removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) { /* Empty due to interface */ }
		});
	}
}
