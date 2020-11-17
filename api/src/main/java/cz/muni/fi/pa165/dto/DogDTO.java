package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.Gender;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

/**
 * Dog DTO representing transfered data about Dog
 *
 * @author Martin.Palic
 */
public class DogDTO {

    private Long id;
    private String name;
    private String breed;
    private Gender gender;
    private Date dateOfBirth;
    @NotNull
    private PersonDTO owner;

    public DogDTO() {
    }

    public DogDTO(String name, String breed, Gender gender, Date dateOfBirth, PersonDTO owner) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PersonDTO getOwner() {
        return owner;
    }

    public void setOwner(PersonDTO owner) {
        this.owner = owner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DogDTO)) return false;
        DogDTO dogDTO = (DogDTO) o;
        return Objects.equals(getId(), dogDTO.getId()) &&
                Objects.equals(getName(), dogDTO.getName()) &&
                Objects.equals(getBreed(), dogDTO.getBreed()) &&
                getGender() == dogDTO.getGender() &&
                Objects.equals(getDateOfBirth(), dogDTO.getDateOfBirth()) &&
                getOwner().equals(dogDTO.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBreed(), getGender(), getDateOfBirth(), getOwner());
    }
}
