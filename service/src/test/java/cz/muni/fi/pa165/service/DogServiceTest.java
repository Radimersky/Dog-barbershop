package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dao.DogDao;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;


/**
 * Dog service test class
 *
 * @author Marek Radimersky, 456518
 */
@ContextConfiguration(classes = {MappingServiceConf.class})
public class DogServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private DogDao dogDao;

    @Autowired
    @InjectMocks
    private DogServiceImpl dogService;

    private Dog dog;

    private Person person;

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
    }


    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullNameTest() {
        doAnswer(invocationOnMock -> {
            Dog dog = invocationOnMock.getArgumentAt(0, Dog.class);
            if (dog == null || dog.getName() == null) {
                throw new IllegalArgumentException("Dog cannot have null name.");
            }
            return dog;
        }).when(dogDao).createDog(any(Dog.class));

        dog.setName(null);
        dogService.create(dog);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithNullOwnerTest() {
        doAnswer(invocationOnMock -> {
            Dog dog = invocationOnMock.getArgumentAt(0, Dog.class);
            if (dog == null || dog.getOwner() == null) {
                throw new IllegalArgumentException("Dog cannot have null owner.");
            }
            return dog;
        }).when(dogDao).createDog(any(Dog.class));

        dog.setOwner(null);
        dogService.create(dog);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullNameTest() {
        doAnswer(invocationOnMock -> {
            Dog dog = invocationOnMock.getArgumentAt(0, Dog.class);
            if (dog == null || dog.getName() == null) {
                throw new IllegalArgumentException("Dog cannot have null name.");
            }
            return dog;
        }).when(dogDao).updateDog(any(Dog.class));

        dog.setName(null);
        dogService.update(dog);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateWithNullOwnerTest() {
        doAnswer(invocationOnMock -> {
            Dog dog = invocationOnMock.getArgumentAt(0, Dog.class);
            if (dog == null || dog.getOwner() == null) {
                throw new IllegalArgumentException("Dog cannot have null owner.");
            }
            return dog;
        }).when(dogDao).updateDog(any(Dog.class));

        dog.setOwner(null);
        dogService.update(dog);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void createWithIdTest() {
        doAnswer(invocationOnMock -> {
            Dog dog = invocationOnMock.getArgumentAt(0, Dog.class);
            if (dog == null || dog.getId() != null) {
                throw new IllegalArgumentException("Dog cannot have id before it is created.");
            }
            return dog;
        }).when(dogDao).createDog(any(Dog.class));

        dog.setId(1L);
        dogService.create(dog);
    }

    @Test
    public void findAllWhenEmptyTest() {
        when(dogDao.getAllDogs()).thenReturn(new ArrayList<>());
        Assert.assertTrue(dogService.getAll().isEmpty());
    }

    @Test
    public void findNonExistingTest() {
        long idToBeFound = 17L;
        when(dogDao.getDog(idToBeFound)).thenReturn(null);
        Assert.assertNull(dogService.get(idToBeFound));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findWithNullIdTest() {
        doThrow(IllegalArgumentException.class).when(dogDao).getDog(null);
        dogService.get(null);
    }

    @Test
    public void getByIdTest() {
        Long id = Long.MAX_VALUE;
        dogService.get(id);
        verify(dogDao).getDog(id);
    }

    @Test
    public void getAllTest() {
        List<Dog> services = new ArrayList<>();
        services.add(dog);

        when(dogDao.getAllDogs()).thenReturn(services);
        List<Dog> servicesGot = dogService.getAll();
        Assert.assertEquals(servicesGot.size(), 1);
        Assert.assertEquals(servicesGot, services);
    }

    @Test
    public void createDogTest() {
        dogService.create(dog);
        verify(dogDao, times(1)).createDog(dog);
    }

    @Test
    public void removeTest() {
        dogService.delete(dog);
        verify(dogDao, times(1)).deleteDog(dog);
    }

    @Test
    public void updateTest() {
        dog.setName("Alik");
        dogService.update(dog);
        verify(dogDao, times(1)).updateDog(dog);
    }

}
