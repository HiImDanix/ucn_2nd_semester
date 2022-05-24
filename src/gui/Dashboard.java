package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import controller.DBController;
import controller.SessionController;
import db.DataAccessException;
import gui.panels.*;
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
	private JPanel roomsCRUDPanel;
	private JButton btnRooms;
	private JButton btnContracts;
	private JButton btnTenants;
	private JButton btnRoomCategories;
	private JLabel imgRoom;
	private JLabel imgTenant;
	private JLabel imgContracts;
	private JLabel imgRoomCategories;

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
			gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
			gbl_topPanel.rowHeights = new int[]{0, 0, 0};
			gbl_topPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_topPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			topPanel.setLayout(gbl_topPanel);
		
				// // ***** Greeting label *****
				lblGreeting = new JLabel("Hi, " + SessionController.getInstance().getLoggedInEmployee().getFirstName());
				GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
				gbc_lblGreeting.insets = new Insets(0, 0, 5, 5);
				gbc_lblGreeting.gridx = 0;
				gbc_lblGreeting.gridy = 0;
				topPanel.add(lblGreeting, gbc_lblGreeting);
				
				btnDarkLight = new JButton(App.darkMode ? Images.SUN.getImageIcon(16, 16) : Images.MOON.getImageIcon(16, 16));
				btnDarkLight.setBorderPainted(false);
				btnDarkLight.setFocusPainted(false);
				btnDarkLight.setContentAreaFilled(false);
				btnDarkLight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				GridBagConstraints gbc_btnDarkLight = new GridBagConstraints();
				gbc_btnDarkLight.insets = new Insets(0, 0, 5, 5);
				gbc_btnDarkLight.gridx = 2;
				gbc_btnDarkLight.gridy = 0;
				topPanel.add(btnDarkLight, gbc_btnDarkLight);
		
				// // ***** Log out button *****
				btnLogout = new JLink("Log out");
				GridBagConstraints gbc_lblLogout = new GridBagConstraints();
				gbc_lblLogout.insets = new Insets(0, 0, 5, 0);
				gbc_lblLogout.gridx = 3;
				gbc_lblLogout.gridy = 0;
				topPanel.add(btnLogout, gbc_lblLogout);
		
			// ***** Tabs pane *****
			tabsPane = new JTabbedPane(JTabbedPane.TOP);
			contentPane.add(tabsPane, BorderLayout.CENTER);
			
			
			// Home tab
			JPanel homePanel = new JPanel();
			homePanel.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Home", null, homePanel, "Dashboard");
			GridBagLayout gbl_homePanel = new GridBagLayout();
			gbl_homePanel.columnWidths = new int[]{0, 0, 0};
			gbl_homePanel.rowHeights = new int[]{0, 0, 0, 0, 0};
			gbl_homePanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			gbl_homePanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			homePanel.setLayout(gbl_homePanel);
			
			imgRoom = new JLabel(Images.DOOR.getImageIcon(100, 100));
			GridBagConstraints gbc_imgRoom = new GridBagConstraints();
			gbc_imgRoom.anchor = GridBagConstraints.SOUTH;
			gbc_imgRoom.insets = new Insets(0, 0, 5, 5);
			gbc_imgRoom.gridx = 0;
			gbc_imgRoom.gridy = 0;
			homePanel.add(imgRoom, gbc_imgRoom);
			
			imgContracts = new JLabel(Images.CONTRACT.getImageIcon(100, 100));
			GridBagConstraints gbc_imgContracts = new GridBagConstraints();
			gbc_imgContracts.insets = new Insets(0, 0, 5, 0);
			gbc_imgContracts.gridx = 1;
			gbc_imgContracts.gridy = 0;
			homePanel.add(imgContracts, gbc_imgContracts);
			
			btnRooms = new JButton("Rooms");
			btnRooms.setPreferredSize(new Dimension(65, 30));
			GridBagConstraints gbc_btnRooms = new GridBagConstraints();
			gbc_btnRooms.anchor = GridBagConstraints.NORTH;
			gbc_btnRooms.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRooms.insets = new Insets(0, 0, 5, 5);
			gbc_btnRooms.gridx = 0;
			gbc_btnRooms.gridy = 1;
			homePanel.add(btnRooms, gbc_btnRooms);
			
			btnContracts = new JButton("Contracts");
			btnContracts.setPreferredSize(new Dimension(79, 30));
			GridBagConstraints gbc_btnContracts = new GridBagConstraints();
			gbc_btnContracts.anchor = GridBagConstraints.NORTH;
			gbc_btnContracts.insets = new Insets(0, 0, 5, 0);
			gbc_btnContracts.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnContracts.gridx = 1;
			gbc_btnContracts.gridy = 1;
			homePanel.add(btnContracts, gbc_btnContracts);
			
			imgTenant = new JLabel(Images.TENANT.getImageIcon(100, 100));
			GridBagConstraints gbc_imgTenant = new GridBagConstraints();
			gbc_imgTenant.anchor = GridBagConstraints.SOUTH;
			gbc_imgTenant.insets = new Insets(0, 0, 5, 5);
			gbc_imgTenant.gridx = 0;
			gbc_imgTenant.gridy = 2;
			homePanel.add(imgTenant, gbc_imgTenant);
			
			imgRoomCategories = new JLabel(Images.ROOM_CATEGORY.getImageIcon(100, 100));
			GridBagConstraints gbc_imgRoomCategories = new GridBagConstraints();
			gbc_imgRoomCategories.insets = new Insets(0, 0, 5, 0);
			gbc_imgRoomCategories.gridx = 1;
			gbc_imgRoomCategories.gridy = 2;
			homePanel.add(imgRoomCategories, gbc_imgRoomCategories);
			
			btnTenants = new JButton("Tenants");
			btnTenants.setPreferredSize(new Dimension(71, 30));
			GridBagConstraints gbc_btnTenants = new GridBagConstraints();
			gbc_btnTenants.anchor = GridBagConstraints.NORTH;
			gbc_btnTenants.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnTenants.insets = new Insets(0, 0, 0, 5);
			gbc_btnTenants.gridx = 0;
			gbc_btnTenants.gridy = 3;
			homePanel.add(btnTenants, gbc_btnTenants);
			
			btnRoomCategories = new JButton("Room Categories");
			btnRoomCategories.setPreferredSize(new Dimension(115, 30));
			GridBagConstraints gbc_btnRoomCategories = new GridBagConstraints();
			gbc_btnRoomCategories.anchor = GridBagConstraints.NORTH;
			gbc_btnRoomCategories.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRoomCategories.gridx = 1;
			gbc_btnRoomCategories.gridy = 3;
			homePanel.add(btnRoomCategories, gbc_btnRoomCategories);

			// CRUD rooms tab
			JPanel emptyPanel2 = null;
			try {
				roomsCRUDPanel = new CRUDRooms();
			} catch (DataAccessException e) {
				e.printStackTrace();
				System.exit(0);
			}
			roomsCRUDPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
			tabsPane.addTab("Rooms", null, roomsCRUDPanel, "Manage rooms");

//			// CRUD employees tab
//			JPanel emptyPanel3 = new JPanel();
//			emptyPanel3.setBorder(new EmptyBorder(15, 0, 0, 0));
//			tabsPane.addTab("Employees", null, emptyPanel3, "Manage employees");

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

		// CRUD tenants tab
		JPanel roomCategoriesCRUDPanel = null;
		try {
			roomCategoriesCRUDPanel = new CRUDRoomCategory();
		} catch (DataAccessException e) {
			e.printStackTrace();
			// exit
			System.exit(0);
		}
		roomCategoriesCRUDPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
		tabsPane.addTab("Room Categories", null, roomCategoriesCRUDPanel, "Categories");
//			// CRUD leaving notices tab
//			JPanel emptyPanel6 = new JPanel();
//			emptyPanel6.setBorder(new EmptyBorder(15, 0, 0, 0));
//			tabsPane.addTab("Leaving notices", null, emptyPanel6, "Manage leave notices");
//
//			// CRUD Reports tab
//			JPanel emptyPanel7 = new JPanel();
//			emptyPanel7.setBorder(new EmptyBorder(15, 0, 0, 0));
//			tabsPane.addTab("Reports", null, emptyPanel7, "View statistics");
			
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

		// Rooms button
		btnRooms.addActionListener(e -> {
			tabsPane.setSelectedIndex(1);
		});

		// Contracts button
		btnContracts.addActionListener(e -> {
			tabsPane.setSelectedIndex(2);
		});

		// Tenants button
		btnTenants.addActionListener(e -> {
			tabsPane.setSelectedIndex(3);
		});

		// Room categories button
		btnRoomCategories.addActionListener(e -> {
			tabsPane.setSelectedIndex(4);
		});
	} // end of event handlers
}
