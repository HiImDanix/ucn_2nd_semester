package model;

import java.time.LocalDateTime;

public abstract class Notification {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Contract contract;

    public Notification(int id, String title, String content, LocalDateTime createdAt, Contract contract) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.contract = contract;
    }

    // Send notification to the user
    public abstract void send();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
