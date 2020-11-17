package cz.muni.fi.pa165.dto;


import java.util.Objects;

/**
 * Person DTO representing transfered data about person
 *
 * @author Martin.Palic
 */
public class PersonDTO {

    private Long Id;
    private String name;
    private String surname;
    private String address;
    private String password; //hash only
    private String phoneNumber;

    public PersonDTO() {
    }

    public PersonDTO(String name, String surname, String address, String phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(o instanceof PersonDTO)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) &&
                Objects.equals(getName(), personDTO.getName()) &&
                Objects.equals(getSurname(), personDTO.getSurname()) &&
                Objects.equals(getAddress(), personDTO.getAddress()) &&
                Objects.equals(getPassword(), personDTO.getPassword()) &&
                Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getAddress(), getPassword(), getPhoneNumber());
    }
}
