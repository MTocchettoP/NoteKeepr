<%-- 
    Document   : users
    Created on : Nov 8, 2017, 1:51:09 PM
    Author     : 733196
--%>


<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="admin.aec.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <h1><fmt:message key="admin.aec.title2" /></h1>
       <a href="<c:url value='/companies'></c:url>"><fmt:message key="admin.aec.back" /></a><br />
        <form action="companies" method="post" >
            <fmt:message key="admin.aec.company" />:<input type="text" name="company" value="${company.companyName}" maxlength="25"><br>
                <input type="hidden" name="companyID" value="${company.companyID}">
            <c:if test="${company == null}">
                <input type="submit" value="<fmt:message key="admin.aec.button.add" />">
                <input type="hidden" name="action" value="addCompany">
            </c:if>
            <c:if test="${company != null}">
                <input type="submit" value="<fmt:message key="admin.aec.button.edit" />">
                <input type="hidden" name="action" value="editCompany">
            </c:if>
        </form>
        <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
    </body>
</html>
