package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Visit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin.Palic
 */
@Repository
public class VisitDaoImpl implements VisitDao {

    @PersistenceContext
    private EntityManager manager;

    public Long createVisit(Visit visit) {
        manager.persist(visit);
        return visit.getId();
    }

    public void updateVisit(Visit visit) {
        manager.merge(visit);
    }

    public void deleteVisit(Visit visit) {
        manager.remove(manager.contains(visit) ? visit : manager.merge(visit));
    }

    public Visit getVisit(Long id) {
        return manager.find(Visit.class, id);
    }

    public List<Visit> getAllVisits() {
        return manager.createQuery("SELECT visit FROM Visit visit", Visit.class).getResultList();
    }
}
