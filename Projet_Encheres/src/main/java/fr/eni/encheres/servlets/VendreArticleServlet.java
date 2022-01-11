package fr.eni.encheres.servlets;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.beans.Infos;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategoriesManager;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "VendreArticleServlet", value = "/Vendre")
public class VendreArticleServlet extends HttpServlet {

	static ArticleManager articleManager = ArticleManager.GetInstance();
	static CategoriesManager categoriesManager = CategoriesManager.GetInstance();
	static UtilisateursManager utilisateursManager = UtilisateursManager.GetInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		Infos infos = (Infos) request.getAttribute("infos");
		Article article = new Article();
		Utilisateur utilisateur=new Utilisateur();
		utilisateur = utilisateursManager.getUtilisateurById(
				(Integer) request.getSession().getAttribute("user_id"), erreurs);

		if(request.getParameter("vendre") != null) {
			String nom = request.getParameter("nom");
			String description = request.getParameter("description");
			String prix = request.getParameter("prix");
			String dateDebut = request.getParameter("date_debut");
			String dateFin = request.getParameter("date_fin");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("code_postal");
			String ville = request.getParameter("ville");
			Categorie categorie = categoriesManager.getByEtiquette(request.getParameter("category"), erreurs);


			articleManager.sauvegarderDepuisLeWeb(
					nom, description, prix, dateDebut, dateFin, rue,
					codePostal, ville, categorie, utilisateur, article, erreurs
			);

			if(!erreurs.hasErrors()) {
				infos.addInfo("Nouvel article ajouté !");
			}
		}

		request.setAttribute("article", article);
		request.setAttribute("categories", categoriesManager.getAllCategories(erreurs) );
		request.setAttribute("utilisateur", utilisateur);

        request.getRequestDispatcher("/WEB-INF/jsps/VendreArticle.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
	}
}
