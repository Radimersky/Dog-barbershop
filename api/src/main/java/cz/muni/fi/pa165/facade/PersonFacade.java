package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.PersonAuthDTO;
import cz.muni.fi.pa165.dto.PersonDTO;

import java.util.List;

/**
 * Facade for handling person managemnent operations
 *
 * @author Martin.Palic
 */
public interface PersonFacade {

    /**
     * resister person with information and store given password
     *
     * @param user         informatioin of person to register
     * @param passwordText plaintext password to be encrpted and stored
     * @return true if person was registered successfully
     */
    boolean registerPerson(PersonDTO user, String passwordText);

    /**
     * get list of all person filtered by given parameters
     *
     * @param phone substring to search in phone field or empty if not part of search
     * @param name  substring to search in name field or empty if not part of search
     * @return Collection of Person that match given criteria
     */
    List<PersonDTO> filterPersons(String phone, String name);

    /**
     * Verifies that person is registered in system and has provided matching password
     *
     * @param person info of person trying to authenticate
     * @return true if person can claim the identity
     */
    boolean authenticatePerson(PersonAuthDTO person);

    /**
     * Get all Persons registerd in system
     *
     * @return List of Person
     */
    List<PersonDTO> getAllPersons();

    /**
     * Get person by id
     * @param Id person id
     * @return person
     */
    PersonDTO getPerson(Long Id);

    /**
     * Check if given person is admin
     * @param person person to check
     * @return true if person is admin, false otherwise
     */
    boolean isAdmin(PersonDTO person);

    /**
     * Check if person with given phone is admin
     * @param phone phone of person to check
     * @return true if person is admin, false otherwise
     */
    boolean isAdmin(String phone);

    /**
     * Update person with new parameters
     * @param person person to update
     */
    void updatePerson(PersonDTO person);

    /**
     * Create new person
     * @param person person to create
     * @return created person id
     */
    Long createPerson(PersonDTO person);

    /**
     * Delete person
     * @param person person to delete
     */
    void deletePerson(PersonDTO person);
}
