<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="EnteteBootstrap.jspf" %>

<jsp:useBean id="infos" type="fr.eni.encheres.bo.beans.Infos" scope="request" />

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

<%@ include file="PiedBootstrap.jspf" %>