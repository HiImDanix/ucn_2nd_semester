<<<<<<<< HEAD:src/test/controller/roomCategoryControllerTest.java
package controller;
========
package test.controller;
>>>>>>>> fd46663c0ef2cf0d22f4e7e94df0fdd52e8889b2:src/test/controller/TestRoomCategoryController.java

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.DBConnection;
import db.DataAccessException;
import model.RoomCategory;
<<<<<<<< HEAD:src/test/controller/roomCategoryControllerTest.java
========
import model.modelIF;
import test.TestIF;
>>>>>>>> fd46663c0ef2cf0d22f4e7e94df0fdd52e8889b2:src/test/controller/TestRoomCategoryController.java

public class roomCategoryControllerTest {

	private RoomCategoryController roomCatCtrl;
	private DBConnection dbCon;
	private RoomCategory testRoomCategory;

	public boolean compareObjects(RoomCategory obj, RoomCategory obj2) {
		return obj.getID() == obj2.getID() &&
				obj.getName().equals(obj2.getName()) &&
				obj.getDescription().equals(obj2.getDescription()) &&
				obj.getPricePerMonth().equals(obj2.getPricePerMonth()) &&
				obj.getPricePerMonthForInternet().equals(obj2.getPricePerMonthForInternet()) &&
				obj.getPricePerMonthForExtraTenant().equals(obj2.getPricePerMonthForExtraTenant()) &&
				obj.getMaxTenants() == obj2.getMaxTenants() &&
				obj.getLeaveNoticeDays() == obj2.getLeaveNoticeDays();
		
	}
	
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
		roomCatCtrl = new RoomCategoryController();
		dbCon = DBConnection.getInstance();
	}
	
	//ID: RCC 1
	@Test
	void testAddandGetById() throws DataAccessException {
		//Arrange 
		int id = -1;
		String name = "test";
		String description = "description";
		BigDecimal pricePerMonth = new BigDecimal(3000);
		BigDecimal priceForInternet = new BigDecimal(300);
		BigDecimal priceExtraTenant = new BigDecimal(0);
		int maxTenants = 1;
		int leaveNoticeDays = 30;
		
		//act 
		try {
			testRoomCategory = roomCatCtrl.addRoomCategory(name, description, pricePerMonth, pricePerMonth, pricePerMonth, maxTenants, leaveNoticeDays);
		} catch (DataAccessException e) {
			fail("Couldn't add employee to db");
		}
		RoomCategory retrievedData = roomCatCtrl.getRoomCategoryById(testRoomCategory.getID()); // Employee shouldn't be null at this point
		
		//Assert
		assertTrue(compareObjects(testRoomCategory, retrievedData), "Two object equals"); //exp: true
	}
	
	@Test
	void testExpectedExceptionNegativePrice() {
		//Arrange 
				int id = -1;
				String name = "test";
				String description = "description";
				BigDecimal pricePerMonth = new BigDecimal(-3000);
				BigDecimal priceForInternet = new BigDecimal(300);
				BigDecimal priceExtraTenant = new BigDecimal(0);
				int maxTenants = 1;
				int leaveNoticeDays = 30;
				
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			roomCatCtrl.addRoomCategory(name, description, pricePerMonth, pricePerMonth, pricePerMonth, maxTenants, leaveNoticeDays);
		}, "NumberFormatException was expected");
		
		Assertions.assertEquals("Price cannot be negative", thrown.getMessage());
		
	}
}
