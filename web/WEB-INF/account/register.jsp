<%-- 
    Document   : register
    Created on : Nov 23, 2017, 3:24:29 PM
    Author     : 733196
--%>

<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="register.title" /></title>
    </head>
    <body>
         <a href="<c:url value='/login'></c:url>"><fmt:message key="register.back" /></a><br />
        <form action="register" method="post" >
            <fmt:message key="register.username" />:<input type="text" name="username" value="${user.username}" maxlength="10" ><br>
            <fmt:message key="register.password" />:<input type="password" name="password" value="${user.password}" maxlength="10" ><br>
            <fmt:message key="register.email" />:<input tmail:ype="email" name="email" value="${user.email}" maxlength="30" ><br>
            <fmt:message key="register.fname" />:<input type="text" name="firstname" value="${user.firstname}" maxlength="50"><br>
            <fmt:message key="register.lname" />:<input type="text" name="lastname" value="${user.lastname}" maxlength="50"><br>
            <fmt:message key="register.company" />:<select name="company">
                        <c:forEach var="comp" items="${companies}">
                            <option value="${comp.companyID}">${comp.companyName}</option>
                        </c:forEach>
                    </select><br>           
            <input type="submit" value="<fmt:message key="register.button.register" />">     
            <input type="hidden" name="action" value="register">
        </form>
         <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
         </c:if>
    </body>
</html>
