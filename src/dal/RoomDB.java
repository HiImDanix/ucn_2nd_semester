package dal;

import controller.RoomCategoryController;
import db.DataAccessException;
import model.Employee;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDB extends DAO<Room> implements RoomDBIF {
    public static final String tableName = "room";
    public enum Columns {
        ID,
        IS_OUT_OF_SERVICE,
        ROOM_CATEGORY_ID;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public RoomDB() {
        // Passing table name and settable column names
        super(tableName, new String[] {
                Columns.IS_OUT_OF_SERVICE.toString(),
                Columns.ROOM_CATEGORY_ID.toString()});
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
    public Room buildDomainObject(ResultSet rs) throws SQLException, DataAccessException {
        Room room = new Room(
                rs.getInt(Columns.ID.toString()),
                new RoomCategoryController().getRoomCategoryById(rs.getInt(Columns.ROOM_CATEGORY_ID.toString())),
                rs.getBoolean(Columns.IS_OUT_OF_SERVICE.toString())
        );
        return room;

    }
}
