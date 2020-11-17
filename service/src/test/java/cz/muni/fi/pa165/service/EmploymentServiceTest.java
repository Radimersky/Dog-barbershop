package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dao.EmploymentDao;
import cz.muni.fi.pa165.entity.Employment;
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
 * @author Matúš Špik
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class EmploymentServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EmploymentDao employmentDao;

    @Autowired
    @InjectMocks
    private EmploymentService employmentService;

    private Employment employment;
    private Employment employment2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createEmployments() {
        Person person = new Person();
        person.setName("John");
        person.setSurname("Doe");
        person.setAddress("Oak Street 12, Some City");
        person.setPhoneNumber("12345678");
        person.setPassword("password");

        employment = new Employment();
        employment.setPerson(person);
        employment.setPositionName("manager");
        employment.setStartDate(Date.from(Instant.parse("2020-01-01T12:30:00.000Z")));
        employment.setEndDate(Date.from(Instant.parse("2020-04-26T23:30:00.000Z")));

        employment2 = new Employment();
        employment.setPerson(person);
        employment.setPositionName("professional");
        employment.setStartDate(Date.from(Instant.parse("2020-02-01T12:30:00.000Z")));
        employment.setEndDate(Date.from(Instant.parse("2020-05-26T23:30:00.000Z")));
    }

    @Test
    public void create() {
        employmentService.create(employment);
        verify(employmentDao).createEmployment(employment);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithIdBeforeCreated() {
        doAnswer(invocationOnMock -> {
            Employment employment = invocationOnMock.getArgumentAt(0, Employment.class);
            if (employment == null || employment.getId() != null) {
                throw new IllegalArgumentException("Employment cannot have id before it is created.");
            }
            return employment;
        }).when(employmentDao).createEmployment(any(Employment.class));

        employment.setId(1L);
        employmentService.create(employment);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullPerson() {
        doAnswer(invocationOnMock -> {
            Employment employment = invocationOnMock.getArgumentAt(0, Employment.class);
            if (employment == null || employment.getPerson() == null) {
                throw new IllegalArgumentException("Employment cannot have null person.");
            }
            return employment;
        }).when(employmentDao).createEmployment(any(Employment.class));

        employment.setPerson(null);
        employmentService.create(employment);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullStartDate() {
        doAnswer(invocationOnMock -> {
            Employment employment = invocationOnMock.getArgumentAt(0, Employment.class);
            if (employment == null || employment.getStartDate() == null) {
                throw new IllegalArgumentException("Employment cannot have null start date.");
            }
            return employment;
        }).when(employmentDao).createEmployment(any(Employment.class));

        employment.setStartDate(null);
        employmentService.create(employment);
    }

    @Test
    public void get() {
        long idToBeFound = 17L;
        employment.setId(idToBeFound);
        when(employmentDao.getEmployment(idToBeFound)).thenReturn(employment);
        Assert.assertEquals(employmentService.get(idToBeFound), employment);
    }

    @Test
    public void getNonExisting() {
        long idToBeFound = 17L;
        when(employmentDao.getEmployment(idToBeFound)).thenReturn(null);
        Assert.assertNull(employmentService.get(idToBeFound));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void getWithNullId() {
        doThrow(IllegalArgumentException.class).when(employmentDao).getEmployment(null);
        employmentService.get(null);
    }

    @Test
    public void update() {
        employment.setPositionName("New Different Position");
        employmentService.update(employment);
        verify(employmentDao).updateEmployment(employment);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullPerson() {
        doAnswer(invocationOnMock -> {
            Employment employment = invocationOnMock.getArgumentAt(0, Employment.class);
            if (employment == null || employment.getPerson() == null) {
                throw new IllegalArgumentException("Employment cannot have null person.");
            }
            return employment;
        }).when(employmentDao).updateEmployment(any(Employment.class));

        employment.setPerson(null);
        employmentService.update(employment);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullStartDate() {
        doAnswer(invocationOnMock -> {
            Employment employment = invocationOnMock.getArgumentAt(0, Employment.class);
            if (employment == null || employment.getStartDate() == null) {
                throw new IllegalArgumentException("Employment cannot have null start date.");
            }
            return employment;
        }).when(employmentDao).updateEmployment(any(Employment.class));

        employment.setStartDate(null);
        employmentService.update(employment);
    }

    @Test
    public void delete() {
        employmentService.delete(employment);
        verify(employmentDao).deleteEmployment(employment);
    }

    @Test
    public void getAllWhenEmpty() {
        when(employmentDao.getAllEmployments()).thenReturn(new ArrayList<>());
        Assert.assertTrue(employmentService.getAll().isEmpty());
    }

    @Test
    public void getAll() {
        List<Employment> employments = new ArrayList<>();
        employments.add(employment);
        employments.add(employment2);

        when(employmentDao.getAllEmployments()).thenReturn(employments);
        List<Employment> servicesGot = employmentService.getAll();
        Assert.assertEquals(servicesGot.size(), 2);
        Assert.assertEquals(servicesGot, employments);
    }
}
