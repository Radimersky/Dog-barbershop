package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.DogDao;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DogService interface implementation
 *
 * @author Aneta Moravcikova, 456444
 */
@Service
public class DogServiceImpl implements DogService {

    @Autowired
    private DogDao dogDao;

    @Override
    public Long create(Dog dog) throws ServiceDataAccessException {
        try {
            return dogDao.createDog(dog);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while creating dog", e);
        }
    }

    @Override
    public void update(Dog dog) throws ServiceDataAccessException {
        try {
            dogDao.updateDog(dog);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating dog", e);
        }
    }

    @Override
    public void delete(Dog dog) throws ServiceDataAccessException {
        try {
            dogDao.deleteDog(dog);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while deleting dog", e);
        }
    }

    @Override
    public Dog get(Long id) throws ServiceDataAccessException {
        try {
            return dogDao.getDog(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting dog with id " + id, e);
        }

    }

    @Override
    public List<Dog> getAll() throws ServiceDataAccessException {
        try {
            return dogDao.getAllDogs();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all dogs", e);
        }

    }
}
