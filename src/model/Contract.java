package model;

import java.time.LocalDateTime;
import java.util.List;

public class Contract {
    private int ID;
    private boolean includeInternet;
    private LocalDateTime startDatetime;
    private Room room;
    private Tenant tenant;
    private List<RoomCondition> roomConditions;
    private Notification notifications;

    public Contract(int ID, boolean includeInternet, LocalDateTime startDate,
                    Room room, Tenant tenant, List<RoomCondition> roomConditions, Notification notifications) {
        this.ID = ID;
        this.includeInternet = includeInternet;
        this.startDatetime = startDate;
        this.room = room;
        this.tenant = tenant;
        this.roomConditions = roomConditions;
        this.notifications = notifications;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isIncludeInternet() {
        return includeInternet;
    }

    public void setIncludeInternet(boolean includeInternet) {
        this.includeInternet = includeInternet;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<RoomCondition> getRoomConditions() {
        return roomConditions;
    }

    public void setRoomConditions(List<RoomCondition> roomConditions) {
        this.roomConditions = roomConditions;
    }

    public Notification getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification notifications) {
        this.notifications = notifications;
    }

    public String toString() {
        return String.format("Contract (ID: %d, includeInternet: %b, startDate: %s", ID, includeInternet, startDatetime);
    }

}
