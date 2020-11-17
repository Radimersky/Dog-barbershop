package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.PerformedServiceDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.dto.ServiceTypeDTO;
import cz.muni.fi.pa165.dto.VisitCreateDTO;
import cz.muni.fi.pa165.dto.VisitDTO;
import cz.muni.fi.pa165.dto.VisitOverviewDTO;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.facade.*;
import cz.muni.fi.pa165.validators.DogDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User controller
 *
 * @author Marek Radiměřský
 */
@Controller
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    private DogFacade dogFacade;

    @Autowired
    private PersonFacade personFacade;

    @Autowired
    private VisitFacade visitFacade;

    @Autowired
    private PerformedServiceFacade performedServiceFacade;

    @Autowired
    private ServiceTypeFacade serviceTypeFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat birth = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat visit = new SimpleDateFormat("dd.MM.yyyy h:mm");
        birth.setLenient(true);
        visit.setLenient(true);
        binder.registerCustomEditor(Date.class, "dateOfBirth", new CustomDateEditor(birth, true));
        binder.registerCustomEditor(Date.class, "start", new CustomDateEditor(visit, true));
        binder.registerCustomEditor(Date.class, "finish", new CustomDateEditor(visit, true));

        if (binder.getTarget() instanceof DogDTO) {
            binder.addValidators(new DogDTOValidator());
        }
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String root(Model model, @CookieValue("phone") String phone) {
        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("dogs", dogs);
        return "user/list";
    }

    @RequestMapping(value = "visit/delete/{id}*", method = RequestMethod.GET)
    public String deleteVisit(Model model, @PathVariable long id, @CookieValue("phone") String phone) {
        removeAllPerformedServicesOfVisit(id);
        VisitDTO visit = visitFacade.getVisit(id);
        if(visit == null) return "user/list";
        visitFacade.deleteVisit(visit);

        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);

        model.addAttribute("alert_success", "Deleted visit for dog with name " + visit.getDog().getName());
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("dogs", dogs);
        return "user/list";
    }

    @RequestMapping(value = "/dog/edit/{id}*", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("dog") DogDTO dog, BindingResult bindingResult, Model model, @CookieValue("phone") String phone) throws Exception {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            model.addAttribute("genders", getDogGenders());
            return "user/dog/edit";
        }

        dog.setOwner(getPersonByPhone(phone));
        dogFacade.updateDog(dog);
        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("dogs", getDogsByOwnersPhone(phone));
        model.addAttribute("alert_success", "Edited dog with name " + dog.getName());
        return "user/list";
    }

    @RequestMapping(value = "/dog/edit/{id}*", method = RequestMethod.GET)
    public String edit(Model model, @CookieValue("phone") String phone, @PathVariable long id) {
        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);
        model.addAttribute("dog", dogFacade.getDog(id));
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("genders", getDogGenders());
        return "user/dog/edit";

    }

    @RequestMapping(value = "/visit/create/{id}*", method = RequestMethod.GET)
    public String createVisit(Model model, @PathVariable long id) {
        VisitCreateDTO visitCreateDTO = new VisitCreateDTO();
        visitCreateDTO.setStart(new Date());

        model.addAttribute("visit", visitCreateDTO);
        model.addAttribute("servicelist", serviceTypeFacade.getAllServiceTypes());
        return "user/visit/create";
    }

    @RequestMapping(value = "/visit/create/{id}*", method = RequestMethod.POST)
    public String createVisit(@ModelAttribute("createvisit") VisitCreateDTO visitCreateDTO, Model model, @CookieValue("phone") String phone, @PathVariable long id) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setDog(dogFacade.getDog(id));
        visitDTO.setStart(visitCreateDTO.getStart());
        visitDTO.setFinish(calculateVisitFinish(visitCreateDTO.getStart(), visitCreateDTO.getServicesId()));
        visitDTO.setId(null);
        Long visitId = visitFacade.createVisit(visitDTO);

        List<String> serviceIds = visitCreateDTO.getServicesId();
        for (String serviceId : serviceIds) {
            PerformedServiceDTO performedServiceDTO = new PerformedServiceDTO();
            ServiceTypeDTO serviceTypeDTO = serviceTypeFacade.getServiceType(Long.parseLong(serviceId));

            performedServiceDTO.setServiceType(serviceTypeDTO);
            performedServiceDTO.setVisit(visitDTO);

            performedServiceDTO.getVisit().setId(visitId);
            performedServiceFacade.createPerformedService(performedServiceDTO);
        }

        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("dogs", dogs);
        model.addAttribute("alert_success", "Visit was created");
        return "user/list";
    }

    @RequestMapping(value = "/delete/{id}*", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable long id, @CookieValue("phone") String phone) {
        removeAllDogVisits(id);
        DogDTO dog = dogFacade.getDog(id);
        if(dog == null) return "user/list";
        dogFacade.deleteDog(dog);

        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);

        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("alert_success", "Deleted dog with name " + dog.getName());
        model.addAttribute("dogs", dogs);
        return "user/list";
    }

    @RequestMapping(value = "/list*", method = RequestMethod.POST)
    public String list(@ModelAttribute("dog") DogDTO dog, Model model, @CookieValue("phone") String phone) {
        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("dogs", dogs);
        return "user/list";
    }

    @RequestMapping(value = "/dog/create/*", method = RequestMethod.GET)
    public String create(Model model) {
        DogDTO dogDTO = new DogDTO();
        model.addAttribute("dog", dogDTO);
        model.addAttribute("genders", getDogGenders());
        return "user/dog/create";
    }

    @RequestMapping(value = "/dog/create/*", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("dog") DogDTO dog, BindingResult bindingResult, Model model, @CookieValue("phone") String phone) throws Exception {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            model.addAttribute("genders", getDogGenders());
            return "user/dog/create";
        }

        dog.setOwner(getPersonByPhone(phone));
        dogFacade.createDog(dog);
        Collection<DogDTO> dogs = getDogsByOwnersPhone(phone);
        model.addAttribute("visits", getDogsVisits(dogs));
        model.addAttribute("dogs", dogs);
        model.addAttribute("alert_success", "Created dog with name " + dog.getName());
        return "user/list";
    }

    private List<DogDTO> getDogsByOwnersPhone(String phone) {
        List<DogDTO> dogs = dogFacade.getAllDogs();
        dogs.removeIf(n -> !(n.getOwner().getPhoneNumber().equals(phone)));
        return dogs;
    }

    private PersonDTO getPersonByPhone(String phone) throws Exception {
        Collection<PersonDTO> persons = personFacade.filterPersons(phone, null);

        if (persons.size() == 1) {
            // Get first person from collection
            return persons.iterator().next();
        } else {
            throw new Exception("Several people have the same phone number");
        }
    }

    private void removeAllDogVisits(Long dogId) {
        List<VisitDTO> visits = visitFacade.getAllVisits();
        for (VisitDTO visit : visits) {
            if (visit.getDog().getId().equals(dogId)) {
                removeAllPerformedServicesOfVisit(visit.getId());
                visitFacade.deleteVisit(visit);
            }
        }
    }

    private void removeAllPerformedServicesOfVisit(Long visitId) {
        List<PerformedServiceDTO> perfServices = performedServiceFacade.getAllPerformedServices();
        for (PerformedServiceDTO service : perfServices) {
            if (service.getVisit().getId().equals(visitId)) {
                performedServiceFacade.deletePerformedService(service);
            }
        }
    }

    private List<Gender> getDogGenders() {
        List<Gender> genders = new ArrayList<>();
        genders.add(Gender.MALE);
        genders.add(Gender.FEMALE);
        return genders;
    }

    /**
     * Prepares visitOverviewDTO
     *
     * @param dogs
     * @return
     */
    private List<VisitOverviewDTO> getDogsVisits(Collection<DogDTO> dogs) {
        List<VisitDTO> visits = visitFacade.getAllVisits();

        List<VisitDTO> myDogsVisits = new ArrayList<>();
        // Find visits of logged user dogs
        for (VisitDTO visit : visits) {
            for (DogDTO dog : dogs) {
                if (visit.getDog().getId().equals(dog.getId())) {
                    myDogsVisits.add(visit);
                }
            }
        }
        // Key = visit id
        Map<Long, VisitOverviewDTO> visitOverviewMap = new HashMap<>();

        List<PerformedServiceDTO> allPerformedServices = performedServiceFacade.getAllPerformedServices();
        // Fill VisitOverviewDTOs
        for (PerformedServiceDTO perfServ : allPerformedServices) {
            for (VisitDTO mydogVisit : myDogsVisits) {
                // If performed service that belongs to my dogs visit is found
                if (perfServ.getVisit().getId().equals(mydogVisit.getId())) {
                    // Get proper visitOverviewDTO from map
                    VisitOverviewDTO visitOverview = visitOverviewMap.get(mydogVisit.getId());
                    // If visitOverview is not yet in map, create new one and fill it. Else just add one more service type
                    if (visitOverview == null) {

                        visitOverview = new VisitOverviewDTO();
                        visitOverview.setDog(mydogVisit.getDog());
                        visitOverview.setStart(mydogVisit.getStart());
                        visitOverview.setFinish(mydogVisit.getFinish());
                        visitOverview.setId(mydogVisit.getId());

                        List<ServiceTypeDTO> serviceTypeDTOS = new ArrayList<>();
                        serviceTypeDTOS.add(perfServ.getServiceType());
                        visitOverview.setServiceTypes(serviceTypeDTOS);

                        visitOverviewMap.put(mydogVisit.getId(), visitOverview);
                    } else {
                        List<ServiceTypeDTO> serviceTypeDTOS = visitOverview.getServiceTypes();
                        serviceTypeDTOS.add(perfServ.getServiceType());
                        visitOverview.setServiceTypes(serviceTypeDTOS);
                    }
                }
            }
        }

        // Convert map to list, because map is no longer needed
        List<VisitOverviewDTO> visitOverviewDTOs = new ArrayList(visitOverviewMap.values());

        // Calculate price
        for (VisitOverviewDTO visitOverviewDTO : visitOverviewDTOs) {
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            List<ServiceTypeDTO> serviceTypeDTOS = visitOverviewDTO.getServiceTypes();
            for (ServiceTypeDTO serviceTypeDTO : serviceTypeDTOS) {
                totalPrice = totalPrice.add(serviceTypeDTO.getPrice());
            }
            visitOverviewDTO.setTotalPrice(totalPrice);
        }

        return visitOverviewDTOs;
    }

    /**
     * Calculates finish time from start date and selected service types
     *
     * @param start
     * @param serviceTypeIds
     * @return
     */
    private Date calculateVisitFinish(Date start, List<String> serviceTypeIds) {
        Long sum = start.getTime();

        Duration durSum = Duration.ZERO;
        for (String serviceTypeId : serviceTypeIds) {
            durSum = durSum.plus(serviceTypeFacade.getServiceType(Long.parseLong(serviceTypeId)).getStandardLength());
        }
        sum += durSum.toMillis();

        return new Date(sum);
    }

}
