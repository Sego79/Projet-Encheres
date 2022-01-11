package fr.eni.encheres.servlets.filters;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AddUserSessionFilter")
public class AddUserSessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// ce filtre met a jour le status pseudo et admin en ours de session pour pas avoir
		// à se reconnecter si ce statut change en cours de session
		Integer userId = (Integer) httpServletRequest.getSession().getAttribute("user_id");
		if(userId != null) {
			Utilisateur utilisateur = UtilisateursManager.GetInstance().getUtilisateurById(userId, new Erreurs());
			httpServletRequest.getSession().setAttribute("user_pseudo", utilisateur.getPseudo());
			httpServletRequest.getSession().setAttribute("is_admin", utilisateur.isAdministrateur());

			httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/Login");
		}


		chain.doFilter(request, response);
	}
}
