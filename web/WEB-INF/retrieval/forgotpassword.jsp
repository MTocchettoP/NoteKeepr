<%-- 
    Document   : forgotpassword
    Created on : Nov 21, 2017, 1:28:46 PM
    Author     : 733196
--%>

<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="forgot.title" /></title>
    </head>
    <body>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                <option value="pt" ${language == 'pt' ? 'selected' : ''}>Português</option>
            </select>
        </form>
        <form action="forgot" method="POST">
            <h1><fmt:message key="forgot.title" /></title></h1>
            <p><fmt:message key="forgot.message" /></title></p>
            <fmt:message key="forgot.email" /></title>:<input type="email" name="email"><br>
            <input type="submit" value="<fmt:message key="forgot.submit" />">
            <input type="hidden" name="action" value="send">
        </form>
        <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
    </body>
</html>
