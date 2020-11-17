package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Person;

import java.util.List;

/**
 * @author Marek Radiměřský
 * <p>
 * Person DAO interface.
 */
public interface PersonDao {

    /**
     * Find person by ID
     *
     * @param id
     * @return Person with ID "id"
     */
    Person getPerson(Long id);

    /**
     * Create person in DB
     *
     * @param person person to create
     */
    Long createPerson(Person person);

    /**
     * Update person in DB
     *
     * @param person person to update
     */
    void updatePerson(Person person);

    /**
     * Delete person from DB
     *
     * @param person person to delete
     */
    void deletePerson(Person person);

    /**
     * Find all persons in DB
     *
     * @return All persons
     */
    List<Person> getAllPersons();

    /**
     * Filters person by phone or name
     * @param phone phone of person to filter
     * @param name name of person to filter
     * @return filtered persons
     */
    List<Person> filterPersons(String phone, String name);

}
