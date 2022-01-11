<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="fr.eni.encheres.bo.Retrait"%>
	
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<%@ include file="../EnteteBootstrap.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lieu de retrait de l'article</title>

<style>
	form {
		display: grid;
		grid-gap: 1em;
		justify-items: end;
		justify-content: space-between;
	}

	button, input:not([type="radio"]):not([type="checkbox"]), select, textarea {
		border: 1px solid black;
		border-radius: 5px;	
	}
	
	label {
		margin-left: 1ex;	
	}
</style>
</head>
<body>
	<h1>Lieu de retrait de l'article</h1>
	
	

<p>Cette transaction concerne l'article numéro : ${requestScope['adresse'].noArticle}</p>

<h1>Adresse de retrait:</h1>

	
	<% request.getAttribute("adresse");
	 %> 
	
	
	<label>Rue : <input type="text" name="rue" value="${adresse.rue}" /></label>
	<label>Code postal : <input type="text" name="codePostal" value="${adresse.codePostal}" /></label>
	<label>Ville : <input type="text" name="ville" value="${adresse.ville}" /></label>


  
<table>
	<tr>
		<td>${adresse.noArticle}</td>
		<td>${adresse.rue} </td>
		<td>${adresse.codePostal}</td>
		<td>${adresse.ville}</td>		
	
		<td><form method="post">
		<input type="hidden" name="id_article"
		 value= ${requestScope['adresse'].noArticle} />
		<input type="submit" name="modifier" value="Modifier" /> 
		<input type="submit" name="supprimer" value="Supprimer" />
		</form></td>	
	</tr>
</table>

</body>
<%@ include file="../PiedBootstrap.jspf" %>
</html>