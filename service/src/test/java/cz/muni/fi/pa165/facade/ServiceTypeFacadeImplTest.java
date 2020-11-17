package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.MappingServiceConf;
import cz.muni.fi.pa165.dto.ServiceTypeDTO;
import cz.muni.fi.pa165.entity.ServiceType;
import cz.muni.fi.pa165.exception.ServiceDataAccessException;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.ServiceTypeService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Aneta Moravcikova, 456444
 */
@ContextConfiguration(classes = MappingServiceConf.class)
public class ServiceTypeFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {


    @Mock
    private ServiceTypeService serviceTypeService;

    @Autowired
    private BeanMappingService mappingService;

    private ServiceTypeFacadeImpl serviceTypeFacade;

    private final ArgumentCaptor<ServiceType> argumentCaptor = ArgumentCaptor.forClass(ServiceType.class);

    private ServiceType serviceType;
    private ServiceTypeDTO serviceTypeDTO;

    @BeforeClass
    public void setUp() throws ServiceDataAccessException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    private void initParams() {
        this.serviceType = new ServiceType();
        this.serviceType.setId(1L);
        this.serviceType.setName("Washing");
        this.serviceType.setDescription("Your dog's fur will be sparkling clean and soft after he's washed.");

        this.serviceTypeDTO = new ServiceTypeDTO();
        this.serviceTypeDTO.setId(1L);
        this.serviceTypeDTO.setName("Washing");
        this.serviceTypeDTO.setDescription("Your dog's fur will be sparkling clean and soft after he's washed.");

        serviceTypeFacade = new ServiceTypeFacadeImpl(mappingService, serviceTypeService);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(serviceTypeService);
    }


    @Test
    public void createTest() {
        Assert.assertEquals(serviceTypeFacade.createServiceType(serviceTypeDTO), Long.valueOf(1L));
        verify(serviceTypeService, times(1)).create(any());
    }

    @Test
    public void update() {
        serviceTypeFacade.updateServiceType(serviceTypeDTO);
        verify(serviceTypeService).update(argumentCaptor.capture());
        Assert.assertEquals(argumentCaptor.getValue(), serviceType);

    }

    @Test
    public void delete() {
        serviceTypeFacade.deleteServiceType(serviceTypeDTO);
        verify(serviceTypeService).remove(argumentCaptor.capture());
        Assert.assertEquals(argumentCaptor.getValue(), serviceType);
    }

    @Test
    public void get() {
        when(serviceTypeService.getById(1L)).thenReturn(serviceType);
        Assert.assertEquals(serviceTypeFacade.getServiceType(1L), serviceTypeDTO);
    }

    @Test
    public void getAll() {
        when(serviceTypeService.getAll()).thenReturn(Collections.singletonList(serviceType));
        Assert.assertEquals(serviceTypeFacade.getAllServiceTypes(), Collections.singletonList(serviceTypeDTO));
    }


}
