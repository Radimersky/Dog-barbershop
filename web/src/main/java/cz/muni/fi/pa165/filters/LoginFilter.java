package cz.muni.fi.pa165.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Martin Palic
 */
//You have to be logged in to access these pages
@WebFilter(urlPatterns = {"/employee/*", "/serviceType/*", "/visit/*", "/dog/*", "/user/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        try {
            Optional<Cookie> auth = Arrays.stream(((HttpServletRequest) r).getCookies()).filter(o -> o.getName().equals("auth")).findFirst();
            Optional<Cookie> phone = Arrays.stream(((HttpServletRequest) r).getCookies()).filter(o -> o.getName().equals("phone")).findFirst();


            if (auth.isPresent() && phone.isPresent() && auth.get().getValue().equals("true")) {
                chain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1>You have to be Logged In to access this page</body></html>");

    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {

    }
}
