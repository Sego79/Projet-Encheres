<%--
  Created by IntelliJ IDEA.
  User: hurin
  Date: 13/12/2021
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="EnteteBootstrap.jspf" %>

<jsp:useBean id="infos" type="fr.eni.encheres.bo.beans.Infos" scope="request" />
<jsp:useBean id="utilisateur" type="fr.eni.encheres.bo.Utilisateur" scope="request" />

<jsp:include page="blocs/erreurs.jsp" >
    <jsp:param name="errors" value="${errors}"/>
</jsp:include>



<h1>Profil de ${utilisateur.pseudo}</h1>

<div class="card" style="width: 18rem;">
    <img class="card-img-top" src="<%=request.getContextPath()%>/images/logo_retreci.png" alt="Avatar">
    <div class="card-body">
        <h5 class="card-title">${utilisateur.pseudo}</h5>
        <p class="card-text">${utilisateur.prenom} ${utilisateur.nom}. </p>
        <p class="card-text">${utilisateur.email}, ${utilisateur.telephone} </p>
        <p class="card-text">${utilisateur.rue} ${utilisateur.codePostal}  ${utilisateur.ville}</p>
        <p class="card-text">${utilisateur.credit} points</p>
        <a href="javascript:window.history.back()" class="btn btn-primary">Revenir</a>
    </div>
</div>


<%@ include file="PiedBootstrap.jspf" %>