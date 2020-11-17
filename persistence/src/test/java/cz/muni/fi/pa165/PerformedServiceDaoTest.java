package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test class for interface PerformedServiceDao
 *
 * @author Matúš Špik
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PerformedServiceDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PerformedServiceDao performedServiceDao;

    @Autowired
    private ServiceTypeDao serviceTypeDao;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private DogDao dogDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void create() {
        PerformedService performedService = createPerformedService();
        performedServiceDao.createPerformedService(performedService);
        PerformedService retreivedPerformedService = performedServiceDao.getPerformedService(performedService.getId());
        Assert.assertEquals(retreivedPerformedService, performedService);
    }

    @Test(expectedExceptions = {DataAccessException.class, ConstraintViolationException.class})
    public void storeWithNullVisit() {
        PerformedService performedServiceToStore = new PerformedService(
                null, createServiceType()
        );
        performedServiceDao.createPerformedService(performedServiceToStore);
    }

    @Test(expectedExceptions = {DataAccessException.class, ConstraintViolationException.class})
    public void storeWithNullServiceType() {
        PerformedService performedServiceToStore = new PerformedService(
                createNewVisit(), null
        );
        performedServiceDao.createPerformedService(performedServiceToStore);
    }

    @Test
    public void createMany() {
        List<PerformedService> performedServices = new ArrayList<>();
        performedServices.add(createPerformedService());
        performedServices.add(createSpecialPerformedService());
        for (PerformedService performedService : performedServices) {
            performedServiceDao.createPerformedService(performedService);
        }
        List<PerformedService> performedServicesReturnedList = performedServiceDao.getAllPerformedServices();
        Assert.assertTrue(performedServicesReturnedList.containsAll(performedServices));
        Assert.assertEquals(performedServicesReturnedList.size(), performedServices.size());
    }

    @Test
    public void update() {
        PerformedService performedService = createPerformedService();
        performedServiceDao.createPerformedService(performedService);
        entityManager.flush();
        entityManager.detach(performedService);
        performedService.setVisit(createSpecialVisit());
        performedServiceDao.updatePerformedService(performedService);
        PerformedService retreivedPerformedService = performedServiceDao.getPerformedService(performedService.getId());
        Assert.assertEquals(retreivedPerformedService, performedService);
    }

    @Test
    public void delete() {
        List<PerformedService> performedServices = new ArrayList<>();
        performedServices.add(createPerformedService());
        performedServices.add(createSpecialPerformedService());
        PerformedService performedServiceToRemove = performedServices.get(new Random().nextInt(performedServices.size()));
        for (PerformedService performedService : performedServices) {
            performedServiceDao.createPerformedService(performedService);
        }
        performedServiceDao.deletePerformedService(performedServiceToRemove);
        Assert.assertFalse(performedServiceDao.getAllPerformedServices().contains(performedServiceToRemove));
    }

    @Test
    public void getByWrongId() {
        Assert.assertNull(performedServiceDao.getPerformedService(Long.MAX_VALUE));
        Assert.assertNull(performedServiceDao.getPerformedService(Long.MIN_VALUE));
    }

    private PerformedService createPerformedService() {
        ServiceType serviceType = createServiceType();
        Visit visit = createNewVisit();
        PerformedService performedService = new PerformedService(visit, serviceType);
        performedServiceDao.createPerformedService(performedService);
        return performedService;
    }

    private PerformedService createSpecialPerformedService() {
        ServiceType serviceType = createServiceType();
        Visit visit = createSpecialVisit();
        PerformedService performedService = new PerformedService(visit, serviceType);
        performedServiceDao.createPerformedService(performedService);
        return performedService;
    }

    private ServiceType createServiceType() {
        String serviceDesription = "Trim performed with electric razor, that can make every dog look stylish.";
        ServiceType serviceType = new ServiceType("Basic trim", Duration.ofMinutes(30), BigDecimal.valueOf(250), serviceDesription);
        serviceTypeDao.createServiceType(serviceType);
        return serviceType;
    }

    private Visit createNewVisit() {
        Person person = new Person("Jane", "Surname", "Another Street 5, Another City", "+" + new Random().nextInt(10000), "password", false);
        personDao.createPerson(person);

        Dog dog = new Dog("Larry", "Half Newfoundland", Gender.MALE, getRandomDate(), person);
        dogDao.createDog(dog);

        Visit visit = new Visit(dog, getRandomDate(), getRandomDate());
        visitDao.createVisit(visit);
        return visit;
    }

    private Visit createSpecialVisit() {
        Person person = new Person("Jane", "Surname", "Special Street 5, Special City", "+" + new Random().nextInt(10000), "password", false);
        personDao.createPerson(person);

        Dog dog = new Dog("Larry", "Special Newfoundland", Gender.MALE, getRandomDate(), person);
        dogDao.createDog(dog);

        Visit visit = new Visit(dog, getRandomDate(), getRandomDate());
        visitDao.createVisit(visit);
        return visit;
    }

    private static Date getRandomDate() {
        long startMillis = Date.from(Instant.EPOCH).getTime();
        long endMillis = Date.from(Instant.now()).getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

}
