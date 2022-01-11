package fr.eni.encheres.servlets.utilisateurs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class CRUDUtilisateurs
 */
@WebServlet("/CRUDUtilisateurs")
public class CRUDUtilisateurs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static UtilisateursManager utilisateursManager = UtilisateursManager.GetInstance();


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		String motDePasse = request.getParameter("mot_de_passe");
		String credit = request.getParameter("credit");
		String administrateur = request.getParameter("administrateur");
		String actif = request.getParameter("actif");
		String idUtilisateur = request.getParameter("id_utilisateur");

		Erreurs errors = new Erreurs();

		Utilisateur nouvelUtilisateur = null;

		if (idUtilisateur == null && request.getParameter("ajouter") != null) {
			
			nouvelUtilisateur = utilisateursManager.createUtilisateurDepuisLeWeb(pseudo, nom, prenom, email, telephone,
					rue, codePostal, ville, motDePasse, credit, administrateur, actif, errors);
		}

		request.setAttribute("nouvel_utilisateur", nouvelUtilisateur);

		
		Utilisateur modifUtilisateur = null;

		if (idUtilisateur != null && request.getParameter("modifier") != null) { // demande d'affichage d'utilisateur
			try {
				Integer intModifUtilisateur = Integer.parseInt(idUtilisateur);
				
				modifUtilisateur = utilisateursManager.getUtilisateurById(intModifUtilisateur, errors);
				
			} catch (NumberFormatException e) {
				errors.addErreur("Id utilisateur malformé !");
			}

		}
		
		
		if (idUtilisateur != null && request.getParameter("ajouter") != null) { // soumission formulaire editer
			
			try {
				Integer intModifUtilisateur = Integer.parseInt(idUtilisateur);
				
				modifUtilisateur = utilisateursManager.getUtilisateurById(intModifUtilisateur, errors);
				
				utilisateursManager.modifUtilisateurDepuisLeWeb(modifUtilisateur, pseudo, nom, prenom, email, telephone,
						rue, codePostal, ville, credit, administrateur, actif, errors);
			} catch (NumberFormatException e) {
				errors.addErreur("Id utilisateur malformé !");
			}
			
		}

		request.setAttribute("modif_utilisateur", modifUtilisateur);
		
		
		
		
		Utilisateur deleteUtilisateur = null;
		
		if(request.getParameter("supprimer") != null && request.getParameter("id_utilisateur") != null) {
			int id_utilisateur = -1;
			try {
				id_utilisateur = Integer.parseInt((String) request.getParameter("id_utilisateur"));
			} catch (NumberFormatException e) {
				errors.addErreur("Nombre ma formaté.");
			}
			try {
				modifUtilisateur = utilisateursManager.getUtilisateurById(id_utilisateur, errors);
				utilisateursManager.supprimerUtilisateur(modifUtilisateur);
			} catch (BLLException e) {
				errors.addErreur(e.getLocalizedMessage());
			}
		}
		
		request.setAttribute("delete_utilisateur", deleteUtilisateur);
		
		
		List<Utilisateur> allUsers = null;
		
		try {
			allUsers = utilisateursManager.getAllUtilisateur();
		} catch (BLLException e) {
			errors.addErreur(e.getLocalizedMessage());
		}

		request.setAttribute("all_users", allUsers);

		request.setAttribute("erreurs", errors);
		
		request.getRequestDispatcher("/WEB-INF/jsps/cruds/CRUDUtilisateurs.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
