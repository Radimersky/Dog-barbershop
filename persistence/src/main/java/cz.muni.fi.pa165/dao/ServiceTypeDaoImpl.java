package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.ServiceType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Matúš Špik
 */
@Repository
public class ServiceTypeDaoImpl implements ServiceTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long createServiceType(ServiceType serviceType) {
        entityManager.persist(serviceType);
        return serviceType.getId();
    }

    @Override
    public void updateServiceType(ServiceType serviceType) {
        entityManager.merge(serviceType);
    }

    @Override
    public void deleteServiceType(ServiceType serviceType) {
        entityManager.remove(this.getServiceType(serviceType.getId()));
    }

    @Override
    public ServiceType getServiceType(Long id) {
        return entityManager.find(ServiceType.class, id);
    }

    @Override
    public List<ServiceType> getAllServiceTypes() {
        return entityManager.createQuery("SELECT s FROM ServiceType s", ServiceType.class)
                .getResultList();
    }
}
