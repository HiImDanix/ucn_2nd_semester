/**
 * 
 */
package controller;

import static org.junit.jupiter.api.Assertions.*;

import controller.DBController;
import org.junit.jupiter.api.*;

import controller.EmployeeController;
import controller.SessionController;
import db.DBConnection;
import db.DataAccessException;
import model.Employee;
import model.modelIF;

import java.io.IOException;

/*
 * Author: Andras Varsanyi, Daniels Kanepe
 */
class SessionControllerTest {
	private static EmployeeController employeeCtrl;
	private static SessionController sessionCtrl;
	private static DBController dbCtrl;
	private DBConnection dbCon;

	@BeforeAll
	static void setUpBeforeClass() {
		sessionCtrl = SessionController.getInstance();
		employeeCtrl = new EmployeeController();
		dbCtrl = new DBController();
	}

	@AfterAll
	static void tearDownAfterClass() throws DataAccessException, IOException {
		dbCtrl.clear();
		dbCtrl.addDemoData();
		DBConnection.getInstance().closeConnection();
	}

	@BeforeEach
	void setUp() throws DataAccessException, IOException {
		dbCtrl.clear();
		dbCon = DBConnection.getInstance();
	}

	@Test
	void testValidLoginInfoShouldLogin() throws DataAccessException {
		//Arrange 
		String email = "example@goodexample.com";
		String password = "password";
		
		//act
		Employee employee = null;
		try {
			employee = employeeCtrl.addEmployee("Test", "Test", email, password);
		} catch (DataAccessException e) {
			fail("Couldn't add employee to db");
		}
		sessionCtrl.authenticate(email, password); // Employee shouldn't be null at this point
		
		//Assert
		assertTrue(sessionCtrl.isLoggedIn()); //exp: true
		assertTrue(compareObjects(employee, sessionCtrl.getLoggedInEmployee())); // same employee
	}

	@Test
	void testInvalidEmailShouldNotLogin() throws DataAccessException {
		//Arrange 
		String email = "example@goodexample.com";
		String password = "password";
		
		//act 
		try {
			employeeCtrl.addEmployee("Test", "Test", email, password);
		} catch (DataAccessException e) {
			fail("Couldn't add employee to db");
		}
		sessionCtrl.authenticate("example@badexample.com", password);
		
		//Assert
		assertFalse(sessionCtrl.isLoggedIn());
		assertNull(sessionCtrl.getLoggedInEmployee()); // logged in employee should be null
	}

	@Test
	void testInvalidPasswordShouldNotLogin() throws DataAccessException {
		//Arrange
		String email = "example@goodexample.com";
		String password = "password";

		//act
		try {
			employeeCtrl.addEmployee("Test", "Test", email, password);
		} catch (DataAccessException e) {
			fail("Couldn't add employee to db");
		}
		sessionCtrl.authenticate(email, "wrongpass");

		//Assert
		assertFalse(sessionCtrl.isLoggedIn());
		assertNull(sessionCtrl.getLoggedInEmployee()); // logged in employee should be null
	}

	public boolean compareObjects(Employee obj, Employee obj2) {
		return	obj.getID() == obj2.getID() &&
				obj.getFirstName().equals(obj2.getFirstName()) &&
				obj.getLastName().equals(obj2.getLastName()) &&
				obj.getEmail().equals(obj2.getEmail()) &&
				obj.getPasswordHash().equals(obj2.getPasswordHash());
	}
}
