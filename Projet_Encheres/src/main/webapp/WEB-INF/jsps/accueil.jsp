<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- @Author Sego  -->

<%@page import="fr.eni.encheres.bo.Enchere"%>
<%--  <%@page import="fr.eni.encheres.messages.LecteurMessage"%>--%>

<%@ page import="java.util.List"%>
<%@ page import="fr.eni.encheres.bo.Categorie" %>
<%@ page import="fr.eni.encheres.bo.Article" %>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="EnteteFinal.jspf" %>

	<jsp:useBean id="infos" type="fr.eni.encheres.bo.beans.Infos" scope="request" />

		<c:if test="${infos.hasInfos()}">

			<div class="alert alert-success" id="infos">
				<c:choose>
					<c:when test="${infos.liste.size() > 1}">
						<ul>
							<c:forEach var="info" items="${infos.liste}">
								<li>${info}</li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						${infos.liste.get(0)}
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>

<br>
<section> 
	<article class="Titre_Choix" id="Titre_Choix">
		
		<br>
		<h1>ENCHERES</h1>

		<h2>Site d'enchères de seconde main</h2>

		<div class="listes_deroulantes" id="listes_deroulantes">

			<!-- Création liste déroulante permettant d'acceder aux enchères selon leur statut -->
			<table>
				<tr>
					</td>
					<form method="get"
						  action="<%=request.getContextPath()%>/AfficherEncheresServlet">
						<th><label for="statut de l'enchère"> Statut des
							enchères : </label> <select name="statut" size="1">
							<option value="non_commence">Non commencées</option>
							<option value="en_cours">En cours</option>
							<option value="terminees">Terminée</option>
							<option value="miennes">Mes ventes</option>
						</select></th>
						<td>

							<input type="submit" value="Ok" />
					</form>
				</tr>


				<!-- Création liste déroulante permettant d'acceder aux enchères selon la categorie -->
				<tr>
					</td>
					<form method="get"
						  action="<%=request.getContextPath()%>/AfficherEncheresServlet">
						<th><label for="cat_enchere"> Catégories :</label>
							<select id="cat_enchere"
									name="categorie" size="1">
								<%
									for(Categorie cat: (List<Categorie>)request.getAttribute("categories")) {
								%>
								<option value="<%= cat.getEtiquette() %>"><%= cat.getLibelle() %></option>
								<% } %>
							</select></th>
						<td>

							<input type="submit" value="Ok" />
					</form>
				</tr>
				
				
				<!-- Création liste déroulante permettant d'acceder aux enchères selon date -->
				<tr>
					<form method="get"
						  action="<%=request.getContextPath()%>/AfficherEncheresServlet">
						<th><label for="Trier par date"> Trier par date :</label>
							<select
									name="trier" size="1">
								<option value="nouvelles">Nouvelles enchères</option>
								<option value="bientot_terminees">Enchères bientôt terminées</option>


							</select>
						</th>
						<td>

							<input type="submit" value="Ok" />
					</form>
				</tr>

				
				
				<!-- Là je crée un bouton permettant d'acceder aux enchères selon leur date -->
				<tr>
					<td>
						<%
						String dateFiltre = "";
						if (request.getParameter("dateFiltre") != null) {
							dateFiltre = request.getParameter("dateFiltre");
						}
						%>

				
					<form
						action="<%=request.getContextPath()%>/AfficherEncheresServlet"
						method="get">
					<input type="date" name="dateFiltre"
						value="<%=dateFiltre%>" /> <input type="submit" value="Filtrer" />
						<a href="<%=request.getContextPath()%>/AfficherEncheresServlet"><input
							type="reset" value="Réinitialiser" /></a>
						</form></td>
				<tr>

				<tr>
					<form method="get"
						  action="<%=request.getContextPath()%>/AfficherEncheresServlet">
						<th><label for="Trier par date"> Qui contient :</label>
							<input type="search"
								   name="contient" >
						</th>
						<td>

							<input type="submit" value="Ok" />
					</form>

					</td>
				</tr>

			</table>
		</div>
</article>
<br>
</section>
	<!-- faire soit sous forme de liste / soit avec image / pouvoir classer par dates -->
	<hr>

	<jsp:include page="blocs/erreurs.jsp">
		<jsp:param name="errors" value="${errors}"/>
	</jsp:include>

	<jsp:include page="blocs/infos.jsp">
		<jsp:param name="infos" value="${infos}"/>
	</jsp:include>



	<!-- Ici je vais juste afficher toutes les enchères en commençant par les plus récentes-->
	<article class="articles container" id="articles">
		<div class="table_articles" id="table_articles" style="display: flex; flex-direction: row; flex-wrap: wrap; margin: auto;">
			<% for(Article article: (List<Article>)request.getAttribute("articles")) { %>
			<div style="text-align: center">
				<img
					src="<%=request.getContextPath()%><%=article.getUrlImage()%>"
					alt="image de l'article" />
				<h3><%=article.getNomArticle()%>
					<small>
						<a href="<%=request.getContextPath()%>/DetailArticle?no_article=<%=article.getNoArticle()%>">(Voir l'article)</a>
					</small></h3>
			</div>
			<% } %>
		</div>

<!-- BOOTSTRAP -->



    <!--div class="container"> Je met en commentaire en attendant
      <div class="row justify-content-around" class="position_container" >


        <div class="col-sm-3 justify-content-center" style="border:1px solid black" >
           <div class="row" ><img  src="<%=request.getContextPath()%>/images/fauteuil.jpg"
							alt="logo sport" class="image" /></div>
           <div class="row"><p class="text-center">enchere1</p>  </div>
         <div class="row"><p class="text-center">date fin</p>  </div>
        </div>

        <div class="col-sm-3" style="border:1px solid black">
            <div class="row"><img class="image" src="<%=request.getContextPath()%>/images/fauteuil.jpg"
							alt="logo fauteuil" /></div>
            <div class="row"><p class="text-center">enchere2</p>  </div>
            <div class="row"><p class="text-center">date fin</p>  </div>
         </div>

         <div class="col-sm-3" style="border:1px solid black">
            <div class="row"> <img class="image" src="<%=request.getContextPath()%>/images/fauteuil.jpg"
							alt="logo informatique" /> </div>
            <div class="row"><p class="text-center">enchere3</p>  </div>
            <div class="row"><p class="text-center">date fin</p>  </div>
        </div>

        <div class="col-sm-3" style="border:1px solid black">
            <div class="row"> <img src="<%=request.getContextPath()%>/images/fauteuil.jpg"
							alt="logo informatique" /></div>
            <div class="row"><p class="text-center">enchere4</p>  </div>
            <div class="row"><p class="text-center">date fin</p>  </div>
         </div>
      </div>


      <div class="row justify-content-around">
        <div class="col-sm-3" style="border:1px solid black">
            <div class="row"> <img src="<%=request.getContextPath()%>/images/fauteuil.jpg"
                    alt="logo Association" /> </div>
            <div class="row"><p>enchere5</p>  </div>
            <div class="row"><p>date fin</p>  </div>
        </div>

        <div class="col-sm-3" style="border:1px solid black">
            <div class="row"> <img src="<%=request.getContextPath()%>/images/fauteuil.jpg"
                    alt="logo Association" />  </div>
            <div class="row"><p>enchere6</p>  </div>
            <div class="row"><p>date fin</p>  </div>
         </div>

         <div class="col-sm-3" style="border:1px solid black">
            <div class="row">  <img src="<%=request.getContextPath()%>/images/fauteuil.jpg"
                    alt="logo Association" /></div>
            <div class="row"><p>enchere7</p>  </div>
            <div class="row"><p>date fin</p>  </div>
         </div>

         <div class="col-sm-3" style="border:1px solid black">
            <div class="row"> <img src="<%=request.getContextPath()%>/images/fauteuil.jpg"
                    alt="logo Association" />     </div>
            <div class="row"><p>enchere8</p>  </div>
            <div class="row"><p>date fin</p>  </div>
         </div>
      </div-->



    </div>
  </article>





	<hr>




<%@ include file="Pied.html" %>