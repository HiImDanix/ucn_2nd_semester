package model;

import java.time.LocalDate;

public class LeaveNotice {
    private int id;
    private LocalDate noticeGivenDate;
    private boolean leavingByChoice;

    public LeaveNotice(int id, LocalDate noticeGivenDate, boolean leavingByChoice) {
        this.id = id;
        this.noticeGivenDate = noticeGivenDate;
        this.leavingByChoice = leavingByChoice;
    }

    public int getId() {
        return id;
    }

    public LocalDate getNoticeGivenDate() {
        return noticeGivenDate;
    }

    public boolean isLeavingByChoice() {
        return leavingByChoice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoticeGivenDate(LocalDate noticeGivenDate) {
        this.noticeGivenDate = noticeGivenDate;
    }

    public void setLeavingByChoice(boolean leavingByChoice) {
        this.leavingByChoice = leavingByChoice;
    }

    @Override
    public String toString() {
        return "LeaveNotice{" +
                "id=" + id +
                ", noticeGivenDate=" + noticeGivenDate +
                ", leavingByChoice=" + leavingByChoice +
                '}';
    }

}
