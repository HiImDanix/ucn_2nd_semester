/**
 * 
 */
package dal;

import controller.ContractController;
import controller.TenantContractController;
import db.DataAccessException;
import model.Contract;
import model.Tenant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TenantDB extends DAO<Tenant> implements TenantDBIF {
	
	private static final String tableName = "tenant";
	public enum Columns {
		ID,
		FIRST_NAME,
		LAST_NAME,
		EMAIL,
		PHONE,
		STUDY_PROOF_ID;

		public String fieldName() {
			return this.name().toLowerCase();
		}
	}
	
	public TenantDB() {
		super(tableName, new String[] {
				Columns.ID.fieldName(),
				Columns.FIRST_NAME.fieldName(),
				Columns.LAST_NAME.fieldName(),
				Columns.EMAIL.fieldName(),
				Columns.PHONE.fieldName(),
				Columns.STUDY_PROOF_ID.fieldName()
		});
	}

	@Override
	protected void setValues(PreparedStatement stmt, Tenant obj) throws SQLException {
		stmt.setString(1, obj.getFirstName());
		stmt.setString(2, obj.getLastName());
		stmt.setString(3, obj.getEmail());
		stmt.setString(4, obj.getPhone());
		if (obj.getStudyProof() != null) {
			stmt.setInt(5, obj.getStudyProof().getID());
		} else {
			stmt.setNull(5, java.sql.Types.NULL);
		}
	}

	@Override
	protected Class<Tenant> getDomainObjectClass() {
		return Tenant.class;
	}

	@Override
	protected Tenant buildDomainObjectWithoutAssociations(ResultSet rs) throws SQLException {
		return new Tenant(
				rs.getInt(Columns.ID.fieldName()),
				rs.getString(Columns.FIRST_NAME.fieldName()),
				rs.getString(Columns.LAST_NAME.fieldName()),
				rs.getString(Columns.EMAIL.fieldName()),
				rs.getString(Columns.PHONE.fieldName()),
				null, 	// TODO: stubbed for now
				new ArrayList<>()
		);
	}

	@Override
	protected void setAssociatedObjects(Tenant tenant, ResultSet rs) throws DataAccessException {
		// contracts
		for (int contractID: new TenantContractController().getContractIDsByTenantID(tenant.getID())) {
			Contract contract = new ContractController().getContractById(contractID);
			tenant.addContract(contract);
		}
	}
}
