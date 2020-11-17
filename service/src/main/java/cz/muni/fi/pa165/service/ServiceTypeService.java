package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.ServiceType;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;

import java.util.List;

/**
 * Interface for service type service
 *
 * @author Marek Radimersky, 456518
 */
public interface ServiceTypeService {

    /**
     * Create new service type
     *
     * @param serviceType type to create
     * @throws ServiceDataAccessException service data access exception
     */
    Long create(ServiceType serviceType) throws ServiceDataAccessException;

    /**
     * Find service type by id
     *
     * @param id id of animal to be found
     * @return service type with given id or null when service type does not exist
     * @throws ServiceDataAccessException service data access exception
     */
    ServiceType getById(Long id) throws ServiceDataAccessException;

    /**
     * Find all service types
     *
     * @return all service types
     * @throws ServiceDataAccessException service data access exception
     */
    List<ServiceType> getAll() throws ServiceDataAccessException;

    /**
     * Update service type
     *
     * @param serviceType type to be updated
     * @throws ServiceDataAccessException service data access exception
     */
    void update(ServiceType serviceType) throws ServiceDataAccessException;

    /**
     * Remove service type
     *
     * @param serviceType to be removed
     * @throws ServiceDataAccessException service data access exception
     */
    void remove(ServiceType serviceType) throws ServiceDataAccessException;
}