package dal;

import controller.ContractController;
import controller.RoomCategoryController;
import db.DataAccessException;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        stmt.setInt(2, obj.getRoomCategory().getID());
    }

    @Override
    public Room buildDomainObject(ResultSet rs) throws DataAccessException {
        try {
            int roomID = rs.getInt(ID.fieldName());

            if (Cache.contains(Room.class, roomID)) {
                return (Room) Cache.get(Room.class, roomID);
            }

            Room room = new Room(
                    roomID,
                    new RoomCategoryController().getRoomCategoryById(rs.getInt(ROOM_CATEGORY_ID.fieldName())),
                    rs.getBoolean(IS_OUT_OF_SERVICE.fieldName()),
                    new ArrayList<>()
            );

            // put room in cache
            Cache.put(room);

            // Add dependency: contracts
            for (int contractID : new ContractController().getAllContractIDsByRoomID(roomID)) {
                room.addContract(new ContractController().getContractById(contractID));
            }

            return room;

            } catch (SQLException e) {
                throw new DataAccessException("Error building Room object", e);
        }
    }
}
