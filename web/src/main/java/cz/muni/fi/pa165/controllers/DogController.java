package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.PerformedServiceDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.dto.VisitDTO;
import cz.muni.fi.pa165.enums.Gender;
import cz.muni.fi.pa165.facade.DogFacade;
import cz.muni.fi.pa165.facade.PerformedServiceFacade;
import cz.muni.fi.pa165.facade.PersonFacade;
import cz.muni.fi.pa165.facade.VisitFacade;
import cz.muni.fi.pa165.validators.DogDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Dog controller
 *
 * @author Marek Radiměřský
 */
@Controller
@RequestMapping("/dog/*")
public class DogController {

    @Autowired
    private DogFacade dogFacade;

    @Autowired
    private PersonFacade personFacade;

    @Autowired
    private VisitFacade visitFacade;

    @Autowired
    private PerformedServiceFacade performedServiceFacade;


    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String root(Model model) {
        model.addAttribute("dogs", dogFacade.getAllDogs());
        return "dog/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

        if (binder.getTarget() instanceof DogDTO) {
            binder.addValidators(new DogDTOValidator());
        }
    }

    @RequestMapping(value = "/create*", method = RequestMethod.GET)
    public String create(Model model) {
        DogDTO dogDTO = new DogDTO();
        dogDTO.setOwner(new PersonDTO());
        model.addAttribute("dog", dogDTO);
        model.addAttribute("ownersList", personFacade.getAllPersons());
        model.addAttribute("genders", getDogGenders());
        return "dog/create";
    }

    @RequestMapping(value = "/create*", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("dog") DogDTO dog, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            model.addAttribute("ownersList", personFacade.getAllPersons());
            model.addAttribute("genders", getDogGenders());
            return "dog/create";
        }
        dogFacade.createDog(dog);
        model.addAttribute("dogs", dogFacade.getAllDogs());
        model.addAttribute("alert_success", "Created dog with name " + dog.getName());
        return "dog/list";
    }

    @RequestMapping(value = "/list*", method = RequestMethod.POST)
    public String list(@ModelAttribute("dog") DogDTO dog, Model model) {
        model.addAttribute("dogs", dogFacade.getAllDogs());
        return "dog/list";
    }

    @RequestMapping(value = "/edit/{id}*", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("dog") DogDTO dog, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            model.addAttribute("ownersList", personFacade.getAllPersons());
            model.addAttribute("genders", getDogGenders());
            return "dog/edit";
        }
        dogFacade.updateDog(dog);
        model.addAttribute("dogs", dogFacade.getAllDogs());
        model.addAttribute("alert_success", "Edited dog with name " + dog.getName());
        return "dog/list";

    }

    @RequestMapping(value = "/edit/{id}*", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable long id) throws Exception {
        DogDTO dog = dogFacade.getDog(id);

        Collection<PersonDTO> persons = personFacade.getAllPersons();
        Long dogOwnerId = -1L;
        for (PersonDTO person : persons) {
            if (person.getId().equals(dog.getOwner().getId())) {
                dogOwnerId = person.getId();
            }
        }

        if (!dogOwnerId.equals(-1L)) {
            model.addAttribute("dog", dogFacade.getDog(id));
            model.addAttribute("dogOwnerId", dogOwnerId);
            model.addAttribute("ownersList", persons);
            model.addAttribute("genders", getDogGenders());
            return "dog/edit";
        } else {
            throw new Exception("Dog with id " + id + " does not have an owner");
        }

    }

    @RequestMapping(value = "/delete/{id}*", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable long id) {
        removeAllDogVisits(id);
        DogDTO dog = dogFacade.getDog(id);
        if(dog == null) return "dog/list";
        dogFacade.deleteDog(dog);
        model.addAttribute("alert_success", "Deleted dog with name " + dog.getName());
        model.addAttribute("dogs", dogFacade.getAllDogs());
        return "dog/list";
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
}
