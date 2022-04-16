package model;

public class Tenant {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private StudyProof studyProof;
    private Room room;

    private boolean fullObjectRetrieved = false;

    public Tenant(int id, String firstName, String lastName, String middleName, String email, String phone, StudyProof studyProof, Room room) {
        this.fullObjectRetrieved = true;

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phone = phone;
        this.studyProof = studyProof;
        this.room = room;
    }

    public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Tenant(int id, String firstName, String lastName, String email, String phone, Room room) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.room = room;
    }

    public boolean isFullObjectRetrieved() {
        return this.fullObjectRetrieved;
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

    @Override
    public String toString() {
        return "Tenant{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + '}';
    }
}
