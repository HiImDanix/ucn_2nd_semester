package controller;

import dal.RoomDB;
import dal.RoomDBIF;
import db.DataAccessException;
import model.Room;
import model.RoomCategory;

import java.math.BigDecimal;
import java.util.List;


public class RoomCategoryController {
//    private RoomCategoryDBIF roomCategoryDB;
//
//    public RoomCategoryController() throws DataAccessException {
//        roomCategoryDB = new RoomCategoryDB();
//    }

    public RoomCategory getRoomCategoryById(int roomCategoryId) throws DataAccessException {
        // return stubbed data for constructor (int id, String name, String description, BigDecimal pricePerMonth, BigDecimal PricePerMonthForInternet, BigDecimal pricePerMonthForExtraTenant, int maxTenants, int leaveNoticeDays, List<Furniture> furniture)
        return new RoomCategory(1, "Single", "Single", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BigDecimal.valueOf(100), 2, 1, new FurnitureController().getFurnitureByRoomCategoryId(1));
    }
}
