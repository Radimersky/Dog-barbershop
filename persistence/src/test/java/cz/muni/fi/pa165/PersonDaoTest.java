package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.PersonDao;
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
import java.util.List;

/**
 * Test class for interface PersonDao
 *
 * @author Aneta Moravcikova, 456444
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class PersonDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PersonDao personDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void create() {
        Person person = createNewPerson();

        List<Person> people = personDao.getAllPersons();
        Assert.assertEquals(people.size(), 1);

        Person personGot = people.get(0);
        Assert.assertEquals(person, personGot);

    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNull() {
        personDao.createPerson(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullName() {
        Person person = new Person(null, "Doe", "Oak Street 12, Some City", "12345678", "password");
        personDao.createPerson(person);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithWrongName() {
        Person person = new Person("00>>***", "Doe", "Oak Street 12, Some City", "12345678", "password");
        personDao.createPerson(person);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullSurname() {
        Person person = new Person("Jane", null, "Oak Street 12, Some City", "12345678", "password");
        personDao.createPerson(person);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithWrongSurname() {
        Person person = new Person("Jane", "****", "Oak Street 12, Some City", "12345678", "password");
        personDao.createPerson(person);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullAddress() {
        Person person = new Person("Jane", "Doe", null, "12345678", "password");
        personDao.createPerson(person);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithWrongAddress() {
        Person person = new Person("Jane", "Doe", "<>>", "12345678", "password");
        personDao.createPerson(person);
    }


    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullPhoneNumber() {
        Person person = new Person("Jane", "Doe", "Oak Street 12, Some City", null, "password");
        personDao.createPerson(person);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithWrongPhoneNumber() {
        Person person = new Person("Jane", "Doe", "Oak Street 12, Some City", "AAAAA", "password");
        personDao.createPerson(person);
    }

    @Test
    public void getById() {
        Person person = createNewPerson();

        Person foundPerson = personDao.getPerson(person.getId());
        Assert.assertEquals(person, foundPerson);
    }

    @Test
    public void getByWrongId() {
        Assert.assertNull(personDao.getPerson(Long.MAX_VALUE));
        Assert.assertNull(personDao.getPerson(Long.MIN_VALUE));
    }

    @Test
    public void update() {
        Person person = createNewPerson();

        entityManager.flush();
        entityManager.detach(person);
        person.setName("DifferentName");
        person.setSurname("DifferentSurname");
        personDao.updatePerson(person);
        Person updatedPerson = personDao.getPerson(person.getId());
        Assert.assertEquals(person, updatedPerson);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNull() {
        personDao.updatePerson(null);
    }

    @Test
    public void delete() {

        Person person = createNewPerson();

        List<Person> people = personDao.getAllPersons();
        Assert.assertEquals(people.size(), 1);
        personDao.deletePerson(person);


        List<Person> people2 = personDao.getAllPersons();
        Assert.assertFalse(personDao.getAllPersons().contains(person));
        Assert.assertEquals(people2.size(), 0);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNull() {
        personDao.deletePerson(null);
    }


    @Test
    public void findAll() {
        Person john = createNewPerson();
        Person jane = createAnotherPerson();

        List<Person> people = personDao.getAllPersons();
        Assert.assertEquals(people.size(), 2);

        Assert.assertEquals(people.get(0), john);
        Assert.assertEquals(people.get(1), jane);
    }

    @Test
    public void findAllInEmpty() {
        List<Person> people = personDao.getAllPersons();
        Assert.assertTrue(people.isEmpty());
    }

    private Person createNewPerson() {
        Person person = new Person("John", "Doe", "Oak Street 12, Some City", "12345678", "password");
        personDao.createPerson(person);
        return person;
    }

    private Person createAnotherPerson() {
        Person person = new Person("Jane", "Surname", "Another Street 5, Another City", "87654321", "password");
        personDao.createPerson(person);
        return person;
    }
}
