/**
 * 
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DataAccessException;
import model.Contract;
import model.Tenant;

public class TenantDB extends DAO<Tenant> implements TenantDBIF {
	
	private static final String tableName = "tenant";
	public enum Columns {
		ID,
		first_name,
		last_name,
		email,
		phone,
		study_proof_id;

		public String fieldName() {
			return this.name().toLowerCase();
		}
	}
	
	public TenantDB() {
		super(tableName, new String[] {
				Columns.first_name.fieldName(),
				Columns.last_name.fieldName(),
				Columns.email.fieldName(),
				Columns.phone.fieldName(),
				Columns.study_proof_id.fieldName()
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
	protected int getId(Tenant obj) {
		return obj.getID();
	}

	@Override
	public Tenant buildDomainObject(ResultSet resultSet) throws DataAccessException {
		try {
			return new Tenant(
					resultSet.getInt(Columns.ID.fieldName()),
					resultSet.getString(Columns.first_name.fieldName()),
					resultSet.getString(Columns.last_name.fieldName()),
					resultSet.getString(Columns.email.fieldName()),
					resultSet.getString(Columns.phone.fieldName()),
					// TODO: stubbed for now
					null,
					new ArrayList<Contract>()
			);
		} catch (SQLException e) {
			throw new DataAccessException("Error building Tenant object from ResultSet", e);
		}
	}



	
}
