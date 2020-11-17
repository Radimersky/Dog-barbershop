package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Employment;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EmploymentService interface
 *
 * @author Matúš Špik
 */

@Service
public interface EmploymentService {

    /**
     * Create new employment
     * 9
     *
     * @param employment employment to create
     * @throws ServiceDataAccessException service data access exception
     */
    void create(Employment employment) throws ServiceDataAccessException;

    /**
     * Update employment
     *
     * @param employment employment to update
     * @throws ServiceDataAccessException service data access exception
     */
    void update(Employment employment) throws ServiceDataAccessException;

    /**
     * Delete employment
     *
     * @param employment employment to delete
     * @throws ServiceDataAccessException service data access exception
     */
    void delete(Employment employment) throws ServiceDataAccessException;

    /**
     * Get employment by id
     *
     * @param id id of employment to be found
     * @return employment with given id
     * @throws ServiceDataAccessException service data access exception
     */
    Employment get(Long id) throws ServiceDataAccessException;

    /**
     * Get all employments
     *
     * @return list of all employments
     * @throws ServiceDataAccessException service data access exception
     */
    List<Employment> getAll() throws ServiceDataAccessException;


    /**
     * Filters employment by employed person phone or name
     * @param phone phone of employed person to filter
     * @param name name of employed person to filter
     * @return filtered employments
     * @throws ServiceDataAccessException service data access exception
     */
    List<Employment> filter(String phone, String name) throws ServiceDataAccessException;
}
