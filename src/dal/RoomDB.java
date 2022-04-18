package dal;

import controller.RoomCategoryController;
import db.DataAccessException;
import model.Employee;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dal.RoomDB.Columns.*;

public class RoomDB extends DAO<Room> implements RoomDBIF {
    public static final String tableName = "room";
    public enum Columns {
        ID,
        IS_OUT_OF_SERVICE,
        ROOM_CATEGORY_ID;

        public String fieldName() {
            return this.name().toLowerCase();
        }
    }

    public RoomDB() {
        // Passing table name and settable column names
        super(tableName, new String[] {
                IS_OUT_OF_SERVICE.fieldName(),
                ROOM_CATEGORY_ID.fieldName()});
    }

    @Override
    public void setValues(PreparedStatement stmt, Room obj) throws SQLException {
        stmt.setBoolean(1, obj.isOutOfService());
        stmt.setInt(2, obj.getRoomCategory().getId());
    }

    @Override
    public int getId(Room obj) {
        return obj.getId();
    }

    @Override
    public Room buildDomainObject(ResultSet rs) throws DataAccessException {
        try {
            return new Room(
                    rs.getInt(ID.fieldName()),
                    new RoomCategoryController().getRoomCategoryById(rs.getInt(ROOM_CATEGORY_ID.fieldName())),
                    rs.getBoolean(IS_OUT_OF_SERVICE.fieldName())
            );
            } catch (SQLException e) {
                throw new DataAccessException("Error building Room object", e);
        }
    }
}
