<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta charset="UTF-8" />
    <title>TestTask</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>ид</th>
                <th>Наименование</th>
                <th>Необходимость</th>
                <th>Количество</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:url var="editAction" value="/parts/edit"/>
                <form:form method="POST" action="${editAction}" accept-charset="utf-8" modelAttribute="part">
                    <td><form:input path="partId" readonly="true" disabled="true"/><form:hidden path="partId"/></td>
                    <td><form:input path="partName"/></td>
                    <td><form:checkbox path="partRequired"/></td>
                    <td><form:input path="partAmount"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${part.partId == null}">
                                <input type="submit" value="<spring:message text="добавить"/>"/>
                            </c:when>
                            <c:otherwise>
                                <input type="submit" value="<spring:message text="сохранить"/>"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </form:form>
            </tr>
        </tbody>
    </table>
</body>
</html>