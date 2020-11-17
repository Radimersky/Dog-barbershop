package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.ServiceType;

import java.util.List;

/**
 * ServiceTypeDao interface
 *
 * @author Matúš Špik
 */
public interface ServiceTypeDao {

    /**
     * Create service type in database
     *
     * @param serviceType service type to create
     */
    Long createServiceType(ServiceType serviceType);

    /**
     * Update service type in database
     *
     * @param serviceType service type to update
     */
    void updateServiceType(ServiceType serviceType);

    /**
     * Delete service type from database
     *
     * @param serviceType service type to delete
     */
    void deleteServiceType(ServiceType serviceType);

    /**
     * Get service type from database
     *
     * @param id service type's id
     * @return service type with given id
     */
    ServiceType getServiceType(Long id);

    /**
     * Get all service types from database
     *
     * @return list of all service types in database
     */
    List<ServiceType> getAllServiceTypes();

}
