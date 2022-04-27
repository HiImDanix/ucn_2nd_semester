package controller;

import dal.RoomDB;
import dal.RoomDBIF;
import db.DataAccessException;
import model.Room;
import model.RoomCategory;

import java.util.ArrayList;
import java.util.List;


public class RoomController {
    private RoomDBIF roomDB;

    public RoomController() {
        roomDB = new RoomDB();
    }

    /*
     * Add Room
     */
    public Room addRoom(RoomCategory category, boolean isOutOfService) throws DataAccessException {
        Room room = new Room(-1, category, isOutOfService, new ArrayList<>());
        room.setId(roomDB.add(room));
        return room;
    }

    /*
     * Get room by id
     */
    public Room getRoomById(int id) throws DataAccessException {
        return roomDB.getById(id);
    }

    /*
     * Update rooms's out of service
     */
    public void updateRoomIsOutOfService(Room room, boolean isOutOfService) throws DataAccessException {
        room.setOutOfService(isOutOfService);
        roomDB.update(room);
    }

    /*
     * Update room's category
     */
    public void updateRoomCategory(Room room, RoomCategory category) throws DataAccessException {
        room.setRoomCategory(category);
        roomDB.update(room);
    }

    /*
     * Delete room
     */
    public void deleteRoom(Room room) throws DataAccessException {
        roomDB.delete(room);
    }

    /*
     * Get all rooms
     */
    public List<Room> getAllRooms() throws DataAccessException {
        return roomDB.getAll();
    }
}
