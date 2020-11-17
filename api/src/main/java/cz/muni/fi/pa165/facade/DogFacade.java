package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;

import java.util.List;

/**
 * Facade for Dog entity
 *
 * @author Aneta Moravcikova, 456444
 */
public interface DogFacade {

    /**
     * Create new dog.
     *
     * @param dogDTO DogDTO to be created
     * @return id of created dog
     */
    Long createDog(DogDTO dogDTO);

    /**
     * Update dog with new parameters
     *
     * @param dogDTO DogDTO to be updated
     */
    void updateDog(DogDTO dogDTO);

    /**
     * Delete dog.
     *
     * @param dogDTO DogDTO to be removed
     */
    void deleteDog(DogDTO dogDTO);

    /**
     * Get dog with specific id.
     *
     * @param id id of dog to be found
     * @return DogDTO of specific dog
     */
    DogDTO getDog(Long id);

    /**
     * Get all dogs.
     *
     * @return list of all DogDTO
     */
    List<DogDTO> getAllDogs();

}
