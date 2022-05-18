package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.RoomCategoryController;
import controller.RoomController;
import controller.TenantController;
import db.DataAccessException;
import model.Contract;
import model.Room;
import model.RoomCategory;
import model.StudyProof;
import model.Tenant;

/*
 * Andras Varsanyi, Ondrej Dobis
 */
class TenantControllerTest {
	
	private TenantController tCtr;
	private RoomCategoryController rcCtr;
	//private List<Contract> contracts;
	private StudyProof studyP;
	private RoomController rCtr;
	ArrayList<Tenant> tenants;

//	@BeforeEach
	void setUp() throws Exception {
		tCtr = new TenantController();
		rcCtr = new RoomCategoryController();
		rCtr = new RoomController();
		tenants = new ArrayList<>();
		// StudyProof studyP = new StudyProof(1, "path/path/path", LocalDate.parse("2022-10-10"));
		/* if (tCtr.getTenantById(1) != null) {
			tCtr.removeTeanant(tCtr.getTenantById(1));
		} */
	}

//	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
	void testAddTenant() throws DataAccessException {
		assertNotNull(tCtr.addTenant("testName", "name1", "email@email.com", "+4552525252", studyP));
		//assertNull(tCtr.addTenant("testName", "name1", "email@email.com", "+455252", studyP)); //phone number length?
		//assertNull(tCtr.addTenant("testName", "name1", "email@email.com", "+455221212121212152", studyP)); //phone number length?
		/* tCtr.removeTenant(tCtr.getTenantById(1)); */
	}
	
//	@Test
	void testGetTenantById() throws DataAccessException {
		assertNull(tCtr.getTenantById(1)); // Empty test
		assertEquals(tCtr.addTenant("testName", "name1", "email1@email.com", "+4552525252", studyP), tCtr.getTenantById(1)); // Tenant added = tenant found
		/* tCtr.removeTenant(tCtr.getTenantById(1)); */
	}

//	@Test
	void testGetAllTenants() throws DataAccessException {
		assertNull(tCtr.getAllTenants()); // empty test
		tCtr.addTenant("testName", "name1", "email1@email.com", "+4552525252", studyP);
		tCtr.addTenant("testName", "name2", "email1@email.com", "+4552522222", studyP);
		assertEquals(2, tCtr.getAllTenants()); // 2 created = 2 found
		/* tCtr.removeTenant(tCtr.getTenantById(1)); // Clean up
		tCtr.removeTenant(tCtr.getTenantById(2)); */ // Clean up
	}

//	@Test
	void testGetTenantsByContractID() throws DataAccessException { // Problem - lists don't match - ArrayList / List<>
		RoomCategory cat = rcCtr.getRoomCategoryById(1); // create room category to create a room
		tenants.add(tCtr.getTenantById(1)); // Make a list with the desired tenant, to compare with result
		Room room = new Room(0, cat, false, new ArrayList<>()); // create a room to create contract
		tCtr.addTenant("testName", "name1", "email1@email.com", "+4552525252", studyP); // create tenant to assign to contract
		tCtr.getTenantById(1).addContract(new Contract(-1, true, LocalDate.parse("2022-01-01"), room, new ArrayList<>(), 
				Collections.emptyList(), Collections.emptyList(), null)); // Assign tenant & room to contract
		assertEquals(tenants, tCtr.getTenantsByContractID(1)); // perform test
		// rcCtr.removeRoomCategory(rcCtr.getRoomCategoryById(1))  // remove category
		rCtr.deleteRoom(room); // delete room
		// tCtr.removeTenant(tCtr.getTenantById(1)); // delete tenant
	}

//	@Test
	void testUpdateTenant() throws DataAccessException {
		tCtr.addTenant("testName", "name1", "email@email.com", "+4552525252", studyP);
		Tenant tenant = tCtr.getTenantById(1);
		tCtr.updateTenant(tenant, "New2", "Name2", "mail2@mail2.com", "+2222222222", studyP);
		assertEquals("New2", tenant.getFirstName());
		assertEquals("Name2", tenant.getLastName());
		assertEquals("mail2@mail2.com", tenant.getEmail());
		assertEquals("+2222222222", tenant.getPhone());
		// tCtr.removeTenant(tCtr.getTenantById(1)); // Clean up
    }

}
