package gui.windows;

import controller.ContractController;
import controller.RoomController;
import controller.TenantController;
import db.DataAccessException;
import gui.Common;
import gui.JButtonPrimary;
import gui.Messages;
import model.Contract;
import model.Room;
import model.Tenant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Predicate;

public class WindowContract extends JDialog {

	private Contract contract;
	private Room room;
	private List<Tenant> tenants;
	private ContractController contractCtrl;
	private Mode mode;

	public enum Mode {
		VIEW,
		EDIT,
		CREATE
	}

	private JPanel contentPane;
	private JButton btnSubmit;
	private JPanel panelRoom;
	private JTextField txtDisplayRoom;
	private JButton btnChooseRoom;
	private JLabel lblRoom;
	private JButton btnChooseTenants;
	private JTextArea txtDisplayTenants;
	private JPanel panelInternet;
	private JRadioButton rdbtnInternetYes;
	private JRadioButton rdbtnInternetNo;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblInternet;
	private JLabel lblStartDate;
	private JTextField txtStartDate;
	private JLabel lblID;
	private JTextField txtID;


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

		contractCtrl = new ContractController();
		this.contract = contract;
		this.room = contract != null ? contract.getRoom() : null;
		this.tenants = contract != null ? contract.getTenants() : null;

		setModal(true);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0};
		contentPane.setLayout(gbl_contentPanel);
				
				lblID = new JLabel("ID");
				GridBagConstraints gbc_lblID = new GridBagConstraints();
				gbc_lblID.anchor = GridBagConstraints.SOUTH;
				gbc_lblID.insets = new Insets(0, 0, 5, 5);
				gbc_lblID.gridx = 0;
				gbc_lblID.gridy = 0;
				contentPane.add(lblID, gbc_lblID);
				
				lblStartDate = new JLabel(String.format("Start date (%s) *", Common.getDateFormat()));
				GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
				gbc_lblStartDate.anchor = GridBagConstraints.SOUTH;
				gbc_lblStartDate.insets = new Insets(0, 0, 5, 0);
				gbc_lblStartDate.gridx = 1;
				gbc_lblStartDate.gridy = 0;
				contentPane.add(lblStartDate, gbc_lblStartDate);
				
				txtID = new JTextField();
				GridBagConstraints gbc_txtID = new GridBagConstraints();
				gbc_txtID.anchor = GridBagConstraints.NORTH;
				gbc_txtID.insets = new Insets(0, 0, 5, 5);
				gbc_txtID.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtID.gridx = 0;
				gbc_txtID.gridy = 1;
				contentPane.add(txtID, gbc_txtID);
				txtID.setColumns(10);
				
				txtStartDate = new JTextField();
				txtStartDate.setColumns(10);
				GridBagConstraints gbc_txtStartDate = new GridBagConstraints();
				gbc_txtStartDate.anchor = GridBagConstraints.NORTH;
				gbc_txtStartDate.insets = new Insets(0, 0, 5, 0);
				gbc_txtStartDate.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtStartDate.gridx = 1;
				gbc_txtStartDate.gridy = 1;
				contentPane.add(txtStartDate, gbc_txtStartDate);
				
				lblInternet = new JLabel("Include internet");
				GridBagConstraints gbc_lblInternet = new GridBagConstraints();
				gbc_lblInternet.anchor = GridBagConstraints.SOUTH;
				gbc_lblInternet.insets = new Insets(0, 0, 5, 5);
				gbc_lblInternet.gridx = 0;
				gbc_lblInternet.gridy = 2;
				contentPane.add(lblInternet, gbc_lblInternet);
		
				lblRoom = new JLabel("Room *");
				GridBagConstraints gbc_lblRoom = new GridBagConstraints();
				gbc_lblRoom.anchor = GridBagConstraints.SOUTH;
				gbc_lblRoom.insets = new Insets(0, 0, 5, 0);
				gbc_lblRoom.gridx = 1;
				gbc_lblRoom.gridy = 2;
				contentPane.add(lblRoom, gbc_lblRoom);
				
				panelInternet = new JPanel();
				GridBagConstraints gbc_panelInternet = new GridBagConstraints();
				gbc_panelInternet.insets = new Insets(0, 0, 5, 5);
				gbc_panelInternet.fill = GridBagConstraints.BOTH;
				gbc_panelInternet.gridx = 0;
				gbc_panelInternet.gridy = 3;
				contentPane.add(panelInternet, gbc_panelInternet);
				GridBagLayout gbl_panelInternet = new GridBagLayout();
				gbl_panelInternet.columnWidths = new int[]{0, 0, 0};
				gbl_panelInternet.rowHeights = new int[]{0, 0};
				gbl_panelInternet.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
				gbl_panelInternet.rowWeights = new double[]{1.0, Double.MIN_VALUE};
				panelInternet.setLayout(gbl_panelInternet);
				
				rdbtnInternetYes = new JRadioButton("Yes");
				buttonGroup.add(rdbtnInternetYes);
				GridBagConstraints gbc_rdbtnInternetYes = new GridBagConstraints();
				gbc_rdbtnInternetYes.anchor = GridBagConstraints.NORTH;
				gbc_rdbtnInternetYes.insets = new Insets(0, 0, 0, 5);
				gbc_rdbtnInternetYes.gridx = 0;
				gbc_rdbtnInternetYes.gridy = 0;
				panelInternet.add(rdbtnInternetYes, gbc_rdbtnInternetYes);
				
				rdbtnInternetNo = new JRadioButton("No");
				buttonGroup.add(rdbtnInternetNo);
				GridBagConstraints gbc_rdbtnInternetNo = new GridBagConstraints();
				gbc_rdbtnInternetNo.anchor = GridBagConstraints.NORTH;
				gbc_rdbtnInternetNo.gridx = 1;
				gbc_rdbtnInternetNo.gridy = 0;
				panelInternet.add(rdbtnInternetNo, gbc_rdbtnInternetNo);

				panelRoom = new JPanel();
				GridBagConstraints gbc_panelRoom = new GridBagConstraints();
				gbc_panelRoom.insets = new Insets(0, 0, 5, 0);
				gbc_panelRoom.fill = GridBagConstraints.BOTH;
				gbc_panelRoom.gridx = 1;
				gbc_panelRoom.gridy = 3;
				contentPane.add(panelRoom, gbc_panelRoom);
				GridBagLayout gbl_panelRoom = new GridBagLayout();
				gbl_panelRoom.columnWidths = new int[]{0, 0, 0};
				gbl_panelRoom.rowHeights = new int[]{0, 0};
				gbl_panelRoom.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
				gbl_panelRoom.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				panelRoom.setLayout(gbl_panelRoom);
				
						txtDisplayRoom = new JTextField();
						txtDisplayRoom.setEnabled(false);
						GridBagConstraints gbc_txtDisplayRoom = new GridBagConstraints();
						gbc_txtDisplayRoom.insets = new Insets(0, 0, 0, 5);
						gbc_txtDisplayRoom.fill = GridBagConstraints.BOTH;
						gbc_txtDisplayRoom.gridx = 0;
						gbc_txtDisplayRoom.gridy = 0;
						panelRoom.add(txtDisplayRoom, gbc_txtDisplayRoom);
						txtDisplayRoom.setColumns(10);

								btnChooseRoom = new JButton("Choose...");
								GridBagConstraints gbc_btnChooseRoom = new GridBagConstraints();
								gbc_btnChooseRoom.fill = GridBagConstraints.BOTH;
								gbc_btnChooseRoom.gridx = 1;
								gbc_btnChooseRoom.gridy = 0;
								panelRoom.add(btnChooseRoom, gbc_btnChooseRoom);


		JLabel lblTenant = new JLabel("Tenants *");
		GridBagConstraints gbc_lblTenant = new GridBagConstraints();
		gbc_lblTenant.gridwidth = 2;
		gbc_lblTenant.anchor = GridBagConstraints.SOUTH;
		gbc_lblTenant.insets = new Insets(0, 0, 5, 0);
		gbc_lblTenant.gridx = 0;
		gbc_lblTenant.gridy = 4;
		contentPane.add(lblTenant, gbc_lblTenant);
		
		btnChooseTenants = new JButton("Choose...");
		GridBagConstraints gbc_btnChooseTenants = new GridBagConstraints();
		gbc_btnChooseTenants.gridwidth = 2;
		gbc_btnChooseTenants.anchor = GridBagConstraints.SOUTH;
		gbc_btnChooseTenants.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChooseTenants.insets = new Insets(0, 0, 5, 0);
		gbc_btnChooseTenants.gridx = 0;
		gbc_btnChooseTenants.gridy = 5;
		contentPane.add(btnChooseTenants, gbc_btnChooseTenants);
		
		txtDisplayTenants = new JTextArea();
		GridBagConstraints gbc_txtDisplayTenants = new GridBagConstraints();
		gbc_txtDisplayTenants.gridwidth = 2;
		gbc_txtDisplayTenants.insets = new Insets(0, 0, 5, 0);
		gbc_txtDisplayTenants.fill = GridBagConstraints.BOTH;
		gbc_txtDisplayTenants.gridx = 0;
		gbc_txtDisplayTenants.gridy = 6;
		contentPane.add(txtDisplayTenants, gbc_txtDisplayTenants);
		txtDisplayTenants.setColumns(10);

		btnSubmit = new JButtonPrimary("Update");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridwidth = 2;
		gbc_btnOk.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 7;
		contentPane.add(btnSubmit, gbc_btnOk);

		switch (mode) {
			case VIEW:
				// Set title
				setTitle("View Contract - " + room.getID());
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
		// except display fields & ID
		txtID.setEnabled(false);
		txtDisplayRoom.setEnabled(false);
		txtDisplayTenants.setEnabled(false);
	}

	// FIll in the fields
	private void fillFields(Contract contract) {
		txtID.setText(String.valueOf(contract.getID()));
		txtStartDate.setText(Common.dateToString(contract.getStartDate()));
		txtDisplayTenants.setText(getTenantRepresentation(contract.getTenants()));
		rdbtnInternetYes.setSelected(contract.isIncludeInternet());
		rdbtnInternetNo.setSelected(!contract.isIncludeInternet());
		txtDisplayRoom.setText(getRoomRepresentation(contract.getRoom()));
	}

	private String getTenantRepresentation(List<Tenant> tenants) {
		StringBuilder tenantRepr = new StringBuilder();
		for (Tenant tenant : tenants) {
			tenantRepr.append(
					String.format("(%d) %s %s\n",
							tenant.getID(),
							tenant.getFirstName(),
							tenant.getLastName()
					)
			);
		}
		return tenantRepr.toString();
	}

	private String getRoomRepresentation(Room room) {
		return String.format("Room %d category %s",
				room.getID(), room.getRoomCategory().getName());
	}

	/*
	 * Returns the selected object, or null
	 */
	public Contract getSelectedObject() {
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

				// Validate: Room is chosen
				if (room == null) {
					Messages.error(this, "Please choose a room.");
					return;
				}

				// Validate: Tenants have been chosen
				if (tenants == null || tenants.size() == 0) {
					Messages.error(this, "Please choose at least one tenant.");
					return;
				}

				// Validate: Up to max amount of tenants have been chosen
				if (tenants.size() > room.getRoomCategory().getMaxTenants()) {
					Messages.error(this, "The room category allows only "
							+ room.getRoomCategory().getMaxTenants() + " tenants.");
					return;
				}

				// Validate: start date is not empty
				if (txtStartDate.getText().isEmpty()) {
					Messages.error(this, "Please enter a start date.");
					return;
				}

				// Parse start date
				LocalDate newStartDate;
				try {
					newStartDate = Common.stringToDate(txtStartDate.getText().strip());
				} catch (DateTimeParseException e1) {
					Messages.error(this, "Please enter a valid contract start date in the format of: "
							+ Common.getDateFormat());
					return;
				}

				// Validate: start date is not in the past
				if (newStartDate.isBefore(LocalDate.now())) {
					Messages.error(this, "The start date cannot be in the past.");
					return;
				}

				// Validate: yes/no radio buttons are selected for internet
				if (!rdbtnInternetYes.isSelected() && !rdbtnInternetNo.isSelected()) {
					Messages.error(this, "Please select whether the contract includes internet.");
					return;
				}

				// parse the internet option
				boolean includeInternet = rdbtnInternetYes.isSelected();

				if (mode == Mode.EDIT) {
					try {
						contractCtrl.updateContract(contract, newStartDate, tenants, room, includeInternet);
					} catch (DataAccessException ex) {
						Messages.error("Error updating contract", "error");
					}

				} else if (mode == Mode.CREATE) {
					try {
						this.contract = contractCtrl.addContract(newStartDate, tenants, room, includeInternet);
					} catch (DataAccessException ex) {
						Messages.error("Error creating contract", "error");
					}
				}
				// Dispose of the window
				this.dispose();
			}
		});

		btnChooseRoom.addActionListener(e -> {
			// Open 'choose room' window
			ChooseRoom frame = null;
			try {
				// Check if the room is out of service or occupied
				Predicate<Room> filter = room -> room.isOutOfService() || new RoomController().isRoomOccupied(room);
				frame = new ChooseRoom(filter, "Cannot choose this room as it is out of service or occupied.");
			} catch (DataAccessException ex) {
				Messages.error("Error opening choose room window", "error");
			}
			frame.setVisible(true);

			// if a room was chosen, update the text field & allow selection of tenants
			if (frame.getSelectedObject() != null) {
				this.room = frame.getSelectedObject();
				txtDisplayRoom.setText(getRoomRepresentation(room));
				btnChooseTenants.setEnabled(true);
			} else {
				btnChooseTenants.setEnabled(false);
			}
		});

		btnChooseTenants.addActionListener(e -> {
			// A room must be chosen first as to determine the max amount of tenants
			if (room == null) {
				Messages.error(this, "Please choose a room.");
				return;
			}

			// Open 'choose tenants' window, specifying max amount of tenants
			ChooseTenant frame = null;
			try {
				Predicate<Tenant> invalidTenantFilter = tenant -> new TenantController().tenantHasValidContract(tenant);
				frame = new ChooseTenant(this.room.getRoomCategory().getMaxTenants(),
							invalidTenantFilter, "the tenant(s) are already assigned to another contract.");
			} catch (DataAccessException ex) {
				Messages.error("Error opening choose tenant window", "error");
			}
			frame.setVisible(true);

			// If tenants have been chosen, update the display
			if (!frame.getSelectedObjects().isEmpty()) {
				this.tenants = frame.getSelectedObjects();
				txtDisplayTenants.setText(getTenantRepresentation(tenants));
			}

		});
	}
}

