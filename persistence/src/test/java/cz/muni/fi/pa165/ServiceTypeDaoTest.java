package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.ServiceTypeDao;
import cz.muni.fi.pa165.entity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

/**
 * Test class for interface ServiceTypeDao
 *
 * @author Marek Radiměřský
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ServiceTypeDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ServiceTypeDao serviceTypeDao;

    @Test
    public void isCorrect() {
        ServiceType serviceType = createServiceType();
        ServiceType serviceTypeFromList = serviceTypeDao.getServiceType(serviceType.getId());
        Assert.assertEquals(serviceTypeFromList, serviceType);
    }

    @Test
    public void createMany() {
        ServiceType serviceType1 = createServiceType();
        ServiceType serviceType2 = createServiceType();

        List<ServiceType> serviceTypes = serviceTypeDao.getAllServiceTypes();

        Assert.assertEquals(serviceTypes.size(), 2);
        Assert.assertTrue(serviceTypes.contains(serviceType1));
        Assert.assertTrue(serviceTypes.contains(serviceType2));
    }


    @Test
    public void createAndGet() {
        ServiceType serviceType = createServiceType();

        List<ServiceType> serviceTypes = serviceTypeDao.getAllServiceTypes();
        Assert.assertEquals(serviceTypes.size(), 1);

        ServiceType serviceTypeFromList = serviceTypes.get(0);
        Assert.assertEquals(serviceTypeFromList, serviceType);
    }

    @Test
    public void getByWrongId() {
        Assert.assertNull(serviceTypeDao.getServiceType(Long.MAX_VALUE));
        Assert.assertNull(serviceTypeDao.getServiceType(Long.MIN_VALUE));
    }


    @Test
    public void updateProperties() {
        ServiceType serviceType = createServiceType();
        entityManager.detach(serviceType);

        serviceType.setName("Deluxe trim");
        serviceType.setPrice(BigDecimal.valueOf(350));
        serviceType.setStandardLength(Duration.ofMinutes(45));
        serviceType.setDescription("Trim performed with electric razor, that can make every dog look fabulous.");

        serviceTypeDao.updateServiceType(serviceType);

        ServiceType serviceTypeFromList = serviceTypeDao.getServiceType(serviceType.getId());
        Assert.assertEquals(serviceTypeFromList, serviceType);
    }

    @Test
    public void delete() {
        ServiceType serviceType = createServiceType();
        ServiceType serviceTypeFromList = serviceTypeDao.getServiceType(serviceType.getId());

        serviceTypeDao.deleteServiceType(serviceTypeFromList);

        List<ServiceType> serviceTypeList = serviceTypeDao.getAllServiceTypes();
        Assert.assertEquals(serviceTypeList.size(), 0);
    }

    private ServiceType createServiceType() {
        String serviceDesription = "Trim performed with electric razor, that can make every dog look stylish.";
        ServiceType serviceType = new ServiceType("Basic trim", Duration.ofMinutes(30), BigDecimal.valueOf(250), serviceDesription);
        serviceTypeDao.createServiceType(serviceType);
        return serviceType;
    }

}
