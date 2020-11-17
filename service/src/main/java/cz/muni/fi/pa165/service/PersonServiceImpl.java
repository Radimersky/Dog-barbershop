package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * PersonService interface implementation
 *
 * @author Matúš Špik
 */

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public Long create(Person person) throws ServiceDataAccessException {
        try {
            return personDao.createPerson(person);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while creating person", e);
        }
    }

    @Override
    public void update(Person person) throws ServiceDataAccessException {
        try {
            Person byId = personDao.getPerson(person.getId());
            byId.setAdmin(person.getAdmin());
            byId.setAddress(person.getAddress());
            byId.setName(person.getName());
            byId.setSurname(person.getSurname());
            byId.setPhoneNumber(person.getPhoneNumber());
            personDao.updatePerson(byId);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating person", e);
        }
    }

    @Override
    public void delete(Person person) throws ServiceDataAccessException {
        try {
            personDao.deletePerson(person);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while deleting person", e);
        }
    }

    @Override
    public Collection<Person> filterPersons(String phone, String name) {
        try {
            return personDao.filterPersons(phone, name);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while filtering persons", e);
        }
    }

    @Override
    public Person findById(Long id) throws ServiceDataAccessException {
        try {
            return personDao.getPerson(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting person with id " + id, e);
        }
    }

    @Override
    public List<Person> findAll() throws ServiceDataAccessException {
        try {
            return personDao.getAllPersons();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all people", e);
        }
    }
}
