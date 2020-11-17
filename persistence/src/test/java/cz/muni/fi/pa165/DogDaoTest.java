package cz.muni.fi.pa165;


import cz.muni.fi.pa165.dao.DogDao;
import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.entity.Person;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test class for interface DogDao
 *
 * @author Martin Palic
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DogDaoTest extends AbstractTestNGSpringContextTests {

    private final String[] validNaming = {"Ivan", "Jozef", "Patrik", "Stefan", "Bernardin", "Cokel", "Bullcivava", "Pudelterrier"};

    @Autowired
    private DogDao dogDao;

    @Autowired
    private PersonDao personDao;

    @PersistenceContext
    private EntityManager entityManager;

    public static Date getRandomBirthDate() {
        long startMillis = Date.from(Instant.EPOCH).getTime();
        long endMillis = Date.from(Instant.now()).getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    @Test
    public void storeAndGet() {
        Dog dogToStore = getRandomDog();
        dogDao.createDog(dogToStore);
        Dog dogRetrieved = dogDao.getDog(dogToStore.getId());
        Assert.assertEquals(dogRetrieved, dogToStore);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void storeWithIncorrectName() {
        Dog dogToStore = getRandomDog();
        dogToStore.setName("Wr0n6|\\|@m3");
        dogDao.createDog(dogToStore);
    }

    @Test(expectedExceptions = {DataAccessException.class, ConstraintViolationException.class})
    public void storeWithNullName() {
        Dog dogToStore = getRandomDog();
        dogToStore.setName(null);
        dogDao.createDog(dogToStore);
    }

    @Test(expectedExceptions = {DataAccessException.class, ConstraintViolationException.class})
    public void storeWithNullOwner() {
        Dog dogToStore = getRandomDog();
        dogToStore.setOwner(null);
        dogDao.createDog(dogToStore);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void storeWithIncorrectBreed() {
        Dog dogToStore = getRandomDog();
        dogToStore.setName("Wr0n6Br33d");
        dogDao.createDog(dogToStore);
    }

    @Test
    public void createAndGetMany() {
        List<Dog> doglist = getRandomDogList(10);
        for (Dog dog : doglist
        ) {
            dogDao.createDog(dog);
        }
        List<Dog> dogReturnedList = dogDao.getAllDogs();
        Assert.assertTrue(dogReturnedList.containsAll(doglist));
        Assert.assertEquals(dogReturnedList.size(), doglist.size());
    }

    @Test
    public void update() {
        Dog dog = getRandomDog();
        dogDao.createDog(dog);
        entityManager.flush();
        entityManager.detach(dog);
        dog.setName("SpecialTestingName");
        dog.setBreed("SpecialTestingBreed");
        dogDao.updateDog(dog);
        Dog returnedDog = dogDao.getDog(dog.getId());
        Assert.assertEquals(returnedDog, dog);
    }

    @Test
    public void remove() {
        List<Dog> dogList = getRandomDogList(10);
        Dog dogToRemove = dogList.get(new Random().nextInt(dogList.size()));
        for (Dog dog : dogList
        ) {
            dogDao.createDog(dog);
        }
        dogDao.deleteDog(dogToRemove);
        Assert.assertFalse(dogDao.getAllDogs().contains(dogToRemove));

    }

    @Test
    public void getByWrongId() {
        Assert.assertNull(dogDao.getDog(Long.MAX_VALUE));
        Assert.assertNull(dogDao.getDog(Long.MIN_VALUE));
    }

    private List<Dog> getRandomDogList(int dogCount) {
        List<Dog> result = new ArrayList<>();
        for (int i = 0; i < dogCount; i++) {
            result.add(getRandomDog());
        }
        return result;
    }

    private Dog getRandomDog() {
        return new Dog(getValidNamingValue(), getValidNamingValue(), getRandomGender(), getRandomBirthDate(), getRandomOwner());
    }

    private Person getRandomOwner() {
        Person person = new Person(getValidNamingValue(), getValidNamingValue(), getValidNamingValue(), "+" + new Random().nextInt(10000), "password");
        personDao.createPerson(person);
        return person;
    }

    private String getValidNamingValue() {
        return validNaming[new Random().nextInt(validNaming.length)];
    }

    private Gender getRandomGender() {
        return Gender.values()[new Random().nextInt(Gender.values().length)];
    }
}
