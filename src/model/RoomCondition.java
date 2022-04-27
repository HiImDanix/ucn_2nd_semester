package model;

import java.time.LocalDateTime;

public class RoomCondition {
    private int ID;
    private String description;
    private LocalDateTime datetimeTaken;
    private String imagePath;
    private Contract contract;

    private boolean fullObjectRetrieved = false;

    public RoomCondition(int ID, String description, LocalDateTime datetimeTaken, String imagePath, Contract contract) {
        fullObjectRetrieved = true;

        this.ID = ID;
        this.description = description;
        this.datetimeTaken = datetimeTaken;
        this.imagePath = imagePath;
        this.contract = contract;
    }

    public RoomCondition(int ID, String description, LocalDateTime datetimeTaken, String imagePath) {
        this(ID, description, datetimeTaken, imagePath, null);
    }

    public boolean isfullObjectRetrieved() {
        return fullObjectRetrieved;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatetimeTaken() {
        return datetimeTaken;
    }

    public void setDatetimeTaken(LocalDateTime datetimeTaken) {
        this.datetimeTaken = datetimeTaken;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "RoomCondition{" + "ID=" + ID + ", description=" + description + ", datetimeTaken=" + datetimeTaken + ", imagePath=" + imagePath + ", contract=" + contract + '}';
    }
}
