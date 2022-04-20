package gui.windows;

import java.awt.*;

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

public class WindowRoom extends JDialog {
	
	public enum Mode {
		VIEW,
		EDIT,
		CREATE
	}

	private JPanel contentPane;
	private JTextField txtID;
	private JButton btnSubmit;
	private JPanel typePanel;
	private JTextField txtCategory;
	private JButton btnChooseCategory;
	
	Room room;
	RoomCategory roomCategory;
	RoomController roomCtrl;
	Mode mode;
	private JPanel panelOutOfService;
	private JRadioButton rdbtnOutOfServiceYes;
	private JRadioButton rdbtnOutOfServiceNo;
	private final ButtonGroup buttonGroup = new ButtonGroup();


	/**
	 * Constructor for create mode
	 */
	public WindowRoom() {
		this(null, Mode.CREATE);
		this.room = null;
		this.roomCategory = null;
	}

	/**
	 * Constructor for view/edit
	 *
	 * @param room the room
	 * @param mode the mode
	 */
	public WindowRoom(Room room, Mode mode) {
		this.mode = mode;
		
		roomCtrl = new RoomController();
		this.room = room;
		this.roomCategory = room != null ? room.getRoomCategory() : null;
		
		setModal(true);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPanel);
		
		JLabel lblID = new JLabel("ID");
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.anchor = GridBagConstraints.SOUTH;
		gbc_lblID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.gridx = 0;
		gbc_lblID.gridy = 0;
		contentPane.add(lblID, gbc_lblID);
		
		
		JLabel lblOutOfService = new JLabel("Out of service");
		GridBagConstraints gbc_lblOutOfService = new GridBagConstraints();
		gbc_lblOutOfService.anchor = GridBagConstraints.SOUTH;
		gbc_lblOutOfService.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOutOfService.insets = new Insets(0, 0, 5, 0);
		gbc_lblOutOfService.gridx = 1;
		gbc_lblOutOfService.gridy = 0;
		contentPane.add(lblOutOfService, gbc_lblOutOfService);
		
		
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
		
		panelOutOfService = new JPanel();
		GridBagConstraints gbc_panelOutOfService = new GridBagConstraints();
		gbc_panelOutOfService.insets = new Insets(0, 0, 5, 0);
		gbc_panelOutOfService.fill = GridBagConstraints.BOTH;
		gbc_panelOutOfService.gridx = 1;
		gbc_panelOutOfService.gridy = 1;
		contentPane.add(panelOutOfService, gbc_panelOutOfService);
		GridBagLayout gbl_panelOutOfService = new GridBagLayout();
		gbl_panelOutOfService.columnWidths = new int[]{0, 0, 0};
		gbl_panelOutOfService.rowHeights = new int[]{0, 0};
		gbl_panelOutOfService.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelOutOfService.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelOutOfService.setLayout(gbl_panelOutOfService);
		
		rdbtnOutOfServiceYes = new JRadioButton("Yes");
		buttonGroup.add(rdbtnOutOfServiceYes);
		GridBagConstraints gbc_rdbtnOutOfServiceYes = new GridBagConstraints();
		gbc_rdbtnOutOfServiceYes.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnOutOfServiceYes.gridx = 0;
		gbc_rdbtnOutOfServiceYes.gridy = 0;
		panelOutOfService.add(rdbtnOutOfServiceYes, gbc_rdbtnOutOfServiceYes);
		
		rdbtnOutOfServiceNo = new JRadioButton("No");
		buttonGroup.add(rdbtnOutOfServiceNo);
		GridBagConstraints gbc_rdbtnOutOfServiceNo = new GridBagConstraints();
		gbc_rdbtnOutOfServiceNo.gridx = 1;
		gbc_rdbtnOutOfServiceNo.gridy = 0;
		panelOutOfService.add(rdbtnOutOfServiceNo, gbc_rdbtnOutOfServiceNo);
		
		
		JLabel lblCategory = new JLabel("Category *");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.SOUTH;
		gbc_lblCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 2;
		contentPane.add(lblCategory, gbc_lblCategory);
		
		typePanel = new JPanel();
		GridBagConstraints gbc_typePanel = new GridBagConstraints();
		gbc_typePanel.gridwidth = 2;
		gbc_typePanel.insets = new Insets(0, 0, 5, 0);
		gbc_typePanel.fill = GridBagConstraints.BOTH;
		gbc_typePanel.gridx = 0;
		gbc_typePanel.gridy = 3;
		contentPane.add(typePanel, gbc_typePanel);
		GridBagLayout gbl_typePanel = new GridBagLayout();
		gbl_typePanel.columnWidths = new int[]{0, 0, 0};
		gbl_typePanel.rowHeights = new int[]{0, 0};
		gbl_typePanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_typePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		typePanel.setLayout(gbl_typePanel);
		
		txtCategory = new JTextField();
		txtCategory.setEnabled(false);
		GridBagConstraints gbc_txtCategory = new GridBagConstraints();
		gbc_txtCategory.insets = new Insets(0, 0, 0, 5);
		gbc_txtCategory.fill = GridBagConstraints.BOTH;
		gbc_txtCategory.gridx = 0;
		gbc_txtCategory.gridy = 0;
		typePanel.add(txtCategory, gbc_txtCategory);
		txtCategory.setColumns(10);
		
		btnChooseCategory = new JButton("Choose...");
		GridBagConstraints gbc_btnChooseCategory = new GridBagConstraints();
		gbc_btnChooseCategory.fill = GridBagConstraints.BOTH;
		gbc_btnChooseCategory.gridx = 1;
		gbc_btnChooseCategory.gridy = 0;
		typePanel.add(btnChooseCategory, gbc_btnChooseCategory);
		
		
		btnSubmit = new JButtonPrimary("Update");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 4;
		contentPane.add(btnSubmit, gbc_btnOk);
		
		switch (mode) {
			case VIEW:
				// Set title
				setTitle("View Room - " + room.getId());
				// Hide 'Update' button if in view mode
				btnSubmit.setVisible(false);
				// Disable 'choose' button if in view mode.
				btnChooseCategory.setEnabled(false);
				// Disable fields
				this.disableFields();
				// Fill fields with content
				this.fillFields(room);
				break;
			case EDIT: 
				// Set title
				setTitle("Edit Room");
				// Enable fields for editing
				this.enableFields();
				// Fill fields with content
				this.fillFields(room);
				break;
			case CREATE:
				// Set title
				setTitle("Add New Room");
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
		txtCategory.setEnabled(false);
	}

	// FIll in the fields
	private void fillFields(Room room) {
		txtID.setText(String.valueOf(room.getId()));
		txtCategory.setText(room.getRoomCategory().getName());
		rdbtnOutOfServiceYes.setSelected(room.isOutOfService());
		rdbtnOutOfServiceNo.setSelected(!room.isOutOfService());
	}
	
	/**
	 * Gets the room used in view/edit, or one created in 'create' mode. Can be null!
	 *
	 * @return the room
	 */
	public Room getRoom() {
		return this.room;
	}
 	
		/*
	 * *******************************************************
	 * *******************  EVENT HANDLERS *******************
	 * *******************************************************
	 */
	private void addEventHandlers() {
		
		// 'update' button: Update the room
		btnSubmit.addActionListener(e -> {
			String message = "";
			if (mode == Mode.EDIT) {
				message = "Are you sure you want to update the room's details?";
			} else if (mode == Mode.CREATE) {
				message = "Create room?";
			}
			if (Messages.confirm(this, message)) {
				
				// Validate Room Category
				if (roomCategory == null) {
					Messages.error(this, "You must choose a room category!");
					return;
				}

				// Validate that out of service is either true or false
				if (rdbtnOutOfServiceYes.isSelected() == false && rdbtnOutOfServiceNo.isSelected() == false) {
					Messages.error(this, "You must choose whether the room is out of service!");
					return;
				}

				if (mode == Mode.EDIT) {
					try {
						roomCtrl.updateRoomIsOutOfService(room, rdbtnOutOfServiceYes.isSelected());
						roomCtrl.updateRoomCategory(room, this.roomCategory);
					} catch (DataAccessException ex) {
						Messages.error("Error updating room", "error");
					}

				} else if (mode == Mode.CREATE) {
					// if mode == Create, create a new room
					try {
						this.room = roomCtrl.addRoom(roomCategory, rdbtnOutOfServiceYes.isSelected());
					} catch (DataAccessException ex) {
						Messages.error("Error creating room", "error");
					}
				}
				// Dispose of the window
				this.dispose();
			}
		});

		btnChooseCategory.addActionListener(e -> {
//			ChooseCustomerType frame = new ChooseCustomerType(auth);
//			frame.setVisible(true);
//			if (frame.getSelectedCustomerType() != null) {
//				this.customerType = frame.getSelectedCustomerType();
//				txtType.setText(customerType.getName());
//			}
			try {
				this.roomCategory = new RoomCategoryController().getRoomCategoryById(0);
				txtCategory.setText(roomCategory.getName());
			} catch (DataAccessException ex) {
				ex.printStackTrace();
			}
		});
	}
}

