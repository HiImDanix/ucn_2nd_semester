package model;

import java.time.LocalDateTime;
import java.util.List;

public class Contract {
    private int ID;
    private boolean includeInternet;
    private LocalDateTime startDatetime;
    private Room room;
    private List<Tenant> tenants;
    private List<RoomCondition> roomConditions;
    private List<Notification> notifications;
    private LeaveNotice leaveNotice;

    public Contract(int ID, boolean includeInternet, LocalDateTime startDate,
                    Room room, List<Tenant> tenants, List<RoomCondition> roomConditions,
                    List<Notification> notifications, LeaveNotice leaveNotice) {
        this.ID = ID;
        this.includeInternet = includeInternet;
        this.startDatetime = startDate;
        this.room = room;
        this.tenants = tenants;
        this.roomConditions = roomConditions;
        this.notifications = notifications;
        this.leaveNotice = leaveNotice;

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

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public List<RoomCondition> getRoomConditions() {
        return roomConditions;
    }

    public void setRoomConditions(List<RoomCondition> roomConditions) {
        this.roomConditions = roomConditions;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }


    @Override
    public String toString() {
        return "Contract{" +
                "ID=" + ID +
                ", includeInternet=" + includeInternet +
                ", startDatetime=" + startDatetime +
                ", room=" + room +
                ", tenants=" + tenants +
                ", roomConditions=" + roomConditions +
                ", notifications=" + notifications +
                ", leaveNotice=" + leaveNotice +
                '}';
    }

}
