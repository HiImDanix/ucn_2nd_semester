package gui.panels;

import controller.DBController;
import db.DataAccessException;
import gui.*;
import gui.panels.tablemodels.MyAbstractTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public abstract class AbstractCRUDPanel extends JPanel {

    abstract MyAbstractTableModel createTableModel() throws DataAccessException;

    private String name;

    private JButton btnAdd;
    private static final long serialVersionUID = -8329527605114016878L;
    private JTable tableMain;
    private MyAbstractTableModel tableModel;
    private JLink btnView;
    private JLink btnEdit;
    private JLink btnDelete;
    private JTextField txtSearch;
    private TableRowSorter<TableModel> rowSorter;
    private JButton btnSync;

    /*
     * name: Name of the domain object (singular)
     */
    protected AbstractCRUDPanel(String name) throws DataAccessException {
        setLayout(new BorderLayout(0, 0));
        tableModel = createTableModel();

        // ***** TOP PANEL *****
        JPanel topPanel = new JPanel();
        this.add(topPanel, BorderLayout.NORTH);
        GridBagLayout gbl_topPanel = new GridBagLayout();
        gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_topPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_topPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_topPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        topPanel.setLayout(gbl_topPanel);

        // ***** Title *****
        JLabel lblTitle = new JLabel(name);
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.gridwidth = 4;
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

        btnSync = new JButton( Images.RELOAD.getImageIcon(16, 16));
        btnSync.setToolTipText("Sync");
        btnSync.setFocusPainted(false);
        btnSync.setContentAreaFilled(false);
        btnSync.setBorderPainted(false);
        btnSync.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        GridBagConstraints gbc_btnSync = new GridBagConstraints();
        gbc_btnSync.insets = new Insets(0, 0, 5, 5);
        gbc_btnSync.gridx = 2;
        gbc_btnSync.gridy = 1;
        topPanel.add(btnSync, gbc_btnSync);

        // ***** button: Add  *****
        btnAdd = new JButton("Add " + name);
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
        gbc_btnAdd.gridx = 3;
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


        addSearchFunctionality();

        btnAdd.addActionListener(e -> btnAddAction());
        btnView.addActionListener(e -> btnViewAction());
        btnEdit.addActionListener(e -> btnEditAction());
        btnDelete.addActionListener(e -> btnDeleteAction());

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

        // On refresh button click, reload the table's data
        btnSync.addActionListener(e -> {
            // Disable refresh button
            btnSync.setEnabled(false);

            // Execute after refresh button is disabled
            SwingUtilities.invokeLater(() -> {
                try {
                    new DBController().clearLocalContainer();
                    getTableModel().refreshData();
                } catch (DataAccessException ex) {
                    Messages.error(this, "Could not load table data...", "Error");
                }
                // Enable refresh button
                btnSync.setEnabled(true);
            });
        });
    }


    private void addSearchFunctionality() {
        JTextField searchField = getSearchField();
        JTable tableToSort = getTable();

        // Create a sorter from tableModel & add to table
        rowSorter = new TableRowSorter<>(tableToSort.getModel());
        tableToSort.setRowSorter(rowSorter);

        // Sort when typing in search field
        searchField.getDocument().addDocumentListener(new DocumentListener(){

            private void search() {
                String text = searchField.getText();
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

    // ***** EVENTS *****



    // ***** METHODS *****

    abstract protected void btnAddAction();
    abstract protected void btnViewAction();
    abstract protected void btnEditAction();
    abstract protected void btnDeleteAction();

    protected String getTableName() {
        return name;
    }

    protected JTextField getSearchField() {
        return txtSearch;
    }

    public JTable getTable() {
        return tableMain;
    }

    public MyAbstractTableModel getTableModel() {
        return tableModel;
    }


}
