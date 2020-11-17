package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.facade.PersonFacade;
import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Matúš Špik, 456690
 */

@RestController
@RequestMapping(ApiUris.PERSONS_URI)
public class PersonController {

    final static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Inject
    private PersonFacade personFacade;

    /**
     * get all persons
     *
     * @return list of PersonDTOs
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<PersonDTO> getPersons() {
        logger.debug("rest getPersons()");
        return personFacade.getAllPersons();
    }

    /**
     * Get one person specified by id
     *
     * @param Id identifier for the person
     * @return PersonDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO getPerson(@PathVariable("id") long Id) throws ResourceNotFoundException {
        logger.debug("rest getPerson({})", Id);
        PersonDTO personDTO = personFacade.getPerson(Id);
        if (personDTO == null) {
            throw new ResourceNotFoundException();
        }
        return personDTO;
    }

    /**
     * Retrieves all people with given name
     *
     * @param name
     * @return List of PersonDTOs
     */
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<PersonDTO> getPersonsWithName(@PathVariable("name") String name) {
        logger.debug("rest getPersonsWithName({})", name);
        return personFacade.filterPersons(null, name);
    }

    /**
     * Retrieves all people with given phone number
     *
     * @param number
     * @return List of PersonDTOs
     */
    @RequestMapping(value = "/phone/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<PersonDTO> getPersonsWithPhoneNumber(@PathVariable("number") String number) {
        logger.debug("rest getPersonsWithPhoneNumber({})", number);
        return personFacade.filterPersons(number, null);
    }

    /**
     * Create a new person by POST method
     *
     * @param personDTO PersonDTO with required fields for creation
     * @return the created person PersonDTO
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO createPerson(@RequestBody PersonDTO personDTO) throws ResourceAlreadyExistingException {

        logger.debug("rest createPerson()");

        try {
            Long Id = personFacade.createPerson(personDTO);
            return personFacade.getPerson(Id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Delete one person by id
     *
     * @param Id identifier for person
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO deletePerson(@PathVariable("id") long Id) throws ResourceNotFoundException {
        logger.debug("rest deletePerson({})", Id);
        try {
            personFacade.deletePerson(personFacade.getPerson(Id));
            return personFacade.getPerson(Id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * @param Id        identifier for person
     * @param personDTO PersonDTO with required fields for update
     * @return the updated person PersonDTO
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO updatePerson(@PathVariable("id") Long Id, @RequestBody PersonDTO personDTO) throws ResourceAlreadyExistingException {

        logger.debug("rest createPerson()");

        PersonDTO person = new PersonDTO();
        person.setId(Id);
        person.setAddress(personDTO.getAddress());
        person.setName(personDTO.getName());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setSurname(personDTO.getSurname());

        try {
            personFacade.updatePerson(person);
            return personFacade.getPerson(Id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

}