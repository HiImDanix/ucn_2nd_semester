package model;

import java.time.LocalDate;

public class StudyProof implements modelIF {
    private int id;
    private String documentPath;
    private LocalDate studentUntilDate;
    private Institution institution;

    private boolean fullObjectRetrieved = false;

    public StudyProof(int id, String documentPath, LocalDate studentUntilDate, Institution institution) {
        this.id = id;
        this.documentPath = documentPath;
        this.studentUntilDate = studentUntilDate;
        this.institution = institution;
    }

    public StudyProof(int id, String documentPath, LocalDate studentUntilDate) {
        this.id = id;
        this.documentPath = documentPath;
        this.studentUntilDate = studentUntilDate;
    }

    public int getID() {
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

    public LocalDate getStudentUntilDate() {
        return studentUntilDate;
    }

    public void setStudentUntilDate(LocalDate studentUntilDate) {
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
        return "StudyProof{" + "id=" + id + ", documentPath=" + documentPath
                + ", studentUntilDate=" + studentUntilDate + ", institution=" + institution + '}';
    }

}
