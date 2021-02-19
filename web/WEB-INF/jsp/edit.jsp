<%@ page import="com.redfox.webapp.util.DateUtil" %>
<%@ page import="com.redfox.webapp.model.*" %>
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
        <h1>Имя:</h1>
        <dl>
            <label>
                <input type="text" name="fullName" size=55 value="${resume.fullName}">
            </label>
        </dl>
        <h2>Контакты</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd>
                    <label>
                        <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                    </label>
                </dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.redfox.webapp.model.AbstractSection"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <label>
                        <input type="text" name="${type}" size=75 value=<%=((TextSection) section).getContent()%>>
                    </label>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <label>
                        <textarea name="${type}" cols=75 rows=5><%=((TextSection) section).getContent()%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <label>
                        <textarea name="${type}" cols=75
                                  rows=5><%=String.join("\n", ((ListTextSection) section).getItems())%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd>
                                <label>
                                    <input type="text" name="${type}" size=100 value="${organization.homePage.name}">
                                </label>
                            </dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd>
                                <label>
                                    <input type="text" name="${type}url" size=100 value="${organization.homePage.url}">
                                </label>
                            </dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position"
                                             type="com.redfox.webapp.model.Organization.Experience"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <label>
                                            <input type="text" name="${type}${counter.index}startDate" size=10
                                                   value="<%=DateUtil.format(position.getStartDate())%>"
                                                   placeholder="MM/yyyy">
                                        </label>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <label>
                                            <input type="text" name="${type}${counter.index}endDate" size=10
                                                   value="<%=DateUtil.format(position.getEndDate())%>"
                                                   placeholder="MM/yyyy">
                                        </label>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd>
                                        <label>
                                            <input type="text" name='${type}${counter.index}title' size=75
                                                   value="${position.title}">
                                        </label>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd>
                                        <label>
                                            <textarea name="${type}${counter.index}description" rows=5
                                                      cols=75>${position.description}</textarea>
                                        </label>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>