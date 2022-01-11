package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.Utilitaires;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.CategoriesManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.beans.Erreurs;

@WebServlet(name = "AccueilServlet", value = "/accueil")
public class AccueilServlet extends HttpServlet {
	static CategoriesManager categoriesManager = CategoriesManager.GetInstance();
	static ArticleManager articleManager = ArticleManager.GetInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");

		// On récupère la liste des articles avant de les trier par date de début
		List<Article> listeArticleTotal = new ArrayList<>();
		articleManager.getCatalogueTotal(listeArticleTotal, erreurs);

		List<Article> articles = articleManager.getCatalogue(erreurs);

		String status = request.getParameter("statut");
		if (status != null) {
			if (status.contentEquals("non_commence")) {
				articles.clear();
				for (Article art : listeArticleTotal) {
					if (!art.aDebute()) {
						articles.add(art);
					}
				}
			}
			if (status.contentEquals("terminees")) {
				articles.clear();
				for (Article art : listeArticleTotal) {
					if (art.estFinie()) {
						articles.add(art);
					}
				}
			}
			if (status.contentEquals("miennes")) {
				Integer userId = (Integer) request.getSession().getAttribute("user_id");
				if (userId != null) {
					articles.clear();
					for (Article art : listeArticleTotal) {
						if (art.getUtilisateur().getNoUtilisateur() == userId) {
							articles.add(art);
						}
					}
				}
			}
		}

		List<Categorie> allCats = CategoriesManager.GetInstance().getAllCategories(erreurs);
		String categorie = request.getParameter("categorie");
		if (categorie != null) {
			for (Categorie cat : allCats) {
				if (cat.getEtiquette().contentEquals("toutes"))
					continue;
				if (cat.getEtiquette().contentEquals(categorie)) {
					List<Article> newArticles = new ArrayList<>();
					for (Article art : articles) {
						if (art.getCategorie().getEtiquette().contentEquals(cat.getEtiquette())) {
							newArticles.add(art);
						}
					}
					articles = newArticles;
					break;
				}
			}
		}

		String dateFiltre = request.getParameter("dateFiltre");
		if (dateFiltre != null) {
			LocalDate dateFiltreLocal = Utilitaires.fromHTMLDateAndTime(dateFiltre, "00:00:00").toLocalDate();
			articles.clear();
			for (Article art : listeArticleTotal) {
				if (dateFiltreLocal.isEqual(art.getDateDebutEnchere())) {
					articles.add(art);
				}
			}
		}

		String contient = request.getParameter("contient");
		if(contient != null) {
			contient = contient.toLowerCase(Locale.ROOT).trim();
			List<Article> newArticles = new ArrayList<>();
			for (Article art : articles) {
				if (
						art.getNomArticle().toLowerCase(Locale.ROOT).contains(contient)
								|| art.getDescription().toLowerCase(Locale.ROOT).contains(contient)) {
					newArticles.add(art);
				}
			}
			articles = newArticles;

		}

		// On trie en fonction de la date de début (cf BO article)
		Collections.sort(articles);

		// on inverse si on a demandé
		String tri = request.getParameter("trier");
		if (tri != null && tri.contentEquals("bientot_terminees")) {
			Collections.reverse(articles);
		}

		try {
			EncheresManager encheresManager = EncheresManager.GetInstance();
			List<Enchere> listeEncheres = null; // problème avec le try catch

			listeEncheres = encheresManager.getAllEncheres();

		} catch (BLLException ex) {
			ex.printStackTrace();
			request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
		}

		request.setAttribute("categories", categoriesManager.getAllCategories(erreurs));
		request.setAttribute("articles", articles);

		request.getRequestDispatcher("/WEB-INF/jsps/accueil.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
