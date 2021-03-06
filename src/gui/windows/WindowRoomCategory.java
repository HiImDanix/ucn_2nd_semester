package gui.windows;

import java.awt.*;
import java.math.BigDecimal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.RoomCategoryController;
import controller.RoomController;
import db.DataAccessException;
import gui.Common;
import gui.JButtonPrimary;
import gui.Messages;
import model.Room;
import model.RoomCategory;

public class WindowRoomCategory extends JDialog {
	
	public enum Mode {
		VIEW,
		EDIT,
		CREATE
	}

	private JPanel contentPane;
	private JTextField txtID;
	private JButton btnSubmit;
	
	RoomCategory roomCategory;
	RoomCategoryController roomCategoryCtrl;
	Mode mode;
	//private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtName;
	private JLabel lblDescription;
	private JLabel lblPrice;
	private JTextField txtPrice;
	private JLabel lblPriceInternet;
	private JTextField txtPriceInternet;
	private JLabel lblNumberOfTenants;
	private JLabel lblPriceExtraTenant;
	private JTextField txtPriceExtraTenant;
	private JTextField txtLeaveNoticeDays;
	private JLabel lblLeaveNoticeDays;
	private JSpinner spinnerMaxNumberOfTenants;
	private BigDecimal priceExtraTenant;
	private BigDecimal priceForInternet;
	private BigDecimal pricePerMonth;
	private String description;
	private String name;
	private int leaveNoticeDays;
	private int maxTenants;
	private JScrollPane scrollPane;
	private JTextArea txtDescription;
	
	/**
	 * Constructor for create mode
	 * @throws DataAccessException 
	 */
	public WindowRoomCategory() throws DataAccessException {
		this(null, Mode.CREATE);
		this.roomCategory = null;
	}

	/**
	 * Constructor for view/edit
	 *
	 * @param room the room
	 * @param mode the mode
	 * @throws DataAccessException 
	 */
	public WindowRoomCategory(RoomCategory roomCategory, Mode mode) {
		this.mode = mode;
		
		roomCategoryCtrl = new RoomCategoryController();
		this.roomCategory = roomCategory;
		
		setModal(true);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setVerifyInputWhenFocusTarget(false);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPanel);
		
		JLabel lblID = new JLabel("ID");
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.anchor = GridBagConstraints.SOUTH;
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.gridx = 0;
		gbc_lblID.gridy = 0;
		contentPane.add(lblID, gbc_lblID);
		
		JLabel lblName = new JLabel("Name*");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.SOUTH;
		gbc_lblName.insets = new Insets(0, 0, 5, 0);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		contentPane.add(lblName, gbc_lblName);
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		GridBagConstraints gbc_txtID = new GridBagConstraints();
		gbc_txtID.anchor = GridBagConstraints.NORTH;
		gbc_txtID.insets = new Insets(0, 0, 5, 5);
		gbc_txtID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtID.gridx = 0;
		gbc_txtID.gridy = 1;
		contentPane.add(txtID, gbc_txtID);
		txtID.setColumns(10);
		
		txtName = new JTextField();
		txtName.setEnabled(true);
		txtName.setColumns(10);
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.anchor = GridBagConstraints.NORTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 1;
		contentPane.add(txtName, gbc_txtName);
		
		lblNumberOfTenants = new JLabel("Maximum number of tenants");
		GridBagConstraints gbc_lblNumberOfTenants = new GridBagConstraints();
		gbc_lblNumberOfTenants.anchor = GridBagConstraints.SOUTH;
		gbc_lblNumberOfTenants.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfTenants.gridx = 0;
		gbc_lblNumberOfTenants.gridy = 2;
		contentPane.add(lblNumberOfTenants, gbc_lblNumberOfTenants);
		
		lblPrice = new JLabel("Price / Month");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.SOUTH;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrice.gridx = 1;
		gbc_lblPrice.gridy = 2;
		contentPane.add(lblPrice, gbc_lblPrice);
		
		spinnerMaxNumberOfTenants = new JSpinner();
		spinnerMaxNumberOfTenants.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		GridBagConstraints gbc_spinnerMaxNumberOfTenants = new GridBagConstraints();
		gbc_spinnerMaxNumberOfTenants.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxNumberOfTenants.gridx = 0;
		gbc_spinnerMaxNumberOfTenants.gridy = 3;
		contentPane.add(spinnerMaxNumberOfTenants, gbc_spinnerMaxNumberOfTenants);
		
		txtPrice = new JTextField();
		txtPrice.setEnabled(true);
		txtPrice.setColumns(10);
		GridBagConstraints gbc_txtPrice = new GridBagConstraints();
		gbc_txtPrice.anchor = GridBagConstraints.NORTH;
		gbc_txtPrice.insets = new Insets(0, 0, 5, 0);
		gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrice.gridx = 1;
		gbc_txtPrice.gridy = 3;
		contentPane.add(txtPrice, gbc_txtPrice);
		
		lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.SOUTH;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 4;
		contentPane.add(lblDescription, gbc_lblDescription);
		
		lblPriceInternet = new JLabel("Price for internet / Month");
		GridBagConstraints gbc_lblPriceInternet = new GridBagConstraints();
		gbc_lblPriceInternet.anchor = GridBagConstraints.SOUTH;
		gbc_lblPriceInternet.insets = new Insets(0, 0, 5, 0);
		gbc_lblPriceInternet.gridx = 1;
		gbc_lblPriceInternet.gridy = 4;
		contentPane.add(lblPriceInternet, gbc_lblPriceInternet);
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(1, 2));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		txtDescription = new JTextArea();
		txtDescription.setTabSize(4);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
		scrollPane.setViewportView(txtDescription);
		
		txtPriceInternet = new JTextField();
		txtPriceInternet.setEnabled(true);
		txtPriceInternet.setColumns(10);
		GridBagConstraints gbc_txtPriceInternet = new GridBagConstraints();
		gbc_txtPriceInternet.anchor = GridBagConstraints.NORTH;
		gbc_txtPriceInternet.insets = new Insets(0, 0, 5, 0);
		gbc_txtPriceInternet.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPriceInternet.gridx = 1;
		gbc_txtPriceInternet.gridy = 5;
		contentPane.add(txtPriceInternet, gbc_txtPriceInternet);
		
		lblPriceExtraTenant = new JLabel("Price for extra tenant / Month");
		GridBagConstraints gbc_lblPriceExtraTenant = new GridBagConstraints();
		gbc_lblPriceExtraTenant.insets = new Insets(0, 0, 5, 0);
		gbc_lblPriceExtraTenant.gridx = 1;
		gbc_lblPriceExtraTenant.gridy = 6;
		contentPane.add(lblPriceExtraTenant, gbc_lblPriceExtraTenant);
		
		txtPriceExtraTenant = new JTextField();
		txtPriceExtraTenant.setEnabled(true);
		txtPriceExtraTenant.setColumns(10);
		GridBagConstraints gbc_txtPriceExtraTenant = new GridBagConstraints();
		gbc_txtPriceExtraTenant.insets = new Insets(0, 0, 5, 0);
		gbc_txtPriceExtraTenant.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPriceExtraTenant.gridx = 1;
		gbc_txtPriceExtraTenant.gridy = 7;
		contentPane.add(txtPriceExtraTenant, gbc_txtPriceExtraTenant);
		
		lblLeaveNoticeDays = new JLabel("Leave notice days");
		GridBagConstraints gbc_lblLeaveNoticeDays = new GridBagConstraints();
		gbc_lblLeaveNoticeDays.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeaveNoticeDays.gridx = 0;
		gbc_lblLeaveNoticeDays.gridy = 8;
		contentPane.add(lblLeaveNoticeDays, gbc_lblLeaveNoticeDays);
		
		txtLeaveNoticeDays = new JTextField();
		GridBagConstraints gbc_txtLeaveNoticeDays = new GridBagConstraints();
		gbc_txtLeaveNoticeDays.insets = new Insets(0, 0, 5, 5);
		gbc_txtLeaveNoticeDays.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLeaveNoticeDays.gridx = 0;
		gbc_txtLeaveNoticeDays.gridy = 9;
		contentPane.add(txtLeaveNoticeDays, gbc_txtLeaveNoticeDays);
		txtLeaveNoticeDays.setColumns(10);
		
		btnSubmit = new JButtonPrimary("Create");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 10;
		contentPane.add(btnSubmit, gbc_btnOk);
		
		switch (mode) {
			case VIEW:
				// Set title
				setTitle("View Room category - " + roomCategory.getID());
				// Hide 'Update' button if in view mode
				btnSubmit.setVisible(false);
				// Disable 'choose' button if in view mode.
				//btnChooseCategory.setEnabled(false);
				// Disable fields
				this.disableFields();
				// Fill fields with content
				this.fillFields(roomCategory);
				break;
			case EDIT: 
				// Set title
				setTitle("Edit Room");
				btnSubmit.setText("Update");
				// Enable fields for editing
				this.enableFields();
				// Fill fields with content
				this.fillFields(roomCategory);
				break;
			case CREATE:
				// Set title
				setTitle("Add New Room category");
				// Change submit button text to 'Create'
				btnSubmit.setText("Create");
				// Enable fields
				this.enableFields();
				
		}	

		addEventHandlers();
	}
	

	/*
	 * *******************************************************
	 * *******************  Methods *******************
	 * *******************************************************
	 */

	// Makes all user editable components disabled
	private void disableFields() {
		Common.toggleUserComponents(this, false);
	}
	
	private void enableFields() {
		Common.toggleUserComponents(this, true);

		// except ID & Category field
		txtID.setEnabled(false);
		//txtCategory.setEnabled(false);
	}

	// FIll in the fields
	private void fillFields(RoomCategory roomCategory) {
		txtID.setText(String.valueOf(roomCategory.getID()));
		txtName.setText(roomCategory.getName());
		txtDescription.setText(roomCategory.getDescription());
		txtPrice.setText(String.valueOf(roomCategory.getPricePerMonth()));
		txtPriceInternet.setText(String.valueOf(roomCategory.getPricePerMonthForInternet()));
		txtPriceExtraTenant.setText(String.valueOf(roomCategory.getPricePerMonthForExtraTenant()));
		spinnerMaxNumberOfTenants.setValue(roomCategory.getMaxTenants());
		txtLeaveNoticeDays.setText(String.valueOf(roomCategory.getLeaveNoticeDays()));
	} 



	/*
	 * Gets the room used in view/edit, or one created in 'create' mode. Can be null!
	 *
	 * @return the room
	 */
	public RoomCategory getRoomCategory() {
		return this.roomCategory;
	}
 	
		
	 // *******************************************************
	 //* *******************  EVENT HANDLERS *******************
	 //* *******************************************************
	 
	private void addEventHandlers() {
		
		// 'update' button: Update the room
		btnSubmit.addActionListener(e -> {
			String message = "";
			if (mode == Mode.EDIT) {
				message = "Are you sure you want to update the room's details?";
			} else if (mode == Mode.CREATE) {
				message = "Create room category?";
			}
			if (Messages.confirm(this, message)) {

				// Validate & parse: name
				String name = txtName.getText().strip();
				if (name.isEmpty()) {
					Messages.error(this, "Name cannot be empty!");
					return;
				}

				// Validate & parse: description
				String description = txtDescription.getText().strip();
				if (description.isEmpty()) {
					Messages.error(this, "Description cannot be empty!");
					return;
				}

				// Validate & parse: price
				BigDecimal pricePerMonth;
				try {
					pricePerMonth = new BigDecimal(txtPrice.getText().strip());
				} catch (NumberFormatException e1) {
					Messages.error(this, "Price must be a decimal number!");
					return;
				}
				if (pricePerMonth.compareTo(BigDecimal.ZERO) <= 0) {
					Messages.error(this, "Price must be greater than zero!");
					return;
				}

				// Validate & parse: price for internet
				BigDecimal priceForInternet;
				try {
					priceForInternet = new BigDecimal(txtPriceInternet.getText().strip());
				} catch (NumberFormatException e1) {
					Messages.error(this, "Price for internet must be a decimal number!");
					return;
				}
				if (priceForInternet.compareTo(BigDecimal.ZERO) < 0) {
					Messages.error(this, "Price for internet must be a positive number!");
					return;
				}

				// Validate & parse: price for extra tenant
				BigDecimal priceForExtraTenant;
				try {
					priceForExtraTenant = new BigDecimal(txtPriceExtraTenant.getText().strip());
				} catch (NumberFormatException e1) {
					Messages.error(this, "Price for extra tenant must be a decimal number!");
					return;
				}
				if (priceForExtraTenant.compareTo(BigDecimal.ZERO) < 0) {
					Messages.error(this, "Price for extra tenant must be a positive number!");
					return;
				}

				// Validate & parse: max number of tenants
				int maxTenants;
				try {
					maxTenants = Integer.parseInt(spinnerMaxNumberOfTenants.getValue().toString());
				} catch (NumberFormatException e1) {
					Messages.error(this, "Max number of tenants must be a number!");
					return;
				}
				if (maxTenants < 1) {
					Messages.error(this, "Max number of tenants must be at least 1!");
					return;
				}

				// Validate & parse: leave notice days
				int leaveNoticeDays;
				try {
					leaveNoticeDays = Integer.parseInt(txtLeaveNoticeDays.getText().strip());
				} catch (NumberFormatException e1) {
					Messages.error(this, "Leave notice days must be a number!");
					return;
				}
				if (leaveNoticeDays < 1) {
					Messages.error(this, "Leave notice days must be at least 1!");
					return;
				}

				if (mode == Mode.EDIT) {
					// if mode == Edit, edit a new room category
					try {
						roomCategoryCtrl.updateRoomCategory(roomCategory, name, description, pricePerMonth, pricePerMonth, 
								priceForExtraTenant, maxTenants, null, leaveNoticeDays);
					} catch (DataAccessException ex) {
						Messages.error("Error updating room category", "error");
					}
					
				} else if (mode == Mode.CREATE) {
					// if mode == Create, create a new room category
					try {
						this.roomCategory = roomCategoryCtrl.addRoomCategory(name, description, pricePerMonth, priceForInternet, 
								priceForExtraTenant, maxTenants, leaveNoticeDays);
					} catch (DataAccessException ex) {
						Messages.error("Error creating room category", "error");
					}
				}
				// Dispose of the window
				this.dispose();
			}
		});
	}
}