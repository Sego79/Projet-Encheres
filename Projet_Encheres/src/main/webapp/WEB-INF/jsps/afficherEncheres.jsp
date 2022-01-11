<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- @Author Sego  -->  

<%@page import="fr.eni.encheres.bo.Enchere"%>
<%--  <%@page import="fr.eni.encheres.messages.LecteurMessage"%>--%>

    <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Liste des enchères</title>
</head>
<body>
<header> 
 <div id="header-logo">
            <!-- logo -->
            <img src="/images/logoProjet.png" alt="logo Association" />
        </div>
        
<h1>ENCHERES</h1>

<h2>Site d'enchères de seconde main</h2>


<!-- Ici je vais juste afficher toutes les enchères -->

<div class="contenu">
	
<!-- création d'un filtre... pour le moment par date... voir pour faire autre type de filtre -->	
		<%
		String dateFiltre="";
		if(request.getParameter("dateFiltre")!=null)
		{
			dateFiltre=request.getParameter("dateFiltre");
		}
		%>
		
			
		<form action="<%=request.getContextPath()%>/AfficherEncheresServlet" method="post">
			<input type="date" name="dateFiltre" value="<%=dateFiltre%>"/>
			<input type="submit" value="Filtrer"/>
			<a href="<%=request.getContextPath()%>/AfficherEncheresServlet"><input type="button" value="Réinitialiser"/></a>
		</form>
</div>


</header>	
	<!-- faire soit sous forme de liste / soit avec image / pouvoir classer par dates -->
	<hr>
<article>
<table>
<tr>
<td style="text-align:center">enchere1</td>
<td style="text-align:center">enchere2</td>
<td style="text-align:center">enchere3</td>
</tr>
<td style="text-align:center">enchere4</td>
<td style="text-align:center">enchere5</td>
<td style="text-align:center">enchere6</td>

</table>



 
 </article>	
	<hr>
	
		
<!-- TP REPAS: renvoyait à une liste de message d'erreur dans la classe "lecteur message"
<%-- 		<%
			List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodesErreur");
			if(listeCodesErreur!=null)
			{
		%>
				<p style="color:red;">Erreur :</p>
		<%
				for(int codeErreur:listeCodesErreur)
				{
		%>
					<p><%=LecteurMessage.getMessageErreur(codeErreur)%></p>
		<%	
				}
			}
		%>
 
		<table align="center">
			<thead>
				<tr>
					<td>Date</td>
					<td>Heure</td>
					<td>Action</td>
				</tr>
			</thead>
				<%
					List<Enchere> listeEnchere = (List<Enchere>) request.getAttribute("listeEnchere");
					if(listeEnchere!=null && listeEnchere.size()>0)
					{
				%>
						<tbody>
							<%
							for(Enchere enchere : listeEnchere)
							{
							%>
								<tr>
									<td><%=enchere.getDateRepas()%></td>
									<td><%=enchere.getHeureRepas()%></td>
									
									<td><a href="<%=request.getContextPath()%>/repas?detail=<%=enchere.getId()%>&<%=dateFiltre%>">détail</a></td>
								</tr>
							<%
								if(String.valueOf(enchere.getId()).equals(request.getParameter("detail")))
								{
							%>
									<tr>
										<td colspan="3">
											<ul>
												<%
												for(Aliments aliment:repas.getListeAliments())
												{
												%>
													<li><%=aliment.getNom()%></li>
												<%
												}
												%>
											</ul>
										</td>
									</tr>
							<%
								}
							}
							%>
						</tbody>
				<%
					}
					else
					{
				%>
					<p>Il n'y a aucune enchères à afficher<P>
				<%
					}
				%>
	
	 --%>
			
		</table>
		
FIN DE LA LISTE MESSAGE D'ERREUR-->

<!-- Là je crée un bouton permettant d'acceder aux enchères selon leur statut -->
<footer> 
<label for="statut de l'enchère">
Choix du statut des enchères : 
</label>

<select name="statut" size="1">
<option value="en cours">En cours</option>
<option value="terminée">Terminée</option>
<option value="annulée">Annulée</option>
</select> 
<input type="submit" value="Ok"/>

<a href="<%=request.getContextPath()%>"><input type="button" value="Retour à l'accueil"/></a>
	
	 <div id="footer-author">
            <!-- auteur -->
            <a href="mailto:troc.encheres@gmail.com" title="Si vous désirez nous écrire">Les objets sont nos amis</a>
        </div>
</footer>

</body>
</html>