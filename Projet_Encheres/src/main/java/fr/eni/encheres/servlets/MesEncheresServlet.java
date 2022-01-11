package fr.eni.encheres.servlets;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "MesEncheresServlet", value = "/MesEncheres")
public class MesEncheresServlet extends HttpServlet {
    static EncheresManager encheresManager = EncheresManager.GetInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Erreurs erreurs = (Erreurs) request.getAttribute("errors");
        Utilisateur utilisateur = new Utilisateur();
        Integer idUtilisateur = (Integer) request.getSession().getAttribute("user_id");
        if(idUtilisateur != null) {
            utilisateur = UtilisateursManager.GetInstance().getUtilisateurById(idUtilisateur, erreurs);
        }
        List<Enchere> mesEncheres = encheresManager.getEncheresByUtilisateur(utilisateur, erreurs);

		List<Article> articles = new ArrayList<>();
		ArticleManager.GetInstance().getCatalogueTotal(articles, erreurs);

		List<Article> ventes = ArticleManager.GetInstance().getCatalogue(erreurs);

        request.setAttribute("encheres_gagnees", mesEncheres.stream().filter(enchere -> enchere.estFinie()).collect(Collectors.toList()));
        request.setAttribute("encheres_en_cours", mesEncheres.stream().filter(enchere -> !enchere.estFinie()).collect(Collectors.toList()));
        request.setAttribute("ventes", ventes);
		Utilisateur finalUtilisateur = utilisateur;
		request.setAttribute("mes_ventes", ventes.stream().filter(
				vente -> (vente.getUtilisateur().getNoUtilisateur() == finalUtilisateur.getNoUtilisateur())).collect(Collectors.toList()));

		request.setAttribute("mes_ventes_non_debutees", articles.stream().filter(vente -> !vente.aDebute()).collect(Collectors.toList()));

        request.getRequestDispatcher("/WEB-INF/jsps/MesEncheres.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
	}
}
