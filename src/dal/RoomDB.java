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
    public static final String[] settableColumnNames = new String[]
            {"is_out_of_service", "room_category_id"}; // except id

    public RoomDB() {
        super(tableName, settableColumnNames);
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
                rs.getInt("id"),
                new RoomCategoryController().getRoomCategoryById(rs.getInt(settableColumnNames[1])),
                rs.getBoolean(settableColumnNames[0])
        );
        return room;

    }
}
