package gui.windows;


import db.DataAccessException;
import gui.JButtonPrimary;
import gui.panels.CRUDRooms;
import gui.panels.CRUDTenants;
import gui.panels.tablemodels.RoomTableModel;
import gui.panels.tablemodels.TenantTableModel;
import model.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChooseRoom extends JDialog {

    private Room selectedObject = null;
    private CRUDRooms CRUDPanel;

    private static final long serialVersionUID = 2968937672159813565L;
    private final JPanel contentPane;
    private JButtonPrimary btnChoose;


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
                RoomTableModel tableModel = CRUDPanel.getTableModel();
                Room object = tableModel.getObj(table.getSelectedRow());
                selectedObject = object;
                this.dispose();
            }
        });
    }

}

