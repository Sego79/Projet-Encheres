<%@page import="fr.eni.encheres.bo.beans.Erreurs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../Entete.html" %>
<%@ page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page import="java.util.List" %>


<main class="crud">
<h1>CRUD Pour les utilisateurs</h1>


<% if(request.getAttribute("nouvel_utilisateur") != null) { %>
	<div class="succes">
		L'utilissateur <%= ((Utilisateur)request.getAttribute("nouvel_utilisateur")).getPseudo() %> 
		d'id <%= ((Utilisateur)request.getAttribute("nouvel_utilisateur")).getNoUtilisateur() %> a été ajouté !
	</div>

<% } %>

<% if(request.getAttribute("modif_utilisateur") != null && request.getParameter("ajouter") != null) { %>
	<div class="succes">
		L'utilissateur <%= ((Utilisateur)request.getAttribute("modif_utilisateur")).getPseudo() %> 
		d'id <%= ((Utilisateur)request.getAttribute("modif_utilisateur")).getNoUtilisateur() %> a été modifié !
	</div>

<% } %>

<% if(request.getAttribute("delete_utilisateur") != null) { %>
	<div class="succes">
		L'utilissateur <%= ((Utilisateur)request.getAttribute("delete_utilisateur")).getPseudo() %> 
		d'id <%= ((Utilisateur)request.getAttribute("delete_utilisateur")).getNoUtilisateur() %> a été supprimé !
	</div>

<% } %>

<% if(request.getAttribute("erreurs") != null && ((Erreurs)request.getAttribute("erreurs")).hasErrors()) { %>
	<div class="erreur">
		Des ereurs sont survenues !
		<ol>
		<% for(String erreur: (List<String>)request.getAttribute("erreurs"))  { %>
			<li><%= erreur %></li>
		<% } %>
		</ol>
	</div>

<% } %>

<%
	boolean passwordReadOnly = false;
	String titre = "Ajouter un utilisateur";

	String pseudo = "";
	String nom = "";
	String prenom = "";
	String email = "";
	String telephone = "";
	String rue = "";
	String codePostal = "";
	String ville = "";
	String credit = "";
	Boolean administrateur = false;
	String id_utilisateur = "";
	Boolean actif = false;
	if(request.getAttribute("modif_utilisateur") != null) {

		passwordReadOnly = true;
		

		Utilisateur utilisateur = (Utilisateur)request.getAttribute("modif_utilisateur");
		
		titre = "Modifier l'utilisateur numero " + utilisateur.getNoUtilisateur();
		titre += " - <a href='" + request.getContextPath() + "/CRUDUtilisateurs'>Revenir à l'ajout</a>";
		
		id_utilisateur = utilisateur.getNoUtilisateur().toString();
		pseudo = utilisateur.getPseudo();
		nom = utilisateur.getNom();
		prenom = utilisateur.getPrenom();
		email = utilisateur.getEmail();
		telephone = utilisateur.getTelephone();
		rue = utilisateur.getRue();
		codePostal = utilisateur.getCodePostal();
		ville = utilisateur.getVille();
		credit = utilisateur.getCredit().toString();
		administrateur = utilisateur.isAdministrateur();
		actif = utilisateur.isActif();
	}

%>
<h2><%= titre %> </h2>

<form method="post" class="form">
<%
if(request.getAttribute("modif_utilisateur") != null) {
%>
	<input type="hidden" name="id_utilisateur" value="<%= id_utilisateur %>" />
<%
}
%>
	<label>Pseudo : <input type="text" name="pseudo" value="<%= pseudo  %>"/></label>
	<label>Mot de passe : <input type="text" name="mot_de_passe" <%= passwordReadOnly ? "disabled" : ""  %>/></label>
	<label>Nom : <input type="text" name="nom" value="<%= nom  %>" /></label>
	<label>Prenom : <input type="text" name="prenom" value="<%= prenom  %>" /></label>
	<label>Email : <input type="text" name="email" value="<%= email  %>" /></label>
	<label>Telephone : <input type="text" name="telephone" value="<%= telephone  %>" /></label>
	<label>Rue : <input type="text" name="rue" value="<%= rue  %>" /></label>
	<label>Code postal : <input type="text" name="codePostal" value="<%= codePostal  %>" /></label>
	<label>Ville : <input type="text" name="ville" value="<%= ville  %>" /></label>
	<label>Credit : <input type="text" step="1" name="credit" value="<%= credit  %>"/></label>
	<label>Est administrateur ? <input type="checkbox" name="administrateur" <%= administrateur ? "checked" : ""  %> /></label>
	<label>Est actif ? <input type="checkbox" name="actif" <%= actif ? "checked" : ""  %> /></label>

	<input type="submit" name="ajouter" />

</form>


<h2>Liste des utilisateurs</h2>

<table class="listing">
<thead>
<tr>
	<th>Numéro</th><th>Pseudo</th><th>Prenom</th><th>Nom</th><th>Email</th><th>Telephone</th><th>Rue</th><th>Code postal</th>
	<th>Ville</th><th>Crédit</th><th>Administrateur</th>
</tr>
</thead>
	<%
	List<Utilisateur> allUsers = (List<Utilisateur>)request.getAttribute("all_users");
	for(Utilisateur user: allUsers) {
	%>
	<tr>
		<td><%= user.getNoUtilisateur() %></td>
		<td><%= user.getPseudo() %></td>
		<td><%= user.getPrenom() %></td>
		<td><%= user.getNom() %></td>
		<td><%= user.getEmail() %></td>
		<td><%= user.getTelephone() %></td>
		<td><%= user.getRue() %></td>
		<td><%= user.getCodePostal() %></td>
		<td><%= user.getVille() %></td>
		<td><%= user.getCredit() %></td>
		<td><%= user.isAdministrateur() %></td>
		<td>
		<form method="post" >
			<input type="hidden" name="id_utilisateur" value="<%= user.getNoUtilisateur() %>" />
			<input type="submit" name="modifier" value="Modifier" />
			<input type="submit" name="supprimer" value="Supprimer" />
		</form>
		</td>
	</tr>
	<% } %>
	
</table>
</main>

<%@include file="../Pied.html" %>