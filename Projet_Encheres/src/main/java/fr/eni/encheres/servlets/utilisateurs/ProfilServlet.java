package fr.eni.encheres.servlets.utilisateurs;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.beans.Infos;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProfilServlet", value = "/Profil")
public class ProfilServlet extends HttpServlet {

	private static UtilisateursManager utilisateursManager = UtilisateursManager.GetInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		Infos infos = (Infos) request.getAttribute("infos");
		request.setAttribute("utilisateur", new Utilisateur(-1));

		String addresse = "WEB-INF/jsps/ProfilUtilisateur.jsp";

		Integer userId = ((Integer) request.getSession().getAttribute("user_id"));
		if(userId == null) {
			erreurs.addErreur("Pas connecté");

		} else {
			Utilisateur utilisateurConnecte = utilisateursManager.getUtilisateurById(userId, erreurs);

			if(request.getParameter("mise_a_jour") != null) {
				utilisateursManager.modifieDepuisLeWeb(
						utilisateurConnecte, request.getParameter("pseudo"),
						request.getParameter("nom"),
						request.getParameter("prenom"),
						request.getParameter("email"),
						request.getParameter("telephone"),
						request.getParameter("rue"),
						request.getParameter("codePostal"),
						request.getParameter("ville"),
						request.getParameter("mot_de_passe_original"),
						request.getParameter("mot_de_passe_nouveau"),
						request.getParameter("mot_de_passe_repete"),
						erreurs
				);

				if(!erreurs.hasErrors()) {
					infos.addInfo("Profil modifié !");
				}

			}

			if(request.getParameter("supprimer") != null) {

				utilisateurConnecte.setActif(false);
				utilisateursManager.sauvegarderUtilisateur(utilisateurConnecte, erreurs);
				infos.addInfo("Au revoir " + utilisateurConnecte.getPseudo());
				addresse = "WEB-INF/jsps/accueil.jsp";
			}

			request.setAttribute("utilisateur", utilisateurConnecte);

		}

		request.getRequestDispatcher(addresse).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
