package gui.windows;

import controller.RoomCategoryController;
import controller.RoomController;
import controller.TenantContractController;
import db.DataAccessException;
import gui.Common;
import gui.JButtonPrimary;
import gui.Messages;
import model.Contract;
import model.Room;
import model.RoomCategory;
import model.Tenant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class WindowContract extends JDialog {

	private Contract contract;
	private Room room;
	private List<Tenant> tenants;
	private TenantContractController tenntContractCtrl;
	private Mode mode;

	public enum Mode {
		VIEW,
		EDIT,
		CREATE
	}

	private JPanel contentPane;
	private JButton btnSubmit;
	private JPanel roomPanel1;
	private JTextField txtDisplayRoom;
	private JButton btnChooseRoom;
	private JLabel lblRoom;
	private JPanel tenantsPanel1;
	private JTextField txtDisplayTenant1;
	private JButton btnChooseTenant1;
	private JPanel tenantsPanel;


	/**
	 * Constructor for create mode
	 */
	public WindowContract() {
		this(null, Mode.CREATE);
		this.contract = null;
		this.room = null;
		this.tenants = null;
	}

	/**
	 * Constructor for view/edit
	 *
	 * @param contract the contract
	 * @param mode the mode
	 */
	public WindowContract(Contract contract, Mode mode) {
		this.mode = mode;

		tenntContractCtrl = new TenantContractController();
		this.contract = contract;
		this.room = contract != null ? contract.getRoom() : null;
		this.tenants = contract != null ? contract.getTenants() : null;

		setModal(true);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0};
		contentPane.setLayout(gbl_contentPanel);
		
				lblRoom = new JLabel("Room *");
				GridBagConstraints gbc_lblRoom = new GridBagConstraints();
				gbc_lblRoom.anchor = GridBagConstraints.SOUTH;
				gbc_lblRoom.insets = new Insets(0, 0, 5, 0);
				gbc_lblRoom.gridx = 0;
				gbc_lblRoom.gridy = 0;
				contentPane.add(lblRoom, gbc_lblRoom);

				roomPanel1 = new JPanel();
				GridBagConstraints gbc_roomPanel1 = new GridBagConstraints();
				gbc_roomPanel1.insets = new Insets(0, 0, 5, 0);
				gbc_roomPanel1.fill = GridBagConstraints.BOTH;
				gbc_roomPanel1.gridx = 0;
				gbc_roomPanel1.gridy = 1;
				contentPane.add(roomPanel1, gbc_roomPanel1);
				GridBagLayout gbl_roomPanel1 = new GridBagLayout();
				gbl_roomPanel1.columnWidths = new int[]{0, 0, 0};
				gbl_roomPanel1.rowHeights = new int[]{0, 0};
				gbl_roomPanel1.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
				gbl_roomPanel1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				roomPanel1.setLayout(gbl_roomPanel1);
				
						txtDisplayRoom = new JTextField();
						txtDisplayRoom.setEnabled(false);
						GridBagConstraints gbc_txtDisplayRoom = new GridBagConstraints();
						gbc_txtDisplayRoom.insets = new Insets(0, 0, 0, 5);
						gbc_txtDisplayRoom.fill = GridBagConstraints.BOTH;
						gbc_txtDisplayRoom.gridx = 0;
						gbc_txtDisplayRoom.gridy = 0;
						roomPanel1.add(txtDisplayRoom, gbc_txtDisplayRoom);
						txtDisplayRoom.setColumns(10);

								btnChooseRoom = new JButton("Choose...");
								GridBagConstraints gbc_btnChooseRoom = new GridBagConstraints();
								gbc_btnChooseRoom.fill = GridBagConstraints.BOTH;
								gbc_btnChooseRoom.gridx = 1;
								gbc_btnChooseRoom.gridy = 0;
								roomPanel1.add(btnChooseRoom, gbc_btnChooseRoom);


		JLabel lblTenant = new JLabel("Tenants *");
		GridBagConstraints gbc_lblTenant = new GridBagConstraints();
		gbc_lblTenant.anchor = GridBagConstraints.SOUTH;
		gbc_lblTenant.insets = new Insets(0, 0, 5, 0);
		gbc_lblTenant.gridx = 0;
		gbc_lblTenant.gridy = 2;
		contentPane.add(lblTenant, gbc_lblTenant);

		addTenantChooseButtons(room != null ? room.getRoomCategory().getMaxTenants() : 0);

		switch (mode) {
			case VIEW:
				// Set title
				setTitle("View Contract - " + room.getId());
				// Hide 'Update' button if in view mode
				btnSubmit.setVisible(false);
				// Disable 'choose' button if in view mode.
				btnChooseRoom.setEnabled(false);
				// Disable fields
				this.disableFields();
				// Fill fields with content
				this.fillFields(contract);
				break;
			case EDIT:
				// Set title
				setTitle("Edit Contract");
				// Enable fields for editing
				this.enableFields();
				// Fill fields with content
				this.fillFields(contract);
				break;
			case CREATE:
				// Set title
				setTitle("Add New Contract");
				// Change submit button text to 'Create'
				btnSubmit.setText("Create");
				// Enable fields
				this.enableFields();

		}

		addEventHandlers();

	}

	/*
	 * Does not work for high amounts
	 */
	public void addTenantChooseButtons(int numberOfButtons) {

		// Generate the required amount of tenant choose buttons & display text fields
		for (int i = 0; i < numberOfButtons; i++) {

			tenantsPanel1 = new JPanel();
			GridBagConstraints gbc_tenantsPanel1 = new GridBagConstraints();
			gbc_tenantsPanel1.fill = GridBagConstraints.BOTH;
			gbc_tenantsPanel1.gridx = 0;
			gbc_tenantsPanel1.gridy = 0+i;
			tenantsPanel.add(tenantsPanel1, gbc_tenantsPanel1);
			GridBagLayout gbl_tenantsPanel1 = new GridBagLayout();
			gbl_tenantsPanel1.columnWidths = new int[]{0, 0, 0};
			gbl_tenantsPanel1.rowHeights = new int[]{0, 0};
			gbl_tenantsPanel1.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			gbl_tenantsPanel1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			tenantsPanel1.setLayout(gbl_tenantsPanel1);

			txtDisplayTenant1 = new JTextField();
			txtDisplayTenant1.setEnabled(false);
			txtDisplayTenant1.setColumns(10);
			GridBagConstraints gbc_txtDisplayTenant1 = new GridBagConstraints();
			gbc_txtDisplayTenant1.fill = GridBagConstraints.BOTH;
			gbc_txtDisplayTenant1.insets = new Insets(0, 0, 0, 5);
			gbc_txtDisplayTenant1.gridx = 0;
			gbc_txtDisplayTenant1.gridy = 0;
			tenantsPanel1.add(txtDisplayTenant1, gbc_txtDisplayTenant1);

			btnChooseTenant1 = new JButton("Choose...");
			GridBagConstraints gbc_btnChooseTenant1 = new GridBagConstraints();
			gbc_btnChooseTenant1.fill = GridBagConstraints.BOTH;
			gbc_btnChooseTenant1.gridx = 1;
			gbc_btnChooseTenant1.gridy = 0;
			tenantsPanel1.add(btnChooseTenant1, gbc_btnChooseTenant1);
		}
		
		tenantsPanel = new JPanel();
		GridBagConstraints gbc_tenantsPanel = new GridBagConstraints();
		gbc_tenantsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_tenantsPanel.fill = GridBagConstraints.BOTH;
		gbc_tenantsPanel.gridx = 0;
		gbc_tenantsPanel.gridy = 3;
		contentPane.add(tenantsPanel, gbc_tenantsPanel);
		GridBagLayout gbl_tenantsPanel = new GridBagLayout();
		gbl_tenantsPanel.columnWidths = new int[]{0, 0};
		gbl_tenantsPanel.rowHeights = new int[]{0, 0};
		gbl_tenantsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_tenantsPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		tenantsPanel.setLayout(gbl_tenantsPanel);

		btnSubmit = new JButtonPrimary("Update");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 4;
		contentPane.add(btnSubmit, gbc_btnOk);

		// resize to fit all new buttons & redraw UI
		this.doLayout();
		this.revalidate();
		this.repaint();



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
//		// except ID & Category field
//		txtID.setEnabled(false);
		txtDisplayRoom.setEnabled(false);
//		txtDisplayTenant.setEnabled(false);
	}

	// FIll in the fields
	private void fillFields(Contract contract) {
//		txtID.setText(String.valueOf(room.getId()));
//		txtDisplayRoom.setText(room.getRoomCategory().getName());
//		txtDisplayTenant.setText(tenant.get);
	}

	/**
	 * Gets the domain object used in view/edit, or one created in 'create' mode. Can be null!
	 *
	 * @return the room
	 */
	public Contract getObject() {
		return this.contract;
	}

		/*
	 * *******************************************************
	 * *******************  EVENT HANDLERS *******************
	 * *******************************************************
	 */
	private void addEventHandlers() {

		// 'update' button: Update the contract
		btnSubmit.addActionListener(e -> {
			String message = "";
			if (mode == Mode.EDIT) {
				message = "Are you sure you want to update the contract's details?";
			} else if (mode == Mode.CREATE) {
				message = "Create contract?";
			}
			if (Messages.confirm(this, message)) {
//
//				// Validate Room Category
//				if (roomCategory == null) {
//					Messages.error(this, "You must choose a room category!");
//					return;
//				}
//
//				// Validate that out of service is either true or false
//				if (rdbtnOutOfServiceYes.isSelected() == false && rdbtnOutOfServiceNo.isSelected() == false) {
//					Messages.error(this, "You must choose whether the room is out of service!");
//					return;
//				}
//
//				if (mode == Mode.EDIT) {
//					try {
//						roomCtrl.updateRoomIsOutOfService(room, rdbtnOutOfServiceYes.isSelected());
//						roomCtrl.updateRoomCategory(room, this.roomCategory);
//					} catch (DataAccessException ex) {
//						Messages.error("Error updating room", "error");
//					}
//
//				} else if (mode == Mode.CREATE) {
//					// if mode == Create, create a new room
//					try {
//						this.room = roomCtrl.addRoom(roomCategory, rdbtnOutOfServiceYes.isSelected());
//					} catch (DataAccessException ex) {
//						Messages.error("Error creating room", "error");
//					}
//				}
			}
			// Dispose of the window
			this.dispose();
		});

		btnChooseRoom.addActionListener(e -> {
			ChooseRoom frame = null;
			try {
				frame = new ChooseRoom();
			} catch (DataAccessException ex) {
				Messages.error("Error opening choose room window", "error");
			}
			frame.setVisible(true);
			if (frame.getSelectedObject() != null) {
				this.room = frame.getSelectedObject();
				txtDisplayRoom.setText(String.format("Room %d category %s",
						room.getId(), room.getRoomCategory().getName()));
			}

			addTenantChooseButtons(this.room != null ? this.room.getRoomCategory().getMaxTenants() : 0);
		});
	}
}

