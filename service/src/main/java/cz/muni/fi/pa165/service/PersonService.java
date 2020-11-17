package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * Person interface
 *
 * @author Matúš Špik
 */

public interface PersonService {

    /**
     * Create person
     *
     * @param person person to create
     * @throws ServiceDataAccessException service data access exception
     */
    Long create(Person person) throws ServiceDataAccessException;

    /**
     * Update person
     *
     * @param person person to update
     * @throws ServiceDataAccessException service data access exception
     */
    void update(Person person) throws ServiceDataAccessException;

    /**
     * Delete person
     *
     * @param person person to delete
     * @throws ServiceDataAccessException service data access exception
     */
    void delete(Person person) throws ServiceDataAccessException;

    /**
     * Filter persons by phone number and name
     *
     * @param phone phone number
     * @param name  name
     * @return List of people with given name and phone number
     * @throws ServiceDataAccessException service data access exception
     */
    Collection<Person> filterPersons(String phone, String name) throws ServiceDataAccessException;

    /**
     * Get person by id
     *
     * @param id id of person to be found
     * @return person with given id
     * @throws ServiceDataAccessException service data access exception
     */
    Person findById(Long id) throws ServiceDataAccessException;

    /**
     * Get all people
     *
     * @return List of all people
     * @throws ServiceDataAccessException service data access exception
     */
    List<Person> findAll() throws ServiceDataAccessException;
}
