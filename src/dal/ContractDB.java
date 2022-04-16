package dal;

import controller.RoomCategoryController;
import controller.RoomController;
import controller.TenantContractController;
import controller.TenantController;
import db.DataAccessException;
import model.Contract;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import static dal.ContractDB.Columns.*;

public class ContractDB extends DAO<Contract> implements ContractDBIF {
    public static final String tableName = "contract";
    public enum Columns {
        ID,
        INCLUDE_INTERNET,
        START_DATE_TIME,
        ROOM_ID;

        public String fieldName() {
            return this.name().toLowerCase();
        }
    }

    public ContractDB() {
        // Passing table name and settable column names
        super(tableName, new String[] {
                ID.fieldName(),
                INCLUDE_INTERNET.fieldName(),
                START_DATE_TIME.fieldName(),
                ROOM_ID.fieldName()
        });
    }

    @Override
    public void setValues(PreparedStatement stmt, Contract obj) throws SQLException {
        stmt.setBoolean(1, obj.isIncludeInternet());
        stmt.setTimestamp(2, java.sql.Timestamp.valueOf(obj.getStartDatetime()));
        stmt.setInt(3, obj.getRoom().getId());
    }

    @Override
    public int getId(Contract obj) {
        return obj.getID();
    }

    @Override
    public Contract buildDomainObject(ResultSet rs) throws SQLException, DataAccessException {
        Contract contract = new Contract(
                rs.getInt(ID.fieldName()),
                rs.getBoolean(INCLUDE_INTERNET.fieldName()),
                rs.getTimestamp(START_DATE_TIME.fieldName()).toLocalDateTime(),
                new RoomController().getRoomById(rs.getInt(ROOM_ID.fieldName())),
                new TenantContractController().getTenantsByContractID(rs.getInt(ID.fieldName())),
                // TODO: STUBS
                Collections.emptyList(),
                Collections.emptyList(),
                null
        );




//        Room room = new Room(
//                rs.getInt(ID.fieldName()),
//                new RoomCategoryController().getRoomCategoryById(rs.getInt(ROOM_CATEGORY_ID.fieldName())),
//                rs.getBoolean(IS_OUT_OF_SERVICE.fieldName())
//        );
        return contract;

    }
}
