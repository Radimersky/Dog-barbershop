package cz.muni.fi.pa165.enums;

/**
 * Gender enumeration for Dog DTO entity
 *
 * @author Martin.Palic
 */
public enum Gender {
    MALE("Male"), FEMALE("Female");

    // declaring private variable for getting values
    private String gender;

    // getter method
    public String getGender() {
        return this.gender;
    }

    // enum constructor - cannot be public or protected
    private Gender(String gender) {
        this.gender = gender;
    }
}
