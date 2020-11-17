package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.ServiceTypeDTO;
import cz.muni.fi.pa165.facade.ServiceTypeFacade;
import cz.muni.fi.pa165.validators.ServiceTypeDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * ServiceType controller
 *
 * @author Aneta Moravcikova, 456444
 */
@Controller
@RequestMapping("/serviceType/*")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeFacade serviceTypeFacade;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof ServiceTypeDTO) {
            binder.addValidators(new ServiceTypeDTOValidator());
        }
    }


    @RequestMapping(value = "/list*", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("serviceTypes", serviceTypeFacade.getAllServiceTypes());
        return "serviceType/list";
    }

    @RequestMapping(value = "/create*", method = RequestMethod.GET)
    public String create(Model model) {
        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();
        model.addAttribute("serviceType", serviceTypeDTO);
        return "serviceType/create";
    }

    @RequestMapping(value = "/create*", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("serviceType") ServiceTypeDTO st,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }

            return "serviceType/create";

        }
        serviceTypeFacade.createServiceType(st);
        model.addAttribute("serviceTypes", serviceTypeFacade.getAllServiceTypes());
        model.addAttribute("alert_success", "Service type created successfully.");
        return "serviceType/list";
    }


    @RequestMapping(value = "/edit/{id}*", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("serviceType", serviceTypeFacade.getServiceType(id));
        return "serviceType/edit";
    }

    @RequestMapping(value = "/edit/{id}*", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("serviceType") ServiceTypeDTO st, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "_error", true);
            }

            return "serviceType/edit";
        }
        st.setStandardLengthFormated(st.getStandardLengthFormated());
        serviceTypeFacade.updateServiceType(st);
        model.addAttribute("serviceTypes", serviceTypeFacade.getAllServiceTypes());
        model.addAttribute("alert_success", "Service type updated successfully.");
        return "serviceType/list";
    }

}

