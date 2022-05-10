package dal;

import db.DBConnection;
import db.DataAccessException;
import model.RoomCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.RoomCategoryController;

import static dal.RoomCategoryDB.Columns.*;

@SuppressWarnings("unused")
public class RoomCategoryDB extends DAO<RoomCategory> implements RoomCategoryDBIF {
    public static final String tableName = "room_category";
    public enum Columns {
        ID,
        NAME,
        DESCRIPTION,
        PRICEPERMONTH,
        PRICEPERMONTHFORINTERNET, 
        PRICEPERMONTHFOREXTRATENANT, 
        MAXTENANTS, 
        FURNITURE,
        LEAVENOTICEDAYS;

        public String fieldName() {
            return this.name().toLowerCase();
        }
    }

    public RoomCategoryDB() {
        // Passing table name and settable column names
        super(tableName, new String[] {
        		Columns.NAME.fieldName(),
        		Columns.DESCRIPTION.fieldName(),
        		Columns.PRICEPERMONTH.fieldName(),
        		Columns.PRICEPERMONTHFORINTERNET.fieldName(),
        		Columns.PRICEPERMONTHFOREXTRATENANT.fieldName(),
        		Columns.MAXTENANTS.fieldName(),
        		Columns.FURNITURE.fieldName(),
        		Columns.LEAVENOTICEDAYS.fieldName()
        });
    }
    
    @Override
    public int add(RoomCategory roomCategory) throws DataAccessException {
        int id = -1;
        try {
            // start transaction
            DBConnection.getInstance().startTransaction();

            // add room category to database, return auto-generated id
            id = (super.add(roomCategory));
            
            // Have to set the id here, because tenantContractController will need it
            roomCategory.setId(id);

            // commit transaction
            DBConnection.getInstance().commitTransaction();
        } catch (DataAccessException e) {
            // Rollback transaction
            DBConnection.getInstance().rollbackTransaction();
            throw new DataAccessException("Error adding Contract to DB", e);
        }
        return id;
    }

    @Override
    public void setValues(PreparedStatement stmt, RoomCategory obj) throws SQLException {
        stmt.setString(1, obj.getName());
        stmt.setString(2, obj.getDescription());
        stmt.setBigDecimal(3, obj.getPricePerMonth());
        stmt.setBigDecimal(4, obj.getPricePerMonthForInternet());
        stmt.setBigDecimal(5, obj.getPricePerMonthForExtraTenant());
        stmt.setInt(6, obj.getMaxTenants());
        //stmt.set(7, obj.getFurniture());
        stmt.setInt(8, obj.getLeaveNoticeDays());
    }

    @Override
    public RoomCategory buildDomainObject(ResultSet rs) throws DataAccessException {
        try {
            return new RoomCategory(
                    rs.getInt(ID.fieldName()),
                    rs.getString(NAME.fieldName()),
                    rs.getString(DESCRIPTION.fieldName()),
                    rs.getBigDecimal(PRICEPERMONTH.fieldName()),
                    rs.getBigDecimal(PRICEPERMONTHFORINTERNET.fieldName()),
                    rs.getBigDecimal(PRICEPERMONTHFOREXTRATENANT.fieldName()),
                    rs.getInt(MAXTENANTS.fieldName()),
                    //rs.get (FURNITURE.fieldName()),
                    rs.getInt(LEAVENOTICEDAYS.fieldName())
            );
        } catch (SQLException e) {
            throw new DataAccessException("Error building RoomCategory object from ResultSet", e);
        }

    }

}
