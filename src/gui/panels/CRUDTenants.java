package gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import controller.TenantController;
import db.DataAccessException;
import gui.panels.tablemodels.RoomTableModel;
import gui.panels.tablemodels.TenantTableModel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import gui.JLink;
import java.awt.Color;
import javax.swing.JTable;

public class CRUDTenants extends JPanel {

	private TenantController tenantCtrl;
	private TenantTableModel tableModel;
	private JTextField tfSearch;
	private TableRowSorter<TableModel> rowSorter;
	private JTable tableTenants;
	
	/**
	 * Create the panel.
	 */
	public CRUDTenants() throws DataAccessException {
		tenantCtrl = new TenantController();
		setLayout(new BorderLayout(0,0));
		tableModel = new TenantTableModel(tenantCtrl.getAllTenants());
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_topPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_topPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_topPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);
		
		JLabel lblTenants = new JLabel("Tenants");
		GridBagConstraints gbc_lblTenants = new GridBagConstraints();
		gbc_lblTenants.gridwidth = 3;
		gbc_lblTenants.insets = new Insets(0, 0, 5, 0);
		gbc_lblTenants.gridx = 0;
		gbc_lblTenants.gridy = 0;
		topPanel.add(lblTenants, gbc_lblTenants);
		
		tfSearch = new JTextField();
		tfSearch.setColumns(10);
		GridBagConstraints gbc_tfSearch = new GridBagConstraints();
		gbc_tfSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSearch.insets = new Insets(0, 0, 5, 5);
		gbc_tfSearch.gridx = 0;
		gbc_tfSearch.gridy = 1;
		topPanel.add(tfSearch, gbc_tfSearch);
		
		JButton btnAddCustomer = new JButton("Add tenant");
		GridBagConstraints gbc_btnAddCustomer = new GridBagConstraints();
		gbc_btnAddCustomer.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddCustomer.gridx = 2;
		gbc_btnAddCustomer.gridy = 1;
		topPanel.add(btnAddCustomer, gbc_btnAddCustomer);
		
		JScrollPane scrollPanel = new JScrollPane();
		add(scrollPanel, BorderLayout.CENTER);
		
		tableTenants = new JTable();
		add(tableTenants, BorderLayout.WEST);
		
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[]{271, 0, 0, 0, 0};
		gbl_bottomPanel.rowHeights = new int[]{21, 0};
		gbl_bottomPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		bottomPanel.setLayout(gbl_bottomPanel);
		
		JLink btnView = new JLink("View", new Color(32, 120, 104));
		btnView.setEnabled(false);
		GridBagConstraints gbc_btnView = new GridBagConstraints();
		gbc_btnView.insets = new Insets(0, 0, 0, 5);
		gbc_btnView.gridx = 1;
		gbc_btnView.gridy = 0;
		bottomPanel.add(btnView, gbc_btnView);
		
		JLink btnEdit = new JLink("Edit", new Color(101, 88, 245));
		btnEdit.setEnabled(false);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
		gbc_btnEdit.gridx = 2;
		gbc_btnEdit.gridy = 0;
		bottomPanel.add(btnEdit, gbc_btnEdit);
		
		JLink btnDelete = new JLink("Delete", new Color(211, 69, 91));
		btnDelete.setEnabled(false);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 0;
		bottomPanel.add(btnDelete, gbc_btnDelete);
		
		// Add filtering
				rowSorter = new TableRowSorter<>(tableModel);
				tableTenants.setRowSorter(rowSorter);
				
		// set table model
				setTableModel(tableModel);
	}
	
	public void setTableModel(TenantTableModel tableModel) {
		this.tableTenants.setModel(tableModel);
		this.tableModel = tableModel;
		// Update table row sorter
		rowSorter = new TableRowSorter<>(tableTenants.getModel());
		tableTenants.setRowSorter(rowSorter);
	}
	
}
