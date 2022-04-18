package dal;

import controller.RoomController;
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
        START_DATE,
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
                START_DATE.fieldName(),
                ROOM_ID.fieldName()
        });
    }

    @Override
    public void setValues(PreparedStatement stmt, Contract obj) throws SQLException {
        stmt.setBoolean(1, obj.isIncludeInternet());
        stmt.setDate(2, java.sql.Date.valueOf(obj.getStartDate()));
        stmt.setInt(3, obj.getRoom().getId());
    }

    @Override
    public int getId(Contract obj) {
        return obj.getID();
    }

    @Override
    public Contract buildDomainObject(ResultSet rs) throws DataAccessException {
        System.out.println(new TenantController().getTenantsByContractID(1));

        try {
            return new Contract(
                    rs.getInt(ID.fieldName()),
                    rs.getBoolean(INCLUDE_INTERNET.fieldName()),
                    rs.getDate(START_DATE.fieldName()).toLocalDate(),
                    new RoomController().getRoomById(rs.getInt(ROOM_ID.fieldName())),
                    new TenantController().getTenantsByContractID(rs.getInt(ID.fieldName())),
                    // TODO: STUBS
                    Collections.emptyList(),
                    Collections.emptyList(),
                    null
            );
        } catch (SQLException e) {
            throw new DataAccessException("Error building Contract object from ResultSet", e);
        }

    }
}
