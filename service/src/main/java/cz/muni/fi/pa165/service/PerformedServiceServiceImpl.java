package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.PerformedServiceDao;
import cz.muni.fi.pa165.entity.PerformedService;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Performed service service implementation
 *
 * @author Marek Radimersky, 456518
 */
@Service
public class PerformedServiceServiceImpl implements PerformedServiceService {

    @Autowired
    private PerformedServiceDao performedServiceDao;

    @Override
    public void create(PerformedService performedService) throws ServiceDataAccessException {
        try {
            performedServiceDao.createPerformedService(performedService);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while creating performed service " + performedService.getServiceType(), e);
        }
    }

    @Override
    public PerformedService getById(Long id) throws ServiceDataAccessException {
        try {
            return performedServiceDao.getPerformedService(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting performed service with id " + id, e);
        }
    }

    @Override
    public List<PerformedService> getAll() throws ServiceDataAccessException {
        try {
            return performedServiceDao.getAllPerformedServices();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all performed services", e);
        }
    }

    @Override
    public void update(PerformedService performedService) throws ServiceDataAccessException {
        try {
            performedServiceDao.updatePerformedService(performedService);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating performed service " + performedService.getServiceType() + " with id " + performedService.getId(), e);
        }
    }

    @Override
    public void remove(PerformedService performedService) throws ServiceDataAccessException {
        try {
            performedServiceDao.deletePerformedService(performedService);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while removing performed service " + performedService.getServiceType() + " with id " + performedService.getId(), e);
        }
    }
}
