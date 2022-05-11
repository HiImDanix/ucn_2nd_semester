package controller;

import dal.RoomDB;
import dal.RoomDBIF;
import db.DataAccessException;
import model.Contract;
import model.Room;
import model.RoomCategory;

import java.time.LocalDate;
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
     * Update room
     */
    public void updateRoom(Room room, RoomCategory roomCategory, boolean isOutOfService) throws DataAccessException {
        // Capture old values
        RoomCategory oldRoomCategory = room.getRoomCategory();
        boolean oldIsOutOfService = room.isOutOfService();

        // Set values
        room.setRoomCategory(roomCategory);
        room.setOutOfService(isOutOfService);

        try {
            roomDB.update(room);
        } catch (DataAccessException e) {
            // Set values back to old values
            room.setRoomCategory(oldRoomCategory);
            room.setOutOfService(oldIsOutOfService);
            throw e;
        }
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

    /*
     * Is room occupied
     */
    public boolean isRoomOccupied(Room room){
        boolean roomHasValidContract = false;
        for (Contract contract: room.getContracts()) {
            if (contract.getLeaveNotice() == null ||
                    contract.getLeaveNotice() != null
                    && contract.getLeaveNotice().getNoticeGivenDate().isAfter(
                    LocalDate.now().plusDays(room.getRoomCategory().getLeaveNoticeDays()))) {
                roomHasValidContract = true;
            }
        }
        return roomHasValidContract;
    }
}
