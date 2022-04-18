package dal;

import java.sql.ResultSet;
import java.util.List;

import db.DataAccessException;
import model.Tenant;

public interface TenantDBIF {

	/**
	 * Get tenant by id
	 * @param id
	 * @return tenant found
	 * @throws DataAccessException
	 */
	Tenant getById(int id) throws DataAccessException;
	
	//Tenant getByName(String name) throws DataAccessException;
	
	/**
	 * Add a new tenant
	 * @param t
	 * @return tenant id
	 * @throws DataAccessException
	 */
	int add(Tenant t) throws DataAccessException;
	
	/**
	 * Update tenant
	 * @param t
	 * @throws DataAccessException
	 */
	void update(Tenant t) throws DataAccessException;
	
	/**
	 * Delete tenant
	 * @param t
	 * @throws DataAccessException
	 */
	void delete(Tenant t) throws DataAccessException; 
	
	List<Tenant> getAll() throws DataAccessException;

	List<Tenant> buildDomainObjects(ResultSet rs) throws DataAccessException;
}
