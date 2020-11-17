package cz.muni.fi.pa165.filters;

import cz.muni.fi.pa165.dto.EmploymentDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.facade.EmploymentFacade;
import cz.muni.fi.pa165.facade.PersonFacade;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


/**
 * @author Martin Palic
 */
//You have to be Employee to access these pages
@WebFilter(urlPatterns = {"/employee/*", "/serviceType/*", "/visit/*", "/dog/*"})
public class EmployeeFilter implements Filter {

    public EmploymentFacade employmentFacade;

    public PersonFacade personFacade;

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        if (personFacade == null || employmentFacade == null) {
            ServletContext servletContext = r.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            personFacade = webApplicationContext.getBean(PersonFacade.class);
            employmentFacade = webApplicationContext.getBean(EmploymentFacade.class);
        }
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        try {
            Optional<Cookie> auth = Arrays.stream(((HttpServletRequest) r).getCookies()).filter(o -> o.getName().equals("auth")).findFirst();
            Optional<Cookie> phone = Arrays.stream(((HttpServletRequest) r).getCookies()).filter(o -> o.getName().equals("phone")).findFirst();


            if (auth.isPresent() && phone.isPresent() && auth.get().getValue().equals("true")) {
                Collection<PersonDTO> personDTOS = personFacade.filterPersons(phone.get().getValue(), null);
                if (personDTOS.size() != 1) {
                    returnError(response);
                    return;
                }
                Optional<EmploymentDTO> employmentDTO = employmentFacade.getAllEmployments().stream().filter(o -> o.getPerson().getPhoneNumber().equals(phone.get().getValue())).findFirst();
                if (!employmentDTO.isPresent()) {
                    returnError(response);
                    return;
                }
                chain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
        }
        returnError(response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {

    }

    private void returnError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/pa165/login-page/access-denied");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().println("<html><body><h1>401 Unauthorized</h1>You have to be Employee to access this page</body></html>");
    }
}
