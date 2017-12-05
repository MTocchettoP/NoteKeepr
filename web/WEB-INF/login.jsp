<%-- 
    Document   : login
    Created on : Sep 19, 2017, 8:40:00 AM
    Author     : 733196
--%>

<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Note Keeper</title>
    </head>
    <body>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                <option value="pt" ${language == 'pt' ? 'selected' : ''}>Português</option>
            </select>
        </form>
        <h1>Login</h1>
        <div>
            <form action="login" method="post">
               <fmt:message key="login.username" />: <input type="text" name="username" value="${userName}"><br>
               <fmt:message key="login.password" />: <input type="password" name="password"  value="${password}"><br>
                <input type="submit" value="<fmt:message key="login.button.login" />">
            </form>
            <a href="<c:url value='forgot'></c:url>"><fmt:message key="login.link.forgot" /></a>&nbsp;
            <a href="<c:url value='register'></c:url>"><fmt:message key="login.link.register" /></a><br />
        </div>
            <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
        
    </body>
</html>
