package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EmploymentDao;
import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Employment;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EmploymentService interface implementation
 *
 * @author Matúš Špik
 */

@Service
public class EmploymentServiceImpl implements EmploymentService {

    @Autowired
    private EmploymentDao employmentDao;

    @Autowired
    private PersonDao personDao;

    @Override
    public void create(Employment employment) throws ServiceDataAccessException {
        try {
            employmentDao.createEmployment(employment);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while creating employment", e);
        }
    }

    @Override
    public void update(Employment employment) throws ServiceDataAccessException {
        try {
            employmentDao.updateEmployment(employment);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating employment", e);
        }
    }

    @Override
    public void delete(Employment employment) throws ServiceDataAccessException {
        try {
            employmentDao.deleteEmployment(employment);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while deleting employment", e);
        }
    }

    @Override
    public Employment get(Long id) throws ServiceDataAccessException {
        try {
            return employmentDao.getEmployment(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting employment with id " + id, e);
        }
    }

    @Override
    public List<Employment> getAll() throws ServiceDataAccessException {
        try {
            return employmentDao.getAllEmployments();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all employments", e);
        }
    }

    @Override
    public List<Employment> filter(String phone, String name) throws ServiceDataAccessException {
        try {
            List<Long> objects = personDao.filterPersons(phone, name).stream().map(Person::getId).collect(Collectors.toList());
            List<Employment> all = employmentDao.getAllEmployments();
            return all.stream().filter(employment -> objects.contains(employment.getPerson().getId())).collect(Collectors.toList());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all employments", e);
        }
    }
}
