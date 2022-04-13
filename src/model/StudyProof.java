package model;

import java.time.LocalDateTime;

public class StudyProof {
    private int id;
    private String documentPath;
    private LocalDateTime studentUntilDate;
    private Institution institution;

    private boolean fullObjectRetrieved = false;

    public StudyProof(int id, String documentPath, LocalDateTime studentUntilDate, Institution institution) {
        this.id = id;
        this.documentPath = documentPath;
        this.studentUntilDate = studentUntilDate;
        this.institution = institution;
    }

    public StudyProof(int id, String documentPath, LocalDateTime studentUntilDate) {
        this.id = id;
        this.documentPath = documentPath;
        this.studentUntilDate = studentUntilDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public LocalDateTime getStudentUntilDate() {
        return studentUntilDate;
    }

    public void setStudentUntilDate(LocalDateTime studentUntilDate) {
        this.studentUntilDate = studentUntilDate;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public boolean isFullObjectRetrieved() {
        return fullObjectRetrieved;
    }

    public String toString() {
        return "StudyProof{" + "id=" + id + ", documentPath=" + documentPath + ", studentUntilDate=" + studentUntilDate + ", institution=" + institution + '}';
    }

}
