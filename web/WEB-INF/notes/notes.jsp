
<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="notes.title"/></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <h1><fmt:message key="notes.title2"/></h1>
         
        <p><c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if></p>
        <table border="1">
            <tr>
                <th><fmt:message key="notes.id"/></th>
                <th><fmt:message key="notes.delete"/></th>
                <th><fmt:message key="notes.edit"/></th>
            </tr>
            <c:forEach var="note" items="${notes}">
                <tr>
                    <td>${note.title}</td>
                    <td>
                        <form action="notes" method="post" >
                            <input type="submit" value="<fmt:message key="notes.button.delete"/>">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedNoteID" value="${note.noteID}">
                        </form>
                    </td>
                    <td>
                        <form action="notes" method="post">
                            <input type="submit" value="<fmt:message key="notes.button.view"/>">
                            <input type="hidden" name="action" value="view">
                            <input type="hidden" name="selectedNoteID" value="${note.noteID}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    
            <h3><fmt:message key="notes.title3"/></h3>
            <form action="notes" method="POST">
                <fmt:message key="notes.noteTitle"/>: <input type="text" name="title" maxlength="30"><br>
                <fmt:message key="notes.contents"/>: <input type="text" name="contents" maxlength="20000"><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="<fmt:message key="notes.button.add"/>">
            </form>
        </body>
</html>
