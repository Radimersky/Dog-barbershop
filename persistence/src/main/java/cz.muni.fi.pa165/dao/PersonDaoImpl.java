package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Marek Radiměřský
 * <p>
 * Person DAO implementation.
 */
@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person getPerson(Long id) {
        return em.find(Person.class, id);
    }

    @Override
    public Long createPerson(Person person) {
        em.persist(person);
        return person.getId();
    }

    @Override
    public void updatePerson(Person person) {
        em.merge(person);
    }

    @Override
    public void deletePerson(Person person) {
        em.remove(person);
    }

    @Override
    public List<Person> getAllPersons() {
        return em.createQuery("SELECT p FROM Person p", Person.class)
                .getResultList();
    }

    @Override
    public List<Person> filterPersons(String phone, String name) {
        String query = "SELECT p FROM Person p";

        if (phone != null && !phone.isEmpty()) {
            phone = phone.replace("'", "''");
            query = query + " WHERE lower(p.phoneNumber) like lower('%" + phone + "%') ";
        }
        if (name != null && !name.isEmpty()) {
            name = name.replace("'", "''");
            if (phone != null && !phone.isEmpty()) {
                query = query + " OR ";
            } else {
                query = query + " WHERE ";
            }
            query = query + " lower(p.name) like lower('%" + name + "%') ";
        }

        return em.createQuery(query, Person.class).getResultList();

    }
}
