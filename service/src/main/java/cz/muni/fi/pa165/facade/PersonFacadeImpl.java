package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.PersonAuthDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Person Facade Implementation
 *
 * @author Martin.Palic
 */
@Service
@Transactional
public class PersonFacadeImpl implements PersonFacade {

    @Autowired
    private PersonService personService;
    @Autowired
    private BeanMappingService mappingService;

    public PersonFacadeImpl() {
    }

    public PersonFacadeImpl(BeanMappingService beanMappingService, PersonService personService) {
        this.personService = personService;
        this.mappingService = beanMappingService;
    }

    @Override
    public boolean registerPerson(PersonDTO user, String passwordText) {
        Person person = mappingService.mapTo(user, Person.class);
        person.setPassword(passwordText);
        personService.create(person);
        return true;
    }

    @Override
    public List<PersonDTO> filterPersons(String phone, String name) {
        return mappingService.mapTo(personService.filterPersons(phone, name), PersonDTO.class);
    }

    @Override
    public boolean authenticatePerson(PersonAuthDTO person) {
        Collection<Person> matched = personService.filterPersons(person.getPhone(), null);
        if (matched.size() != 1) {
            return false;
        }
        Person authPerson = new Person();
        authPerson.setPassword(person.getPassword());
        return authPerson.getPasswordHash().equals(matched.iterator().next().getPasswordHash());
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return mappingService.mapTo(personService.findAll(), PersonDTO.class);
    }

    @Override
    public PersonDTO getPerson(Long Id) {
        return mappingService.mapTo(personService.findById(Id), PersonDTO.class);
    }

    @Override
    public boolean isAdmin(PersonDTO person) {
        Collection<Person> matched = personService.filterPersons(person.getPhoneNumber(), null);
        if (matched.size() != 1) {
            return false;
        }
        return matched.iterator().next().getAdmin();
    }

    @Override
    public boolean isAdmin(String phone) {
        Collection<Person> matched = personService.filterPersons(phone, null);
        if (matched.size() != 1) {
            return false;
        }
        return matched.iterator().next().getAdmin();
    }

    @Override
    public void updatePerson(PersonDTO person) {
        Person person1 = mappingService.mapTo(person, Person.class);
        person1.setAdmin(this.isAdmin(person.getPhoneNumber()));
        personService.update(person1);
    }

    @Override
    public Long createPerson(PersonDTO person) {
        Person person1 = mappingService.mapTo(person, Person.class);
        person1.setPassword(person.getPassword());
        person1.setAdmin(false);
        return personService.create(person1);
    }

    @Override
    public void deletePerson(PersonDTO personDTO) {
        personService.delete(mappingService.mapTo(personDTO, Person.class));
    }
}
