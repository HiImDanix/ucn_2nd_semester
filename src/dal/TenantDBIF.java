package dal;

import db.DataAccessException;
import model.Tenant;

import java.util.List;

public interface TenantDBIF {


	/**
	 * Get tenant by id
	 * @param id - id of tenant
	 * @return Tenant
	 * @throws DataAccessException - if there is an error getting tenant from db
	 */
	Tenant getById(int id) throws DataAccessException;
	
	//Tenant getByName(String name) throws DataAccessException;
	
	/**
	 * Add a new tenant
	 * @param t - Tenant object
	 * @return tenant id
	 * @throws DataAccessException - if there is an error adding tenant to db
	 */
	int add(Tenant t) throws DataAccessException;
	
	/**
	 * Update tenant
	 * @param t - Tenant object
	 * @throws DataAccessException - if there is an error updating tenant in db
	 */
	void update(Tenant t) throws DataAccessException;
	
	/**
	 * Delete tenant
	 * @param t - Tenant object
	 * @throws DataAccessException - if there is an error deleting tenant from db
	 */
	void delete(Tenant t) throws DataAccessException;

	/**
	 * Get all tenants
	 * @return List of Tenant objects
	 * @throws DataAccessException - if there is an error getting tenants from db
	 */
	List<Tenant> getAll() throws DataAccessException;
}
