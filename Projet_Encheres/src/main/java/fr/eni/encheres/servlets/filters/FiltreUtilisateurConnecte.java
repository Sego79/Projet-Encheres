package fr.eni.encheres.servlets.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "FiltreUtilisateurConnecté",urlPatterns={
		"/Vendre", "/MesEncheres","/ValiderOffreServlet", "/MonProfil"
})

public class FiltreUtilisateurConnecte implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if(httpServletRequest.getSession().getAttribute("user_id") == null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/Login");
		}

		else chain.doFilter(request, response);
	}


}
