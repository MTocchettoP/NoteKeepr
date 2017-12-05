<%-- 
    Document   : viewedit
    Created on : Nov 13, 2017, 12:05:42 PM
    Author     : 733196
--%>

<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="account.ve.title" /></title>
    </head>
    <body>
        
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <a href="<c:url value='${uri}'></c:url>"><fmt:message key="account.ve.back" /></a><br />
        <p><c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if></p>
        <c:if test="${edit == null}">
            <fmt:message key="account.ve.username" />:${user.username}<br>
            <fmt:message key="account.ve.password" />:${user.password}<br>
            <fmt:message key="account.ve.email" />:${user.email}<br>
            <fmt:message key="account.ve.fname" />:${user.firstname}<br>
            <fmt:message key="account.ve.lname" />:${user.lastname}<br>
            <fmt:message key="account.ve.company" />: ${user.company.companyName}<br>
            <table>
                <tr>
                    <td>
                        <form action="account" method="post" >
                            <input type="submit" value="<fmt:message key="account.ve.button.edit" />">
                            <input type="hidden" name="action" value="edit">
                        </form>
                    </td>
                    <td>
                        <form action="account" method="post" >
                            <input type="submit" value="<fmt:message key="account.ve.button.delete" />">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="username" value="${user.username}">
                        </form>
                    </td>
                 </tr>
            </table>
        </c:if>
        <c:if test="${edit != null}">
            <form action="account" method="post" >
                <fmt:message key="account.ve.username" />:<input type="text" name="username" value="${user.username}" maxlength="10" readonly><br>
                <fmt:message key="account.ve.password" />:<input type="text" name="password" value="${user.password}" maxlength="10" ><br>
                <fmt:message key="account.ve.email" />:<input type="email" name="email" value="${user.email}" maxlength="30" ><br>
                <fmt:message key="account.ve.fname" />:<input type="text" name="firstname" value="${user.firstname}" maxlength="50"><br>
                <fmt:message key="account.ve.lname" />:<input type="text" name="lastname" value="${user.lastname}" maxlength="50"><br>
                 <fmt:message key="account.ve.company" />: ${user.company.companyName}<br>
                <input type="submit" value="<fmt:message key="account.ve.button.save" />">
                <input type="hidden" name="action" value="save">
            </form>
        </c:if>
    </body>
</html>
