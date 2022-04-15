/**
 * 
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.RoomCategoryController;
import controller.RoomController;
import controller.TenantController;
import db.DataAccessException;
import model.Room;
import model.Tenant;

/**
 * @author Andriis
 *
 */
public class TenantDB extends DAO<Tenant> implements TenantDBIF {
	
	private static final String tableName = "tenant";
	private static final String[] fields = new String[] { "fname" , "lname" , "mname" , "phone" , "room_id" , "email" };
	
	public TenantDB() {
		super(tableName, fields);
	}

	@Override
	protected void setValues(PreparedStatement stmt, Tenant obj) throws SQLException {
		//
	}

	@Override
	protected int getId(Tenant obj) {
		return obj.getId();
	}

	@Override
	protected Tenant buildDomainObject(ResultSet resultSet) throws SQLException, DataAccessException {
		Tenant tenant = new Tenant(resultSet.getInt(fields[0]),
									resultSet.getString(fields[1]),
									resultSet.getString(fields[2]),
									resultSet.getString(fields[3]),
									resultSet.getString(fields[4]),
									new RoomController().getRoomById(resultSet.getInt(fields[5]))
									);
		return tenant;
	}
	

	
}
