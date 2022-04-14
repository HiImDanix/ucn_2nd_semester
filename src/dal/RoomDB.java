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
    public static final String[] columnNames = new String[]
            {"id", "is_out_of_service", "room_category_id"}; // order matters (for down the code)

    public RoomDB() {
        super(tableName, columnNames);
    }

    @Override
    public void setValues(PreparedStatement stmt, Room obj) throws SQLException {
        stmt.setBoolean(1, obj.isOutOfService());
        stmt.setInt(2, obj.getRoomCategory().getId());
    }

    @Override
    public void setValues(PreparedStatement stmt, int id) throws SQLException {
        stmt.setInt(1, id);
    }

    @Override
    public Room buildDomainObject(ResultSet rs) throws SQLException, DataAccessException {
        Room room = new Room(
                rs.getInt(columnNames[0]),
                new RoomCategoryController().getRoomCategoryById(rs.getInt(columnNames[2])),
                rs.getBoolean(columnNames[1])
        );
        return room;

    }

    public void delete(Room room) throws DataAccessException {
        super.delete(room.getId());
    }
}
