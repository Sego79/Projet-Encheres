<%--
  Created by IntelliJ IDEA.
  User: hurin
  Date: 13/12/2021
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="EnteteBootstrap.jspf" %>

<jsp:useBean id="infos" type="fr.eni.encheres.bo.beans.Infos" scope="request" />
<jsp:useBean id="errors" type="fr.eni.encheres.bo.beans.Erreurs" scope="request" />
<jsp:useBean id="utilisateur" type="fr.eni.encheres.bo.Utilisateur" scope="request" />

<c:if test="${infos.hasInfos()}">

    <div class="alert alert-success">
        <c:choose>
            <c:when test="${infos.liste.size() > 1}">
                <ul>
                    <c:forEach var="info" items="${infos.liste}">
                        <li>
                                ${info}
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                ${infos.liste.get(0)}
            </c:otherwise>
        </c:choose>
    </div>
</c:if>


<h1>Profil de ${utilisateur.pseudo}</h1>
<h2>Vous avez ${utilisateur.credit} points</h1>

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
                    aria-describedby="emailHelp" required maxlength="30" name="pseudo" value="${utilisateur.pseudo}">
                <div id="emailHelp" class="form-text">Comment vous apparaitrez
                sur le site.</div>
        </div>

        <div class="mb-3 col">
            <label for="exampleInputName" class="form-label">Nom de
                famille</label>
            <input type="text" class="form-control" id="exampleInputName"
                   aria-describedby="nomHelp" required maxlength="50" name="nom" value="${utilisateur.nom}">
        </div>
    </div>

    <div class="row">
        <div class="mb-3 col">
            <label for="exampleInputFirstname" class="form-label">Prénom</label>
            <input
                    type="text" class="form-control" id="exampleInputFirstname"
                    aria-describedby="prenomHelp" required maxlength="50" name="prenom" value="${utilisateur.prenom}">
        </div>

        <div class="mb-3 col">
            <label for="exampleInputMail" class="form-label">Email</label>
            <input
                    type="email" class="form-control" id="exampleInputMail"
                    aria-describedby="prenomHelp" required maxlength="50" name="email" value="${utilisateur.email}">
        </div>
    </div>

    <div class="row">
        <div class="mb-3 col">
            <label for="exampleInputPhone" class="form-label">Telephone</label>
            <input
                    type="tel" class="form-control" id="exampleInputPhone"
                    aria-describedby="telephoneHelp" required maxlength="15" name="telephone" value="${utilisateur.telephone}">
        </div>

        <div class="mb-3 col">
            <label for="exampleInputRue" class="form-label">Rue</label>
            <input
                    type="text" class="form-control" id="exampleInputRue"
                    aria-describedby="rueHelp" required maxlength="250" name="rue" value="${utilisateur.rue}">
            <div id="rueHelp" class="form-text">Votre numéro et nom de rue.</div>
        </div>
    </div>

    <div class="row">
        <div class="mb-3 col">
            <label for="exampleInputCP" class="form-label">Code postal</label>
            <input
                    type="text" class="form-control" id="exampleInputCP"
                    aria-describedby="cpHelp" required maxlength="10"
                    name="codePostal" value="${utilisateur.codePostal}">
        </div>

        <div class="mb-3 col">
            <label for="exampleInputVille" class="form-label">Ville</label>
            <input
                    type="text" class="form-control" id="exampleInputVille"
                    aria-describedby="villeHelp" required maxlength="50" name="ville" value="${utilisateur.ville}">
        </div>
    </div>

    <div class="row">
        <div class="mb-3 col">
            <label for="exampleInputPasswordActuel" class="form-label">Mot de
                passe</label>
            <input type="password" class="form-control"
                   id="exampleInputPasswordActuel" aria-describedby="passwordActuelHelp" maxlength="30"
                   name="mot_de_passe_original" >
            <div id="passwordActuelHelp" class="form-text">Votre mot de passe actuel si vous voulez en changer.</div>
        </div>

    </div>
    <div class="row">
        <div class="mb-3 col">
            <label for="exampleInputPassword" class="form-label">Mot de
                passe</label>
            <input type="password" class="form-control"
                   id="exampleInputPassword" aria-describedby="passwordHelp" maxlength="30"
                   name="mot_de_passe_nouveau" >
            <div id="passwordHelp" class="form-text">6 lettres minimum.</div>
        </div>

        <div class="mb-3 col">
            <label for="exampleInputPasswordRep" class="form-label">Répétez
                le mot de passe</label>
            <input type="password" class="form-control"
                   id="exampleInputPasswordRep" aria-describedby="passwordHelpRep"
                   maxlength="30" name="mot_de_passe_repete" >
        </div>
    </div>


    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1" disabled>
        <label class="form-check-label" for="exampleCheck1">Se
            souvenir de moi ?</label>
    </div>
    <div class="row mb-3">
        <button type="submit" class="btn btn-primary" name="mise_a_jour">Mettre à jour</button>
    </div>

<hr>
    <div class="row mb-3">
        <button type="submit" class="btn btn-danger" name="supprimer" aria-describedby="supprimerHelp">Supprimer votre compte</button>
        <div id="supprimerHelp" class="form-text ml-3">Vous serez déconnecté et ne pourrez plus accéder au site.</div>
    </div>

</form>



<%@ include file="PiedBootstrap.jspf" %>