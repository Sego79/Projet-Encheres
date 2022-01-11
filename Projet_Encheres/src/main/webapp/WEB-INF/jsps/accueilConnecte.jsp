<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- @Author Sego  -->

<%@page import="fr.eni.encheres.bo.Enchere"%>
<%--  <%@page import="fr.eni.encheres.messages.LecteurMessage"%>--%>

<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Liste des enchères</title>
</head>
<body>
	<header>
		<div id="header-logo">
			<!-- logo -->
			<img src="/images/logoProjet.png" alt="logo Association" />
		</div>
	
	
	<div id="header-user">
            <!-- information utilisateur TODO: donner la possibilité de charger une photo-->
            <div id="header-user-avatar">
                <!-- photo utilisateur -->
                <img src="/images/150/1.jpg" width="75px" alt="Photo utilisateur" />
   </div>
		
		 <div id="header-user">
  <!-- todo le lien avec la bonne servlet de mon compte -->                 
		<!-- Création / connexion à un compte -->
		<a href="<%=request.getContextPath()%>/Login"><input
			type="button" value="Déconnexion" /></a> | <a
			href="<%=request.getContextPath()%>/CRUDUtilisateurs"><input
			type="button" value="Mon compte" /></a>
    </div>
		
<br>
		<h1>ENCHERES</h1>

		<h2>Site d'enchères de seconde main</h2>

		<div class="contenu">

			<!-- Création liste déroulante permettant d'acceder aux enchères selon leur statut -->
			<label for="statut de l'enchère"> Choix du statut des
				enchères : </label> <select name="statut" size="1">
				<option value="en cours">Non commencées</option>
				<option value="en cours">En cours</option>
				<option value="terminée">Terminée</option>
				<option value="annulée">Mes ventes</option>
			</select>
			<form method="link"
				action="<%=request.getContextPath()%>/AfficherEncheresServlet">
				<input type="submit" value="Ok" />
			</form>

			<!-- Création liste déroulante permettant d'acceder aux enchères selon la categorie -->
			<label for="statut de l'enchère"> Catégories :</label> <select
				name="statut" size="1">
				<option value="en cours">Ameublement</option>
				<option value="annulée">Arts de la Table</option>
				<option value="annulée">Bricolage</option>
				<option value="annulée">Décoration</option>
				<option value="terminée">Electroménager</option>
				<option value="annulée">Equipement bébé</option>
				<option value="annulée">Jardinage</option>
				<option value="annulée">Jeux / Jouets</option>
				<option value="annulée">Mode</option>
				<option value="annulée">Sports / Hobbies</option>
				<option value="annulée">Tissus / linge</option>
				<option value="annulée">Divers</option>
			</select>
			<form method="link"
				action="<%=request.getContextPath()%>/AfficherCategoriesServlet">
				<input type="submit" value="Ok" />
			</form>

			<!-- Là je crée un bouton permettant d'acceder aux enchères selon leur date -->
			<%
			String dateFiltre = "";
			if (request.getParameter("dateFiltre") != null) {
				dateFiltre = request.getParameter("dateFiltre");
			}
			%>


			<form action="<%=request.getContextPath()%>/AfficherEncheresServlet"
				method="post">
				<input type="date" name="dateFiltre" value="<%=dateFiltre%>" /> <input
					type="submit" value="Filtrer" /> <a
					href="<%=request.getContextPath()%>/AfficherEncheresServlet"><input
					type="button" value="Réinitialiser" /></a>
			</form>
		</div>


	</header>
	<!-- faire soit sous forme de liste / soit avec image / pouvoir classer par dates -->
	<hr>


	<!-- Ici je vais juste afficher toutes les enchères en commençant par les plus récentes-->
	<article>
		<table>
			<tr>
				<td style="text-align: center"><img src="/images/150/1.jpg"
					alt="" />
					<h3>enchere1</h3></td>
				<td style="text-align: center">
					<h3>enchere2</h3>
				</td>
				<td style="text-align: center">
					<h3>enchere3</h3>
				</td>
			</tr>
			<tr>
				<td style="text-align: center">
					<h3>enchere4</h3>
				</td>
				<td style="text-align: center">
					<h3>enchere5</h3>
				</td>
				<td style="text-align: center">
					<h3>enchere6</h3>
				</td>
			</tr>
		</table>




	</article>
	<hr>





	<footer>


		<a href="<%=request.getContextPath()%>"><input type="button"
			value="Retour à l'accueil" /></a>

		<div id="footer-author">
			<!-- auteur -->
			<a href="mailto:troc.encheres@gmail.com"
				title="Si vous désirez nous écrire">Les objets sont nos amis</a>
		</div>
	</footer>

</body>
</html>