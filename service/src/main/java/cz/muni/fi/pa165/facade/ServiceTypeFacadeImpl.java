package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ServiceTypeDTO;
import cz.muni.fi.pa165.entity.ServiceType;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service type facade implementation
 *
 * @author Marek Radimersky, 456518
 */
@Service
@Transactional
public class ServiceTypeFacadeImpl implements ServiceTypeFacade {

    @Autowired
    private final ServiceTypeService serviceTypeService;

    @Autowired
    private final BeanMappingService beanMappingService;

    public ServiceTypeFacadeImpl(BeanMappingService mappingService, ServiceTypeService serviceTypeService) {
        this.beanMappingService = mappingService;
        this.serviceTypeService = serviceTypeService;
    }

    @Override
    public Long createServiceType(ServiceTypeDTO dto) {
        ServiceType serviceType = beanMappingService.mapTo(dto, ServiceType.class);
        serviceTypeService.create(serviceType);
        return serviceType.getId();
    }

    @Override
    public List<ServiceTypeDTO> getAllServiceTypes() {
        return beanMappingService.mapTo(serviceTypeService.getAll(), ServiceTypeDTO.class);
    }

    @Override
    public ServiceTypeDTO getServiceType(Long id) {
        ServiceType serviceType = serviceTypeService.getById(id);
        return beanMappingService.mapTo(serviceType, ServiceTypeDTO.class);
    }

    @Override
    public void updateServiceType(ServiceTypeDTO dto) {
        serviceTypeService.update(beanMappingService.mapTo(dto, ServiceType.class));
    }

    @Override
    public void deleteServiceType(ServiceTypeDTO dto) {
        serviceTypeService.remove(beanMappingService.mapTo(dto, ServiceType.class));
    }

}
