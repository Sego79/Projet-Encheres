package fr.eni.encheres.servlets.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EstAdministrateurFilter", urlPatterns={"/CRUDUtilisateurs","/CRUDCategories", "/Admin"})
public class EstAdministrateurFilter implements Filter {

    // seuls les admins peuvent acceder a ces pages
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Boolean isAdmin = (Boolean) httpServletRequest.getSession().getAttribute("is_admin");
        if(isAdmin == null || isAdmin == false) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/");
        }

        else chain.doFilter(request, response);
	}
}
