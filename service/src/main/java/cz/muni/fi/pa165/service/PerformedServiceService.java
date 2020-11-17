package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.PerformedService;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;

import java.util.List;

/**
 * Interface for performed service service
 *
 * @author Marek Radimersky, 456518
 */
public interface PerformedServiceService {
    /**
     * Create new performed service
     *
     * @param performedService type to create
     * @throws ServiceDataAccessException service data access exception
     */
    void create(PerformedService performedService) throws ServiceDataAccessException;

    /**
     * Find performed service by id
     *
     * @param id id of animal to be found
     * @return performed service with given id or null when performed service does not exist
     * @throws ServiceDataAccessException service data access exception
     */
    PerformedService getById(Long id) throws ServiceDataAccessException;

    /**
     * Find all performed services
     *
     * @return all performed services
     * @throws ServiceDataAccessException service data access exception
     */
    List<PerformedService> getAll() throws ServiceDataAccessException;

    /**
     * Update performed service
     *
     * @param performedService type to be updated
     * @throws ServiceDataAccessException service data access exception
     */
    void update(PerformedService performedService) throws ServiceDataAccessException;

    /**
     * Remove performed service
     *
     * @param performedService to be removed
     * @throws ServiceDataAccessException service data access exception
     */
    void remove(PerformedService performedService) throws ServiceDataAccessException;
}
