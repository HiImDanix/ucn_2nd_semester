package gui.panels.tablemodels;

import db.DataAccessException;

import javax.swing.table.AbstractTableModel;

public abstract class MyAbstractTableModel<O> extends AbstractTableModel {

    public abstract O getObj(int row);
    public abstract void add(O obj);
    public abstract void remove(int row);
    public abstract void refreshData() throws DataAccessException;
}
