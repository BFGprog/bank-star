package sky.pro.bankstar.model;

import java.util.Objects;
import java.util.UUID;

public class UserDB {

    private UUID id;
    private String firstName;
    private String lastName;

    public UserDB(String firstName, String lastName, UUID id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public UserDB() {
    }

    @Override
    public String toString() {
        return "UserDB{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDB userDB = (UserDB) o;
        return Objects.equals(firstName, userDB.firstName) && Objects.equals(lastName, userDB.lastName) && Objects.equals(id, userDB.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, id);
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
