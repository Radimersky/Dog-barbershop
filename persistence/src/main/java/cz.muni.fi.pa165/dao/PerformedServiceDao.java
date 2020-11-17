package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.PerformedService;

import java.util.List;

/**
 * Performs access operations for PerformedService.class
 *
 * @author Martin.Palic
 */
public interface PerformedServiceDao {

    /**
     * Create PerformedService in database
     *
     * @param performedService PerformedService to create
     */
    void createPerformedService(PerformedService performedService);

    /**
     * Update PerformedService in database
     *
     * @param performedService PerformedService to update
     */
    void updatePerformedService(PerformedService performedService);

    /**
     * Delete PerformedService from database
     *
     * @param performedService PerformedService to delete
     */
    void deletePerformedService(PerformedService performedService);

    /**
     * Get PerformedService from database
     *
     * @param id PerformedService's id
     * @return PerformedService with id
     */
    PerformedService getPerformedService(Long id);

    /**
     * Get all PerformedServices from database
     *
     * @return list of all PerformedServices in database
     */
    List<PerformedService> getAllPerformedServices();

}
