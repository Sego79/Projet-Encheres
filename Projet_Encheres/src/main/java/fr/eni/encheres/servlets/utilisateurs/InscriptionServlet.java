package fr.eni.encheres.servlets.utilisateurs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.beans.Infos;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateur;


@WebServlet("/Inscription")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UtilisateursManager utilisateursManager = UtilisateursManager.GetInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Utilisateur utilisateurTemp = new Utilisateur(-1);
		request.setAttribute("utilisateur_temp", utilisateurTemp);
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		Infos infos = (Infos)request.getAttribute("infos");


		Cookie[] cookies = request.getCookies();
		for(Cookie cook : cookies) {
			if(cook.getName().contentEquals("username")) {
				utilisateurTemp.setPseudo(cook.getValue());
			}
		}

		if(request.getParameter("inscription") != null) {
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			String motDePasse = request.getParameter("mot_de_passe");
			String motDePasseRepete = request.getParameter("mot_de_passe_repete");



			Utilisateur utilisateur = utilisateursManager.traiteRequeteInscription(
					pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, motDePasseRepete, erreurs);

			if(utilisateur != null) {
				request.setAttribute("utilisateur_temp", utilisateur);
			} else {
				utilisateurTemp.setPseudo(pseudo);
			}

			if(request.getParameter("souvenir") != null) {
				response.addCookie(new Cookie("username", utilisateurTemp.getPseudo()));
			}


			if(erreurs.hasErrors()) {
				request.getRequestDispatcher("WEB-INF/jsps/auth/Register.jsp").forward(request, response);
			} else {

				infos.addInfo("Bienvenue sur TrocEncheres " + utilisateur.getPseudo() + " !");

				request.getSession().setAttribute("user_id", utilisateur.getNoUtilisateur());
				request.getSession().setAttribute("user_pseudo", utilisateur.getPseudo());
				request.getSession().setAttribute("is_admin", false);


				request.getRequestDispatcher("/").forward(request, response);
			}
		}
		else {
			request.getRequestDispatcher("WEB-INF/jsps/auth/Register.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
