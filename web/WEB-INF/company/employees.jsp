<%-- 
    Document   : admin
    Created on : Oct 12, 2017, 1:42:20 PM
    Author     : 733196
--%>



<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="company.employees.title"/></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
           <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
           <br/>
        <h1><fmt:message key="company.employees.title2"/></h1>
        <form action="employees/add" method="post" >
            <input type="submit" value="<fmt:message key="company.employees.button.add"/>">
            <input type="hidden" name="action" value="add">
        </form>
            <table border="1">
                <tr><th><fmt:message key="company.employees.username"/></th><th><fmt:message key="company.employees.password"/></th><th><fmt:message key="company.employees.email"/></th><th><fmt:message key="company.employees.fname"/></th><th><fmt:message key="company.employees.lname"/></th><th><fmt:message key="company.employees.company"/></th><th><fmt:message key="company.employees.acive"/></th><th><fmt:message key="company.employees.registered"/></th><th><fmt:message key="company.employees.delete"/></th><th><fmt:message key="company.employees.edit"/></th></tr>
                <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.email}</td>
                    <td>${user.firstname}</td>
                    <td>${user.lastname}</td>
                    <td>${user.active}</td>
                    <td>${user.registered}</td>
                    <td>${user.company.companyName}</td>
                    <td>
                        <form action="employees" method="post" >
                            <input type="submit" value="<fmt:message key="company.employees.button.delete"/>">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedUser" value="${user.username}">
                        </form>
                    </td>
                    <td>
                        <form action="employees/edit" method="post" >
                            <input type="submit" value="<fmt:message key="company.employees.button.edit"/>">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="selectedUser" value="${user.username}">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
    </body>
</html>
