package dal;

import controller.RoomController;
import controller.TenantContractController;
import controller.TenantController;
import db.DBConnection;
import db.DataAccessException;
import model.Contract;
import model.Tenant;

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
                INCLUDE_INTERNET.fieldName(),
                START_DATE.fieldName(),
                ROOM_ID.fieldName()
        });
    }

    @Override
    public int add(Contract contract) throws DataAccessException {
        int id = -1;
        try {
            // start transaction
            DBConnection.getInstance().startTransaction();

            // add contract to db, return auto-generated id
            id = (super.add(contract));
            // Have to set the id here, because tenantContractController will need it
            contract.setID(id);

            // link tenant-contract in db
            for (Tenant tenant : contract.getTenants()) {
                new TenantContractController().add(tenant, contract);
            }

            // commit transaction
            DBConnection.getInstance().commitTransaction();
        } catch (DataAccessException e) {
            // Rollback transaction
            DBConnection.getInstance().rollbackTransaction();
            throw new DataAccessException("Error adding Contract to DB", e);
        }

        // link tenant-contract in domain
        for (Tenant tenant : contract.getTenants()) {
            tenant.addContract(contract);
        }
        return id;
    }

    @Override
    public void setValues(PreparedStatement stmt, Contract obj) throws SQLException {
        stmt.setBoolean(1, obj.isIncludeInternet());
        stmt.setDate(2, java.sql.Date.valueOf(obj.getStartDate()));
        stmt.setInt(3, obj.getRoom().getID());
    }

    @Override
    public int getId(Contract obj) {
        return obj.getID();
    }

    @Override
    public Contract buildDomainObject(ResultSet rs) throws DataAccessException {
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
