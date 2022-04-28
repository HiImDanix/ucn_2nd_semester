package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Contract implements modelIF {
    private int ID;
    private boolean includeInternet;
    private LocalDate startDate;
    private Room room;
    private List<Tenant> tenants;
    private List<RoomCondition> roomConditions;
    private List<Notification> notifications;
    private LeaveNotice leaveNotice;

    public Contract(int ID, boolean includeInternet, LocalDate startDate,
                    Room room, List<Tenant> tenants, List<RoomCondition> roomConditions,
                    List<Notification> notifications, LeaveNotice leaveNotice) {
        this.ID = ID;
        this.includeInternet = includeInternet;
        this.startDate = startDate;
        this.room = room;
        this.tenants = tenants;
        this.roomConditions = roomConditions;
        this.notifications = notifications;
        this.leaveNotice = leaveNotice;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public void addTenant(Tenant tenant) {
        this.tenants.add(tenant);
    }

    public void removeTenant(Tenant tenant) {
        this.tenants.remove(tenant);
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
                ", startDate=" + startDate +
                ", room=" + room +
                ", tenants=" + tenants +
                ", roomConditions=" + roomConditions +
                ", notifications=" + notifications +
                ", leaveNotice=" + leaveNotice +
                '}';
    }

}
