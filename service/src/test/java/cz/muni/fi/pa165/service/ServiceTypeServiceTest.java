package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dao.ServiceTypeDao;
import cz.muni.fi.pa165.entity.ServiceType;
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
import java.util.ArrayList;
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
public class ServiceTypeServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ServiceTypeDao serviceTypeDao;

    @Autowired
    @InjectMocks
    private ServiceTypeService serviceTypeService;

    private ServiceType washing;
    private ServiceType haircut;

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
        washing = new ServiceType();
        washing.setName("Washing");
        washing.setPrice(BigDecimal.TEN);
        washing.setStandardLength(Duration.ofHours(1));
        washing.setDescription("Your dog's fur will be sparkling clean and soft after he's washed.");

        haircut = new ServiceType();
        haircut.setName("Haircut");
        haircut.setPrice(BigDecimal.ONE);
        haircut.setStandardLength(Duration.ofHours(2));
        haircut.setDescription("Full body haircut for your dog with nail trimming included.");

    }

    @Test
    public void create() {
        serviceTypeService.create(washing);
        verify(serviceTypeDao).createServiceType(washing);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullName() {
        doAnswer(invocationOnMock -> {
            ServiceType serviceType = invocationOnMock.getArgumentAt(0, ServiceType.class);
            if (serviceType == null || serviceType.getName() == null) {
                throw new IllegalArgumentException("Service type cannot have null name.");
            }
            return serviceType;
        }).when(serviceTypeDao).createServiceType(any(ServiceType.class));

        haircut.setName(null);
        serviceTypeService.create(haircut);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullDescription() {
        doAnswer(invocationOnMock -> {
            ServiceType serviceType = invocationOnMock.getArgumentAt(0, ServiceType.class);
            if (serviceType == null || serviceType.getDescription() == null) {
                throw new IllegalArgumentException("Service type cannot have null description.");
            }
            return serviceType;
        }).when(serviceTypeDao).createServiceType(any(ServiceType.class));

        haircut.setDescription(null);
        serviceTypeService.create(haircut);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithId() {
        doAnswer(invocationOnMock -> {
            ServiceType serviceType = invocationOnMock.getArgumentAt(0, ServiceType.class);
            if (serviceType == null || serviceType.getId() != null) {
                throw new IllegalArgumentException("ServiceType cannot have id before it is created.");
            }
            return serviceType;
        }).when(serviceTypeDao).createServiceType(any(ServiceType.class));

        haircut.setId(1L);
        serviceTypeService.create(haircut);
    }

    @Test
    public void getAllWhenEmptyDB() {
        when(serviceTypeDao.getAllServiceTypes()).thenReturn(new ArrayList<>());
        Assert.assertTrue(serviceTypeService.getAll().isEmpty());
    }

    @Test
    public void getAll() {
        List<ServiceType> services = new ArrayList<>();
        services.add(haircut);
        services.add(washing);

        when(serviceTypeDao.getAllServiceTypes()).thenReturn(services);
        List<ServiceType> servicesGot = serviceTypeService.getAll();
        Assert.assertEquals(servicesGot.size(), 2);
        Assert.assertEquals(servicesGot, services);
    }

    @Test
    public void get() {
        haircut.setId(1L);
        when(serviceTypeDao.getServiceType(1L)).thenReturn(haircut);
        Assert.assertEquals(serviceTypeService.getById(1L), haircut);
    }

    @Test
    public void getNonExistent() {
        when(serviceTypeDao.getServiceType(100L)).thenReturn(null);
        Assert.assertNull(serviceTypeService.getById(100L));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void getWithNullId() {
        doThrow(IllegalArgumentException.class).when(serviceTypeDao).getServiceType(null);
        serviceTypeService.getById(null);
    }

    @Test
    public void update() {
        haircut.setDescription("New Description");
        serviceTypeService.update(haircut);
        verify(serviceTypeDao).updateServiceType(haircut);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullName() {
        doAnswer(invocationOnMock -> {
            ServiceType serviceType = invocationOnMock.getArgumentAt(0, ServiceType.class);
            if (serviceType == null || serviceType.getName() == null) {
                throw new IllegalArgumentException("Service type cannot have null name.");
            }
            return serviceType;
        }).when(serviceTypeDao).updateServiceType(any(ServiceType.class));

        haircut.setName(null);
        serviceTypeService.update(haircut);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullDescription() {
        doAnswer(invocationOnMock -> {
            ServiceType serviceType = invocationOnMock.getArgumentAt(0, ServiceType.class);
            if (serviceType == null || serviceType.getDescription() == null) {
                throw new IllegalArgumentException("Service type cannot have null description.");
            }
            return serviceType;
        }).when(serviceTypeDao).updateServiceType(any(ServiceType.class));

        haircut.setDescription(null);
        serviceTypeService.update(haircut);
    }

    @Test
    public void remove() {
        serviceTypeService.remove(washing);
        verify(serviceTypeDao).deleteServiceType(washing);
    }
}