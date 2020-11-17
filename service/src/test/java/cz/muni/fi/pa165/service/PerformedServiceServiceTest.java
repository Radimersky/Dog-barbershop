package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dao.PerformedServiceDao;
import cz.muni.fi.pa165.dao.ServiceTypeDao;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Aneta Moravcikova, 456444
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class PerformedServiceServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private PerformedServiceDao performedServiceDao;

    @Mock
    private ServiceTypeDao serviceTypeDao;

    @Autowired
    @InjectMocks
    private PerformedServiceService performedServiceService;

    private ServiceType haircut;
    private Visit visit;
    private Visit visit2;
    private Dog dog;
    private Person owner;
    private PerformedService performedService;
    private PerformedService performedService2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(serviceTypeDao);
    }

    @BeforeMethod
    public void setupBeforeMethod() throws ServiceException {

        haircut = new ServiceType();
        haircut.setName("Haircut");
        haircut.setPrice(BigDecimal.ONE);
        haircut.setStandardLength(Duration.ofHours(2));
        haircut.setDescription("Full body haircut for your dog with nail trimming included.");

        owner = new Person();
        owner.setName("John");
        owner.setSurname("Doe");
        owner.setAddress("Brno");
        owner.setPassword("passw0rd");
        owner.setPhoneNumber("123456");

        dog = new Dog();
        dog.setName("Larry");
        dog.setBreed("Newfoundland");
        dog.setOwner(owner);

        visit = new Visit();
        visit.setStart(Date.from(Instant.parse("2020-01-01T10:00:00.000Z")));
        visit.setFinish(Date.from(Instant.parse("2020-01-01T11:00:00.000Z")));
        visit.setDog(dog);

        performedService = new PerformedService();
        performedService.setVisit(visit);
        performedService.setServiceType(haircut);

        visit2 = new Visit();
        visit2.setStart(Date.from(Instant.parse("2020-03-01T12:30:00.000Z")));
        visit2.setFinish(Date.from(Instant.parse("2020-03-01T15:00:00.000Z")));
        visit2.setDog(dog);

        performedService2 = new PerformedService();
        performedService2.setVisit(visit2);
        performedService2.setServiceType(haircut);

    }

    @Test
    public void create() {
        performedServiceService.create(performedService);
        verify(performedServiceDao).createPerformedService(performedService);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullVisit() {
        doAnswer(invocationOnMock -> {
            PerformedService ps = invocationOnMock.getArgumentAt(0, PerformedService.class);
            if (ps == null || ps.getVisit() == null) {
                throw new IllegalArgumentException("Performed service cannot have null visit.");
            }
            return ps;
        }).when(performedServiceDao).createPerformedService(any(PerformedService.class));

        performedService.setVisit(null);
        performedServiceService.create(performedService);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullServiceType() {
        doAnswer(invocationOnMock -> {
            PerformedService ps = invocationOnMock.getArgumentAt(0, PerformedService.class);
            if (ps == null || ps.getServiceType() == null) {
                throw new IllegalArgumentException("Performed service cannot have null service type.");
            }
            return ps;
        }).when(performedServiceDao).createPerformedService(any(PerformedService.class));

        performedService.setServiceType(null);
        performedServiceService.create(performedService);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithId() {
        doAnswer(invocationOnMock -> {
            PerformedService ps = invocationOnMock.getArgumentAt(0, PerformedService.class);
            if (ps == null || ps.getId() != null) {
                throw new IllegalArgumentException("Performed service cannot have id before it is created.");
            }
            return ps;
        }).when(performedServiceDao).createPerformedService(any(PerformedService.class));

        performedService.setId(1L);
        performedServiceService.create(performedService);
    }

    @Test
    public void getAllWhenEmptyDB() {
        when(performedServiceDao.getAllPerformedServices()).thenReturn(new ArrayList<>());
        Assert.assertTrue(performedServiceService.getAll().isEmpty());
    }

    @Test
    public void getAll() {
        List<PerformedService> services = new ArrayList<>();
        services.add(performedService);
        services.add(performedService2);

        when(performedServiceDao.getAllPerformedServices()).thenReturn(services);
        List<PerformedService> servicesGot = performedServiceService.getAll();
        Assert.assertEquals(servicesGot.size(), 2);
        Assert.assertEquals(servicesGot, services);
    }

    @Test
    public void get() {
        performedService.setId(1L);
        when(performedServiceDao.getPerformedService(1L)).thenReturn(performedService);
        Assert.assertEquals(performedServiceService.getById(1L), performedService);
    }

    @Test
    public void getNonExistent() {
        when(performedServiceDao.getPerformedService(100L)).thenReturn(null);
        Assert.assertNull(performedServiceService.getById(100L));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void getWithNullId() {
        doThrow(IllegalArgumentException.class).when(performedServiceDao).getPerformedService(null);
        performedServiceService.getById(null);
    }

    @Test
    public void update() {
        performedService.setVisit(visit2);
        performedServiceService.update(performedService);
        verify(performedServiceDao).updatePerformedService(performedService);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullVisit() {
        doAnswer(invocationOnMock -> {
            PerformedService ps = invocationOnMock.getArgumentAt(0, PerformedService.class);
            if (ps == null || ps.getVisit() == null) {
                throw new IllegalArgumentException("Performed service cannot have null visit.");
            }
            return ps;
        }).when(performedServiceDao).updatePerformedService(any(PerformedService.class));

        performedService.setVisit(null);
        performedServiceService.update(performedService);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullServiceType() {
        doAnswer(invocationOnMock -> {
            PerformedService ps = invocationOnMock.getArgumentAt(0, PerformedService.class);
            if (ps == null || ps.getServiceType() == null) {
                throw new IllegalArgumentException("Performed service cannot have null service type.");
            }
            return ps;
        }).when(performedServiceDao).updatePerformedService(any(PerformedService.class));

        performedService.setServiceType(null);
        performedServiceService.update(performedService);
    }

    @Test
    public void remove() {
        performedServiceService.remove(performedService);
        verify(performedServiceDao).deletePerformedService(performedService);
    }
}
