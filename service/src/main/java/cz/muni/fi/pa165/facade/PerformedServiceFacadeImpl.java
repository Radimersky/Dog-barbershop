package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.PerformedServiceDTO;
import cz.muni.fi.pa165.entity.PerformedService;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.PerformedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PerformedServiceFacade interface implementation
 *
 * @author Aneta Moravcikova, 456444
 */

@Service
@Transactional
public class PerformedServiceFacadeImpl implements PerformedServiceFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private PerformedServiceService performedServiceService;

    public PerformedServiceFacadeImpl(BeanMappingService beanMappingService, PerformedServiceService performedServiceService) {
        this.beanMappingService = beanMappingService;
        this.performedServiceService = performedServiceService;
    }

    @Override
    public Long createPerformedService(PerformedServiceDTO performedServiceDTO) {
        PerformedService performedService = beanMappingService.mapTo(performedServiceDTO, PerformedService.class);
        performedServiceService.create(performedService);
        return performedService.getId();
    }

    @Override
    public void updatePerformedService(PerformedServiceDTO performedServiceDTO) {
        performedServiceService.update(beanMappingService.mapTo(performedServiceDTO, PerformedService.class));
    }

    @Override
    public void deletePerformedService(PerformedServiceDTO performedServiceDTO) {
        performedServiceService.remove(beanMappingService.mapTo(performedServiceDTO, PerformedService.class));
    }

    @Override
    public PerformedServiceDTO getPerformedService(Long id) {
        PerformedService performedService = performedServiceService.getById(id);
        return beanMappingService.mapTo(performedService, PerformedServiceDTO.class);
    }

    @Override
    public List<PerformedServiceDTO> getAllPerformedServices() {
        return beanMappingService.mapTo(performedServiceService.getAll(), PerformedServiceDTO.class);
    }
}
