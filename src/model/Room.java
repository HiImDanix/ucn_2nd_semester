package model;

public class Room {
    private int roomID;
    private boolean isOutOfService;
    private RoomCategory roomCategory;
    private List<RoomCondition> roomConditions;

    public Room(int roomID, RoomCategory roomCategory) {
        this.roomID = roomID;
        this.roomCategory = roomCategory;
        this.isOutOfService = false;
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

    public String toString() {
        return "Room ID: " + this.roomID + " - " + this.roomCategory.toString();
    }
}

