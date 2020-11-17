package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.dto.VisitDTO;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.entity.Visit;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.VisitService;
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
public class VisitFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private VisitService visitService;

    @Autowired
    private BeanMappingService mappingService;

    private VisitFacadeImpl visitFacade;

    private final ArgumentCaptor<Visit> visitArgumentCaptor = ArgumentCaptor.forClass(Visit.class);

    private Visit visit;
    private VisitDTO visitDTO;

    @BeforeMethod
    private void init() {
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

        DogDTO dogDTO = new DogDTO();
        dogDTO.setId(1L);
        dogDTO.setBreed("pes");
        dogDTO.setDateOfBirth(Date.from(Instant.now()));
        dogDTO.setGender(Gender.MALE);
        dogDTO.setName("PESOpes");
        dogDTO.setOwner(person);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setBreed("pes");
        dog.setDateOfBirth(dogDTO.getDateOfBirth());
        dog.setGender(Gender.MALE);
        dog.setName("PESOpes");
        dog.setOwner(person1);

        visit = new Visit();
        visit.setDog(dog);
        visit.setFinish(Date.from(Instant.now()));
        visit.setStart(Date.from(Instant.now()));
        visit.setId(1L);

        visitDTO = new VisitDTO();
        visitDTO.setDog(dogDTO);
        visitDTO.setFinish(visit.getFinish());
        visitDTO.setStart(visit.getStart());
        visitDTO.setId(1L);

        visitFacade = new VisitFacadeImpl(mappingService, visitService);

    }

    @Test
    public void createVisit() {
        Assert.assertEquals(visitFacade.createVisit(visitDTO), Long.valueOf(1L));
        verify(visitService, times(1)).create(any());
    }

    @Test
    public void updateVisit() {
        visitFacade.updateVisit(visitDTO);
        verify(visitService).update(visitArgumentCaptor.capture());
        Assert.assertEquals(visitArgumentCaptor.getValue(), visit);
    }

    @Test
    public void deleteVisit() {
        visitFacade.deleteVisit(visitDTO);
        verify(visitService).delete(visitArgumentCaptor.capture());
        Assert.assertEquals(visitArgumentCaptor.getValue(), visit);
    }

    @Test
    public void getVisit() {
        when(visitService.get(1L)).thenReturn(visit);
        Assert.assertEquals(visitFacade.getVisit(1L), visitDTO);
    }

    @Test
    public void getAllVisits() {
        when(visitService.getAll()).thenReturn(Collections.singletonList(visit));
        Assert.assertEquals(visitFacade.getAllVisits(), Collections.singletonList(visitDTO));
    }

}
