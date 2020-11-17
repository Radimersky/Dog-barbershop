package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.DogDao;
import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.dao.VisitDao;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.entity.Visit;
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
import java.util.List;
import java.util.Random;

import static cz.muni.fi.pa165.DaoTestUtils.getRandomDate;

/**
 * Test class for interface VisitDao
 *
 * @author Aneta Moravcikova, 456444
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class VisitDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DogDao dogDao;

    @Autowired
    private PersonDao personDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void create() {
        Visit visit = createNewVisit();

        List<Visit> visits = visitDao.getAllVisits();
        Assert.assertEquals(visits.size(), 1);

        Visit visitGot = visits.get(0);
        Assert.assertEquals(visit, visitGot);

    }


    @Test(expectedExceptions = DataAccessException.class)
    public void createNull() {
        personDao.createPerson(null);
    }

    @Test
    public void getById() {
        Visit visit = createNewVisit();

        Visit visitFound = visitDao.getVisit(visit.getId());
        Assert.assertEquals(visit, visitFound);
    }

    @Test
    public void getByWrongId() {
        Assert.assertNull(visitDao.getVisit(Long.MAX_VALUE));
        Assert.assertNull(visitDao.getVisit(Long.MIN_VALUE));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullDog() {
        Visit visit = new Visit(null, getRandomDate(), getRandomDate());
        visitDao.createVisit(visit);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullStart() {
        Visit visit = new Visit(createDog(), null, getRandomDate());
        visitDao.createVisit(visit);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullFinish() {
        Visit visit = new Visit(createDog(), getRandomDate(), null);
        visitDao.createVisit(visit);
    }


    @Test
    public void update() {
        Visit visit = createNewVisit();

        entityManager.flush();
        entityManager.detach(visit);
        visit.setStart(getRandomDate());
        visitDao.updateVisit(visit);
        Visit visitUpdated = visitDao.getVisit(visit.getId());
        Assert.assertEquals(visit, visitUpdated);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNull() {
        visitDao.updateVisit(null);
    }


    @Test
    public void delete() {

        Visit visit = createNewVisit();

        List<Visit> visits = visitDao.getAllVisits();
        Assert.assertEquals(visits.size(), 1);
        visitDao.deleteVisit(visit);

        List<Visit> visits2 = visitDao.getAllVisits();
        Assert.assertEquals(visits2.size(), 0);
        Assert.assertFalse(visitDao.getAllVisits().contains(visit));
    }

    @Test
    public void findAll() {
        Visit visit = createNewVisit();
        Visit visit2 = createNewVisit();

        List<Visit> visits = visitDao.getAllVisits();
        Assert.assertEquals(visits.size(), 2);

        Assert.assertEquals(visits.get(0), visit);
        Assert.assertEquals(visits.get(1), visit2);
    }

    @Test
    public void findAllInEmpty() {
        List<Visit> visits = visitDao.getAllVisits();
        Assert.assertTrue(visits.isEmpty());
    }

    private Visit createNewVisit() {
        Visit visit = new Visit(createDog(), getRandomDate(), getRandomDate());
        visitDao.createVisit(visit);
        return visit;
    }

    private Dog createDog() {
        Person person = new Person("Jane", "Surname", "Another Street 5, Another City", "+" + new Random().nextInt(10000), "password");
        personDao.createPerson(person);

        Dog dog = new Dog("Larry", "Half Newfoundland", Gender.MALE, getRandomDate(), person);
        dogDao.createDog(dog);
        return dog;
    }
}
