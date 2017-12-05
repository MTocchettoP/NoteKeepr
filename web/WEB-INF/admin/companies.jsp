<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>    
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="admin.comp.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
           <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
           <br/>
        <h1><fmt:message key="admin.comp.title2" /></h1>
        <a href="<c:url value='/users'></c:url>"><fmt:message key="admin.comp.back" /></a><br />
        <form action="companies/add" method="post" >
            <input type="submit" value="<fmt:message key="admin.comp.button.add" />">
            <input type="hidden" name="action" value="add">
        </form>
            <table border="1">
                <tr><th><fmt:message key="admin.comp.name" /></th><th><fmt:message key="admin.comp.emp" /></th><th><fmt:message key="admin.comp.edit" /></th></tr>
                <c:forEach items="${companies}" var="company">
                <tr>
                    <td>${company.companyName}</td>
                    <td>${company.size}</td>
                    <td>
                        <form action="companies/edit" method="post" >
                            <input type="submit" value="<fmt:message key="admin.comp.button.edit" />">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="selectedCompany" value="${company.companyID}">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
    </body>
</html>
