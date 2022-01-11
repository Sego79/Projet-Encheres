<%--
  Created by IntelliJ IDEA.
  User: hurin
  Date: 16/12/2021
  Time: 05:17
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../Entete.html" %>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<jsp:useBean id="cats" scope="request" type="java.util.List<fr.eni.encheres.bo.Categorie>" />
<jsp:useBean id="change" scope="request" type="fr.eni.encheres.bo.Categorie" />

<jsp:include page="../blocs/erreurs.jsp">
    <jsp:param name="errors" value="${requestScope.errors}"/>
</jsp:include>

<jsp:include page="../blocs/infos.jsp">
    <jsp:param name="errors" value="${requestScope.infos}"/>
</jsp:include>



<main class="crud">

<h1>CRUD Pour les catégories</h1>
<%
    boolean modification = false;
    if(request.getAttribute("change") != null) modification = true;
%>
    <h2>
        <% if(modification) {%>
        Modification de ${change.etiquette}
        <% } else { %>
        Ajouter nouvelle catégorie
        <% } %>
    </h2>
    <form class="form" method="post">
        <label>Numéro : <input type="text" name="num" value="${change.noCategorie}"/></label>
        <label>Etiquette : <input type="text" name="etiq" value="${change.etiquette}" /></label>
        <label>Libellé : <input type="text" name="lib" value="${change.libelle}" /></label>

        <input type="hidden" name="id" value="${change.id}">
        <input type="submit">

    </form>

    <h2>Liste</h2>
<table class="listing">
    <thead>
    <tr><th>Identifiant</th><th>Numero</th><th>Etiquette</th><th>Libellé</th></tr>
    </thead>
    <tbody>
    <c:forEach var="cat" items="${cats}" >
        <tr>
            <td>${cat.id}</td>
            <td>${cat.noCategorie}</td>
            <td>${cat.etiquette}</td>
            <td>${cat.libelle}</td>
            <td>
                <form method="post">
                    <input type="hidden" name="id_cat" value="${cat.id}">
                    <input type="submit" name="editer" value="Modifier">
                    <input type="submit" name="supprimer" value="Effacer">
                </form>
            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>
</main>
<%@include file="../Pied.html" %>