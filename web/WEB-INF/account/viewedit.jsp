<%-- 
    Document   : viewedit
    Created on : Nov 13, 2017, 12:05:42 PM
    Author     : 733196
--%>

<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Management</title>
    </head>
    <body>
        
        
        <a href="<c:url value='${uri}'></c:url>">Back</a><br />
        <p>${msg}</p>
        <c:if test="${edit == null}">
            Username:${user.username}<br>
            Password:${user.password}<br>
            E-mail:${user.email}<br>
            First Name:${user.firstname}<br>
            Last Name:${user.lastname}<br>
            <table>
                <tr>
                    <td>
                        <form action="account" method="post" >
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="edit">
                        </form>
                    </td>
                    <td>
                        <form action="account" method="post" >
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="username" value="${user.username}">
                        </form>
                    </td>
                 </tr>
            </table>
        </c:if>
        <c:if test="${edit != null}">
            <form action="account" method="post" >
                Username:<input type="text" name="username" value="${user.username}" maxlength="10" readonly><br>
                Password:<input type="text" name="password" value="${user.password}" maxlength="10" ><br>
                E-mail:<input type="email" name="email" value="${user.email}" maxlength="30" ><br>
                First Name:<input type="text" name="firstname" value="${user.firstname}" maxlength="50"><br>
                Last Name:<input type="text" name="lastname" value="${user.lastname}" maxlength="50"><br>

                <input type="submit" value="Save">
                <input type="hidden" name="action" value="save">
            </form>
        </c:if>
    </body>
</html>
