<%-- 
    Document   : users
    Created on : Nov 8, 2017, 1:51:09 PM
    Author     : 733196
--%>


<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body>

        <h1>Add or edit a user</h1>
       <a href="<c:url value='/users'></c:url>">Back</a><br />
        <form action="users" method="post" >
            Username:<input type="text" name="username" value="${selectedUser.username}" maxlength="10" ${readonly}><br>
            Password:<input type="text" name="password" value="${selectedUser.password}" maxlength="10" ><br>
            E-mail:<input type="email" name="email" value="${selectedUser.email}" maxlength="30" ><br>
            First Name:<input type="text" name="firstname" value="${selectedUser.firstname}" maxlength="50"><br>
            Last Name:<input type="text" name="lastname" value="${selectedUser.lastname}" maxlength="50"><br>
            Active: <input type="checkbox" name="active" value="${selectedUser.active}" ${selectedUser.active? 'checked' : ''}><br />
            
            <c:if test="${selectedUser == null}">
                <input type="submit" value="Add">
                <input type="hidden" name="action" value="addUser">
            </c:if>
            <c:if test="${selectedUser != null}">
                <input type="submit" value="Edit">
                <input type="hidden" name="action" value="editUser">
            </c:if>
        </form>
        ${msg}
    </body>
</html>
