package dal;

import db.DataAccessException;
import model.Room;

import java.util.List;

public interface RoomDBIF {
    /*
     * Get room by id
     */
    Room getById(int id) throws DataAccessException;

    /*
     * add room to db
     */
    int add(Room room) throws DataAccessException;

    /*
     * Update room
     */
    void update(Room room) throws DataAccessException;

    /*
     * Delete room
     */
    void delete(Room room) throws DataAccessException;

    /*
     * Get all rooms
     */
    List<Room> getAll() throws DataAccessException;

}
