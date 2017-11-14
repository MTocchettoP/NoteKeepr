<%-- 
    Document   : view
    Created on : Nov 9, 2017, 2:22:17 PM
    Author     : 733196
--%>


<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Notes</title>
    </head>
    <body>
        <a href="<c:url value='/notes'></c:url>">Back</a><br />
        <form action="notes" method="post" >
            <input type="submit" value="Edit">
            <input type="hidden" name="action" value="edit">
             <input type="hidden" name="selectedNoteID" value="${note.noteID}">
        </form>
       ${msg}<br/>
        <c:if test="${edit == null}">
            <h1>${note.title}</h1>
            ${note.dateCreated}</br>
            ${note.contents}
        </c:if>
        <c:if test="${edit != null}">
        <form action="notes" method="post" >    
            Title <input type="text" name="title" value="${note.title}" maxlength="30"><br>
            Date Created:${note.dateCreated}</br>
            Contents: <input type="text" name="contents" value="${note.contents}" maxlength="20000"><br>
            
            <input type="submit" value="Save">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="selectedNoteID" value="${note.noteID}">
        </form>
        </c:if>
        
    </body>
</html>
