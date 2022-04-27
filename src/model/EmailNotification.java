package model;

import java.time.LocalDateTime;

public class EmailNotification extends Notification {
    private String email;

    public EmailNotification(int id, String email, String title, String content, LocalDateTime createdAt, Contract contract) {
        super(id, title, content, createdAt, contract);
        this.email = email;
    }

    public EmailNotification(int id, String email, String title, String content, LocalDateTime createdAt) {
        super(id, title, content, createdAt);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void send() {
        System.out.println("Sending email to " + email);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "EmailNotification{" + "id=" + getID() + ", email="
                + email + ", title=" + getTitle() + ", content=" + getContent() + ", createdAt=" + getCreatedAt() + '}';
    }

}
