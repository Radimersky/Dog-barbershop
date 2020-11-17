package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Dog;

import java.util.List;

/**
 * DogDao interface
 *
 * @author Aneta Moravcikova, 456444
 */
public interface DogDao {

    /**
     * Create dog in database
     *
     * @param dog dog to create
     */
    Long createDog(Dog dog);

    /**
     * Update dog in database
     *
     * @param dog dog to update
     */
    void updateDog(Dog dog);

    /**
     * Delete dog from database
     *
     * @param dog dog to delete
     */
    void deleteDog(Dog dog);

    /**
     * Get dog from database
     *
     * @param id dog's id
     * @return dog with id
     */
    Dog getDog(Long id);

    /**
     * Get all dogs from database
     *
     * @return list of all dogs in database
     */
    List<Dog> getAllDogs();

}
