package model;

import java.util.List;

public class Room {
    private int roomID;
    private boolean isOutOfService;
    private RoomCategory roomCategory;
    private List<RoomCondition> roomConditions;

    private boolean objectsRetrieved = false;

    public Room(int roomID, RoomCategory roomCategory, boolean isOutOfService, List<RoomCondition> roomConditions) {
        objectsRetrieved = true;

        this.roomID = roomID;
        this.roomCategory = roomCategory;
        this.isOutOfService = isOutOfService;
        this.roomConditions = roomConditions;
    }

    public Room(int roomID, RoomCategory roomCategory, boolean isOutOfService) {
        this(roomID, roomCategory, isOutOfService, null);
    }

    public boolean isObjectsRetrieved() {
        return objectsRetrieved;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
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

    public List<RoomCondition> getRoomConditions() {
        return roomConditions;
    }

    public void setRoomConditions(List<RoomCondition> roomConditions) {
        this.roomConditions = roomConditions;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", isOutOfService=" + isOutOfService +
                ", roomCategory=" + roomCategory +
                ", roomConditions=" + roomConditions +
                '}';
    }
}

