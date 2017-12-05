<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>       
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="admin.users.title"/></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
           <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
           <br/>
        <h1><fmt:message key="admin.users.title2"/></h1>
        <form action="users/add" method="post" >
            <input type="submit" value="<fmt:message key="admin.users.button.add"/>">
            <input type="hidden" name="action" value="add">
        </form>
            <table border="1">
                <tr><th><fmt:message key="admin.users.username"/></th><th><fmt:message key="admin.users.password"/></th><th><fmt:message key="admin.users.email"/></th><th><fmt:message key="admin.users.fname"/></th><th><fmt:message key="admin.users.lname"/></th><th><fmt:message key="admin.users.company"/></th><th><fmt:message key="admin.users.acive"/></th><th><fmt:message key="admin.users.registered"/></th><th><fmt:message key="admin.users.delete"/></th><th><fmt:message key="admin.users.edit"/></th></tr>
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
                        <form action="users" method="post" >
                            <input type="submit" value="<fmt:message key="admin.users.button.delete"/>">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedUser" value="${user.username}">
                        </form>
                    </td>
                    <td>
                        <form action="users/edit" method="post" >
                            <input type="submit" value="<fmt:message key="admin.users.button.edit"/>">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="selectedUser" value="${user.username}">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
    </body>
</html>
