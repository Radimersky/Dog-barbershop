package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.PerformedService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin.Palic
 */
@Repository
public class PerformedServiceDaoImpl implements PerformedServiceDao {

    @PersistenceContext
    private EntityManager manager;

    public void createPerformedService(PerformedService performedService) {
        manager.persist(performedService);
    }

    public void updatePerformedService(PerformedService performedService) {
        manager.merge(performedService);
    }

    public void deletePerformedService(PerformedService performedService) {
        manager.remove(manager.contains(performedService) ? performedService : manager.merge(performedService));
    }

    public PerformedService getPerformedService(Long id) {
        return manager.find(PerformedService.class, id);
    }

    public List<PerformedService> getAllPerformedServices() {
        return manager
                .createQuery("SELECT performedService FROM PerformedService performedService", PerformedService.class)
                .getResultList();
    }
}
