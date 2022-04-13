package model;

import java.time.LocalDateTime;

public class RoomCondition {
    private int id;
    private String description;
    private LocalDateTime datetimeTaken;
    private String imagePath;

    public RoomCondition(int id, String description, LocalDateTime datetimeTaken, String imagePath) {
        this.id = id;
        this.description = description;
        this.datetimeTaken = datetimeTaken;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
