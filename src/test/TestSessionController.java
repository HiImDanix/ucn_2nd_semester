/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.EmployeeController;
import controller.SessionController;
import db.DBConnection;
import db.DataAccessException;
import model.Employee;

/**
 * @author Andriis
 *
 */
class TestSessionController {
	
	private EmployeeController employeeCtrl;
	private SessionController sessionCtrl;
	private DBConnection dbCon;
	private Employee testEmployee;

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		DBConnection.getInstance().closeConnection();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		sessionCtrl = SessionController.getInstance();
		employeeCtrl = new EmployeeController();
		dbCon = DBConnection.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		employeeCtrl.deleteEmployee(testEmployee);
		testEmployee = null;
	}

	//ID: 1
	@Test
	void testValidLoginInfoShouldLogin() throws DataAccessException {
		//Arrange 
		String email = "example@goodexample.com";
		String password = "password";
		
		//act 
		try {
			testEmployee = employeeCtrl.addEmployee("Test", "Test", email, password);
		} catch (DataAccessException e) {
			fail("Couldn't add employee to db");
		}
		sessionCtrl.authenticate(email, password); // Employee shouldn't be null at this point
		
		//Assert
		assertTrue(sessionCtrl.isLoggedIn()); //exp: true
	}
	
	//ID: 2
	@Test
	void testInvalidEmailShouldNotLogin() throws DataAccessException {
		//Arrange 
		String email = "example@goodexample.com";
		String password = "password";
		
		//act 
		try {
			testEmployee = employeeCtrl.addEmployee("Test", "Test", email, password);
		} catch (DataAccessException e) {
			fail("Couldn't add employee to db");
		}
		sessionCtrl.authenticate("example@badexample.com", password); // Employee shouldn't be null at this point
		
		//Assert
		assertFalse(sessionCtrl.isLoggedIn()); //exp: false
	}
	
	//ID: 3
		@Test
		void testInvalidPasswordShouldNotLogin() throws DataAccessException {
			//Arrange 
			String email = "example@goodexample.com";
			String password = "password";
			
			//act 
			try {
				testEmployee = employeeCtrl.addEmployee("Test", "Test", email, password);
			} catch (DataAccessException e) {
				fail("Couldn't add employee to db");
			}
			sessionCtrl.authenticate(email, "wrongpass"); // Employee shouldn't be null at this point
			
			//Assert
			assertFalse(sessionCtrl.isLoggedIn()); //exp: false
		}

}
