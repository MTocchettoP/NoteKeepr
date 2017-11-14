
<%@include file="/WEB-INF/jspf/header.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Notes</title>
    </head>
    <body>

        <h1>Notes</h1>
         
        <p>${msg}</p>
        <table border="1">
            <tr>
                <th>Note id</th>
                <th>Delete</th>
                <th>Edit</th>
            </tr>
            <c:forEach var="note" items="${notes}">
                <tr>
                    <td>${note.title}</td>
                    <td>
                        <form action="notes" method="post" >
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedNoteID" value="${note.noteID}">
                        </form>
                    </td>
                    <td>
                        <form action="notes" method="post">
                            <input type="submit" value="View">
                            <input type="hidden" name="action" value="view">
                            <input type="hidden" name="selectedNoteID" value="${note.noteID}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    
            <h3>Add Note</h3>
            <form action="notes" method="POST">
                Title <input type="text" name="title" maxlength="30"><br>
                Contents: <input type="text" name="contents" maxlength="20000"><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Add">
            </form>
        </body>
</html>
