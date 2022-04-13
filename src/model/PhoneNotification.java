package model;

import java.time.LocalDateTime;

public class PhoneNotification extends Notification {
    private String phone;

    public PhoneNotification(int id, String phone, String title, String content, LocalDateTime createdAt, Contract contract) {
        super(id, title, content, createdAt, contract);
        this.phone = phone;
    }

    public PhoneNotification(int id, String phone, String title, String content, LocalDateTime createdAt) {
        super(id, title, content, createdAt);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void send() {
        System.out.println("Sending phone notification to " + getPhone());
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "PhoneNotification{" + "id=" + getId() + ", phone="
                + phone + ", title=" + getTitle() + ", content=" + getContent() + ", createdAt=" + getCreatedAt() + '}';
    }

}
