package test.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//import java.util.List;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ContractController;
import controller.DBController;
import controller.EmployeeController;
import controller.RoomCategoryController;
import controller.RoomController;
import controller.SessionController;
import controller.TenantController;
import db.DBConnection;
import db.DataAccessException;
import gui.Common;
import model.Contract;
import model.Room;
import model.RoomCategory;
import model.StudyProof;
import model.Tenant;
import model.modelIF;
import test.TestIF;

class TenantController_TEST implements TestIF<Tenant> {
	
	private static TenantController tCtr;
	private static RoomCategoryController rcCtr;
	private static ContractController cCtr;
	//private List<Contract> contracts;
	private StudyProof studyP;
	private static RoomController rCtr;
	private DBConnection dbCon;
	private static DBController dbCtrl;
	private StudyProof studyP2;

	@BeforeAll
	static void setUpBeforeClass() {
		tCtr = new TenantController();
		rcCtr = new RoomCategoryController();
		cCtr = new ContractController();
		rCtr = new RoomController();
		dbCtrl = new DBController();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		dbCtrl.clear();
		dbCon = DBConnection.getInstance();
		studyP2 = new StudyProof(1, "path/path/path", LocalDate.parse("2023-10-10"));
	}

	@AfterAll
	static void tearDownAfterClass() throws DataAccessException, IOException {
		dbCtrl.clear();
		dbCtrl.addDemoData();
		DBConnection.getInstance().closeConnection();
	}
	

	@Test
	void testAddTenant() throws DataAccessException {
		assertNotNull(tCtr.addTenant("testName", "name1", "email@email.com", "+4552525252", studyP));
		//assertNull(tCtr.addTenant("testName", "name1", "email@email.com", "+455252", studyP)); //phone number length?
		//assertNull(tCtr.addTenant("testName", "name1", "email@email.com", "+455221212121212152", studyP)); //phone number length?
		/* tCtr.removeTenant(tCtr.getTenantById(1)); */
	}
	
	@Test
	void testGetTenantById() throws DataAccessException {
		//Arrange
		Tenant addedTenant = new Tenant(-1, "testName", "name1", "email1@email.com", "+4552525252", studyP, null);
		Tenant retrievedTenant = null;
		
		//Act
		addedTenant = tCtr.addTenant(addedTenant);
		retrievedTenant = tCtr.getTenantById(1);
		
		//Assert
		assertTrue(addedTenant.getID() != -1);
		assertTrue(compareObjects(addedTenant, retrievedTenant)); // Tenant added = tenant found
		/* tCtr.removeTenant(tCtr.getTenantById(1)); */
	}

	@Test
	void testGetAllTenants() throws DataAccessException {
		assertTrue(tCtr.getAllTenants().isEmpty()); // empty test
		//Arrange
		Tenant tenant1 = new Tenant(-1, "testName", "name1", "email1@email.com", "+4552525252", studyP, null);
		Tenant tenant2 = new Tenant(-1, "testName2", "name2", "email2@email.com", "+4552525252", studyP, null);
		//Act
		tCtr.addTenant(tenant1);
		tCtr.addTenant(tenant2);
		
		//Assert
		assertEquals(2, tCtr.getAllTenants().size()); // 2 created = 2 found
		assertTrue(compareObjects(tenant1, tCtr.getAllTenants().get(0)));
		assertTrue(compareObjects(tenant2, tCtr.getAllTenants().get(1)));
		/* tCtr.removeTenant(tCtr.getTenantById(1)); // Clean up
		tCtr.removeTenant(tCtr.getTenantById(2)); */ // Clean up
	}

	@Test
	void testGetTenantsByContractID() throws DataAccessException { // Problem - lists don't match - ArrayList / List<>
		//Arrange
		RoomCategory cat = rcCtr.addRoomCategory("testCategory", "testDescription", BigDecimal.valueOf(3000), BigDecimal.valueOf(0), BigDecimal.valueOf(300), 1, 30); // add room category to create a room
		Room room = rCtr.addRoom(cat, false); // create room to assign to contract
		Tenant addedTenant = tCtr.addTenant("testName", "name1", "email1@email.com", "+4552525252", studyP); // create tenant to assign to contract
		
		//Act
		cCtr.addContract(LocalDate.parse("2023-01-01"), Arrays.asList(addedTenant), room, true);
		
		//Assert
		assertTrue(tCtr.getTenantsByContractID(0).size() == 1); // perform test
		System.out.println(addedTenant.getLastName());
		System.out.println(tCtr.getTenantsByContractID(0).get(0).getLastName());
		assertTrue(compareObjects(addedTenant, tCtr.getTenantsByContractID(0).get(0)));
	}
	

	@Test
	void testUpdateTenant() throws DataAccessException {
		Tenant tenant = tCtr.addTenant("testName", "name1", "email@email.com", "+4552525252", studyP);
		tCtr.updateTenant(tenant, "New2", "Name2", "mail2@mail2.com", "+2222222222", studyP);
		assertEquals("New2", tenant.getFirstName());
		assertEquals("Name2", tenant.getLastName());
		assertEquals("mail2@mail2.com", tenant.getEmail());
		assertEquals("+2222222222", tenant.getPhone());
		// tCtr.removeTenant(tCtr.getTenantById(1)); // Clean up
    }

	@Override
	public boolean compareObjects(Tenant obj, Tenant obj2) {
		return
				obj.getID() == obj2.getID() &&
				obj.getFirstName().equals(obj2.getFirstName()) &&
				obj.getLastName().equals(obj2.getLastName()) &&
				obj.getEmail().equals(obj2.getEmail()) &&
				obj.getPhone().equals(obj2.getPhone());
	}

}