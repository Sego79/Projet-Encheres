<%--
  Created by IntelliJ IDEA.
  User: hurin
  Date: 16/12/2021
  Time: 04:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../EnteteFinal.jspf" %>

<main>
    <br><br><br><br><br><br>
<h1>Page d'administration</h1>
<ul>
    <li><a href="<%=request.getContextPath()%>/CRUDUtilisateurs">Gérer les utilisateurs</a></li>
    <li><a href="<%=request.getContextPath()%>/CRUDCategories">Gérer les catégories</a></li>
</ul>
</main>

</body>
</html>
