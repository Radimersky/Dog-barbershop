package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Employment;

import java.util.List;

/**
 * Performs access operations for Employment.class
 *
 * @author Martin.Palic
 */
public interface EmploymentDao {

    /**
     * Create Employment in database
     *
     * @param employment Employment to create
     */
    void createEmployment(Employment employment);

    /**
     * Update Employment in database
     *
     * @param employment Employment to update
     */
    void updateEmployment(Employment employment);

    /**
     * Delete Employment from database
     *
     * @param employment Employment to delete
     */
    void deleteEmployment(Employment employment);

    /**
     * Get Employment from database
     *
     * @param id Employment's id
     * @return Employment with id
     */
    Employment getEmployment(Long id);

    /**
     * Get all Employments from database
     *
     * @return list of all Employments in database
     */
    List<Employment> getAllEmployments();

}
