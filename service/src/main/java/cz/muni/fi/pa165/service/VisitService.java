package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Visit;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

/**
 * VisitService interface
 *
 * @author Aneta Moravcikova, 456444
 */
public interface VisitService {

    /**
     * Create new visit
     *
     * @param visit visit to create
     * @throws ServiceDataAccessException service data access exception
     */
    Long create(Visit visit) throws ServiceDataAccessException;

    /**
     * Update visit
     *
     * @param visit visit to update
     * @throws ServiceDataAccessException service data access exception
     */
    void update(Visit visit) throws ServiceDataAccessException;

    /**
     * Delete visit
     *
     * @param visit visit to delete
     * @throws ServiceDataAccessException service data access exception
     */
    void delete(Visit visit) throws ServiceDataAccessException;

    /**
     * Find a visit by id
     *
     * @param id id of visit to be found
     * @return visit with id
     * @throws ServiceDataAccessException service data access exception
     */
    Visit get(Long id) throws ServiceDataAccessException;

    /**
     * Find all visits
     *
     * @return all visits
     * @throws ServiceDataAccessException service data access exception
     */
    List<Visit> getAll() throws ServiceDataAccessException;

    Duration getPlannedLength(Visit visit) throws ServiceDataAccessException;

    BigDecimal getTotalPrice(Visit visit) throws ServiceDataAccessException;

    String getProcedureDescriptionsForVisit(Visit visit) throws ServiceDataAccessException;
}
