package model;

import java.time.LocalDateTime;

public abstract class Notification implements modelIF {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Contract contract;

    private boolean fullObjectRetrieved = false;

    public Notification(int id, String title, String content, LocalDateTime createdAt, Contract contract) {
        fullObjectRetrieved = true;

        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.contract = contract;
    }

    public Notification(int id, String title, String content, LocalDateTime createdAt) {
        this(id, title, content, createdAt, null);
    }

    // Send notification to the user
    public abstract void send();

    public boolean isfullObjectRetrieved() {
        return fullObjectRetrieved;
    }

    public int getID() {
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

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt + ", contract=" + contract + '}';
    }
}
