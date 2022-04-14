package controller;

import db.DataAccessException;
import model.Furniture;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


public class FurnitureController {
//    private FurnitureDBIF furnnitureDB;
//
//    public RoomCategoryController() throws DataAccessException {
//        furnitureDB = new FurnitureDB();
//    }

    public List<Furniture> getFurnitureByRoomCategoryId(int roomCategoryId) throws DataAccessException {
        // return stubbed data for constructor (int id, String name, String description, int quantity)
        Furniture furniture = new Furniture(1, "Chair", "A chair", 1);
        return Arrays.asList(furniture);

    }
}
