package cz.muni.fi.pa165.dao;


import cz.muni.fi.pa165.entity.Employment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin.Palic
 */
@Repository
public class EmploymentDaoImpl implements EmploymentDao {

    @PersistenceContext
    private EntityManager manager;

    public void createEmployment(Employment employment) {
        manager.persist(employment);
    }

    public void updateEmployment(Employment employment) {
        manager.merge(employment);
    }

    public void deleteEmployment(Employment employment) {
        manager.remove(this.getEmployment(employment.getId()));
    }

    public Employment getEmployment(Long id) {
        return manager.find(Employment.class, id);
    }

    public List<Employment> getAllEmployments() {
        return manager
                .createQuery("SELECT employment FROM Employment employment", Employment.class)
                .getResultList();
    }
}
