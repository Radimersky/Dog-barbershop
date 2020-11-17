package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Sample data loading facade implementation
 *
 * @author Aneta Moravcikova, 456444
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

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


    @Override
    public void loadData() throws IOException {

        Person admin = person("admin", "admin", "Oak Street 12, Some City", "password", "000000000", true);
        Person employee = person("employee", "employee", "Oak Street 12, Some City", "password", "111111111", false);
        Person user = person("user", "user", "Oak Street 12, Some City", "password", "222222222", false);
        Person john = person("John", "Doe", "Oak Street 12, Some City", "password", "12345678", true);
        Person jane = person("Jane", "Jones", "New Street 1, Another City", "password", "87654321", false);
        Person mary = person("Mary", "Smith", "Sunflower avenue 5", "password", "13579", false);
        Person daniel = person("Daniel", "Taylor", "Sunflower avenue 10", "password", "123123123", false);
        log.info("People loaded successfully.");

        Dog cookie = dog("Cookie", "Poodle", Gender.FEMALE, java.sql.Date.valueOf("2018-05-12"), mary);
        Dog cookie1 = dog("Cookiea", "Poodle", Gender.FEMALE, java.sql.Date.valueOf("2018-05-12"), admin);
        Dog cookie2 = dog("Cookies", "Poodle", Gender.FEMALE, java.sql.Date.valueOf("2018-05-12"), employee);
        Dog cookie3 = dog("Cookied", "Poodle", Gender.FEMALE, java.sql.Date.valueOf("2018-05-12"), user);
        Dog cookie4 = dog("Cookief", "Poodle", Gender.FEMALE, java.sql.Date.valueOf("2018-05-12"), user);
        Dog larry = dog("Larry", "Newfoundland", Gender.MALE, java.sql.Date.valueOf("2009-04-11"), jane);
        Dog beky = dog("Beky", "Shiba Inu", Gender.FEMALE, java.sql.Date.valueOf("2013-03-25"), jane);
        Dog bibi = dog("Bibi", "Samoyed", Gender.MALE, java.sql.Date.valueOf("2019-03-07"), daniel);
        Dog pancake = dog("Pancake", "Chihuahua", Gender.FEMALE, java.sql.Date.valueOf("2017-04-17"), daniel);
        log.info("Dogs loaded successfully.");

        Employment adminJob = employment(admin, "Admin", java.sql.Date.valueOf("2015-01-01"), java.sql.Date.valueOf("2025-01-01"), BigDecimal.valueOf(10000));
        Employment employeeJob = employment(employee, "Employee", java.sql.Date.valueOf("2015-01-01"), java.sql.Date.valueOf("2025-01-01"), BigDecimal.valueOf(150));
        Employment johnsJob = employment(john, "Manager", java.sql.Date.valueOf("2015-01-01"), java.sql.Date.valueOf("2025-01-01"), BigDecimal.valueOf(3000));
        Employment marysJob = employment(mary, "Dog Groomer", java.sql.Date.valueOf("2019-01-01"), java.sql.Date.valueOf("2024-01-01"), BigDecimal.valueOf(20000));
        Employment danielsJob = employment(daniel, "Dog fur Stylist", java.sql.Date.valueOf("2020-01-01"), java.sql.Date.valueOf("2025-01-01"), BigDecimal.valueOf(20000));
        log.info("Employments loaded successfully.");

        ServiceType washing = serviceType("Washing", "Basic fur washing and drying for dog of any breed.", Duration.ofHours(1), BigDecimal.valueOf(15));
        ServiceType haircut = serviceType("Haircut", "Basic haircut for dog of any breed with nail trimming included.", Duration.ofHours(2), BigDecimal.valueOf(20));
        ServiceType grooming = serviceType("Grooming", "Fur washing, drying and cutting for dog of any breed, with nail trimming included.", Duration.ofHours(3), BigDecimal.valueOf(30));
        ServiceType nailTrim = serviceType("Nail trimming", "Nail trimming for dog of any breed.", Duration.ofMinutes(15), BigDecimal.valueOf(5));
        log.info("Service types loaded successfully.");

        Visit visit1 = visit(cookie, Date.from(Instant.parse("2020-06-15T10:00:00.000Z")), Date.from(Instant.parse("2020-06-15T11:00:00.000Z")));
        Visit visit21 = visit(cookie1, Date.from(Instant.parse("2020-06-15T12:00:00.000Z")), Date.from(Instant.parse("2020-06-15T14:00:00.000Z")));
        Visit visit31 = visit(cookie2, Date.from(Instant.parse("2020-06-15T12:00:00.000Z")), Date.from(Instant.parse("2020-06-15T14:00:00.000Z")));
        Visit visit41 = visit(cookie2, Date.from(Instant.parse("2020-06-15T12:00:00.000Z")), Date.from(Instant.parse("2020-06-15T14:00:00.000Z")));
        Visit visit51 = visit(cookie3, Date.from(Instant.parse("2020-06-15T12:00:00.000Z")), Date.from(Instant.parse("2020-06-15T14:00:00.000Z")));
        Visit visit2 = visit(larry, Date.from(Instant.parse("2020-06-15T12:00:00.000Z")), Date.from(Instant.parse("2020-06-15T14:00:00.000Z")));
        Visit visit3 = visit(beky, Date.from(Instant.parse("2020-06-15T15:00:00.000Z")), Date.from(Instant.parse("2020-06-15T18:00:00.000Z")));
        Visit visit4 = visit(bibi, Date.from(Instant.parse("2020-06-17T10:00:00.000Z")), Date.from(Instant.parse("2020-06-15T11:00:00.000Z")));
        Visit visit5 = visit(pancake, Date.from(Instant.parse("2020-06-25T08:00:00.000Z")), Date.from(Instant.parse("2020-06-15T10:15:00.000Z")));
        Visit visit52 = visit(cookie4, Date.from(Instant.parse("2020-06-25T08:00:00.000Z")), Date.from(Instant.parse("2020-06-15T10:15:00.000Z")));
        Visit visit53 = visit(cookie4, Date.from(Instant.parse("2020-06-25T08:00:00.000Z")), Date.from(Instant.parse("2020-06-15T10:15:00.000Z")));
        log.info("Visits loaded successfully.");

        PerformedService performedService1 = performedService(visit1, washing);
        PerformedService performedService2 = performedService(visit2, haircut);
        PerformedService performedService3 = performedService(visit3, grooming);
        PerformedService performedService4 = performedService(visit4, washing);
        PerformedService performedService5 = performedService(visit5, haircut);
        PerformedService performedService6 = performedService(visit5, nailTrim);
        PerformedService performedService21 = performedService(visit21, haircut);
        PerformedService performedService31 = performedService(visit31, grooming);
        PerformedService performedService41 = performedService(visit41, washing);
        PerformedService performedService51 = performedService(visit51, haircut);
        PerformedService performedService61 = performedService(visit52, nailTrim);
        PerformedService performedService42 = performedService(visit53, washing);
        PerformedService performedService52 = performedService(visit51, haircut);
        PerformedService performedService62 = performedService(visit53, nailTrim);
        log.info("Performed services loaded successfully.");

    }

    private Dog dog(String name, String breed, Gender gender, Date dateOfBirth, Person owner) {
        Dog dog = new Dog();
        dog.setName(name);
        dog.setBreed(breed);
        dog.setGender(gender);
        dog.setDateOfBirth(dateOfBirth);
        dog.setOwner(owner);
        dogService.create(dog);
        return dog;
    }

    private Person person(String name, String surname, String address, String password, String phone, Boolean isAdmin) {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setAddress(address);
        person.setPassword(password);
        person.setAdmin(isAdmin);
        person.setPhoneNumber(phone);
        personService.create(person);
        return person;
    }

    private Visit visit(Dog dog, Date start, Date finish) {
        Visit visit = new Visit();
        visit.setDog(dog);
        visit.setStart(start);
        visit.setFinish(finish);
        visitService.create(visit);
        return visit;
    }

    private ServiceType serviceType(String name, String description, Duration duration, BigDecimal price) {
        ServiceType serviceType = new ServiceType();
        serviceType.setName(name);
        serviceType.setDescription(description);
        serviceType.setPrice(price);
        serviceType.setStandardLength(duration);
        serviceTypeService.create(serviceType);
        return serviceType;
    }

    private Employment employment(Person person, String positionName, Date start, Date end, BigDecimal salary) {
        Employment employment = new Employment();
        employment.setPositionName(positionName);
        employment.setPerson(person);
        employment.setStartDate(start);
        employment.setEndDate(end);
        employment.setSalary(salary);
        employmentService.create(employment);
        return employment;

    }

    private PerformedService performedService(Visit visit, ServiceType serviceType) {
        PerformedService performedService = new PerformedService();
        performedService.setVisit(visit);
        performedService.setServiceType(serviceType);
        performedServiceService.create(performedService);
        return performedService;
    }

}
