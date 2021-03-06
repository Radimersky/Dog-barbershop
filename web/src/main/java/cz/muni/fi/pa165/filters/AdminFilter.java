package cz.muni.fi.pa165.filters;

import cz.muni.fi.pa165.facade.PersonFacade;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
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
import java.util.Optional;

/**
 * @author Martin Palic
 */
//You have to be admin to access these pages
@WebFilter(urlPatterns = {"/employee/*", "/serviceType/*"})
public class AdminFilter extends GenericFilterBean {


    public PersonFacade personFacade;

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        if (personFacade == null) {
            ServletContext servletContext = r.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            personFacade = webApplicationContext.getBean(PersonFacade.class);
        }
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        try {
            Optional<Cookie> auth = Arrays.stream(((HttpServletRequest) r).getCookies()).filter(o -> o.getName().equals("auth")).findFirst();
            Optional<Cookie> phone = Arrays.stream(((HttpServletRequest) r).getCookies()).filter(o -> o.getName().equals("phone")).findFirst();


            if (auth.isPresent() && phone.isPresent() && auth.get().getValue().equals("true") && personFacade.isAdmin(phone.get().getValue())) {
                chain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
        }
        response.sendRedirect("/pa165/login-page/access-denied");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().println("<html><body><h1>401 Unauthorized</h1>You have to be Admin to access this page</body></html>");
    }
}
