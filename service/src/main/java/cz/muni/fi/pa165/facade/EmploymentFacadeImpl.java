package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EmploymentDTO;
import cz.muni.fi.pa165.entity.Employment;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EmploymentFacade interface implementation
 *
 * @author Matúš Špik
 */
@Service
@Transactional
public class EmploymentFacadeImpl implements EmploymentFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private EmploymentService employmentService;

    public EmploymentFacadeImpl() {
    }

    public EmploymentFacadeImpl(BeanMappingService beanMappingService, EmploymentService employmentService) {
        this.beanMappingService = beanMappingService;
        this.employmentService = employmentService;
    }


    @Override
    public Long createEmployment(EmploymentDTO employmentDTO) {
        Employment employment = beanMappingService.mapTo(employmentDTO, Employment.class);
        employmentService.create(employment);
        return employment.getId();
    }

    @Override
    public void updateEmployment(EmploymentDTO employmentDTO) {
        employmentService.update(beanMappingService.mapTo(employmentDTO, Employment.class));
    }

    @Override
    public void deleteEmployment(EmploymentDTO employmentDTO) {
        employmentService.delete(beanMappingService.mapTo(employmentDTO, Employment.class));
    }

    @Override
    public EmploymentDTO getEmployment(Long id) {
        Employment employment = employmentService.get(id);
        return beanMappingService.mapTo(employment, EmploymentDTO.class);
    }

    @Override
    public List<EmploymentDTO> getAllEmployments() {
        return beanMappingService.mapTo(employmentService.getAll(), EmploymentDTO.class);
    }

    @Override
    public List<EmploymentDTO> filterEmployments(String phone, String name) {
        return beanMappingService.mapTo(employmentService.filter(phone, name), EmploymentDTO.class);
    }
}
