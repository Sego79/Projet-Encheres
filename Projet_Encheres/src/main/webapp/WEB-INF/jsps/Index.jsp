<%--
  Created by IntelliJ IDEA.
  User: hurin
  Date: 10/12/2021
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Encheres ENI</title>
</head>
<body>

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


</body>
</html>
