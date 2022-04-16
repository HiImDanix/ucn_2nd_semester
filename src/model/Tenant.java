package model;

import java.util.List;

public class Tenant {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private StudyProof studyProof;
    private Room room;
    private List<Contract> contracts;


    public Tenant(int id, String firstName, String lastName, String email, String phone, StudyProof studyProof, List<Contract> contracts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.studyProof = studyProof;
        this.contracts = contracts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StudyProof getStudyProof() {
        return studyProof;
    }

    public void setStudyProof(StudyProof studyProof) {
        this.studyProof = studyProof;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", studyProof=" + studyProof +
                ", room=" + room +
                ", contracts=" + contracts +
                '}';
    }
}
