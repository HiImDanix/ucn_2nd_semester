package gui.panels.tablemodels;

import db.DataAccessException;

@FunctionalInterface
public interface DataSupplier<R> {
    R get() throws DataAccessException;
}