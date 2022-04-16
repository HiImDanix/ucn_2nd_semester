/**
 * 
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import controller.RoomCategoryController;
import controller.RoomController;
import controller.TenantController;
import db.DataAccessException;
import model.Contract;
import model.Room;
import model.StudyProof;
import model.Tenant;

/**
 * @author Andriis
 *
 */
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
		stmt.setInt(5, obj.getStudyProof().getId());
	}

	@Override
	protected int getId(Tenant obj) {
		return obj.getId();
	}

	@Override
	protected Tenant buildDomainObject(ResultSet resultSet) throws SQLException, DataAccessException {
		return new Tenant(
				resultSet.getInt(Columns.ID.fieldName()),
				resultSet.getString(Columns.first_name.fieldName()),
				resultSet.getString(Columns.last_name.fieldName()),
				resultSet.getString(Columns.email.fieldName()),
				resultSet.getString(Columns.phone.fieldName()),
				new StudyProof(1, "", LocalDateTime.now()),
				new ArrayList<Contract>()
		);


	}
	

	
}
