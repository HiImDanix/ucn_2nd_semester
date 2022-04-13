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
    private LeaveNotice leaveNotice;

    private boolean fullObjectRetrieved = false;

    public Contract(int ID, boolean includeInternet, LocalDateTime startDate,
                    Room room, Tenant tenant, List<RoomCondition> roomConditions,
                    Notification notifications, LeaveNotice leaveNotice) {
        this.ID = ID;
        this.includeInternet = includeInternet;
        this.startDatetime = startDate;
        this.room = room;
        this.tenant = tenant;
        this.roomConditions = roomConditions;
        this.notifications = notifications;
        this.leaveNotice = leaveNotice;

        fullObjectRetrieved = true;
    }

    public Contract(int ID, boolean includeInternet, LocalDateTime startDate) {
        this(ID, includeInternet, startDate, null, null, null, null, null);
    }

    public LeaveNotice getLeaveNotice() {
        return leaveNotice;
    }

    public void setLeaveNotice(LeaveNotice leaveNotice) {
        this.leaveNotice = leaveNotice;
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

    public boolean isfullObjectRetrieved() {
        return fullObjectRetrieved;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "ID=" + ID +
                ", includeInternet=" + includeInternet +
                ", startDatetime=" + startDatetime +
                ", room=" + room +
                ", tenant=" + tenant +
                ", roomConditions=" + roomConditions +
                ", notifications=" + notifications +
                ", leaveNotice=" + leaveNotice +
                '}';
    }

}
