<%@ page import="com.redfox.webapp.model.ContactType" %>
<%@ page import="com.redfox.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.redfox.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd>
                <label>
                    <input type="text" name="fullName" size="50" value="${resume.fullName}">
                </label>
            </dd>
        </dl>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <td>${type.title}</td>
                <dd>
                    <label>
                        <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                    </label>
                </dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <c:choose>
                    <c:when test="${type.name().equals('PERSONAL') || type.name().equals('OBJECTIVE')}">
                        <td>${type.title}</td>
                        <dd>
                            <label>
                                <input type="text" name="${type.name()}" size=30 value="${resume.getSection(type)}">
                            </label>
                        </dd>
                    </c:when>
                    <c:when test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
                        <c:set var="tmpSection" scope="request" value="${resume.getSection(type)}"></c:set>
                        <jsp:useBean id="tmpSection" scope="request" type="com.redfox.webapp.model.ListTextSection"></jsp:useBean>
                        <td>${type.title}</td>
                        <dd>
                            <label>
                                <textarea name='${type}' cols=30
                                          rows=5><%=String.join("\n", (tmpSection.getItems()))%></textarea>
                            </label>
                        </dd>
                    </c:when>
                    <c:otherwise>
                        <p>Undefined</p>
                    </c:otherwise>
                </c:choose>
                <c:remove var="tmpSection" scope="request"/>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>