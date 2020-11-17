package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dto.EmploymentDTO;
import cz.muni.fi.pa165.entity.Employment;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.EmploymentService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Employment facade tests
 *
 * @author Marek Radimersky, 456518
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class EmploymentFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    @Mock
    private EmploymentService employmentService;

    private EmploymentFacadeImpl employmentFacade;

    private EmploymentDTO employmentDTO;

    private Employment employment;

    private EmploymentDTO employmentDTO2;

    private Employment employment2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employmentFacade = new EmploymentFacadeImpl(beanMappingService, employmentService);
    }

    @BeforeMethod
    public void createEntities() {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setAddress("address");
        person1.setName("Name");
        person1.setSurname("Surname");
        person1.setPhoneNumber("123456789");

        employment = new Employment();
        employment.setId(1L);
        employment.setPositionName("Position");
        employment.setEndDate(new Date(2323223232L));
        employment.setStartDate(new Date());
        employment.setPerson(person1);

        employmentDTO = beanMappingService.mapTo(employment, EmploymentDTO.class);

        employment2 = new Employment();
        employment2.setId(2L);
        employment2.setPositionName("Position2");
        employment2.setEndDate(new Date(2323223232L));
        employment2.setStartDate(new Date());
        employment2.setPerson(person1);

        employmentDTO2 = beanMappingService.mapTo(employment2, EmploymentDTO.class);
    }

    @Test
    public void createEmploymentTest() {
        employmentFacade.createEmployment(employmentDTO);
        verify(employmentService).create(employment);
    }

    @Test
    public void updateEmploymentTest() {
        employmentDTO.setPositionName("Janitor");
        Employment updated = beanMappingService.mapTo(employmentDTO, Employment.class);
        employmentFacade.updateEmployment(employmentDTO);
        verify(employmentService).update(updated);
    }

    @Test
    public void deleteEmploymentTest() {
        employmentFacade.deleteEmployment(employmentDTO);
        verify(employmentService).delete(employment);
    }

    @Test
    public void getEmploymentTest() {
        when(employmentService.get(employment.getId())).thenReturn(employment);
        Assert.assertEquals(employmentFacade.getEmployment(employment.getId()), employmentDTO);
    }

    @Test
    public void getAllEmploymentsTest() {
        List<Employment> employmentList = new ArrayList<>();
        employmentList.add(employment);
        employmentList.add(employment2);

        when(employmentService.getAll()).thenReturn(employmentList);
        List<EmploymentDTO> employmentDtoList = employmentFacade.getAllEmployments();
        verify(employmentService).getAll();

        Assert.assertEquals(employmentDtoList.size(), 2);
    }

}
