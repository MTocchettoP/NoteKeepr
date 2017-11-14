<%-- 
    Document   : admin
    Created on : Oct 12, 2017, 1:42:20 PM
    Author     : 733196
--%>



<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
         
           ${msg}<br/>
        <h1>List of Users</h1>
        <form action="users/add" method="post" >
            <input type="submit" value="Add User">
            <input type="hidden" name="action" value="add">
        </form>
        <form>
            <table border="1">
                <tr><th>Username</th><th>Password</th><th>E-Mail</th><th>First Name</th><th>Last Name</th><th>Is Active</th><th>Delete</th><th>Edit</th></tr>
                <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.email}</td>
                    <td>${user.firstname}</td>
                    <td>${user.lastname}</td>
                    <td>${user.active}</td>
                    <td>
                        <form action="users" method="post" >
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedUser" value="${user.username}">
                        </form>
                    </td>
                    <td>
                        <form action="users/edit" method="post" >
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="selectedUser" value="${user.username}">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
