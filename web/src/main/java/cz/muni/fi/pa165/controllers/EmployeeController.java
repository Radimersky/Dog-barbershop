package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.EmploymentDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.facade.EmploymentFacade;
import cz.muni.fi.pa165.facade.PersonFacade;
import cz.muni.fi.pa165.validators.EmploymentDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;

/**
 * Employee controller
 *
 * @author Martin Palic
 */
@Controller
@RequestMapping("/employee/*")
public class EmployeeController {

    @Autowired
    private EmploymentFacade employmentFacade;
    @Autowired
    private PersonFacade personFacade;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof EmploymentDTO) {
            binder.addValidators(new EmploymentDTOValidator());
        }
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String root(Model model) {
        model.addAttribute("employees", employmentFacade.getAllEmployments());
        model.addAttribute("person", new PersonDTO());
        return "employee/list";
    }

    @RequestMapping(value = "/list*", method = RequestMethod.POST)
    public String list(@ModelAttribute("person") PersonDTO person, Model model) {
        model.addAttribute("employees", employmentFacade.filterEmployments(person.getPhoneNumber(), person.getName()));
        model.addAttribute("person", person);
        return "employee/list";
    }

    @RequestMapping(value = "/edit/{id}*", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("employee", employmentFacade.getEmployment(id));
        return "employee/edit";
    }

    @RequestMapping(value = "/create*", method = RequestMethod.GET)
    public String create(Model model) {
        EmploymentDTO employmentDTO = new EmploymentDTO();
        employmentDTO.setPerson(new PersonDTO());
        employmentDTO.setStartDate(new Date());
        employmentDTO.setEndDate(new Date());
        model.addAttribute("employee", employmentDTO);
        return "employee/create";
    }

    @RequestMapping(value = "/create*", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("employee") EmploymentDTO employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }
            return "employee/create";
        }

        Long aLong = personFacade.createPerson(employee.getPerson());
        employee.setStartDate(Date.from(Instant.now()));
        employee.getPerson().setId(aLong);
        employmentFacade.createEmployment(employee);

        model.addAttribute("employees", employmentFacade.getAllEmployments());
        model.addAttribute("alert_success", "Created employee with phone number " + employee.getPerson().getPhoneNumber());
        model.addAttribute("person", new PersonDTO());
        return "employee/list";
    }

    @RequestMapping(value = "/edit/{id}*", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("employee") EmploymentDTO employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                if(!error.getField().equals("person.password")) {
                    model.addAttribute(error.getField() + "_error", true);
                }
            }
            if(!(bindingResult.getFieldErrors().size() == 1 && bindingResult.getFieldErrors().get(0).getField().equals("person.password"))){
                return "employee/edit";
            }
        }
        PersonDTO personDTO = personFacade.filterPersons(employee.getPerson().getPhoneNumber(), null).stream().findFirst().get();
        employee.getPerson().setId(personDTO.getId());
        employee.getPerson().setPassword(personDTO.getPassword());
        personFacade.updatePerson(employee.getPerson());
        EmploymentDTO employmentDTO = employmentFacade.getEmployment(employee.getId());
        employmentDTO.setPositionName(employee.getPositionName());
        employmentFacade.updateEmployment(employmentDTO);
        model.addAttribute("employees", employmentFacade.getAllEmployments());
        model.addAttribute("alert_success", "Edited employee with phone number " + employee.getPerson().getPhoneNumber());
        model.addAttribute("person", new PersonDTO());
        return "employee/list";
    }


    @RequestMapping(value = "/delete/{id}*", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable long id) {
        EmploymentDTO employmentDTO = employmentFacade.getEmployment(id);
        if(employmentDTO == null) return "employee/list";
        employmentFacade.deleteEmployment(employmentDTO);
        model.addAttribute("alert_success", "Deleted employee with phone number " + employmentDTO.getPerson().getPhoneNumber());
        model.addAttribute("employees", employmentFacade.getAllEmployments());
        model.addAttribute("person", new PersonDTO());
        return "employee/list";
    }

}
