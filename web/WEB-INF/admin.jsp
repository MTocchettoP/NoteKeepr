<%-- 
    Document   : admin
    Created on : Oct 12, 2017, 1:42:20 PM
    Author     : 733196
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
        <h1>Admin Page</h1>
        <a href="admin">Refresh</a>&nbsp;<a href="login?action=logout">Logout</a><br />
        
        <h1>Add user</h1>
        
        <form action="admin?action=addUser" method="POST">
            New username:<input type="text" name="newUsername" value="${newUsername}" /><br />
            Password:<input type="text" name="password" value="${password}" /><br />
            <input type="submit" value="Add User" /><br />
            ${err}
        </form>
            
        <h1>List of Users</h1>
        <form action="admin?action=delete" method="POST">
            <table border="1">
                <tr><th>Username</th><th>Password</th><th>Delete</th></tr>
                <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td><input type="radio" name="toDel" value="${user.username}"></td>
                </tr>
                </c:forEach>
                <td>
                    <input type="submit" value="Delete user" />
                </td>
            </table>
        </form>

        ${errDel}
            
    </body>
</html>
