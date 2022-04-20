package gui.windows;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import db.DataAccessException;
import gui.JButtonPrimary;
import gui.panels.CRUDTenants;
import gui.panels.tablemodels.TenantTableModel;
import model.Tenant;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class ChooseTenant extends JDialog {

    private List<Tenant> selectedObjects = new ArrayList<>();
    private CRUDTenants CRUDPanel;

    private static final long serialVersionUID = 2968937672159813565L;
    private final JPanel contentPane;
    private JButtonPrimary btnChoose;

    /*
     * Choose multiple tenants window
     */
    public ChooseTenant(boolean multiple) throws DataAccessException  {
        this();
        if (multiple) {
            this.CRUDPanel.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
    }

    /**
     * Choose a tenant window
     */
    public ChooseTenant() throws DataAccessException {
        this.setTitle("Choose a tenant...");
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

        CRUDPanel = new CRUDTenants();
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

    public Tenant getSelectedObject() {
        if (this.selectedObjects.size() > 0) {
            return this.selectedObjects.get(0);
        }
        return null;
    }

    public List<Tenant> getSelectedObjects() {
        return this.selectedObjects;
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
                TenantTableModel tableModel = CRUDPanel.getTableModel();
                Tenant object = tableModel.getObj(table.getSelectedRow());
                for (int index: table.getSelectedRows()) {
                    this.selectedObjects.add(tableModel.getObj(index));
                }
                this.dispose();
            }
        });
    }

}

