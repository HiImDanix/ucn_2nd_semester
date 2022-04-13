package gui;


import javax.swing.JFrame;


import java.awt.GridBagLayout;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.GridBagConstraints;


import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

import db.DataAccessException;
import model.Employee;
import controller.EmployeeController;
import controller.SessionController;

/**
 * @author Daniels Kanepe
 *
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel centeredLoginPane;
	private JLabel lblTitle;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JPanel spacerPanel;
	private JButton btnLogin;
	private Component verticalStrut;
	
	/**
	 * Create the Login Window
	 */
	public Login() {
		setMinimumSize(new Dimension(250, 250));
		setTitle("Dormify - Log In");
		
		// Content pane
		setBounds(new Rectangle(0, 0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 250);
		contentPane = new JPanel();
		contentPane.setAlignmentY(0.0f);
		contentPane.setAlignmentX(0.0f);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 200, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		centeredLoginPane = new JPanel();
		centeredLoginPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		centeredLoginPane.setAlignmentY(0.0f);
		centeredLoginPane.setAlignmentX(0.0f);
		GridBagConstraints gbc_centeredLoginPane = new GridBagConstraints();
		gbc_centeredLoginPane.insets = new Insets(0, 0, 5, 5);
		gbc_centeredLoginPane.fill = GridBagConstraints.BOTH;
		gbc_centeredLoginPane.gridx = 1;
		gbc_centeredLoginPane.gridy = 1;
		contentPane.add(centeredLoginPane, gbc_centeredLoginPane);
		GridBagLayout gbl_centeredLoginPane = new GridBagLayout();
		gbl_centeredLoginPane.columnWidths = new int[]{200, 0};
		gbl_centeredLoginPane.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 37, 0};
		gbl_centeredLoginPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_centeredLoginPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centeredLoginPane.setLayout(gbl_centeredLoginPane);
		
		lblTitle = new JLabel("Log In");
		lblTitle.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblTitle.setForeground(Palette.SECONDARY_GREY_DARK.getColor());
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.SOUTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		centeredLoginPane.add(lblTitle, gbc_lblTitle);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 1;
		centeredLoginPane.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setName("");
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.gridx = 0;
		gbc_txtEmail.gridy = 2;
		centeredLoginPane.add(txtEmail, gbc_txtEmail);
		
		lblPassword = new JLabel("Password");
		lblPassword.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		centeredLoginPane.add(lblPassword, gbc_lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setToolTipText("");
		txtPassword.setColumns(10);
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.anchor = GridBagConstraints.NORTH;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.gridx = 0;
		gbc_txtPassword.gridy = 4;
		centeredLoginPane.add(txtPassword, gbc_txtPassword);
		
		spacerPanel = new JPanel();
		spacerPanel.setPreferredSize(new Dimension(0, 5));
		spacerPanel.setMaximumSize(new Dimension(0, 5));
		spacerPanel.setMinimumSize(new Dimension(0, 5));
		GridBagConstraints gbc_spacerPanel = new GridBagConstraints();
		gbc_spacerPanel.fill = GridBagConstraints.BOTH;
		gbc_spacerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_spacerPanel.gridx = 0;
		gbc_spacerPanel.gridy = 5;
		centeredLoginPane.add(spacerPanel, gbc_spacerPanel);
		
		verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setMinimumSize(new Dimension(0, 5));
		spacerPanel.add(verticalStrut);
		
		btnLogin = new JButtonPrimary("Log In");
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.BOTH;
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 6;
		centeredLoginPane.add(btnLogin, gbc_btnLogin);
		
		// Add event handlers
		addEventHandlers();
	}
	
	
	/**
	 * Alternative constructor (autofill username & password)
	 * 
	 * @param email
	 * @param password
	 */
	public Login(String email, String password) {
		this();
		txtEmail.setText(email);
		txtPassword.setText(password);
	}
	
	/*
	 * *******************************************************
	 * ***********************  Methods **********************
	 * *******************************************************
	 */
	
	
	/*
	 * *******************************************************
	 * *******************  EVENT HANDLERS *******************
	 * *******************************************************
	 */
	public void addEventHandlers() {
		// Define login action
		Action loginAction = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
				// parse fields
			    String email = txtEmail.getText().toLowerCase().trim();
			    String password = txtPassword.getPassword().toString();
			    
			    // if empty, show error
			    if (email.isEmpty() || password.isEmpty()) {
		            Messages.error(Login.this, "Email or password cannot be empty!");
		            return;
			    }

				SessionController session = SessionController.getInstance();

				// Log in
				try {
					if (session.authenticate(email, password)) {
						// if login successful, close login window
						Login.this.dispose();

						// Show success message
						Messages.success(Login.this, "Login successful!");

						// show main window
						//Dashboard mainWindow = new Dashboard();
						//mainWindow.setVisible(true);
					} else {
						// if login failed, show error
						Messages.error(Login.this, "The e-mail and/or password is incorrect.");
					}
				} catch (DataAccessException ex) {
					// if login failed, show error
					Messages.error(Login.this, "An error occurred while trying to log in.");
				}
		    }
		};
		// Attach login action to button
		btnLogin.addActionListener(loginAction);
		// Attach login action to enter key press on password field
		txtPassword.addActionListener(loginAction);
		// Attach login action to enter key press on e-mail field
		txtEmail.addActionListener(loginAction);
	}
	
}
