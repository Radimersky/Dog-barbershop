package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;

/**
 * Entity class representing a Dog,
 * each dog has one Person.class owner.
 *
 * @author Aneta Moravcikova, 456444
 */
@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Pattern(regexp = "^[a-z A-Z]*$", message = "Dog's name can include only alphabetical characters and space.")
    private String name;

    @Pattern(regexp = "^[a-z0-9 A-Z]*$", message = "Dog's breed can include only alphanumerical characters and space.")
    private String breed;
    private Gender gender;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateOfBirth;

    @NotNull
    @ManyToOne
    private Person owner;

    public Dog() {
    }

    public Dog(String name, String breed, Gender gender, Date dateOfBirth, Person owner) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.owner = owner;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog)) return false;
        Dog dog = (Dog) o;
        return Objects.equals(getId(), dog.getId()) &&
                Objects.equals(getName(), dog.getName()) &&
                Objects.equals(getBreed(), dog.getBreed()) &&
                getGender() == dog.getGender() &&
                Objects.equals(getDateOfBirth(), dog.getDateOfBirth()) &&
                Objects.equals(getOwner(), dog.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBreed(), getGender(), getDateOfBirth(), getOwner());
    }
}
