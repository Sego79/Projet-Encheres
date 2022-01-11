package fr.eni.encheres.servlets.utilisateurs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeconnexionServlet", value = "/Deconnexion")
public class DeconnexionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("user_id");
		request.getSession().removeAttribute("user_pseudo");
		response.sendRedirect(request.getHeader("Referer"));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
