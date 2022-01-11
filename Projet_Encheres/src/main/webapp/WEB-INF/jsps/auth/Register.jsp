<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="utilisateur_temp" scope="request" type="fr.eni.encheres.bo.Utilisateur"/>
<jsp:useBean id="errors" scope="request" type="fr.eni.encheres.bo.beans.Erreurs"/>


<%@ include file="../EnteteBootstrap.jspf"%>

<h1>Bienvenue sur trocenchères</h1>

<form method="post">

	<c:if test="${errors.hasErrors()}">
		<div class="alert alert-danger">
			<p>Certaines données du formulaire sont incorrectes !</p>
			<ol>
				<c:forEach var="erreur" items="${errors.liste}">
					<li>${erreur}</li>
				</c:forEach>

			</ol>
		</div>
	</c:if>


	<div class="row">
	<div class="mb-3 col">
		<label for="exampleInputPseudo" class="form-label">Pseudo</label>
		<input
			type="text" class="form-control" id="exampleInputPseudo"
			aria-describedby="emailHelp" required maxlength="30" name="pseudo" value="${utilisateur_temp.pseudo}">
		<div id="emailHelp" class="form-text">Comment vous apparaitrez
			sur le site.</div>
	</div>

	<div class="mb-3 col">
		<label for="exampleInputName" class="form-label">Nom de
			famille</label>
		<input type="text" class="form-control" id="exampleInputName"
			aria-describedby="nomHelp" required maxlength="50" name="nom" value="${utilisateur_temp.nom}">
	</div>
	</div>

	<div class="row">
	<div class="mb-3 col">
		<label for="exampleInputFirstname" class="form-label">Prénom</label>
		<input
			type="text" class="form-control" id="exampleInputFirstname"
			aria-describedby="prenomHelp" required maxlength="50" name="prenom" value="${utilisateur_temp.prenom}">
	</div>

	<div class="mb-3 col">
		<label for="exampleInputMail" class="form-label">Email</label>
		<input
			type="email" class="form-control" id="exampleInputMail"
			aria-describedby="prenomHelp" required maxlength="50" name="email" value="${utilisateur_temp.email}">
	</div>
	</div>

	<div class="row">
	<div class="mb-3 col">
		<label for="exampleInputPhone" class="form-label">Telephone</label>
		<input
			type="tel" class="form-control" id="exampleInputPhone"
			aria-describedby="telephoneHelp" required maxlength="15" name="telephone" value="${utilisateur_temp.telephone}">
	</div>

	<div class="mb-3 col">
		<label for="exampleInputRue" class="form-label">Rue</label>
		<input
			type="text" class="form-control" id="exampleInputRue"
			aria-describedby="rueHelp" required maxlength="250" name="rue" value="${utilisateur_temp.rue}">
		<div id="rueHelp" class="form-text">Votre numéro et nom de rue.</div>
	</div>
	</div>

	<div class="row">
	<div class="mb-3 col">
		<label for="exampleInputCP" class="form-label">Code postal</label>
		<input
			type="text" class="form-control" id="exampleInputCP"
			aria-describedby="cpHelp" required maxlength="10"
			name="codePostal" value="${utilisateur_temp.codePostal}">
	</div>

	<div class="mb-3 col">
		<label for="exampleInputVille" class="form-label">Ville</label>
		<input
			type="text" class="form-control" id="exampleInputVille"
			aria-describedby="villeHelp" required maxlength="50" name="ville" value="${utilisateur_temp.ville}">
	</div>
	</div>

	<div class="row">
	<div class="mb-3 col">
		<label for="exampleInputPassword" class="form-label">Mot de
			passe</label>
		<input type="password" class="form-control"
			id="exampleInputPassword" aria-describedby="passwordHelp" required maxlength="30"
			name="mot_de_passe" >
		<div id="passwordHelp" class="form-text">6 lettres minimum.</div>
	</div>

	<div class="mb-3 col">
		<label for="exampleInputPasswordRep" class="form-label">Répétez
			le mot de passe</label>
		<input type="password" class="form-control"
			id="exampleInputPasswordRep" aria-describedby="passwordHelpRep"
			required maxlength="30" name="mot_de_passe_repete" >
	</div>
	</div>


	<div class="mb-3 form-check">
		<input type="checkbox" class="form-check-input" id="exampleCheck1" name="souvenir">
		<label class="form-check-label" for="exampleCheck1">Se
			souvenir de moi ?</label>
	</div>
	<button type="submit" class="btn btn-primary" name="inscription">S'inscrire</button>
</form>

<%@ include file="../PiedBootstrap.jspf"%>