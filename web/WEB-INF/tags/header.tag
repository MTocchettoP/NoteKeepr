<%-- 
    Document   : header
    Created on : Nov 10, 2017, 11:33:03 AM
    Author     : 733196
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>

<%-- any content can be specified here e.g.: --%>
<a href="/login?action=logout">Logout</a>&nbsp;
<a href="/account">Account</a>&nbsp;
<c:if test="${admin != null}"><a href="/notes">Notes</a></c:if><br />