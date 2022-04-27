package model;

import java.util.List;

public class Tenant {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private StudyProof studyProof;
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

    public int getID() {
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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void removeContract(Contract contract) {
    	contracts.remove(contract);
    }

    // add contract
    public void addContract(Contract contract) {
    	this.contracts.add(contract);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", studyProof=" + studyProof +
                ", contracts=" + contracts +
                '}';
    }
}
