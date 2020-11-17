package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.ServiceTypeDao;
import cz.muni.fi.pa165.entity.ServiceType;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service type service implementation
 *
 * @author Marek Radimersky, 456518
 */
@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    private ServiceTypeDao serviceTypeDao;


    @Override
    public Long create(ServiceType serviceType) throws ServiceDataAccessException {
        try {
            serviceTypeDao.createServiceType(serviceType);
            return serviceType.getId();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while creating service type " + serviceType.getName(), e);
        }
    }

    @Override
    public ServiceType getById(Long id) throws ServiceDataAccessException {
        try {
            return serviceTypeDao.getServiceType(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting service type with id " + id, e);
        }
    }

    @Override
    public List<ServiceType> getAll() throws ServiceDataAccessException {
        try {
            return serviceTypeDao.getAllServiceTypes();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all service types", e);
        }
    }

    @Override
    public void update(ServiceType serviceType) throws ServiceDataAccessException {
        try {
            serviceTypeDao.updateServiceType(serviceType);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating service type " + serviceType.getName() + " with id " + serviceType.getId(), e);
        }
    }

    @Override
    public void remove(ServiceType serviceType) throws ServiceDataAccessException {
        try {
            serviceTypeDao.deleteServiceType(serviceType);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating service type " + serviceType.getName() + " with id " + serviceType.getId(), e);
        }
    }

}
