package model;

import java.util.List;

public class Room implements modelIF {
    private int id;
    private boolean isOutOfService;
    private RoomCategory roomCategory;
    private List<Contract> contracts;

    public Room(int roomID, RoomCategory roomCategory, boolean isOutOfService, List<Contract> contracts) {
        this.id = roomID;
        this.roomCategory = roomCategory;
        this.isOutOfService = isOutOfService;
        this.contracts = contracts;
    }

    public int getID() {
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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        contracts.add(contract);
    }

    public void removeContract(Contract contract) {
        contracts.remove(contract);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + id +
                ", isOutOfService=" + isOutOfService +
                ", roomCategory=" + roomCategory +
                ", contracts=" + contracts +
                '}';
    }
}

