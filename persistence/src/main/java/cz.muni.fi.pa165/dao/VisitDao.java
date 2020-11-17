package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Visit;

import java.util.List;

/**
 * Performs access operations for Visit.class
 *
 * @author Martin.Palic
 */
public interface VisitDao {

    /**
     * Create Visit in database
     *
     * @param visit Visit to create
     */
    Long createVisit(Visit visit);

    /**
     * Update Visit in database
     *
     * @param visit Visit to update
     */
    void updateVisit(Visit visit);

    /**
     * Delete Visit from database
     *
     * @param visit Visit to delete
     */
    void deleteVisit(Visit visit);

    /**
     * Get Visit from database
     *
     * @param id Visit's id
     * @return Visit with id
     */
    Visit getVisit(Long id);

    /**
     * Get all Visits from database
     *
     * @return list of all Visits in database
     */
    List<Visit> getAllVisits();

}
