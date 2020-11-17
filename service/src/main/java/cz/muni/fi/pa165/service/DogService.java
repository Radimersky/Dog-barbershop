package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;

import java.util.List;

/**
 * DogService interface
 *
 * @author Aneta Moravcikova, 456444
 */
public interface DogService {

    /**
     * Create new dog
     *
     * @param dog dog to create
     * @throws ServiceDataAccessException service data access exception
     */
    Long create(Dog dog) throws ServiceDataAccessException;

    /**
     * Update dog
     *
     * @param dog dog to update
     * @throws ServiceDataAccessException service data access exception
     */
    void update(Dog dog) throws ServiceDataAccessException;

    /**
     * Delete dog
     *
     * @param dog dog to delete
     * @throws ServiceDataAccessException service data access exception
     */
    void delete(Dog dog) throws ServiceDataAccessException;

    /**
     * Find a dog by id
     *
     * @param id id of dog to be found
     * @return dog with id
     * @throws ServiceDataAccessException service data access exception
     */
    Dog get(Long id) throws ServiceDataAccessException;

    /**
     * Find all dogs
     *
     * @return all dogs
     * @throws ServiceDataAccessException service data access exception
     */
    List<Dog> getAll() throws ServiceDataAccessException;
}
