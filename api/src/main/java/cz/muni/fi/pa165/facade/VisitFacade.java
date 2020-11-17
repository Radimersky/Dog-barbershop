package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.VisitDTO;
import cz.muni.fi.pa165.dto.VisitTotalsDTO;

import java.util.List;

/**
 * Facade for visit entity
 *
 * @author Aneta Moravcikova, 456444
 */
public interface VisitFacade {

    /**
     * Create new visit.
     *
     * @param visitDTO VisitDTO to be created
     * @return id of created visit
     */
    Long createVisit(VisitDTO visitDTO);

    /**
     * Update visit with new parameters
     *
     * @param visitDTO VisitDTO to be updated
     */
    void updateVisit(VisitDTO visitDTO);

    /**
     * Delete visit.
     *
     * @param visitDTO VisitDTO to be removed
     */
    void deleteVisit(VisitDTO visitDTO);

    /**
     * Get visit with specific id.
     *
     * @param id id of visit to be found
     * @return VisitDTO of specific visit
     */
    VisitDTO getVisit(Long id);

    /**
     * Get all visits.
     *
     * @return list of all VisitDTO
     */
    List<VisitDTO> getAllVisits();

    /**
     * Get planned Visit Totals
     */
    VisitTotalsDTO getPlannedVisitTotals(VisitDTO visit);

}
