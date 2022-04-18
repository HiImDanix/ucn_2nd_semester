package gui.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.RoomCategoryController;
import controller.RoomController;
import controller.TenantController;
import db.DataAccessException;
import gui.Common;
import gui.JButtonPrimary;
import gui.Messages;
import gui.windows.WindowRoom.Mode;
import model.Contract;
import model.Room;
import model.RoomCategory;
import model.StudyProof;
import model.Tenant;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
	private JTextField txtFname;
	private JButton btnSubmit;
	private JPanel typePanel;
	private JTextField txtStudyProof;
	private JButton btnChooseStudyProof;
	
	Tenant tenant;
	StudyProof studyProof;
	List<Contract> contracts;
	TenantController tenantCtrl;
	Mode mode;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblFname;
	private JLabel lblLname;
	private JTextField txtLname;
	private JLabel lblEmailAddress;
	private JTextField txtEmail;
	private JLabel lblPhone;
	private JTextField txtPhone;
	private JSpinner spinner;
	private JLabel lblNewLabel;
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
	 *
	 * @param room the room
	 * @param mode the mode
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
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPanel);
		
		lblFname = new JLabel("First name *");
		GridBagConstraints gbc_lblFname = new GridBagConstraints();
		gbc_lblFname.anchor = GridBagConstraints.WEST;
		gbc_lblFname.insets = new Insets(0, 0, 5, 5);
		gbc_lblFname.gridx = 2;
		gbc_lblFname.gridy = 0;
		contentPane.add(lblFname, gbc_lblFname);
		
		
		txtFname = new JTextField();
		txtFname.setEnabled(false);
		GridBagConstraints gbc_txtFname = new GridBagConstraints();
		gbc_txtFname.anchor = GridBagConstraints.NORTH;
		gbc_txtFname.insets = new Insets(0, 0, 5, 5);
		gbc_txtFname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFname.gridx = 2;
		gbc_txtFname.gridy = 1;
		contentPane.add(txtFname, gbc_txtFname);
		txtFname.setColumns(10);
		
		lblLname = new JLabel("Last name *");
		GridBagConstraints gbc_lblLname = new GridBagConstraints();
		gbc_lblLname.anchor = GridBagConstraints.WEST;
		gbc_lblLname.insets = new Insets(0, 0, 5, 5);
		gbc_lblLname.gridx = 2;
		gbc_lblLname.gridy = 2;
		contentPane.add(lblLname, gbc_lblLname);
		
		txtLname = new JTextField();
		txtLname.setEnabled(false);
		txtLname.setColumns(10);
		GridBagConstraints gbc_txtLname = new GridBagConstraints();
		gbc_txtLname.insets = new Insets(0, 0, 5, 5);
		gbc_txtLname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLname.gridx = 2;
		gbc_txtLname.gridy = 3;
		contentPane.add(txtLname, gbc_txtLname);
		
		lblEmailAddress = new JLabel("Email address *");
		GridBagConstraints gbc_lblEmailAddress = new GridBagConstraints();
		gbc_lblEmailAddress.anchor = GridBagConstraints.WEST;
		gbc_lblEmailAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailAddress.gridx = 2;
		gbc_lblEmailAddress.gridy = 4;
		contentPane.add(lblEmailAddress, gbc_lblEmailAddress);
		
		txtEmail = new JTextField();
		txtEmail.setEnabled(false);
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 5;
		contentPane.add(txtEmail, gbc_txtEmail);
		
		lblPhone = new JLabel("Phone number *");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.WEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 2;
		gbc_lblPhone.gridy = 6;
		contentPane.add(lblPhone, gbc_lblPhone);
		
		lblNewLabel = new JLabel("+");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 7;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(45, 1, 999, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 7;
		contentPane.add(spinner, gbc_spinner);
		
		txtPhone = new JTextField();
		txtPhone.setEnabled(false);
		txtPhone.setColumns(10);
		GridBagConstraints gbc_txtPhone = new GridBagConstraints();
		gbc_txtPhone.insets = new Insets(0, 0, 5, 5);
		gbc_txtPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPhone.gridx = 2;
		gbc_txtPhone.gridy = 7;
		contentPane.add(txtPhone, gbc_txtPhone);
		
		
		JLabel lblStudyProof = new JLabel("StufyProof");
		GridBagConstraints gbc_lblStudyProof = new GridBagConstraints();
		gbc_lblStudyProof.anchor = GridBagConstraints.SOUTH;
		gbc_lblStudyProof.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStudyProof.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudyProof.gridx = 2;
		gbc_lblStudyProof.gridy = 8;
		contentPane.add(lblStudyProof, gbc_lblStudyProof);
		
		typePanel = new JPanel();
		GridBagConstraints gbc_typePanel = new GridBagConstraints();
		gbc_typePanel.gridwidth = 2;
		gbc_typePanel.insets = new Insets(0, 0, 5, 0);
		gbc_typePanel.fill = GridBagConstraints.BOTH;
		gbc_typePanel.gridx = 2;
		gbc_typePanel.gridy = 9;
		contentPane.add(typePanel, gbc_typePanel);
		GridBagLayout gbl_typePanel = new GridBagLayout();
		gbl_typePanel.columnWidths = new int[]{0, 0, 0};
		gbl_typePanel.rowHeights = new int[]{0, 0};
		gbl_typePanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_typePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		typePanel.setLayout(gbl_typePanel);
		
		txtStudyProof = new JTextField();
		txtStudyProof.setEnabled(false);
		GridBagConstraints gbc_txtStudyProof = new GridBagConstraints();
		gbc_txtStudyProof.insets = new Insets(0, 0, 0, 5);
		gbc_txtStudyProof.fill = GridBagConstraints.BOTH;
		gbc_txtStudyProof.gridx = 0;
		gbc_txtStudyProof.gridy = 0;
		typePanel.add(txtStudyProof, gbc_txtStudyProof);
		txtStudyProof.setColumns(10);
		
		btnChooseStudyProof = new JButton("Choose...");
		GridBagConstraints gbc_btnChooseStudyProof = new GridBagConstraints();
		gbc_btnChooseStudyProof.fill = GridBagConstraints.BOTH;
		gbc_btnChooseStudyProof.gridx = 1;
		gbc_btnChooseStudyProof.gridy = 0;
		typePanel.add(btnChooseStudyProof, gbc_btnChooseStudyProof);
		
		lblID = new JLabel("ID");
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.anchor = GridBagConstraints.EAST;
		gbc_lblID.gridx = 1;
		gbc_lblID.gridy = 10;
		contentPane.add(lblID, gbc_lblID);
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setColumns(10);
		GridBagConstraints gbc_txtID = new GridBagConstraints();
		gbc_txtID.insets = new Insets(0, 0, 5, 5);
		gbc_txtID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtID.gridx = 2;
		gbc_txtID.gridy = 10;
		contentPane.add(txtID, gbc_txtID);
		
		
		btnSubmit = new JButtonPrimary("Update");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 11;
		contentPane.add(btnSubmit, gbc_btnOk);
		
		switch (mode) {
			case VIEW:
				// Set title
				setTitle("View Tenant - " + tenant.getId());
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

		// except ID & Study proof field
		txtID.setEnabled(false);
		txtStudyProof.setEnabled(false);
	}

	// FIll in the fields
	private void fillFields(Tenant tenant) {
		txtFname.setText(String.valueOf(tenant.getFirstName()));
		txtStudyProof.setText(tenant.getLastName());
		txtEmail.setText(tenant.getEmail());
		txtPhone.setText(tenant.getPhone());
		//studyproof tf
		txtID.setText(String.valueOf(tenant.getId()));
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
				
				// Validate fname,lname,email,phone
				if (txtFname.getText() == null || txtLname.getText() == null || txtEmail.getText() == null || txtPhone.getText() == null) {
					Messages.error(this, "You must fill out the fields marked with a star!");
					return;
				}

				if (mode == Mode.EDIT) {
				//TODO: ctrl class update method calls here

				} else if (mode == Mode.CREATE) {
					// if mode == Create, create a new room
					try {
						this.tenant = tenantCtrl.addTenant(txtFname.getText().trim(), txtLname.getText().trim(), txtEmail.getText().trim(), txtPhone.getText().trim(), studyProof, contracts);
					} catch (DataAccessException ex) {
						Messages.error("Error creating tenant", "error");
					}
				}
			}
			// Dispose of the window
			this.dispose();
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