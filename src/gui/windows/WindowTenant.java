package gui.windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.TenantController;
import db.DataAccessException;
import gui.Common;
import gui.JButtonPrimary;
import gui.Messages;
import model.Contract;
import model.StudyProof;
import model.Tenant;

public class WindowTenant extends JDialog {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Mode {
		VIEW,
		EDIT,
		CREATE
	}

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JButton btnSubmit;
	private JPanel typePanel;
	private JTextField txtDisplayStudyProof;
	private JButton btnChooseStudyProof;
	
	Tenant tenant;
	StudyProof studyProof;
	List<Contract> contracts;
	TenantController tenantCtrl;
	Mode mode;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JTextField txtLastName;
	private JLabel lblEmailAddress;
	private JTextField txtEmail;
	private JLabel lblPhone;
	private JTextField txtPhone;
	private JTextField txtID;
	private JLabel lblID;


	/**
	 * Constructor for create mode
	 */
	public WindowTenant() {
		this(null, Mode.CREATE);
		this.tenant = null;
		this.studyProof = null;
		this.contracts = new ArrayList<>(); 
	}

	/**
	 * Constructor for view/edit
	 */
	public WindowTenant(Tenant tenant, Mode mode) {
		this.mode = mode;
		
		tenantCtrl = new TenantController();
		this.tenant = tenant;
		this.studyProof = tenant != null ? tenant.getStudyProof() : null;
		this.contracts = tenant != null ? tenant.getContracts() : null;
		
		setModal(true);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPanel);
		
		lblID = new JLabel("ID");
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.gridx = 0;
		gbc_lblID.gridy = 0;
		contentPane.add(lblID, gbc_lblID);
		
		lblFirstName = new JLabel("First name *");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_lblFirstName.gridx = 1;
		gbc_lblFirstName.gridy = 0;
		contentPane.add(lblFirstName, gbc_lblFirstName);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		GridBagConstraints gbc_txtID = new GridBagConstraints();
		gbc_txtID.anchor = GridBagConstraints.NORTH;
		gbc_txtID.insets = new Insets(0, 0, 5, 5);
		gbc_txtID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtID.gridx = 0;
		gbc_txtID.gridy = 1;
		contentPane.add(txtID, gbc_txtID);
		
		
		txtFirstName = new JTextField();
		GridBagConstraints gbc_txtFirstName = new GridBagConstraints();
		gbc_txtFirstName.anchor = GridBagConstraints.NORTH;
		gbc_txtFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstName.gridx = 1;
		gbc_txtFirstName.gridy = 1;
		contentPane.add(txtFirstName, gbc_txtFirstName);
		txtFirstName.setColumns(10);
		
		lblLastName = new JLabel("Last name *");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 2;
		contentPane.add(lblLastName, gbc_lblLastName);
		
		lblEmailAddress = new JLabel("Email address *");
		GridBagConstraints gbc_lblEmailAddress = new GridBagConstraints();
		gbc_lblEmailAddress.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblEmailAddress.insets = new Insets(0, 0, 5, 0);
		gbc_lblEmailAddress.gridx = 1;
		gbc_lblEmailAddress.gridy = 2;
		contentPane.add(lblEmailAddress, gbc_lblEmailAddress);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		GridBagConstraints gbc_txtLastName = new GridBagConstraints();
		gbc_txtLastName.anchor = GridBagConstraints.NORTH;
		gbc_txtLastName.insets = new Insets(0, 0, 5, 5);
		gbc_txtLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastName.gridx = 0;
		gbc_txtLastName.gridy = 3;
		contentPane.add(txtLastName, gbc_txtLastName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 3;
		contentPane.add(txtEmail, gbc_txtEmail);
		
		lblPhone = new JLabel("Phone number *");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 4;
		contentPane.add(lblPhone, gbc_lblPhone);
		
		
		JLabel lblStudyProof = new JLabel("StufyProof");
		GridBagConstraints gbc_lblStudyProof = new GridBagConstraints();
		gbc_lblStudyProof.anchor = GridBagConstraints.SOUTH;
		gbc_lblStudyProof.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStudyProof.insets = new Insets(0, 0, 5, 0);
		gbc_lblStudyProof.gridx = 1;
		gbc_lblStudyProof.gridy = 4;
		contentPane.add(lblStudyProof, gbc_lblStudyProof);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		GridBagConstraints gbc_txtPhone = new GridBagConstraints();
		gbc_txtPhone.anchor = GridBagConstraints.NORTH;
		gbc_txtPhone.insets = new Insets(0, 0, 5, 5);
		gbc_txtPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPhone.gridx = 0;
		gbc_txtPhone.gridy = 5;
		contentPane.add(txtPhone, gbc_txtPhone);
		
		typePanel = new JPanel();
		GridBagConstraints gbc_typePanel = new GridBagConstraints();
		gbc_typePanel.insets = new Insets(0, 0, 5, 0);
		gbc_typePanel.fill = GridBagConstraints.BOTH;
		gbc_typePanel.gridx = 1;
		gbc_typePanel.gridy = 5;
		contentPane.add(typePanel, gbc_typePanel);
		GridBagLayout gbl_typePanel = new GridBagLayout();
		gbl_typePanel.columnWidths = new int[]{0, 0, 0};
		gbl_typePanel.rowHeights = new int[]{0, 0};
		gbl_typePanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_typePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		typePanel.setLayout(gbl_typePanel);
		
		txtDisplayStudyProof = new JTextField();
		GridBagConstraints gbc_txtDisplayStudyProof = new GridBagConstraints();
		gbc_txtDisplayStudyProof.insets = new Insets(0, 0, 0, 5);
		gbc_txtDisplayStudyProof.fill = GridBagConstraints.BOTH;
		gbc_txtDisplayStudyProof.gridx = 0;
		gbc_txtDisplayStudyProof.gridy = 0;
		typePanel.add(txtDisplayStudyProof, gbc_txtDisplayStudyProof);
		txtDisplayStudyProof.setColumns(10);
		
		btnChooseStudyProof = new JButton("Choose...");
		GridBagConstraints gbc_btnChooseStudyProof = new GridBagConstraints();
		gbc_btnChooseStudyProof.fill = GridBagConstraints.BOTH;
		gbc_btnChooseStudyProof.gridx = 1;
		gbc_btnChooseStudyProof.gridy = 0;
		typePanel.add(btnChooseStudyProof, gbc_btnChooseStudyProof);
		
		
		btnSubmit = new JButtonPrimary("Update");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridwidth = 2;
		gbc_btnOk.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 6;
		contentPane.add(btnSubmit, gbc_btnOk);
		
		switch (mode) {
			case VIEW:
				// Set title
				setTitle("View Tenant - " + tenant.getID());
				// Hide 'Update' button if in view mode
				btnSubmit.setVisible(false);
				// Disable 'choose' button if in view mode.
				btnChooseStudyProof.setEnabled(false);
				// Disable fields
				this.disableFields();
				// Fill fields with content
				this.fillFields(tenant);
				break;
			case EDIT: 
				// Set title
				setTitle("Edit Tenant");
				// Enable fields for editing
				this.enableFields();
				// Fill fields with content
				this.fillFields(tenant);
				break;
			case CREATE:
				// Set title
				setTitle("Add New Tenant");
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

		// except ID & Study proof display field
		txtID.setEnabled(false);
		txtDisplayStudyProof.setEnabled(false);
	}

	// FIll in the fields
	private void fillFields(Tenant tenant) {
		txtFirstName.setText(String.valueOf(tenant.getFirstName()));
		txtDisplayStudyProof.setText(tenant.getStudyProof() != null
				? "Until: " + Common.dateToString(tenant.getStudyProof().getStudentUntilDate())
				: "-");
		txtEmail.setText(tenant.getEmail());
		txtLastName.setText(tenant.getLastName());
		txtPhone.setText(tenant.getPhone());
		txtID.setText(String.valueOf(tenant.getID()));
	}
	
	/**
	 * Gets the room used in view/edit, or one created in 'create' mode. Can be null!
	 *
	 * @return the room
	 */
	public Tenant getTenant() {
		return this.tenant;
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
				message = "Are you sure you want to update the tenant's details?";
			} else if (mode == Mode.CREATE) {
				message = "Create tenant?";
			}
			if (Messages.confirm(this, message)) {

				// Validate & parse: First name
				String firstName = txtFirstName.getText().strip();
				if (firstName.isEmpty()) {
					Messages.error(this, "First name cannot be empty!");
					return;
				}

				// Validate & parse: Last name
				String lastName = txtLastName.getText().strip();
				if (lastName.isEmpty()) {
					Messages.error(this, "Last name cannot be empty!");
					return;
				}

				// Validate & parse: Email
				String email = txtEmail.getText().strip();
				if (email.isEmpty()) {
					Messages.error(this, "Email cannot be empty!");
					return;
				}

				// Validate & parse: Phone
				String phone = txtPhone.getText().strip();
				if (phone.isEmpty()) {
					Messages.error(this, "Phone cannot be empty!");
					return;
				}

				if (mode == Mode.EDIT) {
					try {
						tenantCtrl.updateTenant(tenant, firstName, lastName, email, phone);
					} catch (DataAccessException ex) {
						Messages.error(this, "Server error. Failed to update tenant.");
					}
				} else if (mode == Mode.CREATE) {
					// if mode == Create, create a new room
					try {
						this.tenant = tenantCtrl.addTenant(firstName, lastName, email, phone,
								studyProof, Collections.emptyList());
					} catch (DataAccessException ex) {
						Messages.error("Error creating tenant", "error");
					}
				}

				// Dispose of the window
				this.dispose();
			}
		});

		btnChooseStudyProof.addActionListener(e -> {
//			ChooseCustomerType frame = new ChooseCustomerType(auth);
//			frame.setVisible(true);
//			if (frame.getSelectedCustomerType() != null) {
//				this.customerType = frame.getSelectedCustomerType();
//				txtType.setText(customerType.getName());
//			}
			//TODO: StudyProofController call here
		});
	}
}