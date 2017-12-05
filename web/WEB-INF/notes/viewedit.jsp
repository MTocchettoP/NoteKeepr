<%-- 
    Document   : view
    Created on : Nov 9, 2017, 2:22:17 PM
    Author     : 733196
--%>


<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="notes.ve.title"/></title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <a href="<c:url value='/notes'></c:url>"><fmt:message key="notes.ve.back"/></a><br />
        <form action="notes" method="post" >
            <input type="submit" value="<fmt:message key="notes.ve.button.edit"/>">
            <input type="hidden" name="action" value="edit">
             <input type="hidden" name="selectedNoteID" value="${note.noteID}">
        </form>
       <c:if test="${msg != null}">
              <fmt:message key="${msg}" />  
            </c:if>
       <br/>
        <c:if test="${edit == null}">
            <h1>${note.title}</h1>
            ${note.dateCreated}</br>
            ${note.contents}
        </c:if>
        <c:if test="${edit != null}">
        <form action="notes" method="post" >    
            <fmt:message key="notes.ve.noteTitle"/>: <input type="text" name="title" value="${note.title}" maxlength="30"><br>
            <fmt:message key="notes.ve.date"/>:${note.dateCreated}</br>
            <fmt:message key="notes.ve.contents"/>: <input type="text" name="contents" value="${note.contents}" maxlength="20000"><br>
            
            <input type="submit" value="<fmt:message key="notes.ve.button.save"/>">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="selectedNoteID" value="${note.noteID}">
        </form>
        </c:if>
        
    </body>
</html>
