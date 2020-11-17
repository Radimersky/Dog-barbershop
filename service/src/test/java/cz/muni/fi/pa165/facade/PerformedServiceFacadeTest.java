package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.PerformedServiceService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Performed service facade tests
 *
 * @author Marek Radimersky, 456518
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class PerformedServiceFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    @Mock
    private PerformedServiceService performedServiceService;

    private PerformedServiceFacadeImpl performedServiceFacade;

    private PerformedServiceDTO performedServiceDTO;

    private PerformedService performedService;


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        performedServiceFacade = new PerformedServiceFacadeImpl(beanMappingService, performedServiceService);
    }

    @BeforeMethod
    public void createEntities() {

        PersonDTO person = new PersonDTO();
        person.setId(1L);
        person.setAddress("adresa");
        person.setName("Nemo");
        person.setSurname("Riezpisko");
        person.setPhoneNumber("+123456");

        Person person1 = new Person();
        person1.setId(1L);
        person1.setAddress("adresa");
        person1.setName("Nemo");
        person1.setSurname("Riezpisko");
        person1.setPhoneNumber("+123456");

        DogDTO dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setBreed("pes");
        dogDTO.setDateOfBirth(Date.from(Instant.now()));
        dogDTO.setGender(Gender.MALE);
        dogDTO.setName("PESOpes");
        dogDTO.setOwner(person);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setBreed("pes");
        dog.setDateOfBirth(dogDTO.getDateOfBirth());
        dog.setGender(Gender.MALE);
        dog.setName("PESOpes");
        dog.setOwner(person1);

        Visit visit = new Visit();
        visit.setDog(dog);
        visit.setFinish(Date.from(Instant.now()));
        visit.setStart(Date.from(Instant.now()));
        visit.setId(1L);

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setDog(dogDTO);
        visitDTO.setFinish(visit.getFinish());
        visitDTO.setStart(visit.getStart());
        visitDTO.setId(1L);

        ServiceType serviceType = new ServiceType();
        serviceType.setId(1L);
        serviceType.setName("Washing");
        serviceType.setPrice(BigDecimal.TEN);
        serviceType.setStandardLength(null);
        serviceType.setDescription("Your dog's fur will be sparkling clean and soft after he's washed.");

        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();
        serviceTypeDTO.setId(1L);
        serviceTypeDTO.setName("Washing");
        serviceTypeDTO.setPrice(BigDecimal.TEN);
        serviceTypeDTO.setStandardLength(null);
        serviceTypeDTO.setDescription("Your dog's fur will be sparkling clean and soft after he's washed.");

        performedService = new PerformedService();
        performedService.setId(1L);
        performedService.setServiceType(serviceType);
        performedService.setVisit(visit);

        performedServiceDTO = new PerformedServiceDTO();
        performedServiceDTO.setId(1L);
        performedServiceDTO.setServiceType(serviceTypeDTO);
        performedServiceDTO.setVisit(visitDTO);
    }

    @Test
    public void createPerformedServiceTest() {
        performedServiceFacade.createPerformedService(performedServiceDTO);
        verify(performedServiceService).create(any());
    }

    @Test
    public void updatePerformedServiceTest() {
        performedServiceDTO.setId(3L);
        PerformedService updated = beanMappingService.mapTo(performedServiceDTO, PerformedService.class);
        performedServiceFacade.updatePerformedService(performedServiceDTO);
        verify(performedServiceService).update(any());
    }

    @Test
    public void deletePerformedServiceTest() {
        performedServiceFacade.deletePerformedService(performedServiceDTO);
        verify(performedServiceService).remove(any());
    }

    @Test
    public void getAllPerformedServicesTest() {
        List<PerformedService> performedServiceList = new ArrayList<>();
        performedServiceList.add(performedService);

        when(performedServiceService.getAll()).thenReturn(performedServiceList);
        List<PerformedServiceDTO> performedServiceDtoList = performedServiceFacade.getAllPerformedServices();
        verify(performedServiceService).getAll();

        Assert.assertEquals(performedServiceDtoList.size(), 1);
    }

}
