<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Statut des encheres</title>
</head>

<!--  cette jsp doit faire le lien avec des servlets menant au statut des enchères (en cours etc.) -->
<!--   JSP A FAIRE!!!-->
<!-- lié avec la servlet "Ventes en coursServlet"-->
<body>
	<label for="statut de l'enchère"> Statut des enchères : </label>
	<select name="statut" size="1">
		<option value="en cours">Non commencées</option>
		<option value="en cours">En cours</option>
		<option value="terminée">Terminée</option>
		<option value="annulée">Mes ventes</option>
	</select>


	<form method="link"
		action="<%=request.getContextPath()%>/StatutDesVentesServlet">
		<input type="submit" value="Ok" />
	</form>


</body>
</html>