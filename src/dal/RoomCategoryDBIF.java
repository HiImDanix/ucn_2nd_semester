package dal;

import java.sql.ResultSet;
import java.util.List;

import db.DataAccessException;
import model.RoomCategory;

@SuppressWarnings("unused")
public interface RoomCategoryDBIF {

	RoomCategory getById(int id) throws DataAccessException;

	int add(RoomCategory rc) throws DataAccessException;

	void update(RoomCategory rc) throws DataAccessException;

	void delete(RoomCategory rc) throws DataAccessException; 
	
	List<RoomCategory> getAll() throws DataAccessException;
}
