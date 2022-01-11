package fr.eni.encheres.servlets.utilisateurs;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bll.CategoriesManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CRUDCategories", value = "/CRUDCategories")
public class CRUDCategories extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		request.setAttribute("cats", CategoriesManager.GetInstance().getAllCategories(erreurs));
		request.setAttribute("change", CategoriesManager.GetInstance().getCategorieById(
				request.getParameter("id_cat"), erreurs));
		request.getRequestDispatcher("/WEB-INF/jsps/cruds/CRUDCategories.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
