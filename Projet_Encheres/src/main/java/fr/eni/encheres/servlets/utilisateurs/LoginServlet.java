package fr.eni.encheres.servlets.utilisateurs;

import fr.eni.encheres.Utilitaires;
import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.beans.Infos;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtilisateursManager utilisateursManager = UtilisateursManager.GetInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		Infos infos = (Infos)request.getAttribute("infos");

		Utilisateur utilisateur =  new Utilisateur();
		request.setAttribute("utilisateur", utilisateur);

		Cookie[] cookies = request.getCookies();
		for(Cookie cook : cookies) {
			if(cook.getName().contentEquals("username")) {
				utilisateur.setPseudo(cook.getValue());
			}
		}

		if(request.getParameter("inscription") != null) {
			String pseudo = request.getParameter("pseudo");
			String motDePasse = request.getParameter("password");

			Utilisateur utilisateurExiste = utilisateursManager.getUtilisateurAvecLoginMotDePasse(
					pseudo, motDePasse, erreurs);


			request.setAttribute("utilisateur", utilisateurExiste);


			utilisateurExiste.setPseudo(pseudo);

			if(request.getParameter("souvenir") != null) {
				response.addCookie(new Cookie("username", utilisateurExiste.getPseudo()));
			}


			if(!erreurs.hasErrors()) {

				infos.addInfo("Bon retour sur TrocEncheres " + utilisateurExiste.getPseudo() + " !");

				request.getSession().setAttribute("user_id", utilisateurExiste.getNoUtilisateur());
				request.getSession().setAttribute("user_pseudo", utilisateurExiste.getPseudo());
				request.getSession().setAttribute("is_admin", utilisateurExiste.isAdministrateur());

				request.getRequestDispatcher("/accueil").forward(request, response);
				return;
			} else {
				request.getRequestDispatcher("/WEB-INF/jsps/auth/Login.jsp").forward(request, response);
				return;
			}

		}
		request.getRequestDispatcher("/WEB-INF/jsps/auth/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
