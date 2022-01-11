<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../EnteteBootstrap.jspf" %>


<jsp:useBean id="errors" scope="request" type="fr.eni.encheres.bo.beans.Erreurs" />
<jsp:useBean id="utilisateur" scope="request" type="fr.eni.encheres.bo.Utilisateur" />

Login

<a href="<%=request.getContextPath()%>/RecupererMotDePasse">Mot de passe oublié ?</a>

<h1>Se connecter à trocenchères</h1>

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
    </div>

    <div class="row">
        <div class="mb-3 col">
            <label for="exampleInputPassword" class="form-label">Mot de
                passe</label>
            <input type="password" class="form-control"
                   id="exampleInputPassword" aria-describedby="passwordHelp" required maxlength="30"
                   name="password" >
            <div id="passwordHelp" class="form-text">6 lettres minimum.</div>
        </div>
    </div>


    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1" name="souvenir">
        <label class="form-check-label" for="exampleCheck1">Se
            souvenir de moi ?</label>
    </div>
    <button type="submit" class="btn btn-primary" name="inscription">Se connecter</button>
</form>

<hr>
<p>
    Pas encore inscrit ? <a href="<%= request.getContextPath() %>/Inscription">S'inscrire</a>
</p>

<%@ include file="../PiedBootstrap.jspf" %>