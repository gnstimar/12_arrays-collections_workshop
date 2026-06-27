package se.lexicon;

import java.time.LocalDate;
import java.util.Objects;

public class Contact {
    private final String UUID;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String email;
    private boolean isFavourite;
    private LocalDate birthday;

    public Contact(String firstName, String lastName, String phone) {
        this.UUID = java.util.UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Contact(String UUID, String firstName, String lastName, String phone) {
        this.UUID = UUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getUuid() {
        return UUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.isEmpty()) {
            this.phone = phone;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isFavourite=" + isFavourite +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(UUID, contact.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(UUID);
    }
}
