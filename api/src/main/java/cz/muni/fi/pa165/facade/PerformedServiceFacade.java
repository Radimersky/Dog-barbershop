package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.PerformedServiceDTO;

import java.util.List;

/**
 * Facade for PerformedService
 *
 * @author Aneta Moravcikova, 456444
 */
public interface PerformedServiceFacade {

    /**
     * Create new performed service.
     *
     * @param performedServiceDTO PerformedServiceDTO to be created
     * @return id of created performed service
     */
    Long createPerformedService(PerformedServiceDTO performedServiceDTO);

    /**
     * Update performed service with new parameters
     *
     * @param performedServiceDTO PerformedServiceDTO to be updated
     */
    void updatePerformedService(PerformedServiceDTO performedServiceDTO);

    /**
     * Delete performed service.
     *
     * @param performedServiceDTO PerformedServiceDTO to be removed
     */
    void deletePerformedService(PerformedServiceDTO performedServiceDTO);

    /**
     * Get performed service with specific id.
     *
     * @param id id of performed service to be found
     * @return PerformedServiceDTO of specific performed service
     */
    PerformedServiceDTO getPerformedService(Long id);

    /**
     * Get all performed services.
     *
     * @return list of all PerformedServiceDTO
     */
    List<PerformedServiceDTO> getAllPerformedServices();

}
