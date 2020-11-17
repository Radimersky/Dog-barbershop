package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceTypeDTO;

import java.util.List;

/**
 * Service type facade interface
 *
 * @author Marek Radimersky, 456518
 */
public interface ServiceTypeFacade {

    /**
     * Create new service type
     * @param dto service type dto to create
     * @return created servce type id
     */
    Long createServiceType(ServiceTypeDTO dto);

    /**
     * Get all service types
     * @return all service types
     */
    List<ServiceTypeDTO> getAllServiceTypes();

    /**
     * Get single service type
     * @param id id of service type to get
     * @return service type
     */
    ServiceTypeDTO getServiceType(Long id);

    /**
     * Update service type with new parameters
     * @param dto service type dto to update
     */
    void updateServiceType(ServiceTypeDTO dto);

    /**
     * Delete service type
     * @param dto service type dto to delete
     */
    void deleteServiceType(ServiceTypeDTO dto);
}
