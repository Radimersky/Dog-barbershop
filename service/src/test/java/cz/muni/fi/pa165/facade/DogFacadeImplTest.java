package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.DogService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Martin.Palic
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class DogFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private DogService dogService;

    @Autowired
    private BeanMappingService beanMappingService;

    private final ArgumentCaptor<Dog> dogArgumentCaptor = ArgumentCaptor.forClass(Dog.class);

    private DogFacadeImpl dogFacade;

    private DogDTO dogDTO;

    private Dog dog;

    @BeforeMethod
    private void initTest() {
        MockitoAnnotations.initMocks(this);

        PersonDTO person = new PersonDTO();
        person.setId(1L);
        person.setAddress("adresa");
        person.setName("Nemo");
        person.setSurname("Riezpisko");
        person.setPhoneNumber("+123456");

        Person person1 = new Person();
        person1.setId(1L);
        person1.setAddress("adresa");
        person1.setName("Nemo");
        person1.setSurname("Riezpisko");
        person1.setPhoneNumber("+123456");

        dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setBreed("pes");
        dogDTO.setDateOfBirth(Date.from(Instant.now()));
        dogDTO.setGender(Gender.MALE);
        dogDTO.setName("PESOpes");
        dogDTO.setOwner(person);

        dog = new Dog();
        dog.setId(1L);
        dog.setBreed("pes");
        dog.setDateOfBirth(dogDTO.getDateOfBirth());
        dog.setGender(Gender.MALE);
        dog.setName("PESOpes");
        dog.setOwner(person1);

        dogFacade = new DogFacadeImpl(beanMappingService, dogService);
    }

    @Test
    public void createDogTest() {
        Assert.assertEquals(dogFacade.createDog(dogDTO), Long.valueOf(0L));
        verify(dogService, times(1)).create(any());
    }

    @Test
    public void updateDogTest() {
        dogFacade.updateDog(dogDTO);
        verify(dogService).update(dogArgumentCaptor.capture());
        Assert.assertEquals(dogArgumentCaptor.getValue(), dog);

    }

    @Test
    public void deleteDogTest() {
        dogFacade.deleteDog(dogDTO);
        verify(dogService).delete(dogArgumentCaptor.capture());
        Assert.assertEquals(dogArgumentCaptor.getValue(), dog);
    }

    @Test
    public void getDogTest() {
        when(dogService.get(1L)).thenReturn(dog);
        Assert.assertEquals(dogFacade.getDog(1L), dogDTO);
    }

    @Test
    public void getAllDogsTest() {
        when(dogService.getAll()).thenReturn(Collections.singletonList(dog));
        Assert.assertEquals(dogFacade.getAllDogs(), Collections.singletonList(dogDTO));

    }

}
