package cz.muni.fi.pa165.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author Marek Radiměřský
 * <p>
 * Person entity.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull
    @Pattern(regexp = "^[a-z A-Z]*$", message = "Person's name can include only alphabetical characters.")
    private String name;
    @NotNull
    @Pattern(regexp = "^[a-z A-Z]*$", message = "Person's surname can include only alphabetical characters.")
    private String surname;
    @NotNull
    @Pattern(regexp = "^[a-z0-9 A-Z,]*$", message = "Address can include only alphabetical characters and numbers.")
    private String address;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^[+0-9 ]*$", message = "phone number can include only numbers and plus sign.")
    private String phoneNumber;
    @NotNull
    private Boolean admin;

    public Person() {
    }

    public Person(String name, String surname, String address, String phoneNumber, String password, Boolean admin) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.admin = admin;
        this.setPassword(password);
    }

    public Person(String name, String surname, String address, String phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.admin = false;
        this.setPassword(password);
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setPassword(String password) {
        try {
            if (password == null) password = "";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            this.password = DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException ignored) {
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId()) &&
                Objects.equals(getName(), person.getName()) &&
                Objects.equals(getSurname(), person.getSurname()) &&
                Objects.equals(getAddress(), person.getAddress()) &&
                Objects.equals(getPhoneNumber(), person.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getAddress(), getPhoneNumber());
    }
}
