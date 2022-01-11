package fr.eni.encheres.servlets;

/**
 * 
 * @author Sego
 *
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.RetraitsManager;
import fr.eni.encheres.bo.Retrait;

/**
 * Servlet implementation class ServletRetraits
 */
@WebServlet("/ServletRetraits")
public class ServletRetraits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRetraits() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String noArticle = request.getParameter("noArticle");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		RetraitsManager retraitsManager = null;

		try {
			retraitsManager = RetraitsManager.GetInstance();

			if (noArticle == null)
				System.out.println(
						"Aucun article validé. Veuillez valider l'article afin d'obtenir l'adresse de retrait");

			else

			if (ville == null) {
				Retrait adresseRetrait = new Retrait();
				retraitsManager.ajouterAdresse(adresseRetrait);
				request.setAttribute("adresse", adresseRetrait);
			}

			else {
				List<Retrait> adresseRetrait = retraitsManager.getRetraits();
				request.setAttribute("adresse", adresseRetrait);
			}

		} catch (BLLException ex) {
			ex.printStackTrace();
		} catch (BusinessException ex) {
			ex.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsps/vente/RetraitColis.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String rue, codePostal, ville;
		try {
			RetraitsManager retraitsManager = RetraitsManager.GetInstance();
			rue = request.getParameter("rue");
			codePostal = request.getParameter("codePostal");
			ville = request.getParameter("ville");
			Retrait adresseRetrait = new Retrait(rue, codePostal, ville);
			retraitsManager.ajouterAdresse(adresseRetrait);
			request.setAttribute("adresse", adresseRetrait);
		} catch (BLLException ex) {
			ex.printStackTrace();
		} catch (BusinessException ex) {
			ex.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsps/vente/RetraitColis.jsp");
		rd.forward(request, response);
	}

}
