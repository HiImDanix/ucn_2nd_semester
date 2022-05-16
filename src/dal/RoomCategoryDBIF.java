package dal;

import java.sql.ResultSet;
import java.util.List;

import db.DataAccessException;
import model.RoomCategory;

@SuppressWarnings("unused")
public interface RoomCategoryDBIF {

	/**
	 * Get tenant by id
	 * @param id
	 * @return tenant found
	 * @throws DataAccessException
	 */
	RoomCategory getById(int id) throws DataAccessException;
	
	/**
	 * Add a new tenant
	 * @param t
	 * @return tenant id
	 * @throws DataAccessException
	 */
	int add(RoomCategory rc) throws DataAccessException;
	
	/**
	 * Update tenant
	 * @param t
	 * @throws DataAccessException
	 */
	void update(RoomCategory rc) throws DataAccessException;
	
	/**
	 * Delete tenant
	 * @param t
	 * @throws DataAccessException
	 */
	void delete(RoomCategory rc) throws DataAccessException; 
	
	List<RoomCategory> getAll() throws DataAccessException;
}
