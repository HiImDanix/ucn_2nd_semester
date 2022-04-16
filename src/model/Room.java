package model;

import java.util.List;

public class Room {
    private int id;
    private boolean isOutOfService;
    private RoomCategory roomCategory;

    public Room(int roomID, RoomCategory roomCategory, boolean isOutOfService) {
        this.id = roomID;
        this.roomCategory = roomCategory;
        this.isOutOfService = isOutOfService;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOutOfService() {
        return isOutOfService;
    }

    public void setOutOfService(boolean outOfService) {
        isOutOfService = outOfService;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + id +
                ", isOutOfService=" + isOutOfService +
                ", roomCategory=" + roomCategory +
                '}';
    }
}

