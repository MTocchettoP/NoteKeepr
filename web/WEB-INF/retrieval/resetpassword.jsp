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
        <h1><fmt:message key="reset.title"/></h1>
        <div>
            <form action="forgot" method="post">
               <fmt:message key="reset.new"/>: <input type="password" name="password" value="${newPass}"><br>
               <fmt:message key="reset.confirm"/>: <input type="password" name="password2"  value="${newPass2}"><br>
               <input type="submit" value="<fmt:message key="reset.login"/>">
               <input type="hidden" name="action" value="reset">
            </form>
        </div>
        <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
        
    </body>
</html>
