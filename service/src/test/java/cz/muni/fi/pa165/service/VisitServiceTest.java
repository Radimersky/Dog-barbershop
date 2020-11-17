package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dao.DogDao;
import cz.muni.fi.pa165.dao.VisitDao;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

/**
 * Visit service test class
 *
 * @author Marek Radimersky, 456518
 */
@ContextConfiguration(classes = {MappingServiceConf.class})
public class VisitServiceTest {

    @Mock
    private VisitDao visitDao;

    @Mock
    private DogDao dogDao;

    @Mock
    private PerformedServiceService performedServiceService;

    @Mock
    private ServiceTypeService serviceTypeService;

    @Autowired
    @InjectMocks
    private VisitServiceImpl visitService;
    private Person person;
    private Dog dog;
    private Visit visit;
    private ServiceType serviceType1;
    private ServiceType serviceType2;
    private PerformedService performedService1;
    private PerformedService performedService2;
    private List<PerformedService> performedServices;
    private List<ServiceType> serviceTypes;

    @BeforeClass
    public void setUp() throws ServiceDataAccessException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createEntitites() {
        person = new Person();
        person.setName("John");
        person.setSurname("Doe");
        person.setAddress("Oak Street 12, Some City");
        person.setPhoneNumber("12345678");
        person.setPassword("password");

        dog = new Dog();
        dog.setName("Doge");
        dog.setBreed("poodle");
        dog.setDateOfBirth(Date.valueOf("2018-05-12"));
        dog.setGender(Gender.MALE);
        dog.setOwner(person);

        visit = new Visit();
        visit.setId(1L);
        visit.setDog(dog);
        visit.setStart(Date.valueOf("2019-05-12"));
        visit.setFinish(Date.valueOf("2019-05-13"));


        serviceType1 = new ServiceType();
        serviceType1.setDescription("first service description");
        serviceType1.setName("First Name");
        serviceType1.setPrice(BigDecimal.TEN);
        serviceType1.setStandardLength(Duration.ofHours(1));
        serviceType1.setId(1L);

        performedService1 = new PerformedService();
        performedService1.setVisit(visit);
        performedService1.setId(1L);
        performedService1.setServiceType(serviceType1);

        serviceType2 = new ServiceType();
        serviceType2.setDescription("second service description");
        serviceType2.setId(2L);
        serviceType2.setName("SECOND Name");
        serviceType2.setPrice(BigDecimal.ONE);
        serviceType2.setStandardLength(Duration.ofHours(2));

        performedService2 = new PerformedService();
        performedService2.setVisit(visit);
        performedService2.setId(2L);
        performedService2.setServiceType(serviceType2);

        performedServices = new ArrayList<>();
        performedServices.add(performedService1);
        performedServices.add(performedService2);

        serviceTypes = new ArrayList<>();
        serviceTypes.add(serviceType1);
        serviceTypes.add(serviceType2);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullDogTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getDog() == null) {
                throw new IllegalArgumentException("Dog cannot be null.");
            }
            return visit;
        }).when(visitDao).createVisit(any(Visit.class));

        visit.setDog(null);
        visitService.create(visit);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullStartTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getStart() == null) {
                throw new IllegalArgumentException("Start cannot be null.");
            }
            return visit;
        }).when(visitDao).createVisit(any(Visit.class));

        visit.setStart(null);
        visitService.create(visit);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullFinishTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getFinish() == null) {
                throw new IllegalArgumentException("Finish cannot be null.");
            }
            return visit;
        }).when(visitDao).createVisit(any(Visit.class));

        visit.setFinish(null);
        visitService.create(visit);
    }


    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullDogTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getDog() == null) {
                throw new IllegalArgumentException("Dog cannot be null.");
            }
            return visit;
        }).when(visitDao).updateVisit(any(Visit.class));

        visit.setDog(null);
        visitService.update(visit);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullStartTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getStart() == null) {
                throw new IllegalArgumentException("Start cannot be null.");
            }
            return visit;
        }).when(visitDao).updateVisit(any(Visit.class));

        visit.setStart(null);
        visitService.update(visit);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullFinishTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getFinish() == null) {
                throw new IllegalArgumentException("Finish cannot be null.");
            }
            return visit;
        }).when(visitDao).updateVisit(any(Visit.class));

        visit.setFinish(null);
        visitService.update(visit);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithIdTest() {
        doAnswer(invocationOnMock -> {
            Visit visit = invocationOnMock.getArgumentAt(0, Visit.class);
            if (visit == null || visit.getId() != null) {
                throw new IllegalArgumentException("Visit cannot have id before it is created.");
            }
            return dog;
        }).when(visitDao).createVisit(any(Visit.class));

        visit.setId(1L);
        visitService.create(visit);
    }

    @Test
    public void findAllWhenEmptyTest() {
        when(visitDao.getAllVisits()).thenReturn(new ArrayList<>());
        Assert.assertTrue(visitService.getAll().isEmpty());
    }

    @Test
    public void findNonExistingTest() {
        long idToBeFound = 17L;
        when(visitDao.getVisit(idToBeFound)).thenReturn(null);
        Assert.assertNull(visitService.get(idToBeFound));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findWithNullIdTest() {
        doThrow(IllegalArgumentException.class).when(visitDao).getVisit(null);
        visitService.get(null);
    }

    @Test
    public void getByIdTest() {
        Long id = Long.MAX_VALUE;
        visitService.get(id);
        verify(visitDao).getVisit(id);
    }

    @Test
    public void getAllTest() {
        List<Visit> services = new ArrayList<>();
        services.add(visit);

        when(visitDao.getAllVisits()).thenReturn(services);
        List<Visit> servicesGot = visitService.getAll();
        Assert.assertEquals(servicesGot.size(), 1);
        Assert.assertEquals(servicesGot, services);
    }

    @Test
    public void createVisitTest() {
        visit.setId(null);
        when(dogDao.getDog(any())).thenReturn(dog);
        visitService.create(visit);
        verify(visitDao, times(1)).createVisit(visit);
    }

    @Test
    public void removeTest() {
        visitService.delete(visit);
        verify(visitDao, times(1)).deleteVisit(visit);
    }

    @Test
    public void updateTest() {
        visit.setFinish(Date.valueOf("2019-05-15"));
        visitService.update(visit);
        verify(visitDao, times(1)).updateVisit(visit);
    }


    @Test
    public void getPlannedLengthTest() {
        when(serviceTypeService.getAll()).thenReturn(serviceTypes);
        when(performedServiceService.getAll()).thenReturn(performedServices);
        Assert.assertEquals(visitService.getPlannedLength(visit), Duration.ZERO.plus(serviceType1.getStandardLength()).plus(serviceType2.getStandardLength()));
    }

    @Test
    public void getTotalPrice() {
        when(serviceTypeService.getAll()).thenReturn(serviceTypes);
        when(performedServiceService.getAll()).thenReturn(performedServices);
        Assert.assertEquals(visitService.getTotalPrice(visit), BigDecimal.ZERO.add(serviceType1.getPrice()).add(serviceType2.getPrice()));

    }

    @Test
    public void getProcedureDescriptionsForVisit() {
        when(serviceTypeService.getAll()).thenReturn(serviceTypes);
        when(performedServiceService.getAll()).thenReturn(performedServices);
        Assert.assertEquals(visitService.getProcedureDescriptionsForVisit(visit),
                "\n " + serviceType1.getName() + " \n \n" + serviceType1.getDescription()
                        + "\n " + serviceType2.getName() + " \n \n" + serviceType2.getDescription());

    }
}
