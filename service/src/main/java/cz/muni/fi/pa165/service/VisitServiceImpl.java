package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.DogDao;
import cz.muni.fi.pa165.dao.VisitDao;
import cz.muni.fi.pa165.entity.PerformedService;
import cz.muni.fi.pa165.entity.ServiceType;
import cz.muni.fi.pa165.entity.Visit;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * VisitService interface implementation
 *
 * @author Aneta Moravcikova, 456444
 */
@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DogDao dogDao;

    @Autowired
    private PerformedServiceService performedServiceService;

    @Autowired
    private ServiceTypeService serviceTypeService;


    @Override
    public Long create(Visit visit) throws ServiceDataAccessException {
        try {
            visit.setDog(dogDao.getDog(visit.getDog().getId()));
            visitDao.createVisit(visit);
            return visit.getId();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while creating visit", e);
        }
    }

    @Override
    public void update(Visit visit) throws ServiceDataAccessException {
        try {
            visitDao.updateVisit(visit);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while updating visit", e);
        }
    }

    @Override
    public void delete(Visit visit) throws ServiceDataAccessException {
        try {
            visitDao.deleteVisit(visit);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while deleting visit", e);
        }
    }

    @Override
    public Visit get(Long id) throws ServiceDataAccessException {
        try {
            return visitDao.getVisit(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting visit with id " + id, e);
        }

    }

    @Override
    public List<Visit> getAll() throws ServiceDataAccessException {
        try {
            return visitDao.getAllVisits();
        } catch (Throwable e) {
            throw new ServiceDataAccessException("An error occurred while getting all visits", e);
        }

    }

    @Override
    public Duration getPlannedLength(Visit visit) throws ServiceDataAccessException {
        List<PerformedService> performedServices = performedServiceService.getAll();
        List<ServiceType> serviceTypes = serviceTypeService.getAll();
        List<Long> collect = performedServices.stream()
                .filter(o -> o.getVisit().getId().equals(visit.getId()))
                .map(o -> o.getServiceType().getId())
                .collect(Collectors.toList());
        Optional<Duration> reduce = serviceTypes.stream().filter(o -> collect.contains(o.getId()))
                .map(ServiceType::getStandardLength)
                .reduce(Duration::plus);
        return reduce.get();
    }

    @Override
    public BigDecimal getTotalPrice(Visit visit) throws ServiceDataAccessException {
        List<PerformedService> performedServices = performedServiceService.getAll();
        List<ServiceType> serviceTypes = serviceTypeService.getAll();
        List<Long> collect = performedServices.stream().filter(o -> o.getVisit().getId().equals(visit.getId()))
                .map(o -> o.getServiceType().getId()).collect(Collectors.toList());
        Optional<BigDecimal> reduce = serviceTypes.stream().filter(o -> collect.contains(o.getId()))
                .map(ServiceType::getPrice)
                .reduce(BigDecimal::add);
        return reduce.get();

    }

    @Override
    public String getProcedureDescriptionsForVisit(Visit visit) throws ServiceDataAccessException {
        List<PerformedService> performedServices = performedServiceService.getAll();
        List<ServiceType> serviceTypes = serviceTypeService.getAll();
        List<Long> collect = performedServices.stream().filter(o -> o.getVisit().getId().equals(visit.getId()))
                .map(o -> o.getServiceType().getId()).collect(Collectors.toList());
        Optional<String> reduce = serviceTypes.stream().filter(o -> collect.contains(o.getId()))
                .map(o -> "\n " + o.getName() + " \n \n" + o.getDescription())
                .reduce(String::concat);
        return reduce.get();
    }
}
