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
	private JTextField txtDescription;
	private JLabel lblPrice;
	private JTextField txtPrice;
	private JLabel lblPriceInternet;
	private JTextField txtPriceInternet;
	private JLabel lblNumberOfTenants;
	private JLabel lblPriceExtraTenant;
	private JTextField txtNumberOfTenants;
	private JTextField txtPriceExtraTenant;
	private JTextField txtLeaveNoticeDays;
	private JLabel lblLeaveNoticeDays;
	
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
	public WindowRoomCategory(RoomCategory roomCategory, Mode mode) throws DataAccessException {
		this.mode = mode;
		
		roomCategoryCtrl = new RoomCategoryController();
		//this.room = room;
		
		setModal(true);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
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
		gbc_lblID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.gridx = 0;
		gbc_lblID.gridy = 0;
		contentPane.add(lblID, gbc_lblID);
		
		JLabel lblName = new JLabel("Name*");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.SOUTH;
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
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
		gbc_lblNumberOfTenants.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblNumberOfTenants.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfTenants.gridx = 0;
		gbc_lblNumberOfTenants.gridy = 2;
		contentPane.add(lblNumberOfTenants, gbc_lblNumberOfTenants);
		
		lblPrice = new JLabel("Price / Month");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrice.gridx = 1;
		gbc_lblPrice.gridy = 2;
		contentPane.add(lblPrice, gbc_lblPrice);
		
		txtNumberOfTenants = new JTextField();
		txtNumberOfTenants.setEnabled(true);
		txtNumberOfTenants.setColumns(10);
		GridBagConstraints gbc_txtNumberOfTenants = new GridBagConstraints();
		gbc_txtNumberOfTenants.anchor = GridBagConstraints.NORTH;
		gbc_txtNumberOfTenants.insets = new Insets(0, 0, 5, 5);
		gbc_txtNumberOfTenants.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumberOfTenants.gridx = 0;
		gbc_txtNumberOfTenants.gridy = 3;
		contentPane.add(txtNumberOfTenants, gbc_txtNumberOfTenants);
		
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
		gbc_lblDescription.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 4;
		contentPane.add(lblDescription, gbc_lblDescription);
		
		lblPriceInternet = new JLabel("Price for internet / Month");
		GridBagConstraints gbc_lblPriceInternet = new GridBagConstraints();
		gbc_lblPriceInternet.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblPriceInternet.insets = new Insets(0, 0, 5, 0);
		gbc_lblPriceInternet.gridx = 1;
		gbc_lblPriceInternet.gridy = 4;
		contentPane.add(lblPriceInternet, gbc_lblPriceInternet);
		
		txtDescription = new JTextField();
		txtDescription.setEnabled(true);
		txtDescription.setColumns(10);
		GridBagConstraints gbc_txtDescription = new GridBagConstraints();
		gbc_txtDescription.gridheight = 3;
		gbc_txtDescription.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescription.fill = GridBagConstraints.BOTH;
		gbc_txtDescription.gridx = 0;
		gbc_txtDescription.gridy = 5;
		contentPane.add(txtDescription, gbc_txtDescription);
		
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
		gbc_lblPriceExtraTenant.anchor = GridBagConstraints.WEST;
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
				setTitle("View Room categories - " + roomCategory.getID());
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
		//txtCategory.setText(room.getRoomCategory().getName());
		//rdbtnOutOfServiceYes.setSelected(room.isOutOfService());
		//rdbtnOutOfServiceNo.setSelected(!room.isOutOfService());
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

				// Validate that out of service is either true or false
				

				if (mode == Mode.EDIT) {/*
					try {
						roomCtrl.updateRoomIsOutOfService(room, rdbtnOutOfServiceYes.isSelected());
						roomCtrl.updateRoomCategory(room, this.roomCategory);
					} catch (DataAccessException ex) {
						Messages.error("Error updating room", "error");
					}
				 */
				} else if (mode == Mode.CREATE) {
					// if mode == Create, create a new room
					try {
						this.roomCategory = roomCategoryCtrl.addRoomCategory(txtName.getText().trim(), txtDescription.getText(), 
								new BigDecimal(txtPrice.getText().trim()), new BigDecimal(txtPriceInternet.getText().trim()),
								new BigDecimal(txtPriceExtraTenant.getText().trim()), Integer.parseInt(txtNumberOfTenants.getText().trim()), Integer.parseInt(txtLeaveNoticeDays.getText().trim()));
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