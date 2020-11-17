package cz.muni.fi.pa165;

import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Sample data loading facade implementation test
 *
 * @author Aneta Moravcikova, 456444
 */
@Transactional
@ContextConfiguration(classes = {SampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeTest.class);

    @Autowired
    public SampleDataLoadingFacade sampleDataLoadingFacade;

    @Autowired
    private DogService dogService;

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private PerformedServiceService performedServiceService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Test
    public void createSampleData() throws IOException {
        log.debug("starting test");

        Assert.assertFalse(dogService.getAll().isEmpty());
        Assert.assertFalse(serviceTypeService.getAll().isEmpty());
        Assert.assertFalse(visitService.getAll().isEmpty());
        Assert.assertFalse(performedServiceService.getAll().isEmpty());
        Assert.assertFalse(employmentService.getAll().isEmpty());
        Assert.assertFalse(personService.findAll().isEmpty());
    }

}