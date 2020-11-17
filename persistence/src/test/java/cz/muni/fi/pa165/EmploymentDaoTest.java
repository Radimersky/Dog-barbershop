package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.EmploymentDao;
import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Employment;
import cz.muni.fi.pa165.entity.Person;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test class for interface EmploymentDao
 *
 * @author Matúš Špik
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EmploymentDaoTest extends AbstractTestNGSpringContextTests {

    private final String[] validNaming = {"Ivan", "Jozef", "Patrik", "Stefan", "Martin", "Aneta", "Marek", "Matus"};
    private final String[] positions = {"manager", "professional", "old-timer", "rookie"};

    @Autowired
    private EmploymentDao employmentDao;

    @Autowired
    private PersonDao personDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void create() {
        Employment employment = getRandomEmployment();
        employmentDao.createEmployment(employment);
        Employment retreivedEmployment = employmentDao.getEmployment(employment.getId());
        Assert.assertEquals(retreivedEmployment, employment);
    }

    @Test(expectedExceptions = {DataAccessException.class, ConstraintViolationException.class})
    public void storeWithNullPerson() {
        Employment employmentToStore = new Employment(
                null, getRandomPosition(), getRandomStartEndDates().get(0), getRandomStartEndDates().get(1));
        employmentDao.createEmployment(employmentToStore);
    }

    @Test(expectedExceptions = {DataAccessException.class, ConstraintViolationException.class})
    public void storeWithNullStartDate() {
        Employment employmentToStore = new Employment(
                getRandomPerson(), getRandomPosition(), null, getRandomStartEndDates().get(1));
        employmentDao.createEmployment(employmentToStore);
    }

    @Test
    public void createMany() {
        List<Employment> employments = getRandomEmploymentList(10);
        for (Employment employment : employments) {
            employmentDao.createEmployment(employment);
        }
        List<Employment> employmentReturnedList = employmentDao.getAllEmployments();
        Assert.assertTrue(employmentReturnedList.containsAll(employments));
        Assert.assertEquals(employmentReturnedList.size(), employments.size());
    }

    @Test
    public void update() {
        Employment employment = getRandomEmployment();
        employmentDao.createEmployment(employment);
        entityManager.flush();
        entityManager.detach(employment);
        employment.setPositionName("SpecialTestingName");
        employmentDao.updateEmployment(employment);
        Employment returnedEmployment = employmentDao.getEmployment(employment.getId());
        Assert.assertEquals(returnedEmployment, employment);
    }

    @Test
    public void delete() {
        List<Employment> employments = getRandomEmploymentList(10);
        Employment employmentToRemove = employments.get(new Random().nextInt(employments.size()));
        for (Employment employment : employments) {
            employmentDao.createEmployment(employment);
        }
        employmentDao.deleteEmployment(employmentToRemove);
        Assert.assertFalse(employmentDao.getAllEmployments().contains(employmentToRemove));
    }

    @Test
    public void getByWrongId() {
        Assert.assertNull(employmentDao.getEmployment(Long.MAX_VALUE));
        Assert.assertNull(employmentDao.getEmployment(Long.MIN_VALUE));
    }

    private List<Employment> getRandomEmploymentList(int employmentCount) {
        List<Employment> result = new ArrayList<>();
        for (int i = 0; i < employmentCount; i++) {
            result.add(getRandomEmployment());
        }
        return result;
    }

    private Employment getRandomEmployment() {
        List<Date> dates = getRandomStartEndDates();
        Employment employment = new Employment(getRandomPerson(), getRandomPosition(), dates.get(0), dates.get(1));
        employmentDao.createEmployment(employment);
        return employment;
    }

    private Person getRandomPerson() {
        Person person = new Person(getRandomValidNamingValue(), getRandomValidNamingValue(), getRandomValidNamingValue(), "+" + new Random().nextInt(10000), "password");
        personDao.createPerson(person);
        return person;
    }

    private String getRandomValidNamingValue() {
        return validNaming[new Random().nextInt(validNaming.length)];
    }

    private String getRandomPosition() {
        return positions[new Random().nextInt(positions.length)];
    }

    public static List<Date> getRandomStartEndDates() {
        long startMillis = Date.from(Instant.EPOCH).getTime();
        long endMillis = Date.from(Instant.now()).getTime();
        long startRandomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);
        long endRandomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        List<Date> dates = new ArrayList<>();
        dates.add(new Date(startRandomMillisSinceEpoch));
        dates.add(new Date(endRandomMillisSinceEpoch));

        return dates;
    }
}
