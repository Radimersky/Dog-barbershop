package cz.muni.fi.pa165.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Logout controller
 *
 * @author Aneta Moravcikova, 456444
 */
@Controller
@RequestMapping("/logout-page*")
public class LogoutController {

    @RequestMapping(value = "/logout*", method = RequestMethod.GET)
    public String logout(Model model, UriComponentsBuilder uriComponentsBuilder, RedirectAttributes redirectAttributes, HttpServletResponse response) {

        Cookie phone = new Cookie("phone", null);
        phone.setPath("/");
        phone.setMaxAge(0);
        response.addCookie(phone);

        Cookie auth = new Cookie("auth", "false");
        auth.setPath("/");
        auth.setMaxAge(0);
        response.addCookie(auth);

        redirectAttributes.addFlashAttribute("alert_success", "Logout was successfull");
        return "redirect:" + uriComponentsBuilder.path("/").toUriString();
    }
}
