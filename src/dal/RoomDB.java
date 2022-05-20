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
                ID.fieldName(),
                IS_OUT_OF_SERVICE.fieldName(),
                ROOM_CATEGORY_ID.fieldName()});
    }

    @Override
    public void setValues(PreparedStatement stmt, Room obj) throws SQLException {
        stmt.setBoolean(1, obj.isOutOfService());
        stmt.setInt(2, obj.getRoomCategory().getID());
    }

    @Override
    public Class<Room> getDomainObjectClass() {
        return Room.class;
    }

    @Override
    protected Room buildDomainObjectWithoutAssociations(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt(ID.fieldName()),
                null,
                rs.getBoolean(IS_OUT_OF_SERVICE.fieldName()),
                new ArrayList<>()
        );
    }

    @Override
    protected void setAssociatedObjects(Room room, ResultSet rs) throws DataAccessException, SQLException {
        // set contracts
        for (int contractID : new ContractController().getAllContractIDsByRoomID(room.getID())) {
            room.addContract(new ContractController().getContractById(contractID));
            
        }
        // set room category
        room.setRoomCategory(new RoomCategoryController().getRoomCategoryById(rs.getInt(ROOM_CATEGORY_ID.fieldName())));
    }
}
