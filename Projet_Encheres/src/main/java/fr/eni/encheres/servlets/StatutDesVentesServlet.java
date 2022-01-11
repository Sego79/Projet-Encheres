package fr.eni.encheres.servlets;

/**
 * 
 * @author Sego
 *
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StatutDesVentesServlet
 */
@WebServlet("/StatutDesVentesServlet")
public class StatutDesVentesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatutDesVentesServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("choix") != null) {
			processRequest(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsps/StatutEncheres.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
		doGet(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int choixUtilisateur = Integer.parseInt(request.getParameter("choix"));
		String resultat;
		// Selon le choix de l'utilisateur on choisit soit les enchères non commencées ,
		// soit en cours etc.
		request.setAttribute("choix", choixUtilisateur);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsps/StatutEncheres.jsp");
		if (rd != null) {
			rd.forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
