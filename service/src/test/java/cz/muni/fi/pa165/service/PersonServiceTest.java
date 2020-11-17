package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
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
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matúš Špik
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class PersonServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private PersonDao personDao;

    @Autowired
    @InjectMocks
    private PersonService personService;

    private Person person;
    private Person person2;

    @BeforeClass
    public void setup() throws ServiceException {
    }

    @BeforeMethod
    public void createPersons() {
        MockitoAnnotations.initMocks(this);

        person = new Person();
        person.setName("John");
        person.setSurname("Doe");
        person.setAddress("Oak Street 12, Some City");
        person.setPhoneNumber("12345678");
        person.setPassword("password");

        person2 = new Person();
        person2.setName("John");
        person2.setSurname("Bee");
        person2.setAddress("Oak Street 12, Some City");
        person2.setPhoneNumber("12345678");
        person2.setPassword("password");
    }

    @Test
    public void create() {
        personService.create(person);
        verify(personDao).createPerson(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithIdBeforeCreated() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getId() != null) {
                throw new IllegalArgumentException("Person cannot have id before it is created.");
            }
            return person;
        }).when(personDao).createPerson(any(Person.class));

        person.setId(1L);
        personService.create(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullName() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getName() == null) {
                throw new IllegalArgumentException("Person cannot have null name.");
            }
            return person;
        }).when(personDao).createPerson(any(Person.class));

        person.setName(null);
        personService.create(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullSurname() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getSurname() == null) {
                throw new IllegalArgumentException("Person cannot have null surname.");
            }
            return person;
        }).when(personDao).createPerson(any(Person.class));

        person.setSurname(null);
        personService.create(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullAddress() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getAddress() == null) {
                throw new IllegalArgumentException("Person cannot have null address.");
            }
            return person;
        }).when(personDao).createPerson(any(Person.class));

        person.setAddress(null);
        personService.create(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullPhoneNumber() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getPhoneNumber() == null) {
                throw new IllegalArgumentException("Person cannot have null phoneNumber.");
            }
            return person;
        }).when(personDao).createPerson(any(Person.class));

        person.setPhoneNumber(null);
        personService.create(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullAdmin() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getAdmin() == null) {
                throw new IllegalArgumentException("Person cannot have null admin access.");
            }
            return person;
        }).when(personDao).createPerson(any(Person.class));

        person.setAdmin(null);
        personService.create(person);
    }

    @Test
    public void findById() {
        long idToBeFound = 17L;
        person.setId(idToBeFound);
        when(personDao.getPerson(idToBeFound)).thenReturn(person);
        Assert.assertEquals(personService.findById(idToBeFound), person);
    }

    @Test
    public void findNonExisting() {
        long idToBeFound = 17L;
        when(personDao.getPerson(idToBeFound)).thenReturn(null);
        Assert.assertNull(personService.findById(idToBeFound));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findWithNullId() {
        doThrow(IllegalArgumentException.class).when(personDao).getPerson(null);
        personService.findById(null);
    }

    @Test
    public void update() {
        person.setId(1L);
        when(personDao.getPerson(any())).thenReturn(person);
        person.setPassword("1234");
        personService.update(person);
        verify(personDao).updatePerson(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullName() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getName() == null) {
                throw new IllegalArgumentException("Person cannot have null name.");
            }
            return person;
        }).when(personDao).updatePerson(any(Person.class));
        when(personDao.getPerson(any())).thenReturn(person);

        person.setId(1L);

        person.setName(null);
        personService.update(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullSurname() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getSurname() == null) {
                throw new IllegalArgumentException("Person cannot have null surname.");
            }
            return person;
        }).when(personDao).updatePerson(any(Person.class));
        when(personDao.getPerson(any())).thenReturn(person);

        person.setId(1L);

        person.setSurname(null);
        personService.update(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullAddress() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getAddress() == null) {
                throw new IllegalArgumentException("Person cannot have null address.");
            }
            return person;
        }).when(personDao).updatePerson(any(Person.class));
        when(personDao.getPerson(any())).thenReturn(person);

        person.setId(1L);
        person.setAddress(null);
        personService.update(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullPhoneNumber() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getPhoneNumber() == null) {
                throw new IllegalArgumentException("Person cannot have null phoneNumber.");
            }
            return person;
        }).when(personDao).updatePerson(any(Person.class));
        when(personDao.getPerson(any())).thenReturn(person);

        person.setId(1L);

        person.setPhoneNumber(null);
        personService.update(person);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullAdmin() {
        doAnswer(invocationOnMock -> {
            Person person = invocationOnMock.getArgumentAt(0, Person.class);
            if (person == null || person.getAdmin() == null) {
                throw new IllegalArgumentException("Person cannot have null admin access.");
            }
            return person;
        }).when(personDao).updatePerson(any(Person.class));
        when(personDao.getPerson(any())).thenReturn(person);

        person.setId(1L);
        person.setAdmin(null);
        personService.update(person);
    }

    @Test
    public void delete() {
        personService.delete(person);
        verify(personDao).deletePerson(person);
    }

    @Test
    public void findAll() {
        List<Person> people = new ArrayList<>();
        people.add(person);
        people.add(person2);

        when(personDao.getAllPersons()).thenReturn(people);
        List<Person> servicesGot = personService.findAll();
        Assert.assertEquals(servicesGot.size(), 2);
        Assert.assertEquals(servicesGot, people);
    }

    @Test
    public void findAllWhenEmpty() {
        when(personDao.getAllPersons()).thenReturn(new ArrayList<>());
        Assert.assertTrue(personService.findAll().isEmpty());
    }
}
