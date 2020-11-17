package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dto.PersonAuthDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.PersonService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Martin.Palic
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class PersonFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private PersonService personService;

    @Autowired
    private BeanMappingService beanMappingService;

    private PersonFacadeImpl personFacade;

    private final ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);

    private Person person;
    private PersonDTO personDTO;

    @BeforeMethod
    private void init() {
        MockitoAnnotations.initMocks(this);

        person = new Person();
        person.setPhoneNumber("+421");
        person.setSurname("srnejm");
        person.setName("nejm");
        person.setAddress("asdfdfg");
        person.setAdmin(false);


        personDTO = new PersonDTO();
        personDTO.setPhoneNumber("+421");
        personDTO.setSurname("srnejm");
        personDTO.setName("nejm");
        personDTO.setAddress("asdfdfg");

        personFacade = new PersonFacadeImpl(beanMappingService, personService);
    }

    @Test
    public void registerPersonTest() {
        Assert.assertTrue(personFacade.registerPerson(personDTO, "password"));
        person.setPassword("password");
        verify(personService, times(1)).create(personArgumentCaptor.capture());
        Assert.assertEquals(personArgumentCaptor.getValue(), person);
        Assert.assertEquals(personArgumentCaptor.getValue().getPasswordHash(), person.getPasswordHash());
    }

    @Test
    public void filterPersonsTest() {
        ArgumentCaptor<String> strCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> strCaptor2 = ArgumentCaptor.forClass(String.class);

        when(personService.filterPersons(any(), any())).thenReturn(Collections.singletonList(person));
        Assert.assertEquals(
                Collections.singletonList(personDTO),
                personFacade.filterPersons("+42", "name"));
        verify(personService, times(1))
                .filterPersons(strCaptor1.capture(), strCaptor2.capture());
        Assert.assertEquals(strCaptor1.getValue(), "+42");
        Assert.assertEquals(strCaptor2.getValue(), "name");
    }

    @Test
    public void authenticatePersonTest() {
        ArgumentCaptor<String> strCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> strCaptor2 = ArgumentCaptor.forClass(String.class);

        PersonAuthDTO personAuthDTO = new PersonAuthDTO();
        personAuthDTO.setPassword("password");
        personAuthDTO.setPhone("+421");

        person.setPassword("password");
        personDTO.setPassword(person.getPasswordHash());
        when(personService.filterPersons(any(), any())).thenReturn(Collections.singletonList(person));

        Assert.assertTrue(personFacade.authenticatePerson(personAuthDTO));
        verify(personService, times(1))
                .filterPersons(strCaptor1.capture(), strCaptor2.capture());
        Assert.assertEquals(strCaptor1.getValue(), "+421");
        Assert.assertNull(strCaptor2.getValue());


        personAuthDTO.setPassword("shallnopassword");
        Assert.assertFalse(personFacade.authenticatePerson(personAuthDTO));
    }

    @Test
    public void getAllPersonsTest() {
        when(personService.findAll()).thenReturn(Collections.singletonList(person));
        Assert.assertEquals(personFacade.getAllPersons(), Collections.singletonList(personDTO));
    }
}
