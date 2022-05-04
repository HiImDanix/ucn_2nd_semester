package gui.windows;


import db.DataAccessException;
import gui.JButtonPrimary;
import gui.Messages;
import gui.panels.CRUDRooms;
import gui.panels.tablemodels.MyAbstractTableModel;
import gui.panels.tablemodels.RoomTableModel;
import model.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Predicate;

public class ChooseRoom extends JDialog {

    private Room selectedObject = null;
    private CRUDRooms CRUDPanel;

    private static final long serialVersionUID = 2968937672159813565L;
    private final JPanel contentPane;
    private JButtonPrimary btnChoose;

    private Predicate<Room> filter = null;
    private String excludeMessage = "Cannot choose this room";


    public ChooseRoom(Predicate<Room> exclude, String excludeMessage) throws DataAccessException {
        this();
        this.filter = exclude;
        this.excludeMessage = excludeMessage;
    }

    /**
     * Create the dialog.
     */
    public ChooseRoom() throws DataAccessException {
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

        CRUDPanel = new CRUDRooms();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        getContentPane().add(CRUDPanel, gbc_panel);

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

    public Room getSelectedObject() {
        return selectedObject;
    }

    /*
     * *******************************************************
     * *******************  EVENT HANDLERS *******************
     * *******************************************************
     */
    private void addEventHandlers() {
        CRUDPanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            JTable table = CRUDPanel.getTable();
            if (table.getSelectionModel().isSelectionEmpty()) {
                btnChoose.setEnabled(false);
            } else {
                btnChoose.setEnabled(true);
            }

        });

        // Choose button
        btnChoose.addActionListener(e -> {
            JTable table = CRUDPanel.getTable();
            if (!table.getSelectionModel().isSelectionEmpty()) {
                MyAbstractTableModel tableModel = CRUDPanel.getTableModel();
                Room object = (Room) tableModel.getObj(table.getSelectedRow());
                if (filter == null || !filter.test(object)) {
                    selectedObject = object;
                    this.dispose();
                } else {
                    Messages.error(excludeMessage);
                }
//
//                if (object.isOutOfService() == false) {
//                	selectedObject = object;
//                    this.dispose();
//                }
//                else {
//                	Messages.error(table, "The selected room is out of service!","Error");
//                }
            }
        });
    }

}

