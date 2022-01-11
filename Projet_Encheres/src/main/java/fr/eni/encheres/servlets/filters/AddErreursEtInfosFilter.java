package fr.eni.encheres.servlets.filters;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.beans.Infos;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "AddErreursEtInfosFilter", urlPatterns = "/*")
public class AddErreursEtInfosFilter implements Filter {

	// Ce filtre ajoute un attribut "errors" et "infos" pour pouvoir afficher des bulles d'erreur et info où l'on
	// veut depuis les JSP. Le filtre AddUserSessionFilter en a besoin donc techniquement ca serait bien
	// si il y avait un moyen que ce filtre s'execute avant les autres

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		request.setAttribute("infos", new Infos());
		request.setAttribute("errors", new Erreurs());

		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}
}
