
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>

<%-- any content can be specified here e.g.: --%>
<a href="/login?action=logout"><fmt:message key="header.logout"/></a>&nbsp;
<a href="/account"><fmt:message key="header.acc"/></a>&nbsp;
<c:if test="${admin != null}"><a href="/notes"><fmt:message key="header.notes"/></a></c:if>&nbsp;
<c:if test="${sysadmin != null}"><a href="/companies"><fmt:message key="header.companies"/></a></c:if><br />