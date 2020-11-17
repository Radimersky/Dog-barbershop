package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Dog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation class for DogSao interface
 *
 * @author Aneta Moravcikova, 456444
 */
@Repository
public class DogDaoImpl implements DogDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Long createDog(Dog dog) {
        manager.persist(dog);
        return dog.getId();
    }

    @Override
    public void updateDog(Dog dog) {
        manager.merge(dog);
    }

    @Override
    public void deleteDog(Dog dog) {
        manager.remove(this.getDog(dog.getId()));
    }

    @Override
    public Dog getDog(Long id) {
        return manager.find(Dog.class, id);
    }

    @Override
    public List<Dog> getAllDogs() {
        return manager.createQuery("SELECT dog FROM Dog dog", Dog.class).getResultList();
    }
}