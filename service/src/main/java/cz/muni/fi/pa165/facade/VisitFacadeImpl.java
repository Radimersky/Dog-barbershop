package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.VisitDTO;
import cz.muni.fi.pa165.dto.VisitTotalsDTO;
import cz.muni.fi.pa165.entity.Visit;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * VisitFacade interface implementation
 *
 * @author Aneta Moravcikova, 456444
 */
@Service
@Transactional
public class VisitFacadeImpl implements VisitFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private VisitService visitService;

    public VisitFacadeImpl() {
    }

    public VisitFacadeImpl(BeanMappingService mappingService, VisitService visitService) {
        this.beanMappingService = mappingService;
        this.visitService = visitService;
    }

    @Override
    public Long createVisit(VisitDTO visitDTO) {
        Visit visit = beanMappingService.mapTo(visitDTO, Visit.class);
        visitService.create(visit);
        return visit.getId();
    }

    @Override
    public void updateVisit(VisitDTO visitDTO) {
        visitService.update(beanMappingService.mapTo(visitDTO, Visit.class));
    }

    @Override
    public void deleteVisit(VisitDTO visitDTO) {
        visitService.delete(beanMappingService.mapTo(visitDTO, Visit.class));
    }

    @Override
    public VisitDTO getVisit(Long id) {
        Visit visit = visitService.get(id);
        return beanMappingService.mapTo(visit, VisitDTO.class);
    }

    @Override
    public List<VisitDTO> getAllVisits() {
        return beanMappingService.mapTo(visitService.getAll(), VisitDTO.class);
    }

    @Override
    public VisitTotalsDTO getPlannedVisitTotals(VisitDTO visitDTO) {
        Visit visit = beanMappingService.mapTo(visitDTO, Visit.class);
        VisitTotalsDTO totalsDTO = new VisitTotalsDTO();
        totalsDTO.setProcedureDescriptions(visitService.getProcedureDescriptionsForVisit(visit));
        totalsDTO.setTotalLength(visitService.getPlannedLength(visit));
        totalsDTO.setTotalPrice(visitService.getTotalPrice(visit));
        return totalsDTO;
    }

}
