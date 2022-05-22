package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import controller.DBController;
import controller.SessionController;
import db.DataAccessException;
import gui.panels.AbstractCRUDPanel;
import gui.panels.CRUDContracts;
import gui.panels.CRUDRooms;
import gui.panels.CRUDTenants;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Daniels Kanepe
 *
 */
public class Dashboard extends JFrame {

	private JPanel contentPane;
	private JLabel lblGreeting;
	private JLink btnLogout;
	private JTabbedPane tabsPane;
	private JPanel sellPanel;
	private JPanel sellPaneBottomPanel;
	private JLabel lblSell;
	private JButton btnSellItems;
	private JLabel lblQuotes;
	private JLabel lblOrder;
	private JButton btnViewQuotes;
	private JButton btnViewOrders;
	private JPanel loanPanel;
	private JPanel statisticsPanel;
	private JButton btnEmployee;
	private JButton btnCustomer;
	private JLabel lblEmployee;
	private JLabel lblCustomer;
	private JPanel InventoryPanel;
	private JButton btnContractors;
	private JButton btnSupplyOrders;
	private JButton btnSupplyOffers;
	private JLabel lblRestockItems;
	private JLabel lblManage;
	private JButton btnStorageLocations;
	private JButton btnShelves;
	private JLabel lblInventoryManagePic;
	private JLabel lblInventoryRestockPic;
	private JButton btnProducts;
	private JButton btnCustomerTypes;
	private JLabel lblLoanImage;
	private JButton btnManageLoans;
	private JButton btnReturnLoan;
	private JTabbedPane stTabsPane;
	private JPanel stRevenuePanel;
	private JPanel stOrdersPanel;
	private XChartPanel<XYChart> stRevenueChartPanel;

	JLabel noOrdersLabel;
	JLabel label;
	private JButton btnDarkLight;

	private JButton btnChooseCustomerLoans;
	private JTextField txtCustomerLoans;
	private JButton btnChooseCustomerSell;
	private JTextField txtCustomerSell;
	private JButton btnRefresh;

	/**
	 * Create the frame.
	 */
	public Dashboard() {
		setTitle("Dormify");
		
		// Window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		// *Content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
			// **Top panel (greeting & log out)
			JPanel topPanel = new JPanel();
			contentPane.add(topPanel, BorderLayout.NORTH);
			
			// ***** TOP PANEL *****
			GridBagLayout gbl_topPanel = new GridBagLayout();
			gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
			gbl_topPanel.rowHeights = new int[]{0, 0, 0};
			gbl_topPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_topPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			topPanel.setLayout(gbl_topPanel);
		
				// // ***** Greeting label *****
				lblGreeting = new JLabel("Hi, " + SessionController.getInstance().getLoggedInEmployee().getFirstName());
				GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
				gbc_lblGreeting.insets = new Insets(0, 0, 5, 5);
				gbc_lblGreeting.gridx = 0;
				gbc_lblGreeting.gridy = 0;
				topPanel.add(lblGreeting, gbc_lblGreeting);
				
				btnRefresh = new JButton( Images.RELOAD.getImageIcon(16, 16));
				btnRefresh.setToolTipText("Sync");
				btnRefresh.setFocusPainted(false);
				btnRefresh.setContentAreaFilled(false);
				btnRefresh.setBorderPainted(false);
				btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
				gbc_btnRefresh.insets = new Insets(0, 0, 5, 5);
				gbc_btnRefresh.gridx = 2;
				gbc_btnRefresh.gridy = 0;
				topPanel.add(btnRefresh, gbc_btnRefresh);
				
				btnDarkLight = new JButton(App.darkMode ? Images.SUN.getImageIcon(16, 16) : Images.MOON.getImageIcon(16, 16));
				btnDarkLight.setBorderPainted(false);
				btnDarkLight.setFocusPainted(false);
				btnDarkLight.setContentAreaFilled(false);
				btnDarkLight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				GridBagConstraints gbc_btnDarkLight = new GridBagConstraints();
				gbc_btnDarkLight.insets = new Insets(0, 0, 5, 5);
				gbc_btnDarkLight.gridx = 3;
				gbc_btnDarkLight.gridy = 0;
				topPanel.add(btnDarkLight, gbc_btnDarkLight);
		
				// // ***** Log out button *****
				btnLogout = new JLink("Log out");
				GridBagConstraints gbc_lblLogout = new GridBagConstraints();
				gbc_lblLogout.insets = new Insets(0, 0, 5, 0);
				gbc_lblLogout.gridx = 4;
				gbc_lblLogout.gridy = 0;
				topPanel.add(btnLogout, gbc_lblLogout);
		
			// ***** Tabs pane *****
			tabsPane = new JTabbedPane(JTabbedPane.TOP);
			contentPane.add(tabsPane, BorderLayout.CENTER);
			
			
			// Home tab
			JPanel emptyPanel1 = new JPanel();
			emptyPanel1.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Home", null, emptyPanel1, "Dashboard");

			// CRUD rooms tab
			JPanel emptyPanel2 = null;
			try {
				emptyPanel2 = new CRUDRooms();
			} catch (DataAccessException e) {
				e.printStackTrace();
				System.exit(0);
			}
			emptyPanel2.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Rooms", null, emptyPanel2, "Manage rooms");

			// CRUD employees tab
			JPanel emptyPanel3 = new JPanel();
			emptyPanel3.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Employees", null, emptyPanel3, "Manage employees");

			// CRUD contracts tab
			JPanel contractsCRUDPanel = null;
			try {
				contractsCRUDPanel = new CRUDContracts();
			} catch (DataAccessException e) {
				e.printStackTrace();
				// exit
				System.exit(0);
			}
			contractsCRUDPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Contracts", null, contractsCRUDPanel, "Manage contracts");

			// CRUD tenants tab
			JPanel tenantsCRUDPanel = null;
			try {
				tenantsCRUDPanel = new CRUDTenants();
			} catch (DataAccessException e) {
				e.printStackTrace();
				// exit
				System.exit(0);
			}
			tenantsCRUDPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Tenants", null, tenantsCRUDPanel, "Manage tenants");

			// CRUD leaving notices tab
			JPanel emptyPanel6 = new JPanel();
			emptyPanel6.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Leaving notices", null, emptyPanel6, "Manage leave notices");

			// CRUD Reports tab
			JPanel emptyPanel7 = new JPanel();
			emptyPanel7.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Reports", null, emptyPanel7, "View statistics");
			
		// Attach event handler
		addEventHandlers();
	}

	
	/*
	 * *******************************************************
	 * *******************  METHODS **************************
	 * *******************************************************
	 */


	// Get current tab's AbstractCRUDPanel, if exists
	private AbstractCRUDPanel getCurrentTabCRUDPanel() {

		int tabIndex = tabsPane.getSelectedIndex();

		try {
			return (AbstractCRUDPanel) tabsPane.getComponentAt(tabIndex);
		} catch (ClassCastException e) {
			return null;
		}
	}

	
	
	/*
	 * *******************************************************
	 * *******************  EVENT HANDLERS *******************
	 * *******************************************************
	 */
	
	public void addEventHandlers() {
		
		// Toggle dark/light mode
		btnDarkLight.addActionListener(e -> {
    		try {
				UIManager.setLookAndFeel(App.darkMode ? new FlatLightLaf() : new FlatDarkLaf());
			} catch (UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
    		App.darkMode = !App.darkMode;
    		SwingUtilities.updateComponentTreeUI(this);
			// Change icon
			btnDarkLight.setIcon(App.darkMode ? Images.SUN.getImageIcon(16, 16) : Images.MOON.getImageIcon(16, 16));
		});
		
		// ***** Log out button *****
		btnLogout.addActionListener(e -> {
	    	if (Messages.confirm(Dashboard.this, "Are you sure you want to log out?", "Log Out?")) {
		    	SessionController.getInstance().logout();
				App.showLoginDefaultWithCredentials();
		    	// free up memory by destroying the current dashboard
		    	Dashboard.this.dispose();
	    	}
		});

		// On tab change, reload the table's data
		tabsPane.addChangeListener(e -> {
			try {
				if (getCurrentTabCRUDPanel() != null) {
					getCurrentTabCRUDPanel().getTableModel().refreshData();
				}
			} catch (DataAccessException ex) {
				Messages.error(Dashboard.this, "Could not load table data...", "Error");
			}
		});

		// On refresh button click, reload the table's data
		btnRefresh.addActionListener(e -> {
			// Disable refresh button
			btnRefresh.setEnabled(false);

			// Execute after refresh button is disabled
			SwingUtilities.invokeLater(() -> {
				try {
					new DBController().clearLocalContainer();
					if (getCurrentTabCRUDPanel() != null) {
						getCurrentTabCRUDPanel().getTableModel().refreshData();
					}
				} catch (DataAccessException ex) {
					Messages.error(Dashboard.this, "Could not load table data...", "Error");
				}
				// Enable refresh button
				btnRefresh.setEnabled(true);
			});


		});



	} // end of event handlers
}
