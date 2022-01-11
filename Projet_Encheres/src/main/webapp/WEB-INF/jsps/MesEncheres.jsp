<%--
  Created by IntelliJ IDEA.
  User: hurin
  Date: 15/12/2021
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="EnteteBootstrap.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="encheres_gagnees" type="java.util.List<fr.eni.encheres.bo.Enchere>" scope="request" />
<jsp:useBean id="encheres_en_cours" type="java.util.List<fr.eni.encheres.bo.Enchere>" scope="request" />

<h1>Mes enchères</h1>
<h2>Remportées</h2>
<ul>
    <c:forEach items="${encheres_gagnees}" var="enchere">
        <li><a href="${pageContext.request.contextPath}/DetailArticle?no_article=${enchere.article.noArticle}">${enchere.article.nomArticle}</a></li>
    </c:forEach>
</ul>

<h2>Offre placée</h2>
<ul>
    <c:forEach items="${encheres_en_cours}" var="enchere">
        <li><a href="${pageContext.request.contextPath}/DetailArticle?no_article=${enchere.article.noArticle}">${enchere.article.nomArticle}</a></li>
    </c:forEach>
</ul>

<h2>Toutes les ventes</h2>
<ul>
    <c:forEach items="${requestScope.ventes}" var="article">
        <li><a href="${pageContext.request.contextPath}/DetailArticle?no_article=${article.noArticle}">${article.nomArticle}</a></li>
    </c:forEach>
</ul>
<h2>Toutes mes ventes</h2>
<ul>
    <c:forEach items="${requestScope.mes_ventes}" var="article">
        <li><a href="${pageContext.request.contextPath}/DetailArticle?no_article=${article.noArticle}">${article.nomArticle}</a></li>
    </c:forEach>
</ul>
<h2>Ventes non débutées</h2>
<ul>
    <c:forEach items="${requestScope.mes_ventes_non_debutees}" var="article">
        <li><a href="${pageContext.request.contextPath}/DetailArticle?no_article=${article.noArticle}">${article.nomArticle}</a></li>
    </c:forEach>
</ul>


<%@ include file="PiedBootstrap.jspf" %>
