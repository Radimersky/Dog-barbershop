package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.dto.PerformedServiceDTO;
import cz.muni.fi.pa165.dto.VisitDTO;
import cz.muni.fi.pa165.facade.PerformedServiceFacade;
import cz.muni.fi.pa165.facade.VisitFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Visit controller
 *
 * @author Marek Radiměřský
 */
@Controller
@RequestMapping("/visit/*")
public class VisitController {

    @Autowired
    private VisitFacade visitFacade;

    @Autowired
    private PerformedServiceFacade performedServiceFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat visit = new SimpleDateFormat("dd.MM.yyyy h:mm");
        visit.setLenient(true);
        binder.registerCustomEditor(Date.class, "start", new CustomDateEditor(visit, true));
        binder.registerCustomEditor(Date.class, "finish", new CustomDateEditor(visit, true));
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String root(Model model) {
        model.addAttribute("visits", visitFacade.getAllVisits());
        return "visit/list";
    }

    @RequestMapping(value = "/list*", method = RequestMethod.POST)
    public String list(@ModelAttribute("dog") DogDTO dog, Model model) {
        model.addAttribute("visits", visitFacade.getAllVisits());
        return "visit/list";
    }

    @RequestMapping(value = "/delete/{id}*", method = RequestMethod.GET)
    public String deleteVisit(Model model, @PathVariable long id) {
        VisitDTO visit;
        try {
            removeAllPerformedServicesOfVisit(id);
            visit = visitFacade.getVisit(id);
            visitFacade.deleteVisit(visit);
        }catch(Exception e){
            return "visit/list";
        }
        model.addAttribute("alert_success", "Deleted visit for dog with name " + visit.getDog().getName());
        model.addAttribute("visits", visitFacade.getAllVisits());
        return "visit/list";
    }

    private void removeAllPerformedServicesOfVisit(Long visitId) {
        List<PerformedServiceDTO> perfServices = performedServiceFacade.getAllPerformedServices();
        for (PerformedServiceDTO service : perfServices) {
            if (service.getVisit().getId().equals(visitId)) {
                performedServiceFacade.deletePerformedService(service);
            }
        }
    }
}
