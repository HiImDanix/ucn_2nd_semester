package gui.windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import db.DataAccessException;
import gui.JButtonPrimary;
import gui.panels.CRUDRooms;
import gui.panels.tablemodels.RoomTableModel;
import model.Room;

public class ChooseRoom extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private CRUDRooms roomsPanel;
	private JButtonPrimary btnChoose;
	
	private Room selectedRoom = null;

	/**
	 * Create the dialog.
	 * @throws DataAccessException 
	 */
	public ChooseRoom() throws DataAccessException {
		setVisible(true);
		this.setTitle("Choose a room...");
		setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{420, 0};
		gbl_contentPane.rowHeights = new int[]{210, 25, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		roomsPanel = new CRUDRooms();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(roomsPanel, gbc_panel);
		
		btnChoose = new JButtonPrimary("Choose...");
		btnChoose.setEnabled(false);
		GridBagConstraints gbc_btnChoose = new GridBagConstraints();
		gbc_btnChoose.anchor = GridBagConstraints.EAST;
		gbc_btnChoose.gridx = 0;
		gbc_btnChoose.gridy = 1;
		contentPane.add(btnChoose, gbc_btnChoose);
		
		// Attach event handlers
		this.addEventHandlers();
	}
	/*
	 * *******************************************************
	 * *******************  Methods *******************
	 * *******************************************************
	 */
		public boolean isRoomSelected() {
			return selectedRoom != null;
		}
	
		public Room getSelectedCustomer() {
			return selectedRoom;
		}
	
	/*
	 * *******************************************************
	 * *******************  EVENT HANDLERS *******************
	 * *******************************************************
	 */
	private void addEventHandlers() {
		roomsPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
			JTable table = roomsPanel.getTable();
			if (table.getSelectionModel().isSelectionEmpty()) {
				btnChoose.setEnabled(false);
			} else {
				btnChoose.setEnabled(true);
			}
			
		});
		
		// Choose button
		btnChoose.addActionListener(e -> {
			JTable table = roomsPanel.getTable();
			if (!table.getSelectionModel().isSelectionEmpty()) {
				RoomTableModel tableModel = roomsPanel.getTableModel();
				Room room = tableModel.getObj(table.getSelectedRow());
				selectedRoom = room;
				this.dispose();
			}
		});
	}

}
