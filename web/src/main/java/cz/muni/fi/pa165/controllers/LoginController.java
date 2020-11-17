package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.PersonAuthDTO;
import cz.muni.fi.pa165.facade.PersonFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Login controller
 *
 * @author Martin Palic
 */
@Controller
@RequestMapping("/login-page*")
public class LoginController {

    @Autowired
    private PersonFacade personFacade;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("login-page", "personauth", new PersonAuthDTO());
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied(RedirectAttributes redirectAttributes, UriComponentsBuilder uriComponentsBuilder) {
        redirectAttributes.addFlashAttribute("alert_fail", "You don't have rights to access requested page");
        return "redirect:" + uriComponentsBuilder.path("/").toUriString();
    }

    @RequestMapping(value = "/login*", method = RequestMethod.POST)
    public String submit(@ModelAttribute("employee") PersonAuthDTO personauth,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder, HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            return null;
        }

        if (personFacade.authenticatePerson(personauth)) {
            Cookie phone = new Cookie("phone", personauth.getPhone());
            phone.setPath("/");
            phone.setMaxAge(-1);
            response.addCookie(phone);

            Cookie auth = new Cookie("auth", "true");
            auth.setPath("/");
            auth.setMaxAge(-1);
            response.addCookie(auth);
            redirectAttributes.addFlashAttribute("alert_success", "Login was successfull");

            return "redirect:" + uriComponentsBuilder.path("/").toUriString();
        }
        redirectAttributes.addFlashAttribute("alert_fail", "Login has failed");
        return "redirect:" + uriComponentsBuilder.path("/").toUriString();
    }

}
