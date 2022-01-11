package fr.eni.encheres.servlets;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Article;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VentesEnCoursServlet", value = "/VentesEnCours")
public class VentesEnCoursServlet extends HttpServlet {
	static ArticleManager articleManager = ArticleManager.GetInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		List<Article> articles= new ArrayList<>();

		articleManager.getCatalogueTotal(articles, erreurs);

		request.setAttribute("articles",articles);

		request.getRequestDispatcher("/WEB-INF/jsps/vente/ListerVentes.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
