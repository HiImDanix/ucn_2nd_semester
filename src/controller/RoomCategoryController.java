package controller;

import dal.RoomCategoryDB;
import dal.RoomCategoryDBIF;
import db.DataAccessException;
import model.Furniture;
import model.RoomCategory;

import java.math.BigDecimal;
import java.util.List;


@SuppressWarnings("unused")
public class RoomCategoryController {
    private RoomCategoryDBIF roomCategoryDB;
    private List<Furniture> furniture;

    public RoomCategoryController() {
        roomCategoryDB = new RoomCategoryDB();
    }
	
	public RoomCategory addRoomCategory(String name, String description, BigDecimal pricePerMonth,
			BigDecimal pricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants, int leaveNoticeDays) throws DataAccessException {
		RoomCategory roomCategory = new RoomCategory(-1, name, description, pricePerMonth, pricePerMonthForInternet,
				pricePerMonthForExtraTenant, maxTenants,  leaveNoticeDays);
		roomCategory.setId(roomCategoryDB.add(roomCategory));
        return roomCategory;
	}
	
    public void updateRoomCategory(RoomCategory roomCategory, String name, String description, BigDecimal pricePerMonth,
			BigDecimal pricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants,
			List<Furniture> furniture, int leaveNoticeDays)
			throws DataAccessException {
		// capture old values
    	String oldName = roomCategory.getName();
    	String oldDescription = roomCategory.getDescription();
    	BigDecimal oldPricePerMonth = roomCategory.getPricePerMonth();
		BigDecimal oldPricePerMonthForInternet = roomCategory.getPricePerMonthForInternet();
		BigDecimal oldPricePerMonthForExtraTenant = roomCategory.getPricePerMonthForExtraTenant();
		int oldMaxTenants = roomCategory.getMaxTenants();
		List<Furniture> oldFurniture = roomCategory.getFurniture();
		int oldLeaveNoticeDays = roomCategory.getLeaveNoticeDays();

		// update new values
		updateValues(roomCategory, name, description, pricePerMonth, pricePerMonthForInternet, pricePerMonthForExtraTenant, maxTenants, furniture, leaveNoticeDays);

		// update database
		try {
			roomCategoryDB.update(roomCategory);
		} catch (DataAccessException e) {
			// revert to old values
			updateValues(roomCategory, oldName, oldDescription, oldPricePerMonth, oldPricePerMonthForInternet, oldPricePerMonthForExtraTenant, oldMaxTenants, oldFurniture, oldLeaveNoticeDays);
			throw e;
		}
	}

	private void updateValues(RoomCategory roomCategory, String oldName, String oldDescription, BigDecimal oldPricePerMonth, BigDecimal oldPricePerMonthForInternet, BigDecimal oldPricePerMonthForExtraTenant, int oldMaxTenants, List<Furniture> oldFurniture, int oldLeaveNoticeDays) {
		roomCategory.setName(oldName);
		roomCategory.setDescription(oldDescription);
		roomCategory.setPricePerMonth(oldPricePerMonth);
		roomCategory.setPricePerMonthForInternet(oldPricePerMonthForInternet);
		roomCategory.setPricePerMonthForExtraTenant(oldPricePerMonthForExtraTenant);
		roomCategory.setMaxTenants(oldMaxTenants);
		roomCategory.setFurniture(oldFurniture);
		roomCategory.setLeaveNoticeDays(oldLeaveNoticeDays);
	}

	public RoomCategory getRoomCategoryById(int roomCategoryId) throws DataAccessException {
        // return stubbed data for constructor (int id, String name, String description, BigDecimal pricePerMonth, BigDecimal PricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants, int leaveNoticeDays, List<Furniture> furniture)
    	return roomCategoryDB.getById(roomCategoryId);
    }
    
    public List<RoomCategory> getAllRoomCategories() throws DataAccessException {
    	return roomCategoryDB.getAll();
    }
}
