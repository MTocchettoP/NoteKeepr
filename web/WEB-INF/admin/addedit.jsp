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
        <title> <fmt:message key="admin.ae.title" /></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <h1><fmt:message key="admin.ae.title2" /></h1>
       <a href="<c:url value='/users'></c:url>"><fmt:message key="admin.ae.back" /></a><br />
        <form action="users" method="post" >
            <fmt:message key="admin.ae.username" />:<input type="text" name="username" value="${selectedUser.username}" maxlength="10" ${readonly}><br>
            <fmt:message key="admin.ae.password" />:<input type="text" name="password" value="${selectedUser.password}" maxlength="10" ><br>
            <fmt:message key="admin.ae.email" />:<input type="email" name="email" value="${selectedUser.email}" maxlength="30" ><br>
            <fmt:message key="admin.ae.fname" />:<input type="text" name="firstname" value="${selectedUser.firstname}" maxlength="50"><br>
            <fmt:message key="admin.ae.lname" />:<input type="text" name="lastname" value="${selectedUser.lastname}" maxlength="50"><br>
            <fmt:message key="admin.ae.company" />:<select name="company">
                        <option value="${selectedUser.company.companyID}" selected>${selectedUser.company.companyName}</option>
                        <c:forEach var="comp" items="${companies}">
                            <option value="${comp.companyID}">${comp.companyName}</option>
                        </c:forEach>
                    </select><br>   
            <fmt:message key="admin.ae.acive" />: <input type="checkbox" name="active" value="${selectedUser.active}" ${selectedUser.active? 'checked' : ''}><br />
            
            <c:if test="${selectedUser == null}">
                <input type="submit" value="<fmt:message key="admin.ae.button.add" />">
                <input type="hidden" name="action" value="addUser">
            </c:if>
            <c:if test="${selectedUser != null}">
                <input type="submit" value="<fmt:message key="admin.ae.button.edit" />">
                <input type="hidden" name="action" value="editUser">
            </c:if>
        </form>
        <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
    </body>
</html>
